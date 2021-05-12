package com.me.demo.calendar;

import android.view.View;
import android.widget.Button;

import com.me.demo.R;
import com.me.demo.base.BaseFragment;

/**
 * Create by lzf on 5/12/21
 */
public class CalendarFragment extends BaseFragment<ICalendarContract.ICalendarView, CalendarPresenterImpl>
        implements View.OnClickListener {

    private Button mOrderBtn1;
    private Button mOrderBtn2;

    @Override
    protected int getContentView() {
        return R.layout.fragment_calendar;
    }

    @Override
    protected void initView(View view) {
        mOrderBtn1 = view.findViewById(R.id.calendar_order_btn1);
        mOrderBtn2 = view.findViewById(R.id.calendar_order_btn2);

        if (mPresenter != null) {
            mPresenter.start(mContext);
        }
    }

    @Override
    protected void initListener() {
        mOrderBtn1.setOnClickListener(this);
        mOrderBtn2.setOnClickListener(this);
    }

    @Override
    protected void unInitListener() {

    }

    @Override
    protected CalendarPresenterImpl initPresenter() {
        return new CalendarPresenterImpl();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.calendar_order_btn1:
                if (mPresenter != null) {
                    mPresenter.createAlarmManager(0);
                    mPresenter.orderCalendar(mContext, 22);
                }
                break;
            case R.id.calendar_order_btn2:
                if (mPresenter != null) {
                    mPresenter.createAlarmManager(1);
                    mPresenter.orderCalendar(mContext, 23);
                }
                break;
        }
    }
}
