package com.me.demo.webdav;

import com.me.demo.base.BasePresenter;
import com.thegrizzlylabs.sardineandroid.DavResource;

import java.util.List;

/**
 * Create by lzf on 6/10/21
 */
public class WebDavPresenterImpl extends BasePresenter<IWebDavContract.IWebDavView>
        implements IWebDavContract.IWebDavPresenter {
    private IWebDavContract.IWebDavModel mWebDavModel;
    private IWebDavContract.IWebDavView mWebDavView;

    public WebDavPresenterImpl() {
        this.mWebDavModel = new WebDavModelImpl();
    }

    @Override
    public void start() {
        mWebDavView = getView();
    }

    @Override
    public void createWebDav(String url, String username, String password) {
        mWebDavModel.onCreateWebDav(url, username, password,
                new IWebDavContract.IOnCreateWebDavCallback() {
                    @Override
                    public void onCreateWebDavSuccess() {
                        if (mWebDavView != null) {
                            mWebDavView.onCreateWebDavSuccess();
                        }
                    }

                    @Override
                    public void onCreateWebDavFailed(int errorCode) {
                        if (mWebDavView != null) {
                            mWebDavView.onCreateWebDavFailed(errorCode);
                        }
                    }
                });
    }

    @Override
    public void list() {
        mWebDavModel.onList(new IWebDavContract.IOnListCallback() {
            @Override
            public void onListSuccess(List<DavResource> list) {
                if (mWebDavView != null) {
                    mWebDavView.onListSuccess(list);
                }
            }

            @Override
            public void onListFailed(int errorCode) {
                if (mWebDavView != null) {
                    mWebDavView.onListFailed(errorCode);
                }
            }
        });
    }

    @Override
    public void getStoragePath() {
        mWebDavModel.onGetStoragePath(new IWebDavContract.IOnGetStoragePathCallback() {
            @Override
            public void onGetStoragePathSuccess(String path) {
                if (mWebDavView != null) {
                    mWebDavView.onGetStoragePathSuccess(path);
                }
            }

            @Override
            public void onGetStoragePathFailed(int errorCode) {
                if (mWebDavView != null) {
                    mWebDavView.onGetStoragePathFailed(errorCode);
                }
            }
        });
    }

    @Override
    public void isExistFile(String fileName) {
        mWebDavModel.onExist(fileName, new IWebDavContract.IOnExistCallback() {
            @Override
            public void onExistSuccess(boolean isExist) {
                if (mWebDavView != null) {
                    mWebDavView.onExistSuccess(isExist);
                }
            }

            @Override
            public void onExistFailed(int errorCode) {
                if (mWebDavView != null) {
                    mWebDavView.onExistFailed(errorCode);
                }
            }
        });
    }

    @Override
    public void createDirectory(String dirName) {
        mWebDavModel.onCreateDir(dirName, new IWebDavContract.IOnCreateDirectoryCallback() {
            @Override
            public void onCreateDirectorySuccess() {
                if (mWebDavView != null) {
                    mWebDavView.onCreateDirectorySuccess();
                }
            }

            @Override
            public void onCreateDirectoryFailed(int errorCode) {
                if (mWebDavView != null) {
                    mWebDavView.onCreateDirectoryFailed(errorCode);
                }
            }
        });
    }

    @Override
    public void downloadFile(String targetPath, String originalPath) {
        mWebDavModel.onDownFile(targetPath, originalPath, new IWebDavContract.IOnDownloadFileCallback() {
            @Override
            public void onDownloadFileSuccess() {
                if (mWebDavView != null) {
                    mWebDavView.onDownloadFileSuccess();
                }
            }

            @Override
            public void onDownloadFileFailed(int errorCode) {
                if (mWebDavView != null) {
                    mWebDavView.onDownloadFileFailed(errorCode);
                }
            }
        });
    }

    @Override
    public void uploadFile(String targetPath, String originalPath) {
        mWebDavModel.onUploadFile(targetPath, originalPath, new IWebDavContract.IOnUploadFileCallback() {
            @Override
            public void onUploadFileSuccess() {
                if (mWebDavView != null) {
                    mWebDavView.onUploadFileSuccess();
                }
            }

            @Override
            public void onUploadFileFailed(int errorCode) {
                if (mWebDavView != null) {
                    mWebDavView.onUploadFileFailed(errorCode);
                }
            }
        });
    }

    @Override
    public void deleteFile(String fileName) {
        mWebDavModel.onDeleteFile(fileName, new IWebDavContract.IOnDeleteFileCallback() {
            @Override
            public void onDeleteFileSuccess() {
                if (mWebDavView != null) {
                    mWebDavView.onDeleteFileSuccess();
                }
            }

            @Override
            public void onDeleteFileFailed(int errorCode) {
                if (mWebDavView != null) {
                    mWebDavView.onDeleteFileFailed(errorCode);
                }
            }
        });
    }

    @Override
    public void stop() {
        mWebDavModel.destroy();
    }
}
