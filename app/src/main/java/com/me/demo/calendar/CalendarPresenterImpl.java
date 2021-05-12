package com.me.demo.calendar;

import android.content.Context;
import android.widget.Toast;

import com.me.demo.base.BasePresenter;
import com.me.demo.util.AlarmManagerUtils;

/**
 * Create by lzf on 5/12/21
 */
public class CalendarPresenterImpl extends BasePresenter<ICalendarContract.ICalendarView> {

    private AlarmManagerUtils alarmManagerUtils;

    public void start(Context context) {
        alarmManagerUtils = AlarmManagerUtils.getInstance(context);
    }

    public void createAlarmManager(int requestCode) {
        alarmManagerUtils.createAlarmManager(requestCode);
    }

    /**
     * 设置日历提醒
     */
    public void orderCalendar(Context context, int minute) {
        alarmManagerUtils.alarmManagerStartWork(minute);
        Toast.makeText(context, "设置成功:" + minute, Toast.LENGTH_SHORT).show();
    }
}
