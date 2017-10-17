package com.voxed.repositories;

import com.voxed.model.Vox;
import com.voxed.model.VoxDetail;
import com.voxed.retrofit.mappers.VoxMapper;
import com.voxed.retrofit.services.VoxService;
import com.voxed.retrofit.utils.HtmlToJson;
import com.voxed.utils.rx.DataSource;
import com.voxed.utils.rx.ObservableUtils;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class VoxRepositoryImp implements VoxRepository {

    private final VoxService mService;

    public VoxRepositoryImp(Retrofit retrofit) {
        mService = retrofit.create(VoxService.class);
    }

    @Override
    public Observable<DataSource<List<Vox>>> getVoxes() {
        return mService.getVoxes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(HtmlToJson.sVoxesMapper)
                .compose(ObservableUtils.mapResponses(VoxMapper.sCollection));
    }

    @Override
    public Observable<DataSource<VoxDetail>> getVox(String id, String category) {
        return mService.getVox(category, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(HtmlToJson.sVoxMapper)
                .compose(ObservableUtils.mapResponses(VoxMapper.sSingle));
    }
}
