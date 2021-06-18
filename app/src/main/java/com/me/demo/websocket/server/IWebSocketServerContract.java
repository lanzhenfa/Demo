package com.me.demo.websocket.server;

/**
 * Create by lzf on 6/18/21
 */
public interface IWebSocketServerContract {

    interface IWebSocketView {
        void onWebSocketStartSuccess();

        void onWebSocketStartFailed(int errorCode, String errorMsg);

        void onWebSocketSendMessageSuccess(String message);

        void onWebSocketSendMessageFailed(int errorCode, String errorMsg);
    }

    interface IWebSocketPresenter {
        void start();

        void webSocketStart(int port);

        void webSocketSendMessage(String message);
    }

    interface IWebSocketModel {
        void onWebSocketStart(int port, IOnWebSocketStartCallback callback);

        void onWebSocketSendMessage(String message, IOnWebSocketSendMessageCallback callback);
    }

    interface IOnWebSocketStartCallback {
        void onWebSocketStartSuccess();

        void onWebSocketStartFailed(int errorCode, String errorMsg);
    }

    interface IOnWebSocketSendMessageCallback {
        void onWebSocketSendMessageSuccess(String message);

        void onWebSocketSendMessageFailed(int errorCode, String errorMsg);
    }
}
