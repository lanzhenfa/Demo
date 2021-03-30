package com.me.demo.test;

import android.view.View;

import com.me.demo.R;
import com.me.demo.base.BaseFragment;

/**
 * Create by lzf on 2021-03-30
 */
public class TestFragment extends BaseFragment<ITestContract.ITestView, TestPresenterImpl>
        implements ITestContract.ITestView {

    @Override
    protected int getContentView() {
        return R.layout.fragment_test;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void unInitListener() {

    }

    @Override
    protected TestPresenterImpl initPresenter() {
        return new TestPresenterImpl();
    }
}
