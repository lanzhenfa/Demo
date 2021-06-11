package com.me.demo.base;

import android.app.Application;
import android.util.Log;

/**
 * Create by lzf on 2021-03-29
 */
public class BaseApplication extends Application {

    private String TAG = getClass().getSimpleName();
    private static BaseApplication mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "==onCreate==");
        mContext = this;
    }

    public static BaseApplication getInstance() {
        return mContext;
    }
}
