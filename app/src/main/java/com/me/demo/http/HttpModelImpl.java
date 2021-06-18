package com.me.demo.http;

import android.text.TextUtils;
import android.util.Log;

import com.me.demo.http.IHttpContract.IHttpModel;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

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
public class HttpModelImpl implements IHttpModel {
    private String TAG = getClass().getSimpleName();

    private int HTTP_TIME_OUT = 10 * 1000;
    private String HTTP_ENCODING = "utf-8";
    //http请求的key
    private String HTTP_DATA = "data";
    private String HTTP_CMDID = "cmdid";
    private String HTTP_SERIAL_NO = "sn";
    private String HTTP_MD5 = "md5";
    //http返回结果的key
    private String HTTP_ERROR_CODE = "errorcode";
    private String HTTP_ERROR_MESSAGE = "errormessage";
    //握手协议
    private int CMDID_SHAKE_HANDS = 10000;
    //曲库更新协议
    private String CMDID_UPDATE_DATABASE = "10001";

    private Disposable mPostDispose;

    private String getDefaultUrl() {
        return "http://192.168.31.209";
    }

    private HttpClient getDefaultHttpClient() {
        //设置超时时间
        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, HTTP_TIME_OUT);
        HttpConnectionParams.setSoTimeout(httpParams, HTTP_TIME_OUT);
        return new DefaultHttpClient(httpParams);
    }

    @Override
    public void onShakeHands(final IHttpContract.IOnHttpShakeHandsCallback callback) {
        Log.d(TAG, ">>shakeHands");
        mPostDispose = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                JSONObject mJSONObj = new JSONObject();
                JSONObject mDataJSONObj = new JSONObject();
                mDataJSONObj.put(HTTP_CMDID, CMDID_SHAKE_HANDS);
                mDataJSONObj.put(HTTP_SERIAL_NO, "0000000000");
                mJSONObj.put(HTTP_DATA, mDataJSONObj);

                String mJSONStr = mJSONObj.toString();
                Log.d(TAG, "request body:" + mJSONStr);
                HttpClient httpClient = getDefaultHttpClient();
                HttpPost httpPost = new HttpPost(getDefaultUrl());
                httpPost.addHeader("Content-Type", "application/json");
                httpPost.setEntity(new StringEntity(mJSONStr, HTTP_ENCODING));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                int code = httpResponse.getStatusLine().getStatusCode();
                Log.d(TAG, "response code:" + code);
                String response = "";
                if (code >= 200 && code <= 300) {
                    HttpEntity httpEntity = httpResponse.getEntity();
                    response = EntityUtils.toString(httpEntity, HTTP_ENCODING);
                    Log.d(TAG, "response body:" + response);
                } else {
                    Log.d(TAG, "code:" + code);
                }
                emitter.onNext(response);
            }
        }).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String result) throws Exception {
                        if (TextUtils.isEmpty(result)) {
                            callback.onShakeHandsFailed(-1, "response null");
                        } else {
                            JSONObject mResponseJSONObj = new JSONObject(result);
                            int errorCode = mResponseJSONObj.getInt(HTTP_ERROR_CODE);
                            int errorMsg = mResponseJSONObj.getInt(HTTP_ERROR_MESSAGE);
                            Log.d(TAG, "errorCode:" + errorCode + "  errorMsg:" + errorMsg);
                            callback.onShakeHandsSuccess();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callback.onShakeHandsFailed(-1, throwable.getMessage());
                    }
                });
    }

    @Override
    public void onUpdateDatabase() {
        Log.d(TAG, ">>updateDatabase");
        JSONObject mJSONObj = new JSONObject();
        try {
            JSONObject mDataJSONObj = new JSONObject();
            mDataJSONObj.put(HTTP_CMDID, CMDID_UPDATE_DATABASE);
            mDataJSONObj.put(HTTP_MD5, "0000000000");
            mJSONObj.put(HTTP_DATA, mDataJSONObj);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "exception:" + e.getMessage());
        }

        String mJSONStr = mJSONObj.toString();
        Log.d(TAG, "request body:" + mJSONStr);
    }
}
