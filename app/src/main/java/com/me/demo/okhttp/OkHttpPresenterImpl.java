package com.me.demo.okhttp;

import com.me.demo.base.BasePresenter;
import com.me.demo.okhttp.IOkHttpContract.IOkHttpModel;
import com.me.demo.okhttp.IOkHttpContract.IOkHttpPresenter;
import com.me.demo.okhttp.IOkHttpContract.IOkHttpView;
import com.me.demo.okhttp.IOkHttpContract.IOnDownloadDatabaseCallback;
import com.me.demo.okhttp.IOkHttpContract.IOnShakeHandsCallback;
import com.me.demo.okhttp.IOkHttpContract.IOnUpdateDatabaseCallback;

/**
 * Create by lzf on 6/18/21
 */
public class OkHttpPresenterImpl extends BasePresenter<IOkHttpView>
        implements IOkHttpPresenter {
    private String TAG = getClass().getSimpleName();
    private IOkHttpModel mMode;

    public OkHttpPresenterImpl() {
        mMode = new OkHttpModelImpl();
    }

    @Override
    public void start() {
        mMode.onInit();
    }

    @Override
    public void shakeHands() {
        mMode.onShakeHands(new IOnShakeHandsCallback() {
            @Override
            public void onShakeHandsSuccess() {
                getView().onShakeHandsSuccess();
            }

            @Override
            public void onShakeHandsFailed(int errorCode, String errorMsg) {
                getView().onShakeHandsFailed(errorCode, errorMsg);
            }
        });
    }

    @Override
    public void updateDatabase() {
        mMode.onUpdateDataBase(new IOnUpdateDatabaseCallback() {
            @Override
            public void onUpdateDatabaseSuccess(boolean isNeedDownload, String url) {
                getView().onUpdateDatabaseSuccess(isNeedDownload, url);
            }

            @Override
            public void onUpdateDatabaseFailed(int errorCode, String errorMsg) {
                getView().onUpdateDatabaseFailed(errorCode, errorMsg);
            }
        });
    }

    @Override
    public void downloadDatabase(String url, String toPath) {
        mMode.onDownloadDatabase(url, toPath, new IOnDownloadDatabaseCallback() {
            @Override
            public void onDownloadDatabaseSuccess() {
                getView().onDownloadDatabaseSuccess();
            }

            @Override
            public void onDownloadDatabaseFailed(int errorCode, String errorMsg) {
                getView().onDownloadDatabaseFailed(errorCode, errorMsg);
            }
        });
    }
}
