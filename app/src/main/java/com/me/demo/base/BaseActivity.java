package com.me.demo.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Create by lzf on 2021-03-29
 */
public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity {

    protected String TAG = getClass().getSimpleName();

    protected Context mContext;
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "--onCreate--");
        setContentView(getContentView());

        mContext = this;
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }

        initView();
        initListener();
    }

    protected abstract int getContentView();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void unInitListener();

    protected abstract T initPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "--onDestroy--");
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        unInitListener();
    }
}
