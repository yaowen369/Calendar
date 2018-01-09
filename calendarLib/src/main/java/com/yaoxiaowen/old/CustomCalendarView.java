package com.yaoxiaowen.old;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.yaoxiaowen.calendar.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * author：yaowen on 17/7/15 12:10
 * email：yaowen369@gmail.com
 * www.yaoxiaowen.com
 *
 *  参考博客:
 *  http://blog.csdn.net/xmxkf/article/details/51454685
 */


/**
 * YaoWen(43194) modify  at 2017/12/27 20:42
 *
 * 从头开始
 */
@Deprecated
class CustomCalendarView extends View {

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
    private Paint bgPaint;

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
        mTextColorPreUnFinish = a.getColor(R.styleable.CustomCalendarView_mTextColorPreUnFinish, Color.BLUE);
        mTextSizePre = a.getDimension(R.styleable.CustomCalendarView_mTextSizePre, 40);
        mSelectTextColor = a.getColor(R.styleable.CustomCalendarView_mSelectTextColor, Color.YELLOW);
        mCurrentBg = a.getColor(R.styleable.CustomCalendarView_mCurrentBg, Color.GRAY);

        try {
            int dashPathId = a.getResourceId(R.styleable.CustomCalendarView_mCurrentBgDashPath, com.yaoxiaowen.calendar.R.array.customCalendar_currentDay_bg_DashPath);
            int[] array = getResources().getIntArray(dashPathId);

            //debug info
            StringBuilder sb = new StringBuilder();
            sb.append("lenth=" + array.length + "\t");
            for (int i=0;i <array.length;i++){
                sb.append(" " + i + "=" + array[i] + "\t ");
            }
            Log.i(TAG, sb.toString());

            mCurrentBgDashPath = new float[array.length];
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
        mPaint = new Paint();
        bgPaint = new Paint();
        mPaint.setAntiAlias(true);   //抗锯齿
        bgPaint.setAntiAlias(true);  //抗锯齿

        map = new HashMap<>();

        //标题高度
        mPaint.setTextSize(mTextSizeMonth);
        titleHeight = FontUtil.getFontHeight(mPaint) + 2 * mMonthSpac;
        //星期高度
        mPaint.setTextSize(mTextSizeWeek);
        weekHeight = FontUtil.getFontHeight(mPaint);
        //日期高度
        mPaint.setTextSize(mTextSizeDay);
        dayHeight = FontUtil.getFontHeight(mPaint);
        //次数字体高度
        mPaint.setTextSize(mTextSizePre);
        preHeight = FontUtil.getFontHeight(mPaint);

        //每行高度 = 行间距 + 日期字体高度 + 字间距 + 次数字体高度
        oneHeight = mLineSpac + dayHeight + mTextSpac + preHeight;

        //默认当前月份
        String cDateStr = getMonthStr(new Date());
        setMonth(cDateStr);
    }

    private void setMonth(String Month){
        //在initCompute调用该方法时，就调用了一遍，这里岂不是 又重新调用了一遍
        month = str2Date(Month);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        //获取今天是多少号
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        Date cM = str2Date(getMonthStr(new Date()));
        //判断是否为当月
        if (cM.getTime() == month.getTime()) {
            isCurrentMonth = true;
            selectDay = currentDay;
        }else {
            isCurrentMonth = false;
            selectDay = 0;
        }

        Log.i(TAG, "设置月份:" + month + "\t今天:" + currentDay + "号,是否为当前月 :"  + isCurrentMonth);
        calendar.setTime(month);

        dayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        //第一行 1号显示在什么位置 (星期几)
        firstIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        lineNum = 1;
        //第一行能展示的天数
        firstLineNum = 7 - firstIndex;
        lastLineNum = 0;
        int shengyu = dayOfMonth - firstLineNum;
        while (shengyu>7){
            lineNum++;
            shengyu -= 7;
        }

        if (shengyu > 0){
            lineNum++;
            lastLineNum = shengyu;
        }

        StringBuilder debugSb = new StringBuilder();
        debugSb.append(getMonthStr(month) + "一共有" + dayOfMonth + "天，第一天的索引是:" + firstIndex )
                .append(", 有" + lineNum + "行")
                .append(", 第一行" + firstLineNum + "个")
                .append(", 最后一行" + lastLineNum + "个");

        Log.i(TAG, debugSb.toString());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, "onMeasure() 开始测量过程 ");
        //宽度，填充父窗体
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        columnWidth = widthSize/7;

