package com.voxed.ui;

/**
 * Created by Agustin Tomas Larghi on 2/7/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public interface Presenter<T extends View> {

    void onViewCreated(T view);
    void onDestroyView();

}
