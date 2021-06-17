package com.me.demo.udp.server;

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
public class UdpServerModelImpl implements IUdpServerContract.IUdpServerModel {
    private String TAG = getClass().getSimpleName();
    private DatagramSocket mDatagramSocket;
    private String mDstIp;
    private Disposable mDispose;
    private Disposable mReceiveDispose;

    @Override
    public void onUdpServerConnect(String ip, IUdpServerContract.IOnUdpServerConnectCallback callback) {
        Log.d(TAG, "==onUdpServerConnect== ip:" + ip);
        this.mDstIp = ip;
        try {
            mDatagramSocket = new DatagramSocket(Config.UDP_SERVER_PORT);
            callback.onUdpServerConnectSuccess();
        } catch (SocketException e) {
            e.printStackTrace();
            Log.e(TAG, "exception:" + e.getMessage());
            callback.onUdpServerConnectFailed(-1);
        }
    }

    @Override
    public void onServerSendMessage(final String message, final IUdpServerContract.IOnUdpServerSendMessageCallback callback) {
        Log.e(TAG, "==onServerSendMessage== message:" + message);
        mDispose = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                byte[] buffer = message.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(
                        buffer, buffer.length, InetAddress.getByName(mDstIp), Config.UDP_CLIENT_PORT);
                mDatagramSocket.send(datagramPacket);
                emitter.onNext(true);
            }
        }).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        callback.onUdpServerSendSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "exception:" + throwable.getMessage());
                        callback.onUdpServerSendFailed(-1, throwable.getMessage());
                    }
                });
    }

    @Override
    public void onServerReceiveMessage(final IUdpServerContract.IOnUdpServerReceiveMessageCallback callback) {
        Log.e(TAG, "==onServerReceiveMessage==");
        mReceiveDispose = Observable.interval(0, 2000, TimeUnit.MILLISECONDS).
                subscribeOn(Schedulers.io()).
                subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        byte[] buffer = new byte[1024];
                        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                        mDatagramSocket.receive(datagramPacket);
                        callback.onUdpServerReceiveSuccess(new String(
                                datagramPacket.getData(),
                                datagramPacket.getOffset(),
                                datagramPacket.getLength(),
                                Charset.defaultCharset()));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "exception:" + throwable.getMessage());
                        callback.onUdpServerReceiveFailed(-1, throwable.getMessage());
                    }
                });
    }

    @Override
    public void onDestroy() {
        if (mDispose != null && !mDispose.isDisposed()) {
            mDispose.dispose();
        }
        if (mReceiveDispose != null && !mReceiveDispose.isDisposed()) {
            mReceiveDispose.dispose();
        }
        if (mDatagramSocket != null) {
            mDatagramSocket.close();
        }
    }
}