        //高度 = 标题高度+星期高度+日期行数+每行高度
        float height = titleHeight + weekHeight + (lineNum*oneHeight);
        int actualWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);

        //debug 信息
        StringBuilder debugSb = new StringBuilder();
        debugSb.append("标题高度:" + titleHeight)
                .append("\t 星期高度:" + weekHeight)
                .append("\t 每行高度:" + oneHeight)
                .append("\t 行数:" + lineNum)
                .append("\t 控件高度:" + height)
                .append("\t 控件宽度:" + MeasureSpec.toString(actualWidth))
                .append("\t 每行宽度:" + columnWidth);
        Log.i(TAG, debugSb.toString());

        setMeasuredDimension(actualWidth,
                (int)height);
        Log.i(TAG, "onMeasure() 测量过程 已经结束了 ");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG, "onDraw() 开始 draw 过程");
        drawMonth(canvas);
        Log.i(TAG, "onDraw() drawMonth 已经走完了");
        drawWeek(canvas);
        Log.i(TAG, "onDraw() drawWeek 已经走完了");
        drawDayAndPre(canvas);
        Log.i(TAG, "onDraw() draw过程已经走完了");
    }

    //绘制月份
    private int rowLStart, rowRStart, rowWidth;
    private void drawMonth(Canvas canvas){
        //背景
        bgPaint.setColor(mBgMonth);
        RectF rect= new RectF(0, 0, getWidth(), titleHeight);
        canvas.drawRect(rect, bgPaint);

        //绘制月份
        mPaint.setTextSize(mTextSizeMonth);
        mPaint.setColor(mTextColorMonth);
        float textLen = FontUtil.getFontLength(mPaint, getMonthStr(month));
        float textStart = (getWidth() - textLen) / 2;
        canvas.drawText(getMonthStr(month), textStart, mMonthSpac+FontUtil.getFontLeading(mPaint), mPaint);

        //绘制左右箭头
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mMonthRowL);
        int h = bitmap.getHeight();
        rowWidth = bitmap.getWidth();
        //float left, float top
        rowLStart = (int)(textStart - 2*mMonthRowSpac - rowWidth);
        canvas.drawBitmap(bitmap, rowLStart+mMonthRowSpac, (titleHeight-h)/2, new Paint());

        bitmap = BitmapFactory.decodeResource(getResources(), mMonthRowR);
        rowRStart = (int)(textStart + textLen);
        canvas.drawBitmap(bitmap, rowRStart+mMonthRowSpac, (titleHeight-h)/2, new Paint());

    }

    //绘制星期
    private void drawWeek(Canvas canvas){
        //背景
        bgPaint.setColor(mBgWeek);
        RectF rect = new RectF(0, titleHeight, getWidth(), titleHeight+weekHeight);
        canvas.drawRect(rect, bgPaint);

        //绘制星期:  七天
        mPaint.setTextSize(mTextSizeWeek);
        mPaint.setColor(mTextColorWeek);
        for (int i=0; i<WEEK_STR.length; i++){
            int len = (int)FontUtil.getFontLength(mPaint, WEEK_STR[i]);
            int x = i*columnWidth + (columnWidth - len) / 2;
            canvas.drawText(WEEK_STR[i], x, titleHeight+FontUtil.getFontLeading(mPaint), mPaint);
        }

    }

    //绘制日期和次数
    private void drawDayAndPre(Canvas canvas){
        float top = titleHeight + weekHeight;
        //行
        for (int line=0; line<lineNum; line++){
            if (line == 0){
                //第一行
                drawDayAndPre(canvas, top, firstLineNum, 0, firstIndex);
            }else if (line == lineNum-1){
                //最后一行
                top += oneHeight;
                drawDayAndPre(canvas, top, lastLineNum, firstLineNum+(line-1)*7, 0);
            }else {
                //满行
                top += oneHeight;
                drawDayAndPre(canvas, top, 7, firstLineNum+(line-1)*7, 0);
            }
        }
    }

    /**
     * 绘制某一行的日期
     * @param canvas
     * @param top 顶部坐标
     * @param count   此行需要绘制的日期数量(不一定都是七天)
     * @param overDay  已经绘制过的日期，从 overDay+1 开始绘制
     * @param startIndex  此行第一个日期的星期索引
     */
    private void drawDayAndPre(Canvas canvas, float top, int count, int overDay, int startIndex){
        //deubg info
        StringBuilder debugSb = new StringBuilder();
        debugSb.append("总共" + dayOfMonth + "天")
                .append("\t 有" + lineNum +"行")
                .append("\t 已经绘制了" + overDay + "天")
                .append("\t 下面绘制" + count + "天");
        Log.i(TAG, debugSb.toString());

        //背景
        float topPre = top + mLineSpac + dayHeight;
        bgPaint.setColor(mBgDay);
        RectF rectF = new RectF(0, top, getWidth(), topPre);
        canvas.drawRect(rectF, bgPaint);

        bgPaint.setColor(mBgPre);
        rectF = new RectF(0, topPre, getWidth(), topPre + mTextSpac + dayHeight);
        canvas.drawRect(rectF, bgPaint);

        mPaint.setTextSize(mTextSizeDay);
        float dayTextLeading = FontUtil.getFontLeading(mPaint);
        mPaint.setTextSize(mTextSizePre);
        float preTextLeading = FontUtil.getFontLeading(mPaint);

        for (int i=0; i<count; i++){
            int left = (startIndex + i) * columnWidth;
            int day = (overDay +i + 1);

            mPaint.setTextSize(mTextSizeDay);

            //如果是当前月，当天日期，要做特殊处理
            if (isCurrentMonth && currentDay==day){
                mPaint.setColor(mTextColorDay);
                bgPaint.setColor(mCurrentBg);
                bgPaint.setStyle(Paint.Style.STROKE);  //空心
                PathEffect effect = new DashPathEffect(mCurrentBgDashPath, 1);
                bgPaint.setPathEffect(effect);  //设置曲线画笔间隔
                bgPaint.setStrokeWidth(mCurrentBgStrokeWidth);  //画笔宽度
                //绘制空心圆背景
                canvas.drawCircle(left+columnWidth/2, top+mLineSpac+dayHeight/2,
                        mSelectRadius-mCurrentBgStrokeWidth, bgPaint);

            }// end of 当天日期的处理

            //绘制后将画笔还原，避免脏笔
            bgPaint.setPathEffect(null);
            bgPaint.setStrokeWidth(0);
            bgPaint.setStyle(Paint.Style.FILL);

            //选中的日期，如果是本月，选中日期正好是当天日期，下面的背景会覆盖上面绘制的虚线背景
            if (selectDay == day){
                //选中的日期 字体白色，橙色背景
                mPaint.setColor(mSelectTextColor);
                bgPaint.setColor(mSelectBg);

                //绘制橙色圆背景，参数1：中心点X轴， 参数2：中心点Y轴， 参数三：半径 ， 参数四：paint对象
                canvas.drawCircle(left+columnWidth/2, top+mLineSpac+dayHeight/2, mSelectRadius, bgPaint);
            }else {
                mPaint.setColor(mTextColorDay);
            }

            int len = (int)FontUtil.getFontLength(mPaint, day+"");
            int x = left + (columnWidth - len) / 2;
            canvas.drawText(day+"", x, top+mLineSpac+dayTextLeading, mPaint);

            //绘制次数
            mPaint.setTextSize(mTextSizePre);
            Helper.DayFinish finish = map.get(day);
            String preStr = "0/0";
            if (finish != null){
                //区分完成 未完成
                if (finish.finish >= finish.all){
                    mPaint.setColor(mTextColorPreFinish);
                }else {
                    mPaint.setColor(mTextColorPreUnFinish);
                }
                preStr = finish.finish + "/" + finish.all;
            }else {
                mPaint.setColor(mTextColorPreUnFinish);
            }

            len = (int)FontUtil.getFontLength(mPaint, preStr);
            x = left + (columnWidth - len) / 2;
            canvas.drawText(preStr, x, topPre+mTextSpac+preTextLeading, mPaint);

        }
    }//end of "  private void drawDayAndPre(Canvas canvas, float top, int count...  "


    //获取月份标题
    private String getMonthStr(Date month){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
        return sdf.format(month);
    }

    private Date str2Date(String str){
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月");
            return df.parse(str);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /****************************事件处理↓↓↓↓↓↓↓****************************/
    //焦点坐标
    private PointF focusPoint = new PointF();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        focusPoint.set(event.getX(), event.getY());
        switch (action){
            case MotionEvent.ACTION_DOWN:
                touchFocusMove(focusPoint, false);
                break;
            case MotionEvent.ACTION_MOVE:
                touchFocusMove(focusPoint, false);
                break;
            case MotionEvent.ACTION_OUTSIDE:  //??? 为什么outside和 cancel 也需要用呢
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                touchFocusMove(focusPoint, true);
                bringToFront();
        }
        return true;
    }

    //--   焦点滑动  --//
    public void touchFocusMove(final PointF point, boolean eventEnd){
        Log.i(TAG, "点击坐标: " + point + ", 事件是否结束 : " + eventEnd);

        if (listener == null){
            return;
        }

        // 标题和星期只有在事件结束后才响应
        if (point.y <= titleHeight){
            //事件在标题上
            if (eventEnd){
                if (point.x>=rowLStart && point.x<(rowLStart+2*mMonthRowSpac+rowWidth)){
                    Log.i(TAG, "点击左箭头");
                    listener.onLeftRowClick();
                }else if (point.x>rowRStart && point.x<(rowRStart + 2*mMonthRowSpac+rowWidth)){
                    Log.i(TAG, "点击右箭头");
                    listener.onRightRowClick();
                }else if (point.x>rowLStart && point.x<rowRStart){
                    listener.onTitleClick(getMonthStr(month), month);
                }
            }
        }else if (point.y <= (titleHeight+weekHeight)){
            //事件在星期部分
            if (eventEnd){
                // 根据X坐标找到 具体的焦点日期
                int xIndex = (int)(point.x/columnWidth);
                Log.i(TAG, "列宽:" + columnWidth + "\t 坐标余数" + xIndex);
                //Todo 这个计算貌似不太对
                if ((point.x/columnWidth-xIndex) > 0){
                    xIndex += 1;
                }
                listener.onWeekClick(xIndex-1, WEEK_STR[xIndex-1]);
            }
        }else {
            // 日期部分按下和滑动时重绘, 只有在事件结束后才相应
            touchDay(point, eventEnd);
        }
    }//end of "touchFocusMove()"

    //控制事件是否相应
    private boolean responseEnd = false;
    //事件点在 日期区域 范围内
    private void touchDay(final PointF point, boolean eventEnd){
        //根据Y坐标 找到焦点行
        boolean availability = false;  //事件是否 有效
        //日期部分
        float top = titleHeight+weekHeight + oneHeight;
        int foucsLine = 1;
        while (foucsLine <= lineNum){
            if (top >= point.y){
                availability = true;
                break;
            }
            top += oneHeight;
            foucsLine++;
        }

        if (availability){
            //根据X坐标 找到具体的焦点日期
            int xIndex = (int)(point.x / columnWidth);
            if ((point.x/columnWidth - xIndex) > 0){
                xIndex += 1;
            }
            Log.i(TAG, "列宽: " + columnWidth + "\t x坐标余数:" + (point.x/columnWidth));

            if (xIndex<=0){
                xIndex = 1; //避免调到 上一行 最后一个日期
            }

            if (xIndex>7){
                xIndex = 7;  //避免调到下一行第一个日期
            }

            if (foucsLine == 1){
                //第一行
                if (xIndex <= firstIndex){
                    Log.e(TAG, "点到了 空位置");

                    //下面的设置就看不懂了
                    setSelectedDay(selectDay, eventEnd);
                }else if (foucsLine == lineNum){
                    //最后一行
                    if (xIndex > lastLineNum){
                        Log.e(TAG, "点到结束空位了");
                        setSelectedDay(selectDay, true);
                    }else {
                        setSelectedDay(firstLineNum + (foucsLine-2)*7, eventEnd);
                    }
                }else {
                    setSelectedDay(firstLineNum + (foucsLine-2)*7+xIndex, eventEnd);
                }
            }
        }else {
            //超出日期区域后,视为事件结束, 响应最后一个选择日期的回调
            setSelectedDay(selectDay, true);
        }

    }// end of "touchDay()..."

    private void setSelectedDay(int day, boolean eventEnd){
        Log.i(TAG, "选中 " +day + ", 事件是否结束 " +eventEnd );

        selectDay = day;
        invalidate();
        if (listener!=null && eventEnd && responseEnd && lastSelectDay!=selectDay){
            lastSelectDay = selectDay;
            listener.onDayClick(selectDay, getMonthStr(month)+selectDay + "日", map.get(selectDay));
        }
        responseEnd = !eventEnd;
    }
    /***********************事件处理  ↑↑↑↑↑↑↑**************************/


    /***********************接口API↓↓↓↓↓↓↓**************************/
    private Map<Integer, Helper.DayFinish> map;
    public void setRenwu(String month, List<Helper.DayFinish> list){
        setMonth(month);

        if (list!=null && list.size()>0){
            map.clear();
            for (Helper.DayFinish finish : list){
                map.put(finish.day, finish);
            }
        }
        invalidate();
    }// end of "setRenwu()"



    //点击事件
    private OnCalendarClickListener listener = null;
    public void setOnClickListener(OnCalendarClickListener listenr){
        this.listener = listenr;
    }

    public interface OnCalendarClickListener {
        void onLeftRowClick();
        void onRightRowClick();
        void onTitleClick(String monthStr, Date month);
        void onWeekClick(int weekIndex, String weekStr);
        void onDayClick(int day, String dayStr, Helper.DayFinish finish);
    }

    /***********************接口API↑↑↑↑↑↑↑**************************/

}
