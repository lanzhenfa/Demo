package com.me.demo.http;

import android.view.View;
import android.widget.Button;

import com.me.demo.R;
import com.me.demo.base.BaseFragment;

/**
 * Create by lzf on 6/18/21
 */
public class HttpFragment extends BaseFragment<IHttpContract.IHttpView, HttpPresenterImpl>
        implements IHttpContract.IHttpView, View.OnClickListener {
    private Button mShakeHandsBtn;
    private Button mUpdateDbBtn;

    @Override
    protected int getContentView() {
        return R.layout.fragment_http;
    }

    @Override
    protected void initView(View view) {
        mShakeHandsBtn = view.findViewById(R.id.http_shake_hands_btn);
        mUpdateDbBtn = view.findViewById(R.id.http_update_db_btn);
    }

    @Override
    protected void initListener() {
        mShakeHandsBtn.setOnClickListener(this);
        mUpdateDbBtn.setOnClickListener(this);
    }

    @Override
    protected void unInitListener() {

    }

    @Override
    protected HttpPresenterImpl initPresenter() {
        return new HttpPresenterImpl();
    }

    @Override
    public void onClick(View view) {
        if (mPresenter == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.http_shake_hands_btn:
                mPresenter.shakeHands();
                break;
            case R.id.http_update_db_btn:
                break;
        }
    }
}
