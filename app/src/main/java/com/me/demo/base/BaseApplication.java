package com.me.demo.base;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * Create by lzf on 2021-03-29
 */
public class BaseApplication extends Application {

    private String TAG = getClass().getSimpleName();
    private static BaseApplication mContext;
    private Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "==onCreate==");
        mContext = this;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static BaseApplication getInstance() {
        return mContext;
    }

    public void runOnUiThread(Runnable runnable) {
        mHandler.post(runnable);
    }

    public void runOnUiThread(Runnable runnable, long delay) {
        mHandler.postDelayed(runnable, delay);
    }
}
