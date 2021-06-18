package com.me.demo.websocket.server;

import com.me.demo.base.BasePresenter;
import com.me.demo.websocket.server.IWebSocketServerContract.IWebSocketModel;
import com.me.demo.websocket.server.IWebSocketServerContract.IWebSocketView;

import static com.me.demo.websocket.server.IWebSocketServerContract.*;

/**
 * Create by lzf on 6/18/21
 */
public class WebSocketServerPresenterImpl extends BasePresenter<IWebSocketView>
        implements IWebSocketPresenter {

    private IWebSocketModel mModel;
    private IWebSocketView mView;

    public WebSocketServerPresenterImpl() {
        mModel = new WebSocketServerModelImpl();
    }

    @Override
    public void start() {
        mView = getView();
    }

    @Override
    public void webSocketStart(int port) {
        mModel.onWebSocketStart(port, new IOnWebSocketStartCallback() {
            @Override
            public void onWebSocketStartSuccess() {
                if (mView != null) {
                    mView.onWebSocketStartSuccess();
                }
            }

            @Override
            public void onWebSocketStartFailed(int errorCode, String errorMsg) {
                if (mView != null) {
                    mView.onWebSocketStartFailed(errorCode, errorMsg);
                }
            }
        });
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
