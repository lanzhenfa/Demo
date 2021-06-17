package com.me.demo.udp.server;

/**
 * Create by lzf on 6/16/21
 */
public interface IUdpServerContract {

    interface IUdpServerView {
        void onUdpServerConnectSuccess();

        void onUdpServerConnectFailed(int errorCode);

        void onUdpServerSendSuccess();

        void onUdpServerSendFailed(int errorCode, String errorMsg);

        void onUdpServerReceiveSuccess(String message);

        void onUdpServerReceiveFailed(int errorCode, String errorMsg);
    }

    interface IUdpServerPresenter {

        void start();

        void udpServerConnect(String ip);

        void serverSendMessage(String message);

        void serverReceiveMessage();

        void destroy();
    }

    interface IUdpServerModel {
        void onUdpServerConnect(String ip, IOnUdpServerConnectCallback callback);

        void onServerSendMessage(String message, IOnUdpServerSendMessageCallback callback);

        void onServerReceiveMessage(IOnUdpServerReceiveMessageCallback callback);

        void onDestroy();
    }

    interface IOnUdpServerConnectCallback {
        void onUdpServerConnectSuccess();

        void onUdpServerConnectFailed(int errorCode);
    }

    interface IOnUdpServerSendMessageCallback {
        void onUdpServerSendSuccess();

        void onUdpServerSendFailed(int errorCode, String errorMsg);
    }

    interface IOnUdpServerReceiveMessageCallback {
        void onUdpServerReceiveSuccess(String message);

        void onUdpServerReceiveFailed(int errorCode, String errorMsg);
    }
}
