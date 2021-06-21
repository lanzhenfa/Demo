package com.me.demo.okhttp;

/**
 * Create by lzf on 6/18/21
 */
public interface IOkHttpContract {
    interface IOkHttpView {
        void onShakeHandsSuccess();

        void onShakeHandsFailed(int errorCode, String errorMsg);

        void onUpdateDatabaseSuccess(boolean isNeedDownload, String url);

        void onUpdateDatabaseFailed(int errorCode, String errorMsg);

        void onDownloadDatabaseSuccess();

        void onDownloadDatabaseFailed(int errorCode, String errorMsg);
    }

    interface IOkHttpPresenter {
        void start();

        void shakeHands();

        void updateDatabase();

        void downloadDatabase(String url, String toPath);
    }

    interface IOkHttpModel {
        void onInit();

        void onShakeHands(IOnShakeHandsCallback callback);

        void onUpdateDataBase(IOnUpdateDatabaseCallback callback);

        void onDownloadDatabase(String url, String toPath, IOnDownloadDatabaseCallback callback);
    }

    interface IOnShakeHandsCallback {
        void onShakeHandsSuccess();

        void onShakeHandsFailed(int errorCode, String errorMsg);
    }

    interface IOnUpdateDatabaseCallback {
        void onUpdateDatabaseSuccess(boolean isNeedDownload, String url);

        void onUpdateDatabaseFailed(int errorCode, String errorMsg);
    }

    interface IOnDownloadDatabaseCallback {
        void onDownloadDatabaseSuccess();

        void onDownloadDatabaseFailed(int errorCode, String errorMsg);
    }
}
