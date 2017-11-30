package com.congp.app.activity.main;



import android.util.Log;

import com.congp.app.data.ResultApk;
import com.congp.app.network.Service;
import com.congp.app.network.Utils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by congp on 11/29/2017.
 */

public class Presenter {
     Service service;
     MView mView;

    public Presenter(MView MView) {
        this.mView = MView;
        this.service = Utils.getIapiService();
    }
    public void getApk(){
        service.provider("15201701").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onFail);
    }

    private void onFail(Throwable throwable) {
        mView.fail(String.valueOf(throwable));
        Log.e(TAG, "onFail: ",throwable );
    }

    private void onSuccess(ResultApk resultApk) {
        mView.success(resultApk);
    }
}
