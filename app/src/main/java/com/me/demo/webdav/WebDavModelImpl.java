package com.me.demo.webdav;

import android.text.TextUtils;
import android.util.Log;

import com.me.demo.util.FileUtils;
import com.thegrizzlylabs.sardineandroid.DavResource;
import com.thegrizzlylabs.sardineandroid.Sardine;
import com.thegrizzlylabs.sardineandroid.impl.OkHttpSardine;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Create by lzf on 6/10/21
 */
public class WebDavModelImpl implements IWebDavContract.IWebDavModel {
    private String TAG = getClass().getSimpleName();

    private Sardine mSardine;
    private String mUsername;
    private String mPassword;
    private String mServerUrl;

    private Disposable mDispose;

    @Override
    public void onCreateWebDav(String url, String username, String password,
                               IWebDavContract.IOnCreateWebDavCallback callback) {
        mServerUrl = url;
        mUsername = username;
        mPassword = password;
        mSardine = new OkHttpSardine();
        mSardine.setCredentials(mUsername, mPassword);
        callback.onCreateWebDavSuccess();
    }

    @Override
    public void onList(final IWebDavContract.IOnListCallback callback) {
        Log.d(TAG, "==onList==");
        mDispose = Observable.create(new ObservableOnSubscribe<List<DavResource>>() {
            @Override
            public void subscribe(ObservableEmitter<List<DavResource>> emitter) throws Exception {
                Log.d(TAG, "mServerUrl:" + mServerUrl);
                //如果是目录一定别忘记在后面加上一个斜杠
                emitter.onNext(mSardine.list(mServerUrl));
            }
        }).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<List<DavResource>>() {
                    @Override
                    public void accept(List<DavResource> davResources) throws Exception {
                        callback.onListSuccess(davResources);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "onList exception:" + throwable.getMessage());
                        callback.onListFailed(-1);
                    }
                });
    }

    @Override
    public void onGetStoragePath(IWebDavContract.IOnGetStoragePathCallback callback) {
        if (TextUtils.isEmpty(mServerUrl)) {
            callback.onGetStoragePathFailed(-1);
        } else {
            callback.onGetStoragePathSuccess(mServerUrl);
        }
    }

    @Override
    public void onExist(final String fileName, final IWebDavContract.IOnExistCallback callback) {
        Log.e(TAG, "==onExist==");
        mDispose = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                String path = mServerUrl + fileName;
                Log.e(TAG, "file/dir path:" + path);
                emitter.onNext(mSardine.exists(path));
            }
        }).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean result) throws Exception {
                        callback.onExistSuccess(result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "onExist exception:" + throwable.getMessage());
                        callback.onExistFailed(-1);
                    }
                });
    }

    @Override
    public void onCreateDir(final String dirName, final IWebDavContract.IOnCreateDirectoryCallback callback) {
        Log.e(TAG, "==onCreateDir==");
        mDispose = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                String path = mServerUrl + dirName;
                Log.e(TAG, "create dir path:" + path);
                boolean result = mSardine.exists(path);
                if (!result) {
                    mSardine.createDirectory(path);
                }
                emitter.onNext(result);
            }
        }).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean result) throws Exception {
                        if (result) {
                            callback.onCreateDirectoryFailed(-2);
                        } else {
                            callback.onCreateDirectorySuccess();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "onCreateDir exception:" + throwable.getMessage());
                        callback.onCreateDirectoryFailed(-1);
                    }
                });
    }

    @Override
    public void onDownFile(final String targetPath, final String originalPath, final IWebDavContract.IOnDownloadFileCallback callback) {
        Log.e(TAG, "==onDownFile==");
        mDispose = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                String path = mServerUrl + originalPath;
                Log.e(TAG, "download path:" + path);
                emitter.onNext(FileUtils.writeFileToSD(mSardine.get(path), targetPath));
            }
        }).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean result) throws Exception {
                        callback.onDownloadFileSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "onDownFile exception:" + throwable.getMessage());
                        callback.onDownloadFileFailed(-1);
                    }
                });
    }

    @Override
    public void onUploadFile(final String targetPath, final String originalPath, final IWebDavContract.IOnUploadFileCallback callback) {
        Log.e(TAG, "==onUploadFile==");
        mDispose = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                String path = mServerUrl + targetPath;
                Log.e(TAG, "upload path:" + path);
                byte[] data = FileUtils.readFileToByteArray(new File(originalPath));
                mSardine.put(path, data);
                emitter.onNext(true);
            }
        }).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean result) throws Exception {
                        callback.onUploadFileSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "onUploadFile exception:" + throwable.getMessage());
                        callback.onUploadFileFailed(-1);
                    }
                });
    }

    @Override
    public void onDeleteFile(final String fileName, final IWebDavContract.IOnDeleteFileCallback callback) {
        Log.e(TAG, "==onDeleteFile==");
        mDispose = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                String path = mServerUrl + fileName;
                Log.e(TAG, "delete file name:" + path);
                mSardine.delete(path);
                emitter.onNext(true);
            }
        }).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean result) throws Exception {
                        callback.onDeleteFileSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "onDeleteFile exception:" + throwable.getMessage());
                        callback.onDeleteFileFailed(-1);
                    }
                });
    }

    @Override
    public void destroy() {
        if (mDispose != null && !mDispose.isDisposed()) {
            mDispose.dispose();
        }
    }
}
