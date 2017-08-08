package com.yaoxiaowen.demo;


import com.yaoxiaowen.calendar.CustomCalendarView;
import com.yaoxiaowen.calendar.Helper;
import com.yaoxiaowen.demo.base.BaseActivity;
import com.yaoxiaowen.demo.log.LogUtils;
import com.yaoxiaowen.demo.log.ToastUtils;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

//    List<Helper.DayFinish>
    private List<Helper.DayFinish> list = new ArrayList<>();
    private CustomCalendarView cal;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        list.add(new Helper.DayFinish(1,2,2));
        list.add(new Helper.DayFinish(2,1,2));
        list.add(new Helper.DayFinish(3,0,2));
        list.add(new Helper.DayFinish(4,2,2));
        list.add(new Helper.DayFinish(5,2,2));
        list.add(new Helper.DayFinish(6,2,2));
        list.add(new Helper.DayFinish(7,2,2));
        list.add(new Helper.DayFinish(8,0,2));
        list.add(new Helper.DayFinish(9,1,2));
        list.add(new Helper.DayFinish(10,2,2));
        list.add(new Helper.DayFinish(11,5,2));
        list.add(new Helper.DayFinish(12,2,2));
        list.add(new Helper.DayFinish(13,2,2));
        list.add(new Helper.DayFinish(14,3,2));
        list.add(new Helper.DayFinish(15,2,2));
        list.add(new Helper.DayFinish(16,1,2));
        list.add(new Helper.DayFinish(17,0,2));
        list.add(new Helper.DayFinish(18,2,2));
        list.add(new Helper.DayFinish(19,2,2));
        list.add(new Helper.DayFinish(20,0,2));
        list.add(new Helper.DayFinish(21,2,2));
        list.add(new Helper.DayFinish(22,1,2));
        list.add(new Helper.DayFinish(23,2,0));
        list.add(new Helper.DayFinish(24,0,2));
        list.add(new Helper.DayFinish(25,2,2));
        list.add(new Helper.DayFinish(26,2,2));
        list.add(new Helper.DayFinish(27,2,2));
        list.add(new Helper.DayFinish(28,2,2));
        list.add(new Helper.DayFinish(29,2,2));
        list.add(new Helper.DayFinish(30,2,2));
        list.add(new Helper.DayFinish(31,2,2));

    }

    @Override
    protected void initView() {
        cal = (CustomCalendarView) findViewById(R.id.cal);


        cal.setRenwu("2017年1月", list);
    }

    @Override
    protected void initListener() {
        cal.setOnClickListener(new CustomCalendarView.OnCalendarClickListener() {
            @Override
            public void onLeftRowClick() {
                ToastUtils.showToast(getBaseContext(), "点击 左箭头");
                LogUtils.i(TAG, "点击 左箭头");
            }

            @Override
            public void onRightRowClick() {
                ToastUtils.showToast(getBaseContext(), "点击 右箭头");
                LogUtils.i(TAG, "点击 右箭头");
            }

            @Override
            public void onTitleClick(String monthStr, Date month) {
                ToastUtils.showToast(getBaseContext(), "点击 title -> monthStr=" + monthStr
                        + "\t month=" + month);
                LogUtils.i(TAG, "点击 title -> monthStr=" + monthStr
                        + "\t month=" + month);
            }

            @Override
            public void onWeekClick(int weekIndex, String weekStr) {
                ToastUtils.showToast(getBaseContext(), "点击 周, weekIndex=" + weekIndex + "\t weekStr=" + weekStr);
                LogUtils.i(TAG, "点击 周, weekIndex=" + weekIndex + "\t weekStr=" + weekStr);
            }

            @Override
            public void onDayClick(int day, String dayStr, Helper.DayFinish finish) {
                ToastUtils.showToast(getBaseContext(), "点击 日期, day=" +day
                        + "\t dayStr=" + dayStr + "\t finish=" + finish);
                LogUtils.i(TAG,  "点击 日期, day=" +day
                        + "\t dayStr=" + dayStr + "\t finish=" + finish);
            }
        });
    }
}
