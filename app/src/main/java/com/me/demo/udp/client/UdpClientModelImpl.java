package com.me.demo.udp.client;

import android.util.Log;

import com.me.demo.util.Config;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Create by lzf on 6/16/21
 */
public class UdpClientModelImpl implements IUdpClientContract.IUdpModel {
    private String TAG = getClass().getSimpleName();
    private DatagramSocket mDatagramSocket;
    private String mDstIp;

    private Disposable mSendDispose;
    private Disposable mReceiveDispose;

    @Override
    public void onUdpConnect(String ip, IUdpClientContract.IOnUdpConnectCallback callback) {
        Log.e(TAG, "==onUdpConnect== ip:" + ip);
        this.mDstIp = ip;
        try {
            mDatagramSocket = new DatagramSocket(Config.UDP_CLIENT_PORT);
            callback.onUdpConnectSuccess();
        } catch (SocketException e) {
            e.printStackTrace();
            Log.e(TAG, "exception:" + e.getMessage());
            callback.onUdpConnectFailed(-1);
        }
    }

    @Override
    public void onSendMessage(final String message, final IUdpClientContract.IOnUdpSendMessageCallback callback) {
        Log.e(TAG, "==onSendMessage== message:" + message);
        mSendDispose = Observable.interval(0, 2000, TimeUnit.MILLISECONDS).
                subscribeOn(Schedulers.io()).
                subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        byte[] buffer = message.getBytes();
                        DatagramPacket datagramPacket = new DatagramPacket(
                                buffer, buffer.length, InetAddress.getByName(mDstIp), Config.UDP_SERVER_PORT);
                        mDatagramSocket.send(datagramPacket);

                        callback.onUdpSendSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "exception:" + throwable.getMessage());
                        callback.onUdpSendFailed(-1, throwable.getMessage());
                    }
                });
    }

    @Override
    public void onReceiveMessage(final IUdpClientContract.IOnUdpReceiveMessageCallback callback) {
        Log.e(TAG, "==onReceiveMessage==");
        mReceiveDispose = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                byte[] buffer = new byte[1024];
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                mDatagramSocket.receive(datagramPacket);
                emitter.onNext(new String(datagramPacket.getData(), datagramPacket.getOffset(),
                        datagramPacket.getLength(), Charset.defaultCharset()));
            }
        }).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String result) throws Exception {
                        if ("ok".equals(result)) {
                            if (mSendDispose != null && !mSendDispose.isDisposed()) {
                                mSendDispose.dispose();
                            }
                        }
                        callback.onUdpReceiveSuccess(result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "exception:" + throwable.getMessage());
                        callback.onUdpReceiveFailed(-1, throwable.getMessage());
                    }
                });
    }

    @Override
    public void onDestroy() {
        if (mSendDispose != null && !mSendDispose.isDisposed()) {
            mSendDispose.dispose();
        }
        if (mReceiveDispose != null && !mReceiveDispose.isDisposed()) {
            mReceiveDispose.dispose();
        }
        if (mDatagramSocket != null) {
            mDatagramSocket.close();
        }
    }
}
