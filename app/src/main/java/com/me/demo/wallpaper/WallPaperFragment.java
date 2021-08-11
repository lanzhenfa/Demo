package com.me.demo.wallpaper;

import android.view.View;
import android.widget.Button;

import com.me.demo.R;
import com.me.demo.base.BaseFragment;
import com.me.demo.base.BasePresenter;

/**
 * Create by lzf on 8/5/21
 */
public class WallPaperFragment extends BaseFragment implements View.OnClickListener {

    private Button mSetWallPaperBtn;

    @Override
    protected int getContentView() {
        return R.layout.fragment_wallpaper;
    }

    @Override
    protected void initView(View view) {
        mSetWallPaperBtn = view.findViewById(R.id.fragment_wallpaper_set_btn);
    }

    @Override
    protected void initListener() {
        mSetWallPaperBtn.setOnClickListener(this);
    }

    @Override
    protected void unInitListener() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_wallpaper_set_btn:
                WallPaperManger.getInstance().startVideoWallpaper();
                break;
        }
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
}
