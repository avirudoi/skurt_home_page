package com.avirpro.aviseekbar.listener;


import com.avirpro.aviseekbar.model.MainModelArray;

/**
 * Created by Avi Rudoi
 */
public interface ApiListener {

    void onSuccess(MainModelArray feed);

    void onFailure(String message);
}
