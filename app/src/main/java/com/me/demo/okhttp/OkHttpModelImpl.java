package com.me.demo.okhttp;

import android.util.Log;

import com.me.demo.base.BaseApplication;
import com.me.demo.okhttp.IOkHttpContract.IOkHttpModel;
import com.me.demo.okhttp.IOkHttpContract.IOnDownloadDatabaseCallback;
import com.me.demo.okhttp.IOkHttpContract.IOnShakeHandsCallback;
import com.me.demo.okhttp.IOkHttpContract.IOnUpdateDatabaseCallback;
import com.me.demo.util.FileUtils;
import com.thegrizzlylabs.sardineandroid.Sardine;
import com.thegrizzlylabs.sardineandroid.impl.OkHttpSardine;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Create by lzf on 6/18/21
 */
public class OkHttpModelImpl implements IOkHttpModel {
    private String TAG = getClass().getSimpleName();

    /****************************请求的关键字****************************/
    private String OKHTTP_REQUEST_DATA = "data";
    private String OKHTTP_REQUEST_CMDID = "cmdid";
    private String OKHTTP_REQUEST_SERIAL_NO = "sn";
    private String OKHTTP_REQUEST_MD5 = "md5";

    /****************************回复的关键字****************************/
    private String OKHTTP_RESPONSE_ERROR_CODE = "errorcode";
    private String OKHTTP_RESPONSE_ERROR_MESSAGE = "errormessage";
    //是否需要下载曲库 1:需要下载 0：不需要下载
    private String OKHTTP_RESPONSE_IS_NEED_DOWNLOAD = "isNeedDownloadDb";
    //曲库路径
    private String OKHTTP_RESPONSE_DATABASE_URL = "url";

    private String OKHTTP_MEDIA_TYPE_KEY = "Content-Type";
    private String OKHTTP_MEDIA_TYPE = "application/json";
    private String OKHTTP_MEDIA_TYPE_WITH_CHARSET = "application/json; charset=utf-8";

    /****************************协议****************************/
    //握手协议
    private int CMDID_SHAKE_HANDS = 10000;
    //曲库更新协议
    private int CMDID_UPDATE_DATABASE = 10001;

    private Sardine mSardine;
    private Disposable mShakeHandsDispose;

    public void onInit() {
        mSardine = new OkHttpSardine();
    }

    @Override
    public void onShakeHands(final IOnShakeHandsCallback callback) {
        Log.d(TAG, ">>onShakeHands");
        JSONObject mJSONObj = new JSONObject();
        try {
            JSONObject mDataJSONObj = new JSONObject();
            mDataJSONObj.put(OKHTTP_REQUEST_CMDID, CMDID_SHAKE_HANDS);
            mDataJSONObj.put(OKHTTP_REQUEST_SERIAL_NO, getSerialNo());
            mJSONObj.put(OKHTTP_REQUEST_DATA, mDataJSONObj);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "exception:" + e.getMessage());
        }

        String mJSONStr = mJSONObj.toString();
        Log.d(TAG, "request body:" + mJSONStr);
        final Request request = new Request.Builder().
                url(getDefaultUrl()).
                post(RequestBody.create(MediaType.parse(OKHTTP_MEDIA_TYPE_WITH_CHARSET), mJSONStr)).
                build();
        Log.e(TAG, ">>init OkHttpClient");
        final OkHttpClient okHttpClient = new OkHttpClient.Builder().
                connectTimeout(5 * 1000, TimeUnit.MILLISECONDS).
                build();
        Log.e(TAG, ">>newCall request");
        mShakeHandsDispose = Observable.interval(0, 5 * 1000, TimeUnit.MILLISECONDS).
                subscribeOn(Schedulers.io()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Log.e(TAG, "accept aLong:" + aLong);
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull final Call call, @NotNull final IOException e) {
                        Log.d(TAG, "onFailure:" + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.onShakeHandsFailed(-1, e.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String responseBody = response.body().string();
                        Log.d(TAG, "onResponse:" + responseBody);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mShakeHandsDispose != null && !mShakeHandsDispose.isDisposed()) {
                                    mShakeHandsDispose.dispose();
                                }
                                callback.onShakeHandsSuccess();
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void onUpdateDataBase(final IOnUpdateDatabaseCallback callback) {
        Log.d(TAG, ">>onUpdateDataBase");
        final JSONObject mJSONObj = new JSONObject();
        try {
            JSONObject mDataJSONObj = new JSONObject();
            mDataJSONObj.put(OKHTTP_REQUEST_CMDID, CMDID_UPDATE_DATABASE);
            mDataJSONObj.put(OKHTTP_REQUEST_MD5, getDatabaseMd5());
            mJSONObj.put(OKHTTP_REQUEST_DATA, mDataJSONObj);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "exception:" + e.getMessage());
        }

        String mJSONStr = mJSONObj.toString();
        Log.d(TAG, "request body:" + mJSONStr);
        Request request = new Request.Builder().
                url(getDefaultUrl()).
                header(OKHTTP_MEDIA_TYPE_KEY, OKHTTP_MEDIA_TYPE).
                post(RequestBody.create(MediaType.parse(OKHTTP_MEDIA_TYPE_WITH_CHARSET), mJSONStr)).
                build();
        Log.e(TAG, ">>init OkHttpClient");
        OkHttpClient okHttpClient = new OkHttpClient.Builder().
                connectTimeout(5 * 1000, TimeUnit.MILLISECONDS).
                build();
        Log.e(TAG, ">>newCall request");
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull final Call call, @NotNull final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onUpdateDatabaseFailed(-1, e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseBody = response.body().string();
                Log.d(TAG, "onResponse:" + responseBody);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject mJSONObj = new JSONObject(responseBody);
                            callback.onUpdateDatabaseSuccess(
                                    mJSONObj.getInt(OKHTTP_RESPONSE_IS_NEED_DOWNLOAD) == 1,
                                    mJSONObj.getString(OKHTTP_RESPONSE_DATABASE_URL));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onUpdateDatabaseFailed(-2, e.getMessage());
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onDownloadDatabase(final String url, final String toPath, final IOnDownloadDatabaseCallback callback) {
        Log.d(TAG, ">>onDownloadDatabase");
        Disposable mDispose = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                Log.e(TAG, "download from url:" + url);
                Log.e(TAG, "download to path:" + toPath);
                boolean result = false;
                if (mSardine != null && mSardine.exists(url)) {
                    Log.e(TAG, "exist file, start download");
                    result = FileUtils.writeFile(mSardine.get(url), toPath);
                }
                emitter.onNext(result);
            }
        }).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean result) throws Exception {
                        callback.onDownloadDatabaseSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "exception:" + throwable.getMessage());
                        callback.onDownloadDatabaseFailed(-1, throwable.getMessage());
                    }
                });
    }

    private String getDefaultUrl() {
        return "http://192.168.31.209:8123";
    }

    private String getSerialNo() {
        return "0000000000";
    }

    private String getDatabaseMd5() {
        return "0000000000";
    }

    private void runOnUiThread(Runnable runnable) {
        BaseApplication.getInstance().runOnUiThread(runnable);
    }
}
