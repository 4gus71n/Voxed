package com.voxed.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.voxed.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Agustin Tomas Larghi on 02/02/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */

public class SelectionTextView extends RelativeLayout implements View.OnClickListener {

    //region Constant variables declaration
    private static final int DEFAULT_PLACEHOLDER_COLOR = R.color.colorGreyLight;
    private static final int DEFAULT_VALUE_COLOR = R.color.colorText;
    private static final int DEFAULT_LABEL_COLOR = R.color.colorGreyLight2;
    private static final boolean DEFAULT_BOLD = false;
    //endregion

    //region Callback interface declaration
    public interface Callback {
        void onWholeLayoutClicked(SelectionTextView view);
        void onValueChanged(SelectionTextView view, String value);
    }

    /**
     * Default callback in case that you don't need to implement every method
     */
    public static abstract class DefaultCallback implements Callback {

        public abstract void onWholeLayoutClicked(SelectionTextView view);

        @Override
        public void onValueChanged(SelectionTextView view, String value) {
            //Empty by default
        }
    }
    //endregion

    //region Variables declaration
    @Bind(R.id.selection_error_text_view)
    TextView mErrorTextView;

    @Bind(R.id.selection_label_text_view)
    TextView mLabelTextView;

    @Bind(R.id.selection_value_text_view)
    TextView mValueTextView;

    private String mValuePlaceholder = "", mLabelText = "", mValue = "", mError = "";
    private int mColorPlaceholder, mValueColor, mLabelColor;
    private Callback mCallback;
    private boolean mIsBold;
    //endregion

    //region Custom View lifecycle methods declaration
    @Override
    public Parcelable onSaveInstanceState() {
        //begin boilerplate code that allows parent classes to save state
        Parcelable superState = super.onSaveInstanceState();

        SavedState ss = new SavedState(superState);
        //end

        ss.value = mValue;
        ss.error = mError;

        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        //begin boilerplate code so parent classes can restore state
        if(!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState)state;
        super.onRestoreInstanceState(ss.getSuperState());
        //end

        mValue = ss.value;
        mError = ss.error;
        updateView();
    }

    static class SavedState extends BaseSavedState {
        String value, error;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.value = in.readString();
            this.error = in.readString();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(this.value);
            out.writeString(this.error);
        }

        //required field that makes Parcelables from a Parcel
        public static final Creator<SavedState> CREATOR =
                new Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }

    public SelectionTextView(Context context) {
        super(context);
        initializeView(context, null);
    }

    public SelectionTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context, attrs);
    }

    public SelectionTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SelectionTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeView(context, attrs);
    }

    private void initializeView(Context context, AttributeSet attrs) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        layoutInflater.inflate(R.layout.view_selection_text, this, true);

        ButterKnife.bind(this);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SelectionTextView,
                0, 0);

        try {
            mValue = a.getString(R.styleable.SelectionTextView_textValue);
            mValuePlaceholder = a.getString(R.styleable.SelectionTextView_textPlaceholder);
            mLabelText = a.getString(R.styleable.SelectionTextView_textLabel);
            mColorPlaceholder = a.getColor(R.styleable.SelectionTextView_colorPlaceholder,
                    ContextCompat.getColor(getContext(), DEFAULT_PLACEHOLDER_COLOR));
            mValueColor = a.getColor(R.styleable.SelectionTextView_colorValue,
                    ContextCompat.getColor(getContext(), DEFAULT_VALUE_COLOR));
            mLabelColor = a.getColor(R.styleable.SelectionTextView_colorLabel,
                    ContextCompat.getColor(getContext(), DEFAULT_LABEL_COLOR));
            mIsBold = a.getBoolean(R.styleable.SelectionTextView_valueBold, DEFAULT_BOLD);
        } finally {
            a.recycle();
        }

        setOnClickListener(this);
        updateView();
    }

    private void updateView() {
        if (mIsBold) {
            mValueTextView.setTypeface(null, Typeface.BOLD);
        }
        mLabelTextView.setTextColor(mLabelColor);
        mLabelTextView.setText(mLabelText);
        if (TextUtils.isEmpty(mValue) && !TextUtils.isEmpty(mValuePlaceholder)) {
            mValueTextView.setTextColor(mColorPlaceholder);
            mValueTextView.setText(mValuePlaceholder);
        } else if (!TextUtils.isEmpty(mValue)) {
            mValueTextView.setTextColor(mValueColor);
            mValueTextView.setText(mValue);
        }

        if (!TextUtils.isEmpty(mError)) {
            mErrorTextView.setText(mError);
            mErrorTextView.setVisibility(VISIBLE);
        } else {
            mErrorTextView.setVisibility(GONE);
        }
    }
    //endregion

    //region Public API methods declaration
    public String getValue() {
        return mValue;
    }

    public void setValue(String textValue) {
        if (mCallback != null) {
            mCallback.onValueChanged(this, textValue);
        }

        mValue = textValue;
        mError = "";
        updateView();
    }

    public void setError(String textError) {
        mError = textError;
        updateView();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!enabled) {
            mLabelColor = ContextCompat.getColor(getContext(), DEFAULT_PLACEHOLDER_COLOR);
        } else {
            mLabelColor = ContextCompat.getColor(getContext(), DEFAULT_VALUE_COLOR);
        }
        updateView();
    }

    public String getError() {
        return mError;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }
    //endregion

    //region OnClick methods declaration
    @Override
    public void onClick(View view) {
        if (isEnabled()) {
            mCallback.onWholeLayoutClicked(this);
        }
    }
    //endregion
}
