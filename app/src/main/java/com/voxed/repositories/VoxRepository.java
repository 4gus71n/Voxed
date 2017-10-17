package com.voxed.repositories;

import com.voxed.model.Vox;
import com.voxed.model.VoxDetail;
import com.voxed.utils.rx.DataSource;

import java.util.List;

import rx.Observable;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public interface VoxRepository {
    Observable<DataSource<List<Vox>>> getVoxes();

    Observable<DataSource<VoxDetail>> getVox(String id, String category);
}
