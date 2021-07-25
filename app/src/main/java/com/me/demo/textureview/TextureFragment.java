package com.me.demo.textureview;

import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.me.demo.R;
import com.me.demo.base.BaseFragment;

import java.io.IOException;

/**
 * Create by lzf on 7/21/21
 */
public class TextureFragment extends BaseFragment<ITextureContract.ITextureView, TexturePresenterImpl>
        implements ITextureContract.ITextureView, View.OnClickListener, TextureView.SurfaceTextureListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener {

    private ConstraintLayout mParentView;
    private TextureView mTextureView;
    private MediaPlayer mMediaPlayer;

    @Override
    protected int getContentView() {
        return R.layout.fragment_texture;
    }

    @Override
    protected void initView(View view) {
        mParentView = view.findViewById(R.id.fragment_texture_parent);
        mTextureView = view.findViewById(R.id.fragment_texture_view);
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
        String url = "storage/emulated/0/kmbox/resource/00004569.ts";
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
        mTextureView.setSurfaceTextureListener(this);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        Surface s = new Surface(surfaceTexture);
        try {
            mMediaPlayer = new MediaPlayer();
            AssetFileDescriptor fd = mContext.getAssets().openFd("tiamo.mp4");
            mMediaPlayer.setDataSource(fd.getFileDescriptor(),
                    fd.getStartOffset(), fd.getLength());
            mMediaPlayer.setSurface(s);
            mMediaPlayer.prepare();
            mMediaPlayer.setOnBufferingUpdateListener(this);
            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnVideoSizeChangedListener(this);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setLooping(true);
            mMediaPlayer.start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        mMediaPlayer.stop();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void unInitListener() {

    }

    @Override
    protected TexturePresenterImpl initPresenter() {
        return new TexturePresenterImpl();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyPlayer();
    }
}
