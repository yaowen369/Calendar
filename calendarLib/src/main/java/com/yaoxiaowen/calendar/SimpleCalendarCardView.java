package com.yaoxiaowen.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * 简单的不带农历的日历控件
 * Created by huanghaibin on 2017/11/15.
 */

public class SimpleCalendarCardView extends BaseCalendarCardView {
    
    public static final String TAG = "SimpleCalendarCardView";

    private int mRadius;

    public SimpleCalendarCardView(Context context) {
        super(context);
    }

    //Todo yaowen add
    public SimpleCalendarCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;
    }

    @Override
    protected void onLoopStart(int x, int y) {

    }

    @Override
    protected void onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        canvas.drawCircle(cx, cy, mRadius, mSchemePaint);
    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        float baselineY = mTextBaseLine + y;
        int cx = x + mItemWidth / 2;

        //Todo debugInfo
        StringBuilder sb = new StringBuilder();
        sb.append("onDrawText()-> cx=" + cx + "  baselineY=" + baselineY + "  calendar=" + calendar);

        if (hasScheme) {
           sb.append("  ---1111---- ");
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);

        } else {
            sb.append("  ---2222---- ");
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);
        }


        // YaoWen(43194) modify  at 2018/1/3 21:16
        //绘制农历，后来新添加
        canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 5 + 10, mCurMonthLunarTextPaint);

        LogUtils.i(TAG, sb.toString());
    }//end of "onDrawText(...)"
}
