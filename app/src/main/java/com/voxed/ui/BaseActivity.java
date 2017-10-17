package com.voxed.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.voxed.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Agustin Tomas Larghi on 29/6/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class BaseActivity extends AppCompatActivity implements BaseToolbarCallback {

    @Bind(R.id.activity_toolbar)
    Toolbar mToolbar;

    @Bind(R.id.toolbar_title)
    TextView mTitle;

    @Bind(R.id.toolbar_button_right)
    Button mToolbarButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mTitle.setText(title);
    }


    protected void setDisplayHomeAsUpEnabled(boolean enabled) {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowTitleEnabled(enabled);
        actionBar.setDisplayHomeAsUpEnabled(enabled);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    protected void setToolbarButtonTitle(String text) {
        mToolbarButton.setText(text);
    }

    protected void setRightButtonVisible(boolean enabled) {
        mToolbarButton.setVisibility(enabled ? View.VISIBLE : View.GONE);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.slide_out_left);
    }

    @Override
    public void setRightButtonEnabled(boolean value) {
        mToolbarButton.setEnabled(value);
    }
}
