package com.yaoxiaowen.demo;


import com.yaoxiaowen.calendar.Calendar;
import com.yaoxiaowen.calendar.CalendarView;
import com.yaoxiaowen.demo.base.BaseActivity;
import com.yaoxiaowen.demo.log.Utils_Log;
import com.yaoxiaowen.demo.log.Utils_Toast;


public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private CalendarView mCalendarView;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initView() {
        mCalendarView = (CalendarView) findViewById(R.id.myCalendarView);

    }

    @Override
    protected void initListener() {
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onDateChange(Calendar calendar) {
                Utils_Log.i(TAG, "OnDateChangeListener() -> onDateChange() -> calendar " + calendar);
                Utils_Toast.showToast(MainActivity.this, "onDateChange() -> calendar " + calendar);
            }

            @Override
            public void onYearChange(int year) {
                Utils_Log.i(TAG, "OnDateChangeListener() -> onYearChange() -> year " + year);
                Utils_Toast.showToast(MainActivity.this, "onYearChange() -> year " + year);
            }
        });


        mCalendarView.setOnDateSelectedListener(new CalendarView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Calendar calendar) {
                Utils_Log.i(TAG, "OnDateSelectedListener() -> onDateSelected() -> calendar " + calendar);
                Utils_Toast.showToast(MainActivity.this, "onDateSelected() -> calendar " + calendar);
            }
        });
    }//end of "initListener(..."
}
