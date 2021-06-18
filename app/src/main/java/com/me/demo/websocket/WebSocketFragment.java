package com.me.demo.websocket;

import android.view.View;
import android.widget.FrameLayout;

import com.me.demo.R;
import com.me.demo.base.BaseFragment;
import com.me.demo.base.BasePresenter;
import com.me.demo.websocket.client.IWebSocketClientContract;
import com.me.demo.websocket.client.WebSocketClientPresenterImpl;
import com.me.demo.websocket.client.WebSocketClientView;
import com.me.demo.websocket.server.WebSocketServerView;

/**
 * Create by lzf on 6/18/21
 */
public class WebSocketFragment extends BaseFragment {

    private FrameLayout mWebSocketClient;
    private FrameLayout mWebSocketServer;

    @Override
    protected int getContentView() {
        return R.layout.fragment_websocket;
    }

    @Override
    protected void initView(View view) {
        mWebSocketClient = view.findViewById(R.id.websocket_client);
        mWebSocketClient.addView(new WebSocketClientView(mContext));

        mWebSocketServer = view.findViewById(R.id.websocket_server);
        mWebSocketServer.addView(new WebSocketServerView(mContext));
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void unInitListener() {

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
}
