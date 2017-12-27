package com.yaoxiaowen.calendar;

/**
 * Created by Administrator on 2017/7/19.
 */


/**
 * YaoWen(43194) modify  at 2017/12/27 20:42
 *
 * 从头开始
 */
@Deprecated
public class Helper {



    public static class DayFinish{
        int day;
        int all;
        int finish;
        public DayFinish(int day, int finish, int all) {
            this.day = day;
            this.all = all;
            this.finish = finish;
        }

        @Override
        public String toString() {
            return "DayFinish{" +
                    "day=" + day +
                    ", all=" + all +
                    ", finish=" + finish +
                    '}';
        }
    }
}
