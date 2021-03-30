package com.me.demo.base;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;

/**
 * Create by lzf on 2021-03-30
 */
public class BasePresenter<T> {

    private Reference<T> mViewRef;

    protected void attachView(T view) {
        //建立关系
        mViewRef = new SoftReference<>(view);
    }

    protected void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
        }
    }

    protected T getView() {
        return mViewRef != null ? mViewRef.get() : null;
    }
}
