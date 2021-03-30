package com.me.demo.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Create by lzf on 2021-03-29
 */
public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment {

    protected String TAG = getClass().getSimpleName();

    protected Context mContext;
    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View mView = inflater.inflate(getContentView(), container, false);
        initView(mView);
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
        initListener();
        return mView;
    }

    protected abstract int getContentView();

    protected abstract void initView(View view);

    protected abstract void initListener();

    protected abstract void unInitListener();

    protected abstract T initPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        unInitListener();
    }
}
