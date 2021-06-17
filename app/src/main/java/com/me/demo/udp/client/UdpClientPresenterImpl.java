package com.me.demo.udp.client;

import com.me.demo.base.BasePresenter;

/**
 * Create by lzf on 6/16/21
 */
public class UdpClientPresenterImpl extends BasePresenter<IUdpClientContract.IUdpView>
        implements IUdpClientContract.IUdpPresenter {

    private IUdpClientContract.IUdpModel mUdpModel;
    private IUdpClientContract.IUdpView mUdpView;

    public UdpClientPresenterImpl() {
        mUdpModel = new UdpClientModelImpl();
    }

    @Override
    public void start() {
        mUdpView = getView();
    }

    @Override
    public void udpConnect(String ip) {
        mUdpModel.onUdpConnect(ip, new IUdpClientContract.IOnUdpConnectCallback() {
            @Override
            public void onUdpConnectSuccess() {
                if (mUdpView != null) {
                    mUdpView.onUdpConnectSuccess();
                }
            }

            @Override
            public void onUdpConnectFailed(int errorCode) {
                if (mUdpView != null) {
                    mUdpView.onUdpConnectFailed(errorCode);
                }
            }
        });
    }

    @Override
    public void sendMessage(String message) {
        mUdpModel.onSendMessage(message, new IUdpClientContract.IOnUdpSendMessageCallback() {
            @Override
            public void onUdpSendSuccess() {
                if (mUdpView != null) {
                    mUdpView.onUdpSendSuccess();
                }
            }

            @Override
            public void onUdpSendFailed(int errorCode, String errorMsg) {
                if (mUdpView != null) {
                    mUdpView.onUdpSendFailed(errorCode, errorMsg);
                }
            }
        });
    }

    @Override
    public void receiveMessage() {
        mUdpModel.onReceiveMessage(new IUdpClientContract.IOnUdpReceiveMessageCallback() {
            @Override
            public void onUdpReceiveSuccess(String message) {
                if (mUdpView != null) {
                    mUdpView.onUdpReceiveSuccess(message);
                }
            }

            @Override
            public void onUdpReceiveFailed(int errorCode, String errorMsg) {
                if (mUdpView != null) {
                    mUdpView.onUdpReceiveFailed(errorCode, errorMsg);
                }
            }
        });
    }

    @Override
    public void destroy() {
        mUdpModel.onDestroy();
    }
}
