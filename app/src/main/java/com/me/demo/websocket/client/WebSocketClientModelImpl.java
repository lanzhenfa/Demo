package com.me.demo.websocket.client;

import android.util.Log;

import com.me.demo.base.BaseApplication;
import com.me.demo.websocket.client.IWebSocketClientContract.IOnWebSocketSendMessageCallback;
import com.me.demo.websocket.client.IWebSocketClientContract.IWebSocketModel;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Create by lzf on 6/18/21
 */
public class WebSocketClientModelImpl implements IWebSocketModel {
    private String TAG = getClass().getSimpleName();
    private WebSocketClient mWebSocketClient;
    private Disposable mDispose;

    @Override
    public void onInitWebSocket(String url) {
        Log.e(TAG, ">>onInitWebSocket:" + url);
        try {
            mWebSocketClient = new WebSocketClient(new URI(url));
            Log.e(TAG, "websocket client connect");
            mWebSocketClient.connectBlocking();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Log.e(TAG, "URISyntaxException exception:" + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.e(TAG, "InterruptedException exception:" + e.getMessage());
        }
    }

    @Override
    public void onWebSocketSendMessage(final String message, final IOnWebSocketSendMessageCallback callback) {
        Log.e(TAG, ">>onWebSocketSendMessage:" + message);
        mDispose = Observable.interval(0, 2000, TimeUnit.MILLISECONDS).
                subscribeOn(Schedulers.io()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                if (mWebSocketClient.isClosed() || mWebSocketClient.isClosing()) {
                    Log.e(TAG, "mWebSocketClient isClosed or isClosing");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onWebSocketSendMessageFailed(-2, "websocket closed or closing");
                        }
                    });
                    return;
                }
                Log.d(TAG, "client start send");
                mWebSocketClient.send(message);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onWebSocketSendMessageSuccess(message);
                    }
                });
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(final Throwable throwable) throws Exception {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onWebSocketSendMessageFailed(-1, throwable.getMessage());
                    }
                });
            }
        });
    }

    private void runOnUiThread(Runnable runnable) {
        BaseApplication.getInstance().runOnUiThread(runnable);
    }

    private void dispose() {
        if (mDispose != null && !mDispose.isDisposed()) {
            mDispose.dispose();
        }
    }
}
