package com.me.demo.http;

import com.me.demo.base.BasePresenter;

/**
 * Create by lzf on 6/18/21
 */
public class HttpPresenterImpl extends BasePresenter<IHttpContract.IHttpView>
        implements IHttpContract.IHttpPresenter {

    private IHttpContract.IHttpModel mMode;

    public HttpPresenterImpl() {
        mMode = new HttpModelImpl();
    }

    @Override
    public void shakeHands() {
        mMode.onShakeHands(new IHttpContract.IOnHttpShakeHandsCallback() {
            @Override
            public void onShakeHandsSuccess() {

            }

            @Override
            public void onShakeHandsFailed(int errorCode, String errorMsg) {

            }
        });
    }

    @Override
    public void updateDatabase() {

    }
}
