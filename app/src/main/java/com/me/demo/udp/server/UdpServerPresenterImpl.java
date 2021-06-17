package com.me.demo.udp.server;

import com.me.demo.base.BasePresenter;

/**
 * Create by lzf on 6/16/21
 */
public class UdpServerPresenterImpl extends BasePresenter<IUdpServerContract.IUdpServerView>
        implements IUdpServerContract.IUdpServerPresenter {

    private IUdpServerContract.IUdpServerModel mUdpServerModel;
    private IUdpServerContract.IUdpServerView mUdpServerView;

    public UdpServerPresenterImpl() {
        mUdpServerModel = new UdpServerModelImpl();
    }

    @Override
    public void start() {
        mUdpServerView = getView();
    }

    @Override
    public void udpServerConnect(String ip) {
        mUdpServerModel.onUdpServerConnect(ip, new IUdpServerContract.IOnUdpServerConnectCallback() {
            @Override
            public void onUdpServerConnectSuccess() {
                if (mUdpServerView != null) {
                    mUdpServerView.onUdpServerConnectSuccess();
                }
            }

            @Override
            public void onUdpServerConnectFailed(int errorCode) {
                if (mUdpServerView != null) {
                    mUdpServerView.onUdpServerConnectFailed(errorCode);
                }
            }
        });
    }

    @Override
    public void serverSendMessage(String message) {
        mUdpServerModel.onServerSendMessage(message, new IUdpServerContract.IOnUdpServerSendMessageCallback() {
            @Override
            public void onUdpServerSendSuccess() {
                if (mUdpServerView != null) {
                    mUdpServerView.onUdpServerSendSuccess();
                }
            }

            @Override
            public void onUdpServerSendFailed(int errorCode, String errorMsg) {
                if (mUdpServerView != null) {
                    mUdpServerView.onUdpServerSendFailed(errorCode, errorMsg);
                }
            }
        });
    }

    @Override
    public void serverReceiveMessage() {
        mUdpServerModel.onServerReceiveMessage(new IUdpServerContract.IOnUdpServerReceiveMessageCallback() {
            @Override
            public void onUdpServerReceiveSuccess(String message) {
                if (mUdpServerView != null) {
                    mUdpServerView.onUdpServerReceiveSuccess(message);
                }
            }

            @Override
            public void onUdpServerReceiveFailed(int errorCode, String errorMsg) {
                if (mUdpServerView != null) {
                    mUdpServerView.onUdpServerReceiveFailed(errorCode, errorMsg);
                }
            }
        });
    }

    @Override
    public void destroy() {
        mUdpServerModel.onDestroy();
    }
}
