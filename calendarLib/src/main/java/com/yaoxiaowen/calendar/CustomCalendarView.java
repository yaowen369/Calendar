package com.yaoxiaowen.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Date;

/**
 * author：yaowen on 17/7/15 12:10
 * email：yaowen369@gmail.com
 * www.yaoxiaowen.com
 *
 *  参考博客:
 *  http://blog.csdn.net/xmxkf/article/details/51454685
 */

public class CustomCalendarView extends View {

    private static final String TAG = "CustomCalendarView";

    //各部分背景
    private @ColorInt int mBgMonth, mBgWeek, mBgDay, mBgPre;
    //标题的颜色,大小 等数据
    private int mTextColorMonth;
    private float mTextSizeMonth;
    private int mMonthRowL, mMonthRowR;
    private float mMonthRowSpac;
    private float mMonthSpac;

    //星期的颜色 大小
    private int mTextColorWeek;
    private float mTextSizeWeek;
    // 日期文本的颜色, 大小
    private int mTextColorDay;
    private float mTextSizeDay;
    //任务次数文本的颜色,大小
    private int mTextColorPreFinish, mTextColorPreUnFinish;
    private float mTextSizePre;
    //选中的文本的颜色
    private int mSelectTextColor;
    //选中背景
    private int mSelectBg, mCurrentBg;
    private float mSelectRadius, mCurrentBgStrokeWidth;
    private float[] mCurrentBgDashPath;

    //行间距
    private float mLineSpac;
    //字体上下间距
    private float mTextSpac;

    private Paint mPaint;
    private Paint bgPaing;

    private Float titleHeight, weekHeight, dayHeight, preHeight, oneHeight;
    private int columnWidth; //每列宽度

    private Date month;  //当前月份
    private boolean isCurrentMonth; //展示的月份是否是当前月
    private int currentDay, selectDay, lastSelectDay;   //当前日期, 选中日期, 上一次选中的 日期(避免造成重复的回调)

    private int dayOfMonth; //月份天数
    private int firstIndex; //当月第一天位置索引
    private int firstLineNum, lastLineNum; //第一行, 最后一行能展示多少日期
    private int lineNum; //日期行数
    private static final String[] WEEK_STR = new String[]{"Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat"};


    public CustomCalendarView(Context context) {
        this(context, null);
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取自定义属性
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomCalendarView, defStyleAttr, 0);

        mBgMonth = a.getColor(R.styleable.CustomCalendarView_mBgMonth, Color.TRANSPARENT);
        mBgWeek = a.getColor(R.styleable.CustomCalendarView_mBgWeek, Color.TRANSPARENT);
        mBgDay = a.getColor(R.styleable.CustomCalendarView_mBgDay, Color.TRANSPARENT);
        mBgPre = a.getColor(R.styleable.CustomCalendarView_mBgPre, Color.TRANSPARENT);


        mMonthRowL = a.getResourceId(R.styleable.CustomCalendarView_mMonthRowL, R.drawable.custom_calendar_row_left);
        mMonthRowR = a.getResourceId(R.styleable.CustomCalendarView_mMonthRowR, R.drawable.custom_calendar_row_right);
        mMonthRowSpac = a.getDimension(R.styleable.CustomCalendarView_mMonthRowSpac, 20);
        mTextColorMonth = a.getColor(R.styleable.CustomCalendarView_mTextColorMonth, Color.BLACK);
        mTextSizeMonth = a.getDimension(R.styleable.CustomCalendarView_mTextSizeMonth, 100);
        mMonthSpac = a.getDimension(R.styleable.CustomCalendarView_mMonthSpac, 20);
        mTextColorWeek = a.getColor(R.styleable.CustomCalendarView_mTextColorWeek, Color.BLACK);
        mTextSizeWeek = a.getDimension(R.styleable.CustomCalendarView_mTextSizeWeek, 70);
        mTextColorDay = a.getColor(R.styleable.CustomCalendarView_mTextColorDay, Color.GRAY);
        mTextSizeDay = a.getDimension(R.styleable.CustomCalendarView_mTextSizeDay, 70);
        mTextColorPreFinish = a.getColor(R.styleable.CustomCalendarView_mTextColorPreFinish, Color.BLUE);
        mTextColorPreUnFinish = a.getColor(R.styleable.CustomCalendarView_mTextColorPreUnFinsih, Color.BLUE);
        mTextSizePre = a.getDimension(R.styleable.CustomCalendarView_mTextSizePre, 40);
        mSelectTextColor = a.getColor(R.styleable.CustomCalendarView_mSelectTextColor, Color.YELLOW);
        mCurrentBg = a.getColor(R.styleable.CustomCalendarView_mCurrentBg, Color.GRAY);

        try {
            int dashPathId = a.getResourceId(R.styleable.CustomCalendarView_mCurrentBgDashPath, com.yaoxiaowen.calendar.R.array.customCalendar_currentDay_bg_DashPath);
            int[] array = getResources().getIntArray(dashPathId);

            mCurrentBgDashPath = new float[]{array.length};
            for (int i=0; i<array.length; i++){
                mCurrentBgDashPath[i] = array[i];
            }
        }catch (Exception e){
            e.printStackTrace();
            mCurrentBgDashPath = new float[]{2f, 3f, 2f, 3f};
        }

        mSelectBg = a.getColor(R.styleable.CustomCalendarView_mSelectBg, Color.YELLOW);
        mSelectRadius = a.getDimension(R.styleable.CustomCalendarView_mSelectRadius, 20);
        mCurrentBgStrokeWidth = a.getDimension(R.styleable.CustomCalendarView_mCurrentBgStrokeWidth, 5);
        mLineSpac = a.getDimension(R.styleable.CustomCalendarView_mLineSpac, 20);
        mTextSpac = a.getDimension(R.styleable.CustomCalendarView_mTextSpac, 20);

        a.recycle();

        initCompute();

    }//end of "CustomCalendarView("

    /**
     * 初始化相关常量, 构造方法中调用
     */
    private void initCompute(){

    }
}
