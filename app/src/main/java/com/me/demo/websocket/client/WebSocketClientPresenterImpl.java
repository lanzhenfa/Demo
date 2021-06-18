package com.me.demo.websocket.client;

import com.me.demo.base.BasePresenter;
import com.me.demo.websocket.client.IWebSocketClientContract.IOnWebSocketSendMessageCallback;
import com.me.demo.websocket.client.IWebSocketClientContract.IWebSocketModel;

import static com.me.demo.websocket.client.IWebSocketClientContract.*;

/**
 * Create by lzf on 6/18/21
 */
public class WebSocketClientPresenterImpl extends BasePresenter<IWebSocketView>
        implements IWebSocketPresenter {

    private IWebSocketModel mModel;
    private IWebSocketView mView;

    public WebSocketClientPresenterImpl() {
        mModel = new WebSocketClientModelImpl();
    }

    @Override
    public void start() {
        mView = getView();
    }

    @Override
    public void initWebSocket(String url) {
        mModel.onInitWebSocket(url);
    }

    @Override
    public void webSocketSendMessage(String message) {
        mModel.onWebSocketSendMessage(message, new IOnWebSocketSendMessageCallback() {
            @Override
            public void onWebSocketSendMessageSuccess(String message) {
                if (mView != null) {
                    mView.onWebSocketSendMessageSuccess(message);
                }
            }

            @Override
            public void onWebSocketSendMessageFailed(int errorCode, String errorMsg) {
                if (mView != null) {
                    mView.onWebSocketSendMessageFailed(errorCode, errorMsg);
                }
            }
        });
    }
}
