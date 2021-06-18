package com.me.demo.websocket.client;

/**
 * Create by lzf on 6/18/21
 */
public interface IWebSocketClientContract {

    interface IWebSocketView {
        void onWebSocketSendMessageSuccess(String message);

        void onWebSocketSendMessageFailed(int errorCode, String errorMsg);
    }

    interface IWebSocketPresenter {
        void start();

        void initWebSocket(String url);

        void webSocketSendMessage(String message);
    }

    interface IWebSocketModel {
        void onInitWebSocket(String url);

        void onWebSocketSendMessage(String message, IOnWebSocketSendMessageCallback callback);
    }

    interface IOnWebSocketSendMessageCallback {
        void onWebSocketSendMessageSuccess(String message);

        void onWebSocketSendMessageFailed(int errorCode, String errorMsg);
    }
}
