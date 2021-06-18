package com.me.demo.websocket.server;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.me.demo.R;
import com.me.demo.base.BaseFragment;
import com.me.demo.base.BaseView;
import com.me.demo.util.Config;

import static com.me.demo.websocket.server.IWebSocketServerContract.*;

/**
 * Create by lzf on 6/18/21
 */
public class WebSocketServerView extends BaseView<IWebSocketView, WebSocketServerPresenterImpl>
        implements IWebSocketView, View.OnClickListener {

    private EditText mPortEdit;
    private Button mStartBtn;
    private TextView mStartTxt;
    private EditText mMessageEdit;
    private Button mSendBtn;
    private TextView mSendTxt;

    public WebSocketServerView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_websocket_server;
    }

    @Override
    protected void initView() {
        mPortEdit = findViewById(R.id.websocket_server_port_edit);
        mStartBtn = findViewById(R.id.websocket_server_start_btn);
        mStartTxt = findViewById(R.id.websocket_server_start_txt);
        mMessageEdit = findViewById(R.id.websocket_server_message_edit);
        mSendBtn = findViewById(R.id.websocket_server_send_message_btn);
        mSendTxt = findViewById(R.id.websocket_server_send_message_txt);
    }

    @Override
    protected void initListener() {
        mStartBtn.setOnClickListener(this);
        mSendBtn.setOnClickListener(this);
    }

    @Override
    protected void unInitListener() {

    }

    @Override
    protected WebSocketServerPresenterImpl initPresenter() {
        return new WebSocketServerPresenterImpl();
    }

    @Override
    public void onClick(View view) {
        if (mPresenter == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.websocket_server_start_btn:
                mPresenter.webSocketStart(Integer.valueOf(mPortEdit.getText().toString()));
                break;
            case R.id.websocket_server_send_message_btn:
                mPresenter.webSocketSendMessage(mMessageEdit.getText().toString());
                break;
        }
    }

    @Override
    public void onWebSocketStartSuccess() {
        Log.d(TAG, ">>onWebSocketStartSuccess");
        mStartTxt.setText("服务端启动成功");
    }

    @Override
    public void onWebSocketStartFailed(int errorCode, String errorMsg) {
        Log.e(TAG, ">>onWebSocketStartFailed:" + errorMsg);
        mStartTxt.setText("服务端启动失败：" + errorMsg);
    }

    @Override
    public void onWebSocketSendMessageSuccess(String message) {
        Log.d(TAG, ">>onWebSocketSendMessageSuccess：" + message);
        mStartTxt.setText("服务端回复成功：" + message);
    }

    @Override
    public void onWebSocketSendMessageFailed(int errorCode, String errorMsg) {
        Log.e(TAG, ">>onWebSocketSendMessageFailed:" + errorMsg);
        mSendTxt.setText("服务端回复失败：" + errorMsg);
    }
}
