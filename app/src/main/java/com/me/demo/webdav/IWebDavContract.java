package com.me.demo.webdav;

import com.thegrizzlylabs.sardineandroid.DavResource;

import java.util.List;

/**
 * Create by lzf on 6/10/21
 */
public interface IWebDavContract {
    interface IWebDavView {
        void onCreateWebDavSuccess();

        void onCreateWebDavFailed(int errorCode);

        void onListSuccess(List<DavResource> list);

        void onListFailed(int errorCode);

        void onGetStoragePathSuccess(String path);

        void onGetStoragePathFailed(int errorCode);

        void onExistSuccess(boolean isExist);

        void onExistFailed(int errorCode);

        void onCreateDirectorySuccess();

        void onCreateDirectoryFailed(int errorCode);

        void onDownloadFileSuccess();

        void onDownloadFileFailed(int errorCode);

        void onUploadFileSuccess();

        void onUploadFileFailed(int errorCode);

        void onDeleteFileSuccess();

        void onDeleteFileFailed(int errorCode);
    }

    interface IWebDavPresenter {

        void start();

        void createWebDav(String url, String username, String password);

        void list();

        void getStoragePath();

        void isExistFile(String fileName);

        void createDirectory(String dirName);

        void downloadFile(String targetPath, String originalPath);

        void uploadFile(String targetPath, String originalPath);

        void deleteFile(String fileName);

        void stop();
    }

    interface IWebDavModel {
        void onCreateWebDav(String url, String username, String password, IOnCreateWebDavCallback callback);

        void onList(IOnListCallback callback);

        void onGetStoragePath(IOnGetStoragePathCallback callback);

        void onExist(String fileName, IOnExistCallback callback);

        void onCreateDir(String dirName, IOnCreateDirectoryCallback callback);

        void onDownFile(String targetPath, String originalPath, IOnDownloadFileCallback callback);

        void onUploadFile(String targetPath, String originalPath, IOnUploadFileCallback callback);

        void onDeleteFile(String fileName, IOnDeleteFileCallback callback);

        void destroy();
    }

    interface IOnCreateWebDavCallback {
        void onCreateWebDavSuccess();

        void onCreateWebDavFailed(int errorCode);
    }

    interface IOnListCallback {
        void onListSuccess(List<DavResource> list);

        void onListFailed(int errorCode);
    }

    interface IOnGetStoragePathCallback {
        void onGetStoragePathSuccess(String path);

        void onGetStoragePathFailed(int errorCode);
    }

    interface IOnExistCallback {
        void onExistSuccess(boolean isExist);

        void onExistFailed(int errorCode);
    }

    interface IOnCreateDirectoryCallback {
        void onCreateDirectorySuccess();

        void onCreateDirectoryFailed(int errorCode);
    }

    interface IOnDownloadFileCallback {
        void onDownloadFileSuccess();

        void onDownloadFileFailed(int errorCode);
    }

    interface IOnUploadFileCallback {
        void onUploadFileSuccess();

        void onUploadFileFailed(int errorCode);
    }

    interface IOnDeleteFileCallback {
        void onDeleteFileSuccess();

        void onDeleteFileFailed(int errorCode);
    }
}
