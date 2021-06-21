package com.me.demo.okhttp;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.me.demo.R;
import com.me.demo.base.BaseFragment;
import com.me.demo.okhttp.IOkHttpContract.IOkHttpView;

/**
 * Create by lzf on 6/18/21
 */
public class OkHttpFragment extends BaseFragment<IOkHttpView, OkHttpPresenterImpl>
        implements IOkHttpView, View.OnClickListener {
    private Button mShakeHandsBtn;
    private Button mUpdateDbBtn;

    @Override
    protected int getContentView() {
        return R.layout.fragment_okhttp;
    }

    @Override
    protected void initView(View view) {
        mShakeHandsBtn = view.findViewById(R.id.okhttp_shake_hands_btn);
        mUpdateDbBtn = view.findViewById(R.id.okhttp_update_db_btn);

        if (mPresenter != null) {
            mPresenter.start();
        }
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
    protected OkHttpPresenterImpl initPresenter() {
        return new OkHttpPresenterImpl();
    }

    @Override
    public void onShakeHandsSuccess() {
        Log.d(TAG, ">>onShakeHandsSuccess");
        if (mPresenter != null) {
            mPresenter.updateDatabase();
        }
    }

    @Override
    public void onShakeHandsFailed(int errorCode, String errorMsg) {
        Log.e(TAG, ">>onShakeHandsFailed:" + errorMsg);
    }

    @Override
    public void onUpdateDatabaseSuccess(boolean isNeedDownload, String url) {
        Log.d(TAG, ">>onUpdateDatabaseSuccess");
        Log.e(TAG, "isNeedDownload:" + isNeedDownload);
        if (isNeedDownload && mPresenter != null) {
            mPresenter.downloadDatabase(url, "/sdcard/kmbox/db/local_kmbox.db");
        }
    }

    @Override
    public void onUpdateDatabaseFailed(int errorCode, String errorMsg) {
        Log.e(TAG, ">>onUpdateDatabaseFailed:" + errorMsg);
    }

    @Override
    public void onDownloadDatabaseSuccess() {
        Log.d(TAG, ">>onDownloadDatabaseSuccess");
    }

    @Override
    public void onDownloadDatabaseFailed(int errorCode, String errorMsg) {
        Log.e(TAG, ">>onDownloadDatabaseFailed:" + errorMsg);
    }

    @Override
    public void onClick(View view) {
        if (mPresenter == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.okhttp_shake_hands_btn:
                mPresenter.shakeHands();
                break;
            case R.id.okhttp_update_db_btn:
                mPresenter.updateDatabase();
                break;
        }
    }
}
