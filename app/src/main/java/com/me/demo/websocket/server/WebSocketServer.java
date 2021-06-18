package com.me.demo.websocket.server;

import android.util.Log;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import java.net.InetSocketAddress;

/**
 * Create by lzf on 6/18/21
 */
public class WebSocketServer extends org.java_websocket.server.WebSocketServer {
    private String TAG = getClass().getSimpleName();
    private WebSocket mWebSocket;

    public WebSocketServer(int port) {
        super(new InetSocketAddress(port));
    }

    public WebSocketServer(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        Log.e(TAG, ">>server onOpen");
        Log.d(TAG, "handshake:" + handshake.getResourceDescriptor());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        Log.e(TAG, ">>server onClose");
        Log.d(TAG, "code:" + code + "  \nreason:" + reason + "  \nremote:" + remote);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        Log.e(TAG, ">>server onMessage:" + message);
        this.mWebSocket = conn;
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        Log.e(TAG, ">>server onError:" + ex.getMessage());
    }

    @Override
    public void onStart() {
        Log.e(TAG, ">>server onStart");
    }

    public boolean sendMessage(String message) {
        Log.e(TAG, ">>sendMessage:" + message);
        if (mWebSocket == null) {
            Log.e(TAG, "mWebSocket null, return");
            return false;
        }
        Log.e(TAG, "start send message");
        mWebSocket.send(message);

        return true;
    }
}
