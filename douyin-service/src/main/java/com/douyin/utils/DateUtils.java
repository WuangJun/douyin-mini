package com.douyin.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Author:WJ
 * Date:2023/2/19 0:51
 * Description:<>
 */


public class DateUtils {

    /**
     * long型转换为日期时间型
     *
     * @param longtime
     * @return
     */
    public static Date getLong2Date(long longtime) throws ParseException {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //long转Date
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sd.format(new Date(longtime)));
        return date;

    }

    public static Long getDate2Long(Date date) throws ParseException {
        String dateString = getcst(date);
        return getLongDate(dateString);
    }
    /**
     * 将cst时间格式转化为正常日期
     *
     * @param da
     * @return
     * @throws ParseException
     */
    public static String getcst(Date da) throws ParseException {
        SimpleDateFormat sim1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        SimpleDateFormat sim2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s1 = String.valueOf(da);
        Date dates = sim1.parse(s1);
        String f = sim2.format(dates);
        return f;

    }

    /**
     * 日期类型转换为long型
     *
     * @param formatTime
     * @return
     */
    public static Long getLongDate(String formatTime) {
        long time = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parse = sdf.parse(formatTime);

            time = parse.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * string转date类型
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date StringToDate(String str) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = (Date) formatter.parse(str);
        return date;
    }

    /**
     * 获取当前日期时间戳
     *
     * @return
     */
    public static long curTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取前一天日期，格式为20220815
     * @return
     */
    public static String getYesterdayDateString() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMdd");

        return dateFormat.format(calendar.getTime());
    }

    /**
     * 获取当前时间的整点的时间戳（毫秒）
     * @return
     */
    public static Long getIntegralPoint(){
        Date date = new Date();
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND,0);
        date = ca.getTime();
        return date.getTime();
    }
    /**
     * 获取当天时间的0点的时间戳（毫秒）
     * @return
     */
    public static Long getTodayZero(){
        Date date = new Date();
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DECEMBER,0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND,0);
        date = ca.getTime();
        return date.getTime();
    }
    /**
     * 获取当天时间的0点的时间戳（秒）
     * @return
     */
    public static Long getTodayZeroMiao(){
        Date date = new Date();
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DECEMBER,0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
//        ca.set(Calendar.MILLISECOND,0);
        long timeInMillis = ca.getTimeInMillis();
        return (timeInMillis/1000);
    }
    /**
     *格式化时间   时分秒毫秒均变为0
     * @param dates 传入的时间
     * @return
     */
    public static Long getTodayZero(Date dates){
        Calendar ca = Calendar.getInstance();
        ca.setTime(dates);
        ca.set(Calendar.DECEMBER,0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND,0);
        dates = ca.getTime();
        return dates.getTime();
    }

}
