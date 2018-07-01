package com.vvicey.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author nana
 * @Date 18-7-1 下午1:49
 * @Description 将日期转换为cron表达式
 */
public class QuartzCronDateUtils {

    /***
     *  将日期按照给定日期格式进行转换
     * @param date 要转换的日期
     * @param dateFormat : e.g:yyyy-MM-dd HH:mm:ss
     * @return 返回相应格式的字符串日期
     */
    private static String formatDateByPattern(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /***
     * 转换日期为cron表达式
     * @param date:时间点
     * @return 返回cron表达式
     */
    public static String getCron(Date date) {
        String dateFormat = "ss mm HH dd MM ? yyyy";
        return formatDateByPattern(date, dateFormat);
    }

}