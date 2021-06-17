package com.me.demo.udp.server;

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
public class UdpServerFragment extends BaseFragment<IUdpServerContract.IUdpServerView, UdpServerPresenterImpl>
        implements IUdpServerContract.IUdpServerView, View.OnClickListener {

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
        return R.layout.fragment_udp_server;
    }

    @Override
    protected void initView(View view) {
        mIpEdit = view.findViewById(R.id.udp_server_ip_edit);
        mIpEdit.setText(Config.DEFAULT_IP);
        mConnectBtn = view.findViewById(R.id.udp_server_connect_btn);
        mConnectTxt = view.findViewById(R.id.udp_server_connect_txt);
        mSendEdit = view.findViewById(R.id.udp_server_send_message_edit);
        mSendBtn = view.findViewById(R.id.udp_server_send_btn);
        mSendTxt = view.findViewById(R.id.udp_server_send_txt);
        mReceiveBtn = view.findViewById(R.id.udp_server_receive_btn);
        mReceiveTxt = view.findViewById(R.id.udp_server_receive_txt);

        if (mPresenter != null) {
            mPresenter.start();
            mPresenter.serverReceiveMessage();
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
    protected UdpServerPresenterImpl initPresenter() {
        return new UdpServerPresenterImpl();
    }

    @Override
    public void onClick(View view) {
        if (mPresenter == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.udp_server_connect_btn:
                mPresenter.udpServerConnect(mIpEdit.getText().toString());
                break;
            case R.id.udp_server_send_btn:
                mPresenter.serverSendMessage(mSendEdit.getText().toString());
                break;
            case R.id.udp_server_receive_btn:
                mPresenter.serverReceiveMessage();
                break;
        }
    }

    @Override
    public void onUdpServerConnectSuccess() {
        Log.d(TAG, "==onUdpServerConnectSuccess==");
        mConnectTxt.setText("服务器==> udp连接成功");
    }

    @Override
    public void onUdpServerConnectFailed(int errorCode) {
        Log.e(TAG, "==onUdpServerConnectFailed==");
        mConnectTxt.setText("服务器==> udp连接失败：" + errorCode);
    }

    @Override
    public void onUdpServerSendSuccess() {
        Log.d(TAG, "==onUdpServerSendSuccess==");
        mSendTxt.setText("服务器==> udp发送成功");
    }

    @Override
    public void onUdpServerSendFailed(int errorCode, String errorMsg) {
        Log.e(TAG, "==onUdpServerSendFailed==");
        mSendTxt.setText("服务器==> udp发送失败：" + errorMsg);
    }

    @Override
    public void onUdpServerReceiveSuccess(final String message) {
        Log.d(TAG, "==onUdpReceiveSuccess==");
        BaseApplication.getInstance().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mReceiveTxt.setText("服务器==> udp接收到：" + message);
            }
        });
    }

    @Override
    public void onUdpServerReceiveFailed(final int errorCode, final String errorMsg) {
        Log.e(TAG, "==onUdpServerReceiveFailed==");
        BaseApplication.getInstance().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mReceiveTxt.setText("服务器==> udp接收失败：" + errorMsg);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.destroy();
        }
    }
}
