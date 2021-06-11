package com.me.demo.main;

import android.util.Log;

import com.me.demo.R;
import com.me.demo.base.BaseApplication;
import com.me.demo.base.BasePresenter;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Create by lzf on 2021-03-30
 */
public class MainPresenterImpl extends BasePresenter<IMainContract.IMainView> {
    private String TAG = getClass().getSimpleName();

    public void start() {
        IMainContract.IMainView view = getView();
        view.updateMainRecycler(initItemData());
    }

    private LinkedList<String> initItemData() {
        String[] mItemArray = BaseApplication.getInstance().
                getResources().getStringArray(R.array.main_item);
        Log.d(TAG, "initItemData:" + Arrays.toString(mItemArray));
        return new LinkedList<>(Arrays.asList(mItemArray));
    }
}
