package com.yaoxiaowen.calendar;


import java.io.Serializable;

/**
 * 日历对象、
 */
@SuppressWarnings("all")
public class Calendar implements Serializable {

    /**年*/
    private int year;

    /**月*/
    private int month;

    /**日*/
    private int day;

    /**是否是本月*/
    private boolean isCurrentMonth;

    /**是否是今天*/
    private boolean isCurrentDay;//

    /**农历*/
    private String lunar;

    /**计划，可以用来标记当天是否有任务*/
    private String scheme;

    /**各种自定义标记颜色、没有则选择默认颜色*/
    private int schemeColor;

    /**是否是周末*/
    private boolean isWeekend;

    /**星期*/
    private int week;

    public Calendar() {
    }

    public Calendar(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isCurrentMonth() {
        return isCurrentMonth;
    }


    public void setCurrentMonth(boolean currentMonth) {
        this.isCurrentMonth = currentMonth;
    }

    public boolean isCurrentDay() {
        return isCurrentDay;
    }

    public void setCurrentDay(boolean currentDay) {
        isCurrentDay = currentDay;
    }


    public String getLunar() {
        return lunar;
    }

    public void setLunar(String lunar) {
        this.lunar = lunar;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public int getSchemeColor() {
        return schemeColor;
    }

    public void setSchemeColor(int schemeColor) {
        this.schemeColor = schemeColor;
    }

    public boolean isWeekend() {
        return isWeekend;
    }

    public void setWeekend(boolean weekend) {
        isWeekend = weekend;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Calendar) {
            if (((Calendar) o).getYear() == year && ((Calendar) o).getMonth() == month && ((Calendar) o).getDay() == day)
                return true;
        }
        return super.equals(o);
    }

    @Override
    public String toString() {
        return year + "" + (month < 10 ? "0" + month : month) + "" + (day < 10 ? "0" + day : day);
    }


    public String toComplexString() {
        return "Calendar{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", isCurrentMonth=" + isCurrentMonth +
                ", isCurrentDay=" + isCurrentDay +
                ", lunar='" + lunar + '\'' +
                ", scheme='" + scheme + '\'' +
                ", schemeColor=" + schemeColor +
                ", isWeekend=" + isWeekend +
                ", week=" + week +
                '}';
    }
}
