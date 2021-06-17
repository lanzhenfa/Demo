package com.me.demo.udp.client;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.me.demo.R;
import com.me.demo.base.BaseApplication;
import com.me.demo.base.BaseFragment;
import com.me.demo.util.Config;

/**
 * Create by lzf on 6/16/21
 */
public class UdpClientFragment extends BaseFragment<IUdpClientContract.IUdpView, UdpClientPresenterImpl>
        implements IUdpClientContract.IUdpView, View.OnClickListener {

    private EditText mIpEdit;
    private Button mConnectBtn;
    private TextView mConnectTxt;
    private EditText mSendEdit;
    private Button mSendBtn;
    private TextView mSendTxt;
    private Button mReceiveBtn;
    private TextView mReceiveTxt;

    @Override
    protected int getContentView() {
        return R.layout.fragment_udp_client;
    }

    @Override
    protected void initView(View view) {
        mIpEdit = view.findViewById(R.id.udp_client_ip_edit);
        mIpEdit.setText(Config.DEFAULT_IP);
        mConnectBtn = view.findViewById(R.id.udp_client_connect_btn);
        mConnectTxt = view.findViewById(R.id.udp_client_connect_txt);
        mSendEdit = view.findViewById(R.id.udp_client_send_message_edit);
        mSendBtn = view.findViewById(R.id.udp_client_send_btn);
        mSendTxt = view.findViewById(R.id.udp_client_send_txt);
        mReceiveBtn = view.findViewById(R.id.udp_client_receive_btn);
        mReceiveTxt = view.findViewById(R.id.udp_client_receive_txt);

        if (mPresenter != null) {
            mPresenter.start();
        }
    }

    @Override
    protected void initListener() {
        mConnectBtn.setOnClickListener(this);
        mSendBtn.setOnClickListener(this);
        mReceiveBtn.setOnClickListener(this);
    }

    @Override
    protected void unInitListener() {

    }

    @Override
    protected UdpClientPresenterImpl initPresenter() {
        return new UdpClientPresenterImpl();
    }

    @Override
    public void onClick(View view) {
        if (mPresenter == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.udp_client_connect_btn:
                mPresenter.udpConnect(mIpEdit.getText().toString());
                break;
            case R.id.udp_client_send_btn:
                mPresenter.sendMessage(mSendEdit.getText().toString());
                break;
            case R.id.udp_client_receive_btn:
                mPresenter.receiveMessage();
                break;
        }
    }

    @Override
    public void onUdpConnectSuccess() {
        Log.d(TAG, "==onUdpConnectSuccess==");
        mConnectTxt.setText("客户端==>udp连接成功");
    }

    @Override
    public void onUdpConnectFailed(int errorCode) {
        Log.e(TAG, "==onUdpConnectFailed==");
        mConnectTxt.setText("客户端==>udp连接失败：" + errorCode);
    }

    @Override
    public void onUdpSendSuccess() {
        Log.d(TAG, "==onUdpSendSuccess== msg:" + mSendEdit.getText().toString());
        BaseApplication.getInstance().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSendTxt.setText("客户端==>udp发送成功:");
            }
        });
    }

    @Override
    public void onUdpSendFailed(final int errorCode, final String errorMsg) {
        Log.e(TAG, "==onUdpSendFailed==");
        BaseApplication.getInstance().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSendTxt.setText("客户端==> udp发送失败：" + errorMsg);
            }
        });
    }

    @Override
    public void onUdpReceiveSuccess(String message) {
        Log.d(TAG, "==onUdpReceiveSuccess==");
        mReceiveTxt.setText("客户端==>udp接收到：" + message);
    }

    @Override
    public void onUdpReceiveFailed(int errorCode, String errorMsg) {
        Log.e(TAG, "==onUdpReceiveFailed==");
        mReceiveTxt.setText("客户端==>udp接收失败：" + errorMsg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.destroy();
        }
    }
}
