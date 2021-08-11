package com.me.demo.wallpaper;

import android.app.WallpaperManager;
import android.content.Context;
import android.util.Log;

import com.me.demo.R;
import com.me.demo.base.BaseApplication;

import java.io.IOException;

public class WallPaperManger {
    private static WallPaperManger sInstance = new WallPaperManger();

    public static WallPaperManger getInstance() {
        return sInstance;
    }

    private WallPaperManger() {
    }

    private static final String TAG = "WallPaperManger";

    /**
     * 启动当前视频壁纸
     */
    public void startVideoWallpaper() {
        BaseApplication instance = BaseApplication.getInstance();
        WallPaperUtil.clearWallPaper(instance);
        VideoWallPaperService.setToLiveWallPaper(instance);
    }

    private boolean mIsOpenVideoWallPaper = false;

    public boolean switchVideoWallPaper() {
        this.mIsOpenVideoWallPaper = !isOpenVideoWallPaper();
        return mIsOpenVideoWallPaper;
    }

    public boolean isOpenVideoWallPaper() {
        return mIsOpenVideoWallPaper;
    }

    public void setDefault() {
        BaseApplication instance = BaseApplication.getInstance();
        WallPaperUtil.clearWallPaper(instance);
        //onSetWallpaperForResource(instance,R.drawable.app_bg_back);
        onSetWallpaperForResource(instance, R.drawable.ic_launcher_background);
    }

    /**
     * 使用资源文件设置壁纸
     * 直接设置为壁纸，不会有任何界面和弹窗出现
     */
    public void onSetWallpaperForResource(Context context, int resId) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        try {
            wallpaperManager.setResource(resId);
        } catch (IOException e) {
            Log.e(TAG, "exception:" + e.getMessage());
        }
    }
}
