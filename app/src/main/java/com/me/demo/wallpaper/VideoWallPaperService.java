package com.me.demo.wallpaper;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * 视频壁纸服务类
 */
public class VideoWallPaperService extends WallpaperService {

    private final static String TAG = "VideoWallPaperService";

    private MediaPlayer mPlayer;

    public static void setToLiveWallPaper(Context context) {
        Log.v(TAG, "setToLiveWallPaper");
        WallPaperUtil.setLiveWallPaper(context, VideoWallPaperService.class.getCanonicalName());
    }

    @Override
    public Engine onCreateEngine() {
        Log.i(TAG, "onCreateEngine方法执行");
        return new LiveEngine();
    }

    //内部类Engine，实现了壁纸窗口的创建以及Surface的维护工作
    //自定义Engine定制一个个性化的动态壁纸
    class LiveEngine extends VideoWallPaperService.Engine {
        /**
         * 创建surface
         *
         * @param surfaceHolder
         */
        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
        }

        /**
         * 创建完成，可以获取surfaceholder
         *
         * @param holder
         */
        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            Log.v(TAG, "onSurfaceCreated");
            if (mPlayer == null) {
                try {
                    //设置SD卡上的资源文件
                    mPlayer = new MediaPlayer();
                    AssetManager assetMg = getApplicationContext().getAssets();
                    /*int random=(int)(Math.random()*10)%videos.length;
                    String name = videos[random];
                    Log.e(TAG, "onSurfaceCreated >>>> ["+random+"],["+name+"]");*/
                    AssetFileDescriptor fileDescriptor = assetMg.openFd("tiamo.mp4");

                    mPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
                            fileDescriptor.getStartOffset(), fileDescriptor.getLength());
                    //mPlayer.setDataSource(animFile.getAbsolutePath());
                    //设置Surface
                    mPlayer.setSurface(holder.getSurface());
                    //重复播放
                    mPlayer.setLooping(true);
                    mPlayer.setVolume(0, 0);
                    //开始播放
                    mPlayer.prepare();
                    mPlayer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        /**
         * 桌面可见性改变
         *
         * @param visible
         */
        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            Log.i(TAG, "Visible Change to: " + visible);
            if (visible) {
                mPlayer.start();
            } else {
                mPlayer.pause();
            }
        }

        /**
         * 画面销毁
         *
         * @param holder
         */
        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            Log.i(TAG, "onSurfaceDestroyed");
            mPlayer.release();
            mPlayer = null;
        }
    }
}
