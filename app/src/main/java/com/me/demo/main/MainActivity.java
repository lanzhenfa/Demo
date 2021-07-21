package com.me.demo.main;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.me.demo.R;
import com.me.demo.base.BaseActivity;
import com.me.demo.base.BaseFragment;
import com.me.demo.calendar.CalendarFragment;
import com.me.demo.database.DatabaseFragment;
import com.me.demo.http.HttpFragment;
import com.me.demo.okhttp.OkHttpFragment;
import com.me.demo.surfaceview.SurfaceFragment;
import com.me.demo.udp.client.UdpClientFragment;
import com.me.demo.udp.server.UdpServerFragment;
import com.me.demo.util.Config;
import com.me.demo.webdav.WebDavFragment;
import com.me.demo.websocket.WebSocketFragment;

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

        int firstAndLastColumnW = 15;
        int firstRowTopMargin = 15;
        MainGridItemDecoration mItemDecoration = new MainGridItemDecoration(
                this, firstAndLastColumnW, firstRowTopMargin, firstRowTopMargin);
        mItemDecoration.setFirstRowTopMargin(firstRowTopMargin);
        mItemDecoration.setLastRowBottomMargin(firstRowTopMargin);
        mMainRecycler.addItemDecoration(mItemDecoration);
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
        mMainItems.addAll(items);
        mMainAdapter.updateItemData(items);
        mMainAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMainItemClick(int position) {
        Log.d(TAG, "onMainItemClick position:" + position);
        switch (position) {
            case Config.FRAGMENT_TAG_CALENDAR:
                replaceFragment(new CalendarFragment());
                break;
            case Config.FRAGMENT_TAG_DATABASE:
                replaceFragment(new DatabaseFragment());
                break;
            case Config.FRAGMENT_TAG_WEBDAV:
                replaceFragment(new WebDavFragment());
                break;
            case Config.FRAGMENT_TAG_UDP_CLIENT:
                replaceFragment(new UdpClientFragment());
                break;
            case Config.FRAGMENT_TAG_UDP_SERVER:
                replaceFragment(new UdpServerFragment());
                break;
            case Config.FRAGMENT_TAG_WEBSOCKET:
                replaceFragment(new WebSocketFragment());
                break;
            case Config.FRAGMENT_TAG_HTTP:
                replaceFragment(new HttpFragment());
                break;
            case Config.FRAGMENT_TAG_OKHTTP:
                replaceFragment(new OkHttpFragment());
                break;
            case Config.FRAGMENT_TAG_SURFACE:
                replaceFragment(new SurfaceFragment());
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
