package com.me.demo.websocket.server;

import android.util.Log;

import com.me.demo.base.BaseApplication;
import com.me.demo.websocket.server.IWebSocketServerContract.IOnWebSocketSendMessageCallback;
import com.me.demo.websocket.server.IWebSocketServerContract.IOnWebSocketStartCallback;
import com.me.demo.websocket.server.IWebSocketServerContract.IWebSocketModel;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Create by lzf on 6/18/21
 */
public class WebSocketServerModelImpl implements IWebSocketModel {
    private String TAG = getClass().getSimpleName();

    private WebSocketServer mWebSocketServer;
    private Disposable mDispose;

    @Override
    public void onWebSocketStart(int port, final IOnWebSocketStartCallback callback) {
        Log.e(TAG, ">>onWebSocketStart:" + port);
        try {
            mWebSocketServer = new WebSocketServer(port);
            Log.e(TAG, "websocket server start");
            mWebSocketServer.start();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    callback.onWebSocketStartSuccess();
                }
            });
        } catch (final Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    callback.onWebSocketStartFailed(-1, e.getMessage());
                }
            });
        }
    }

    @Override
    public void onWebSocketSendMessage(final String message, final IOnWebSocketSendMessageCallback callback) {
        Log.e(TAG, ">>onWebSocketSendMessage:" + message);
        mDispose = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                emitter.onNext(mWebSocketServer.sendMessage(message));
            }
        }).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean result) throws Exception {
                        if (result) {
                            callback.onWebSocketSendMessageSuccess(message);
                        } else {
                            callback.onWebSocketSendMessageFailed(-1, "send message failed");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callback.onWebSocketSendMessageFailed(-1, throwable.getMessage());
                    }
                });
    }

    private void runOnUiThread(Runnable runnable) {
        BaseApplication.getInstance().runOnUiThread(runnable);
    }
}
