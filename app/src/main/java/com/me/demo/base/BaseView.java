package com.me.demo.base;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Create by lzf on 6/18/21
 */
public abstract class BaseView<V, T extends BasePresenter<V>> extends FrameLayout {

    protected String TAG = getClass().getSimpleName();

    protected Context mContext;
    protected T mPresenter;

    public BaseView(@NonNull Context context) {
        super(context);
        mContext = context;
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }

        LayoutInflater.from(mContext).inflate(getContentView(), this, true);
        initView();
    }

    public BaseView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow");
        initListener();
    }

    protected abstract int getContentView();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void unInitListener();

    protected abstract T initPresenter();

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow");
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        unInitListener();
    }
}
