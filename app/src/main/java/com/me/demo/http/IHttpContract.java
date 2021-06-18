package com.me.demo.http;

/**
 * Create by lzf on 6/18/21
 */
public interface IHttpContract {

    interface IHttpView {
    }

    interface IHttpPresenter {
        void shakeHands();

        void updateDatabase();
    }

    interface IHttpModel {
        void onShakeHands(IOnHttpShakeHandsCallback callback);

        void onUpdateDatabase();
    }

    interface IOnHttpShakeHandsCallback {
        void onShakeHandsSuccess();

        void onShakeHandsFailed(int errorCode, String errorMsg);
    }
}
