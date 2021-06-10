package com.me.demo.database;

import android.view.View;

import com.me.demo.R;
import com.me.demo.base.BaseFragment;

/**
 * Create by lzf on 5/19/21
 */
public class DatabaseFragment extends BaseFragment<IDatabaseContract.IDatabaseView, DatabasePresenterImpl>
        implements IDatabaseContract.IDatabaseView {

    @Override
    protected int getContentView() {
        return R.layout.fragment_database;
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
    protected DatabasePresenterImpl initPresenter() {
        return new DatabasePresenterImpl();
    }
}
