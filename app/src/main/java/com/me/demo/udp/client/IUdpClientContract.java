package com.me.demo.udp.client;

/**
 * Create by lzf on 6/16/21
 */
public interface IUdpClientContract {

    interface IUdpView {
        void onUdpConnectSuccess();

        void onUdpConnectFailed(int errorCode);

        void onUdpSendSuccess();

        void onUdpSendFailed(int errorCode, String errorMsg);

        void onUdpReceiveSuccess(String message);

        void onUdpReceiveFailed(int errorCode, String errorMsg);
    }

    interface IUdpPresenter {

        void start();

        void udpConnect(String ip);

        void sendMessage(String message);

        void receiveMessage();

        void destroy();
    }

    interface IUdpModel {
        void onUdpConnect(String ip, IOnUdpConnectCallback callback);

        void onSendMessage(String message, IOnUdpSendMessageCallback callback);

        void onReceiveMessage(IOnUdpReceiveMessageCallback callback);

        void onDestroy();
    }

    interface IOnUdpConnectCallback {
        void onUdpConnectSuccess();

        void onUdpConnectFailed(int errorCode);
    }

    interface IOnUdpSendMessageCallback {
        void onUdpSendSuccess();

        void onUdpSendFailed(int errorCode, String errorMsg);
    }

    interface IOnUdpReceiveMessageCallback {
        void onUdpReceiveSuccess(String message);

        void onUdpReceiveFailed(int errorCode, String errorMsg);
    }
}
