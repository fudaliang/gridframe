/**
 * Project Name:gridapp-common
 * File Name:DateUtils.java
 * Package Name:com.softcloud.grid.common.utils
 * Date:2019年6月14日下午3:29:49
 * Copyright (c) 2019, softcloud All Rights Reserved.
 *
*/

package com.softcloud.grid.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName: DateUtils <br/>
 * Function: 时间转换工具 <br/>
 * @version
 * @since JDK 1.8
 */
public class DateUtils {

    private static final Logger log = LoggerFactory.getLogger(DateUtils.class);

    /**
     * timestampToString:时间戳转换为指定格式日期字符串 <br/>
     *
     * date: 2019年6月14日 下午3:31:52
     * 
     * @author juvensun
     * @param timestamp
     *            时间戳
     * @param pattern
     *            yyyy-MM-dd HH:mm:ss 24小时时间进制
     * @return 2019-06-14 15:45:23
     * @since JDK 1.8
     */
    public static String timestampToString(long timestamp, String pattern) {
        return new SimpleDateFormat(pattern).format(timestamp);
    }

    /**
     * strToDate:字符串转换为日期类型 <br/>
     *
     * date: 2019年7月2日 上午10:54:15
     * 
     * @author juvenbin
     * @param dateStr
     *            日期字符串
     * @param pattern
     *            日期格式
     * @return 日期
     * @since JDK 1.8
     */
    public static Date strToDate(String dateStr, String pattern) {
        Date date = null;
        try {
            date = new SimpleDateFormat(pattern).parse(dateStr);
        } catch (ParseException e) {
            log.error("日期转换错误", e);
        }
        return date;
    }
}
