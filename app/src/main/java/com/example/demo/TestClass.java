package com.example.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by guozhk on 16-5-4.
 */
public class TestClass {
    public static void main(String[] args) {


        String s1 = "凉都大道004,141,多边形,104.856211;104.856395;104.856467;104.856593;104.856669;104.856768;104.856813;104.856836;104.856809;104.856795;104.856791;104.856764;104.856755;104.856741;104.856732;104.856692;104.856692;104.856647;104.856616;104.856584,26.586112;26.586084;26.58608;26.586047;26.586039;26.586023;26.586011;26.585991;26.58593;26.585865;26.585829;26.585765;26.585724;26.585684;26.585639;26.585542;26.585498;26.585413;26.585345;26.585304,0&钟山大道,137,多边形,104.881303;104.881663;104.881937;104.882116;104.882381;104.882022;104.881748;104.881474;104.881366;104.881366,26.580149;26.579394;26.578922;26.578635;26.578288;26.578897;26.57939;26.579927;26.580194;26.580194,0\n";
        System.out.println(s1.contains("&"));
        String[] r = s1.split("\\&");
        System.out.println(r[0]);
        for (int i = 0; i < r.length; i++) {
            String[] rr = r[0].split("\\,");
            for (int j = 0; j < rr.length; j++) {

                System.out.println("r[" + j + "]:" + rr[j]);
            }
            System.out.println("====================");
        }
    }

    /**
     * 毫秒值变String时间值
     */
    public static String millis2Time(long millis) {
        Date date = new Date(millis);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String t = sdf.format(date);
        return t;
    }

    /**
     * String时间值变毫秒值
     */
    public static long time2Millis(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millis = c.getTimeInMillis();
        return millis;
    }

    private static long toLong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        long timeStemp = 0;
        try {
            date = simpleDateFormat.parse("2010-06-5 00:00:00");
            timeStemp = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(timeStemp);
        return timeStemp;
    }

    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String date = sdf.format(new Date(Long.valueOf(seconds)));
        System.out.println(date);
        return date;
    }
}
