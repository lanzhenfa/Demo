package com.me.demo.websocket.client;

import android.util.Log;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * Create by lzf on 6/18/21
 */
public class WebSocketClient extends org.java_websocket.client.WebSocketClient {
    private String TAG = getClass().getSimpleName();

    public WebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.e(TAG, ">>client onOpen");
        Log.d(TAG, "status:" + handshakedata.getHttpStatusMessage());
    }

    @Override
    public void onMessage(String message) {
        Log.e(TAG, ">>client onMessage");
        Log.d(TAG, "message:" + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.e(TAG, ">>client onClose");
        Log.d(TAG, "code:" + code + "  \nreason:" + reason + "  \nremote:" + remote);
    }

    @Override
    public void onError(Exception ex) {
        Log.e(TAG, ">>client onError");
        Log.d(TAG, "exception:" + ex.getMessage());
    }
}
