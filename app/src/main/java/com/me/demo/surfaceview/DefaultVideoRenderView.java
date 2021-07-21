package com.me.demo.surfaceview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.cardview.widget.CardView;

/**
 * 默认的RenderView
 * 该类的目的是修改RenderView的实现而不影响应用层,只需应用层重新编译即可
 */
public class DefaultVideoRenderView extends CardView {

    private IRenderViewListener mListerner;


    public DefaultVideoRenderView(Context context) {
        super(context);
        config();
    }

    public DefaultVideoRenderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        config();
    }

    public DefaultVideoRenderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        config();
    }

    private void config() {
//        setBackgroundColor(0xFF000000);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (mListerner != null) {
            mListerner.onVisibilityChanged(visibility);
        }
    }

    public interface IRenderViewListener {
        void onVisibilityChanged(int visibility);
    }

    public void setListerner(IRenderViewListener listerner) {
        mListerner = listerner;
    }

}
