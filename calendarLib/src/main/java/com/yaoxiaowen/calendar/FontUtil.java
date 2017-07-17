package com.yaoxiaowen.calendar;

import android.graphics.Paint;

/**
 * YaoWen(43194) create at tongcheng work pc,
 * time:  2017/7/17 20:50  qq:2669932513
 */
public class FontUtil {

    /**
     * 返回在 paint画笔下, str 的长度
     * @param paint
     * @param str
     * @return
     */
    public static float getFontLength(Paint paint, String str){
        return paint.measureText(str);
    }

    /**
     * 返回 制定笔 的文字高度
     * @param paint
     * @return
     */
    public static float getFontHeight(Paint paint){
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.descent - fm.ascent;
    }

    /**
     * 返回 制定笔 离文字顶部的基准距离
     * 不过根据博客，这个地方不一定正确
     * http://mikewang.blog.51cto.com/3826268/871765/
     * @param paint
     * @return
     */
    public static float getFontLeading(Paint paint){
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.leading - fm.ascent;
    }
}
