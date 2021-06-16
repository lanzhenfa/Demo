package com.me.demo.webdav;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.me.demo.R;
import com.me.demo.base.BaseFragment;
import com.thegrizzlylabs.sardineandroid.DavResource;

import java.util.List;

/**
 * Create by lzf on 6/10/21
 */
public class WebDavFragment extends BaseFragment<IWebDavContract.IWebDavView, WebDavPresenterImpl>
        implements IWebDavContract.IWebDavView, View.OnClickListener {

    //与服务建立连接模块
    private Button mCreateBtn;
    private TextView mCreateTxt;
    //获取资源列表模块
    private Button mListBtn;
    private TextView mListTxt;
    //获取服务路径模块
    private Button mGetStoragePathBtn;
    private TextView mGetStoragePathTxt;
    //判断文件/文件夹是否存在模块
    private EditText mIsExistedEdit;
    private Button mIsExistedBtn;
    private TextView mIsExistedTxt;
    //创建文件夹模块
    private EditText mCreateDirEdit;
    private Button mCreateDirBtn;
    private TextView mCreateDirTxt;
    //下载文件模块
    private EditText mDownloadFileTargetEdit;
    private EditText mDownloadFileEdit;
    private Button mDownloadFileBtn;
    private TextView mDownloadFileTxt;
    //上传文件模块
    private EditText mUploadFileTargetEdit;
    private EditText mUploadFileEdit;
    private Button mUploadFileBtn;
    private TextView mUploadFileTxt;
    //删除文件模块
    private EditText mDeleteFileEdit;
    private Button mDeleteFileBtn;
    private TextView mDeleteFileTxt;

    @Override
    protected int getContentView() {
        return R.layout.fragment_webdav;
    }

    @Override
    protected void initView(View view) {
        mCreateBtn = view.findViewById(R.id.webdav_create_btn);
        mCreateTxt = view.findViewById(R.id.webdav_create_txt);

        mListBtn = view.findViewById(R.id.webdav_list_btn);
        mListTxt = view.findViewById(R.id.webdav_list_txt);

        mGetStoragePathBtn = view.findViewById(R.id.webdav_storage_path_btn);
        mGetStoragePathTxt = view.findViewById(R.id.webdav_storage_path_txt);

        mIsExistedEdit = view.findViewById(R.id.webdav_file_input);
        mIsExistedBtn = view.findViewById(R.id.webdav_exist_btn);
        mIsExistedTxt = view.findViewById(R.id.webdav_exist_txt);

        mCreateDirEdit = view.findViewById(R.id.webdav_create_dir_input);
        mCreateDirBtn = view.findViewById(R.id.webdav_create_dir_btn);
        mCreateDirTxt = view.findViewById(R.id.webdav_create_dir_txt);

        mDownloadFileTargetEdit = view.findViewById(R.id.webdav_download_file_target_input);
        mDownloadFileEdit = view.findViewById(R.id.webdav_download_file_input);
        mDownloadFileBtn = view.findViewById(R.id.webdav_download_file_btn);
        mDownloadFileTxt = view.findViewById(R.id.webdav_download_file_txt);

        mUploadFileTargetEdit = view.findViewById(R.id.webdav_upload_file_target_input);
        mUploadFileEdit = view.findViewById(R.id.webdav_upload_file_input);
        mUploadFileBtn = view.findViewById(R.id.webdav_upload_file_btn);
        mUploadFileTxt = view.findViewById(R.id.webdav_upload_file_txt);

        mDeleteFileEdit = view.findViewById(R.id.webdav_delete_file_input);
        mDeleteFileBtn = view.findViewById(R.id.webdav_delete_file_btn);
        mDeleteFileTxt = view.findViewById(R.id.webdav_delete_file_txt);

        mDownloadFileTargetEdit.setText("/sdcard/");
        mUploadFileEdit.setText("/sdcard/");

        if (mPresenter != null) {
            mPresenter.start();
        }
    }

    @Override
    protected void initListener() {
        mCreateBtn.setOnClickListener(this);
        mListBtn.setOnClickListener(this);
        mGetStoragePathBtn.setOnClickListener(this);
        mIsExistedBtn.setOnClickListener(this);
        mCreateDirBtn.setOnClickListener(this);
        mDownloadFileBtn.setOnClickListener(this);
        mUploadFileBtn.setOnClickListener(this);
        mDeleteFileBtn.setOnClickListener(this);

        mIsExistedEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mIsExistedBtn.setText(String.format("判断是否存在:%s", charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mCreateDirEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCreateDirBtn.setText(String.format("创建文件夹:%s", charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mDownloadFileEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mDownloadFileBtn.setText(String.format("下载文件（夹）:%s", charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mUploadFileEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mUploadFileBtn.setText(String.format("上传文件（夹）:%s", charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mDeleteFileEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mDeleteFileBtn.setText(String.format("删除文件（夹）:%s", charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void unInitListener() {

    }

    @Override
    protected WebDavPresenterImpl initPresenter() {
        return new WebDavPresenterImpl();
    }

    @Override
    public void onClick(View view) {
        if (mPresenter == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.webdav_create_btn:
                String url = "http://192.168.31.209/webdav/";
                String username = "admin";
                String password = "admin";
                mPresenter.createWebDav(url, username, password);
                break;
            case R.id.webdav_list_btn:
                mPresenter.list();
                break;
            case R.id.webdav_storage_path_btn:
                mPresenter.getStoragePath();
                break;
            case R.id.webdav_exist_btn:
                String mFileName = mIsExistedEdit.getText().toString();
                Log.d(TAG, "file name:" + mFileName);
                if (TextUtils.isEmpty(mFileName)) {
                    mIsExistedTxt.setText("文件名称不为空");
                    return;
                }
                mPresenter.isExistFile(mFileName);
                break;
            case R.id.webdav_create_dir_btn:
                String mDirName = mCreateDirEdit.getText().toString();
                Log.d(TAG, "dir name:" + mDirName);
                if (TextUtils.isEmpty(mDirName)) {
                    mCreateDirTxt.setText("文件夹名称不为空");
                    return;
                }
                mPresenter.createDirectory(mDirName);
                break;
            case R.id.webdav_download_file_btn:
                String mDownloadTargetPath = mDownloadFileTargetEdit.getText().toString();
                String mDownloadOriginalPath = mDownloadFileEdit.getText().toString();
                Log.d(TAG, "mDownloadTargetPath:" + mDownloadTargetPath +
                        "  mDownloadOriginalPath:" + mDownloadOriginalPath);
                if (TextUtils.isEmpty(mDownloadTargetPath)) {
                    mCreateDirTxt.setText("下载的文件（夹）不为空");
                    return;
                }
                mPresenter.downloadFile(mDownloadTargetPath, mDownloadOriginalPath);
                break;
            case R.id.webdav_upload_file_btn:
                String mUploadTargetPath = mUploadFileTargetEdit.getText().toString();
                String mUploadOriginalPath = mUploadFileEdit.getText().toString();
                Log.d(TAG, "mUploadTargetPath:" + mUploadTargetPath +
                        "  mUploadOriginalPath:" + mUploadOriginalPath);
                if (TextUtils.isEmpty(mUploadTargetPath)) {
                    mUploadFileTxt.setText("上传的文件（夹）不为空");
                    return;
                }
                mPresenter.uploadFile(mUploadTargetPath, mUploadOriginalPath);
                break;
            case R.id.webdav_delete_file_btn:
                String mDeletePath = mDeleteFileEdit.getText().toString();
                Log.d(TAG, "mDeletePath:" + mDeletePath);
                if (TextUtils.isEmpty(mDeletePath)) {
                    mDeleteFileTxt.setText("删除的文件（夹）不为空");
                    return;
                }
                mPresenter.deleteFile(mDeletePath);
                break;
        }
    }

    @Override
    public void onCreateWebDavSuccess() {
        Log.d(TAG, "==onCreateWebDavSuccess==");
        mCreateTxt.setText("初始化WebDav配置成功");
    }

    @Override
    public void onCreateWebDavFailed(int errorCode) {
        Log.e(TAG, "==onGetStoragePathFailed==");
        switch (errorCode) {
            case -1:
                Log.e(TAG, "create webdav failed");
                mCreateTxt.setText("初始化WebDav配置失败");
                break;
        }
    }

    @Override
    public void onListSuccess(List<DavResource> list) {
        Log.d(TAG, "==onListSuccess==");
        String listNames = "";
        for (DavResource res : list) {
            listNames = listNames + res + "\n";
        }

        Log.d(TAG, "listNames:" + listNames);
        mListTxt.setText(String.format("获取资源列表：\n %s", listNames));
    }

    @Override
    public void onListFailed(int errorCode) {
        Log.e(TAG, "==onListFailed==");
        switch (errorCode) {
            case -1:
                Log.e(TAG, "list failed");
                mListTxt.setText("获取资源列表失败");
                break;
        }
    }

    @Override
    public void onGetStoragePathSuccess(String path) {
        Log.d(TAG, "==onGetStoragePathSuccess==" + path);
        mGetStoragePathTxt.setText(String.format("获取到的硬盘路径：%s", path));
    }

    @Override
    public void onGetStoragePathFailed(int errorCode) {
        Log.e(TAG, "==onGetStoragePathFailed==");
        switch (errorCode) {
            case -1:
                Log.e(TAG, "storage path null");
                mGetStoragePathTxt.setText(String.format("获取到的硬盘路：%s", null));
                break;
        }
    }

    @Override
    public void onExistSuccess(boolean isExist) {
        Log.d(TAG, "==onExistSuccess==");
        mIsExistedTxt.setText(isExist ? "存在该文件" : "不存在该文件");
    }

    @Override
    public void onExistFailed(int errorCode) {
        Log.e(TAG, "==onExistFailed==");
        switch (errorCode) {
            case -1:
                Log.e(TAG, "exist failed");
                mIsExistedTxt.setText("判断是否存在失败");
                break;
        }
    }

    @Override
    public void onCreateDirectorySuccess() {
        Log.d(TAG, "==onCreateDirectorySuccess==");
        mCreateDirTxt.setText("成功创建文件夹");
    }

    @Override
    public void onCreateDirectoryFailed(int errorCode) {
        Log.e(TAG, "==onCreateDirectoryFailed==");
        switch (errorCode) {
            case -1:
                Log.e(TAG, "create dir failed");
                mCreateDirTxt.setText("创建文件夹失败");
                break;
            case -2:
                Log.e(TAG, "already existed dir");
                mCreateDirTxt.setText("已经存在该文件夹，无需创建");
                break;
        }
    }

    @Override
    public void onDownloadFileSuccess() {
        Log.d(TAG, "==onDownloadFileSuccess==");
        mDownloadFileTxt.setText("成功下载文件（夹）");
    }

    @Override
    public void onDownloadFileFailed(int errorCode) {
        Log.e(TAG, "==onDownloadFileFailed==");
        switch (errorCode) {
            case -1:
                Log.e(TAG, "dowload file/dir failed");
                mDownloadFileTxt.setText("下载文件（夹）失败");
                break;
        }
    }

    @Override
    public void onUploadFileSuccess() {
        Log.d(TAG, "==onUploadFileSuccess==");
        mUploadFileTxt.setText("成功上传文件（夹）");
    }

    @Override
    public void onUploadFileFailed(int errorCode) {
        Log.e(TAG, "==onUploadFileFailed==");
        switch (errorCode) {
            case -1:
                Log.e(TAG, "upload file/dir failed");
                mUploadFileTxt.setText("上传文件（夹）失败");
                break;
        }
    }

    @Override
    public void onDeleteFileSuccess() {
        Log.d(TAG, "==onDeleteFileSuccess==");
        mDeleteFileTxt.setText("成功删除文件（夹）");
    }

    @Override
    public void onDeleteFileFailed(int errorCode) {
        Log.e(TAG, "==onDeleteFileFailed==");
        switch (errorCode) {
            case -1:
                Log.e(TAG, "delete file/dir failed");
                mDeleteFileTxt.setText("删除文件（夹）失败");
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.stop();
        }
    }
}
