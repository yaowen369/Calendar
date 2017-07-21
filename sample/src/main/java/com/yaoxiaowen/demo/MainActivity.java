package com.yaoxiaowen.demo;


import com.yaoxiaowen.calendar.CustomCalendarView;
import com.yaoxiaowen.calendar.Helper;
import com.yaoxiaowen.demo.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

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

    }
}
