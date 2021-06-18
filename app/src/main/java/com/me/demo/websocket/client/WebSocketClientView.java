package com.me.demo.websocket.client;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.me.demo.R;
import com.me.demo.base.BaseView;
import com.me.demo.util.Config;

/**
 * Create by lzf on 6/18/21
 */
public class WebSocketClientView extends BaseView<IWebSocketClientContract.IWebSocketView, WebSocketClientPresenterImpl>
        implements IWebSocketClientContract.IWebSocketView, View.OnClickListener {

    private EditText mIpEdit;
    private EditText mPortEdit;
    private Button mConnectBtn;
    private TextView mConnectTxt;
    private EditText mSendEdit;
    private Button mSendBtn;
    private TextView mSendTxt;

    public WebSocketClientView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_websocket_client;
    }

    @Override
    protected void initView() {
        mIpEdit = findViewById(R.id.websocket_client_ip_edit);
        mPortEdit = findViewById(R.id.websocket_client_port_edit);
        mIpEdit.setText(Config.DEFAULT_WEBSOCKET_IP);
        mConnectBtn = findViewById(R.id.websocket_client_connect_btn);
        mConnectTxt = findViewById(R.id.websocket_client_connect_txt);
        mSendEdit = findViewById(R.id.websocket_client_send_message_edit);
        mSendBtn = findViewById(R.id.websocket_client_send_btn);
        mSendTxt = findViewById(R.id.websocket_client_send_txt);
    }

    @Override
    protected void initListener() {
        mConnectBtn.setOnClickListener(this);
        mSendBtn.setOnClickListener(this);
    }

    @Override
    protected void unInitListener() {

    }

    @Override
    protected WebSocketClientPresenterImpl initPresenter() {
        return new WebSocketClientPresenterImpl();
    }

    @Override
    public void onClick(View view) {
        if (mPresenter == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.websocket_client_connect_btn:
                mPresenter.initWebSocket(mIpEdit.getText().toString() + ":" + mPortEdit.getText().toString());
                break;
            case R.id.websocket_client_send_btn:
                mPresenter.webSocketSendMessage(mSendEdit.getText().toString());
                break;
        }
    }

    @Override
    public void onWebSocketSendMessageSuccess(String message) {
        Log.e(TAG, ">>onWebSocketSendMessageSuccess:" + message);
        mSendTxt.setText(message);
    }

    @Override
    public void onWebSocketSendMessageFailed(int errorCode, String errorMsg) {
        Log.e(TAG, ">>onWebSocketSendMessageFailed:" + errorMsg);
        mSendTxt.setText(errorMsg);
    }
}
