package com.me.demo.surfaceview;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.me.demo.R;
import com.me.demo.base.BaseFragment;

import java.io.IOException;

/**
 * Create by lzf on 7/21/21
 */
public class SurfaceFragment extends BaseFragment<ISurfaceContract.ISurfaceView, SurfacePresenterImpl>
        implements ISurfaceContract.ISurfaceView, View.OnClickListener {

    private ConstraintLayout mParentView;
    private DefaultVideoRenderView mCardView;
    private SurfaceView mSurfaceView;
    private MediaPlayer mMediaPlayer;
    private SurfaceHolder mSurfaceHolder;

    @Override
    protected int getContentView() {
        return R.layout.fragment_surface;
    }

    @Override
    protected void initView(View view) {
        mParentView = view.findViewById(R.id.fragment_surface_parent);
        mCardView = view.findViewById(R.id.fragment_render_view);
        mSurfaceView = new SurfaceView(mCardView.getContext());
        //添加SurfaceView
        mCardView.addView(mSurfaceView);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.e(TAG, ">>surfaceCreated");
                initPlayer();
                mMediaPlayer.setDisplay(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.e(TAG, ">>surfaceChanged");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.e(TAG, ">>surfaceDestroyed");
                destroyPlayer();
            }
        });
    }

    /**
     * 初始化MediaPlayer
     */
    private void initPlayer() {
        Log.d(TAG, ">>initPlayer");
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mMediaPlayer.start();
                mMediaPlayer.setLooping(true);
                mMediaPlayer.setVolume(0.2f, 0.2f);
            }
        });
        mMediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                Log.d(TAG, ">>onInfo");
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                    Log.d(TAG, "MEDIA_INFO_VIDEO_RENDERING_START");
                }
                return false;
            }
        });
        String url = "http://192.168.31.209/webdav/sda1/kmbox/resource/10015010.mkv";
        try {
            mMediaPlayer.setDataSource(url);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            Log.e(TAG, "exception:" + e.getMessage());
        }
    }

    /**
     * 释放资源
     */
    private void destroyPlayer() {
        Log.e(TAG, ">>destroyPlayer");
        if (mMediaPlayer != null) {
            //停止音频的播放
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    protected void initListener() {
        mSurfaceView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void unInitListener() {

    }

    @Override
    protected SurfacePresenterImpl initPresenter() {
        return new SurfacePresenterImpl();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyPlayer();
    }
}
