package com.me.demo.main;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.me.demo.R;
import com.me.demo.base.BaseActivity;
import com.me.demo.base.BaseFragment;
import com.me.demo.test.TestFragment;
import com.me.demo.util.Config;

import java.util.LinkedList;

public class MainActivity extends BaseActivity<IMainContract.IMainView, MainPresenterImpl>
        implements IMainContract.IMainView, IMainItemListener {

    private RecyclerView mMainRecycler;
    private MainRecyclerAdapter mMainAdapter;
    private LinkedList<String> mMainItems = new LinkedList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mMainRecycler = findViewById(R.id.activity_main_recycler);
        mMainRecycler.setLayoutManager(new GridLayoutManager(mContext, 4));
        mMainAdapter = new MainRecyclerAdapter(mMainItems);
        mMainRecycler.setAdapter(mMainAdapter);
        //默认显示列表
        updateMainView(true);

        if (mPresenter != null) {
            mPresenter.start();
        }
    }

    @Override
    protected void initListener() {
        mMainAdapter.registerMainItemListener(this);
    }

    @Override
    protected void unInitListener() {

    }

    @Override
    protected MainPresenterImpl initPresenter() {
        return new MainPresenterImpl();
    }

    @Override
    public void updateMainRecycler(LinkedList<String> items) {
        if (mMainAdapter == null) {
            Log.e(TAG, "main adapter null");
            return;
        }
        mMainAdapter.updateItemData(items);
        mMainAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMainItemClick(int position) {
        Log.d(TAG, "onMainItemClick position:" + position);
        switch (position) {
            case Config.FRAGMENT_TAG_TEST:
                replaceFragment(new TestFragment());
                break;
            default:
                break;
        }
    }

    private void replaceFragment(BaseFragment baseFragment) {
        FragmentTransaction mBeginTransaction = getSupportFragmentManager().beginTransaction();
        mBeginTransaction.replace(R.id.activity_main_fragment_container, baseFragment);
        mBeginTransaction.addToBackStack(null);
        mBeginTransaction.commit();
        //隐藏列表
        updateMainView(false);
    }

    private void updateMainView(boolean isShowRecycler) {
        if (mMainRecycler != null) {
            mMainRecycler.setVisibility(isShowRecycler ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        updateMainView(true);
    }
}
