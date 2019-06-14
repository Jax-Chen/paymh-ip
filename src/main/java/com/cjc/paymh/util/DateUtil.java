package com.cjc.paymh.util;

import com.cjc.paymh.controller.IndexController;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Date Utility Class used to convert Strings to Dates and Timestamps
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *  Modified by <a href="mailto:dan@getrolling.com">Dan Kibler </a>
 *  to correct time pattern. Minutes should be mm not MM (MM is month).
 */
public class DateUtil {
    private static Log          log          = LogFactory.getLog(DateUtil.class);
    protected static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
    private static final String TIME_PATTERN = "HH:mm";

    /**
     * Checkstyle rule: utility classes should not have public constructor
     */
    public DateUtil() {
    }

    //Timestamp和String之间转换的函数：
    public static String getTimestampToString(Timestamp obj) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//定义格式，不显示毫秒
        String str = df.format(obj);
        return str;
    }

    /*
     * 自定义 转换模式将Timestamp 输出
     */
    public static String getTimestampToString(String formatPattern, Timestamp obj) {
        SimpleDateFormat df = new SimpleDateFormat(formatPattern);
        String str = df.format(obj);
        return str;
    }
    
    public static String getDateToString(String formatPattern, Date obj) {
        SimpleDateFormat df = new SimpleDateFormat(formatPattern);
        String str = df.format(obj);
        return str;
    }

    //String转化为Timestamp:
    public static Timestamp getStringToTimestamp(String str) {
        Timestamp ts = Timestamp.valueOf(str);
        return ts;
    }

    public static Date strToDate(String str, String pattern) {
        Date dateTemp = null;
        SimpleDateFormat formater2 = new SimpleDateFormat(pattern);
        try {
            dateTemp = formater2.parse(str);
        } catch (Exception e) {
            log.error("exception in convert string to date!");
        }
        return dateTemp;
    }

    /**
     * Return default datePattern (yyyy-MM-dd)
     * @return a string representing the date pattern on the UI
     */
    public static String getDatePattern() {
        return "yyyy-MM-dd";
    }

    public static String getDateTimePattern() {
        return DateUtil.getDatePattern() + " HH:mm:ss.S";
    }
    
    public static String getYearMonthPattern() {
        return "yyyyMM";
    }

    /**
     * This method attempts to convert an Oracle-formatted date
     * in the form dd-MMM-yyyy to yyyy-MM-dd.
     *
     * @param aDate date from database as a string
     * @return formatted string for the ui
     */
    public static String getDate(Date aDate) {
        SimpleDateFormat df;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(getDatePattern());
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }
    
    public static String getCurMonth() {
    	Date aDate = new Date();
        SimpleDateFormat df;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(getYearMonthPattern());
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }
    
    public static String getPreMonth() {
    	Date aDate = new Date();
    	Calendar calendar = Calendar.getInstance();   
    	calendar.setTime(aDate);
    	calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        SimpleDateFormat df;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(getYearMonthPattern());
            returnValue = df.format(calendar.getTime());
        }

        return (returnValue);
    }

    /**
     * This method generates a string representation of a date/time
     * in the format you specify on input
     *
     * @param aMask the date pattern the string is in
     * @param strDate a string representation of a date
     * @return a converted Date object
     * @see SimpleDateFormat
     * @throws ParseException when String doesn't match the expected format
     */
    public static Date convertStringToDate(String aMask, String strDate) throws ParseException {
        SimpleDateFormat df;
        Date date;
        df = new SimpleDateFormat(aMask);

//        if (log.isDebugEnabled()) {
//            log.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
//        }

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            //log.error("ParseException: " + pe);
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return (date);
    }

    /**
     * This method returns the current date time in the format:
     * yyyy-MM-dd HH:MM a
     *
     * @param theTime the current time
     * @return the current date/time
     */
    public static String getTimeNow(Date theTime) {
        return getDateTime(TIME_PATTERN, theTime);
    }

    /**
     * This method returns the current date in the format: yyyy-MM-dd
     *
     * @return the current date
     * @throws ParseException when String doesn't match the expected format
     */
    public static Calendar getToday() throws ParseException {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

        // This seems like quite a hack (date -> string -> date),
        // but it works ;-)
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
        cal.setTime(convertStringToDate(todayAsString));

        return cal;
    }

    public static Calendar getCurrentDay() throws ParseException {
        Calendar cal = Calendar.getInstance();
        return cal;
    }
    
    public static Date getTodayDate(){
    	SimpleDateFormat df = new SimpleDateFormat(getDatePattern());
    	try {
			return convertStringToDate(df.format(new Date()));
		} catch (ParseException e) {
			return new Date();
		} 
    }
    
    public static String getTodayTimeStr(){
    	SimpleDateFormat df = new SimpleDateFormat(getDatePattern());
    	return df.format(new Date());
    }
    
    public static String getTodayDateStr(String pattern){
    	SimpleDateFormat df = new SimpleDateFormat(pattern);
    	return df.format(new Date());
    }

    /**
     * This method generates a string representation of a date's date/time
     * in the format you specify on input
     *
     * @param aMask the date pattern the string is in
     * @param aDate a date object
     * @return a formatted string representation of the date
     *
     * @see SimpleDateFormat
     */
    public static String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null) {
            log.error("aDate is null!");
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * This method generates a string representation of a date based
     * on the System Property 'dateFormat'
     * in the format you specify on input
     *
     * @param aDate A date to convert
     * @return a string representation of the date
     */
    public static String convertDateToString(Date aDate) {
        return getDateTime(getDatePattern(), aDate);
    }

    /**
     * This method converts a String to a date using the datePattern
     *
     * @param strDate the date to convert (in format yyyy-MM-dd)
     * @return a date object
     * @throws ParseException when String doesn't match the expected format
     */
    public static Date convertStringToDate(String strDate) throws ParseException {
        Date aDate = null;

        try {
            if (log.isDebugEnabled()) {
                log.debug("converting date with pattern: " + getDatePattern());
            }

            aDate = convertStringToDate(getDatePattern(), strDate);
        } catch (ParseException pe) {
            log.error("Could not convert '" + strDate + "' to a date, throwing exception");
            log.error(pe);
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return aDate;
    }
    
    
    public static Date convertStringToDateTime(String strDate) throws ParseException {
        Date aDate = null;

        try {
            if (log.isDebugEnabled()) {
                log.debug("converting date with pattern: " + getDatePattern());
            }

            aDate = convertStringToDate(getDateTimePattern(), strDate);
        } catch (ParseException pe) {
            log.error("Could not convert '" + strDate + "' to a date, throwing exception");
            log.error(pe);
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return aDate;
    }

    /**
     *
     * @param aDate
     * @return
     */
    public static String convertDateToString(String pattern, Date aDate) {
        return getDateTime(pattern, aDate);
    }
    
    public static String getSpecDateString(Date aDate) {
    	if (aDate == null){
    		return null;
    	}
    	return getDateTime("yyyy/MM/dd", aDate) + " 00:00:00";
    }

    /**
     * 取得从startDate开始的前(正)/后(负)day天
     * @param startDate
     * @param day
     * @return
     */
    public static Date getRelativeDate(Date startDate, int day) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(startDate);
            calendar.add(Calendar.DAY_OF_MONTH, day);
            return calendar.getTime();
        } catch (Exception e) {
            log.error(e);
            return startDate;
        }
    }
    
    public static Date getJustToday() {
    	Calendar calendar = Calendar.getInstance();
        try {
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        } catch (Exception e) {
            log.error(e);
            return new Date();
        }
    }

    /**
     * 根据日期获取星期几
     *
     * @param date java.util.Date对象,不能为null
     * @return
     */
    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 统计两个时间差，返回的是天数(即24小时算一天，少于24小时就为0，用这个的时候最好把小时、分钟等去掉)
     * @return
     */
    public static int countDays(String beginStr, String endStr, String Foramt) {
        Date end = strToDate(endStr, Foramt);
        Date begin = strToDate(beginStr, Foramt);
        long times = end.getTime() - begin.getTime();
        return (int) (times / 60 / 60 / 1000 / 24);
    }
    
    public static int countDays(Date beginDate, Date endDate) {
        long times = endDate.getTime() - beginDate.getTime();
        return (int) (times / 60 / 60 / 1000 / 24);
    }
    
    public static boolean isDate(DateFormat df, String val) {
    	try {
    		df.parse(val);
		  } catch (ParseException e) {
			   return false;
		  }
		  return true;
    }
    
    public static boolean isAfterNow(Date ymdDate, int hour) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(ymdDate);
    	cal.add(Calendar.HOUR, hour);
    	return -1 == Calendar.getInstance().compareTo(cal);
    }
    
    /**
     * 转换成中文 年月日
     */
    public static String dateToStrLong(Date dateDate) {
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd HH:mm:ss");
    	String dateString = formatter.format(dateDate);
    	return dateString;
    }
    
	public static String getCurYear() {
		return convertDateToString("yyyy", new Date());
	}
    
//    public static void main(String[] args) {
//    	Date d = DateUtil.getNextNumMonthLastDay(1,new Date());
//    	logger.info(DateUtil.convertDateToString("yyyy-MM-dd HH:mm:ss", d));
//	}
    
    /**
     * N月后的最后一天最后一秒
     * 0为当月
     * @param num
     * @param date
     * @return
     */
    public static Date getNextNumMonthLastDay(Integer num, Date date){
    	
    	if(num==null||num<0){
    		return null;
    	}
    	
//    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, num);//对月份进行计算,减去12个月
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        Date d = cal.getTime();
        
        return d;
    }
    
    public static Date getValidateDate(Integer num, Date date){
    	if(num==null||num<0){
    		return null;
    	}
    	
    	Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, num);
        cal.add(Calendar.DAY_OF_YEAR, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        Date d = cal.getTime();
        
        return d;
        
    }
    
    /**
     * 查找当月非双休日
     * @param year
     * @param month
     * @return
     */
    private static List<Date> getDates(int year,int month){    
        List<Date> dates = new ArrayList<Date>();    
            
        Calendar cal = Calendar.getInstance();    
        cal.set(Calendar.YEAR, year);    
        cal.set(Calendar.MONTH,  month - 1);    
        cal.set(Calendar.DATE, 1);    
            
            
        while(cal.get(Calendar.YEAR) == year &&     
                cal.get(Calendar.MONTH) < month){    
            int day = cal.get(Calendar.DAY_OF_WEEK);    
                
            if(!(day == Calendar.SUNDAY || day == Calendar.SATURDAY)){    
                dates.add((Date)cal.getTime().clone());    
            }    
            cal.add(Calendar.DATE, 1);    
        }    
        return dates;    
    
    }
    
    public static Date getRandomWorkDay(int year, int month){
    	List<Date> dates = getDates(year, month); 
    	return dates.get(new Random().nextInt(dates.size()));
    }
    
    /***
	 * 取得当月天数 
	 * @return 当月天数 
	 */
	public static int getCurrentMonthLastDay()
	{  
	    Calendar a = Calendar.getInstance();
	    // 把日期设置为当月第一天  
	    a.set(Calendar.DATE, 1);
	    // 日期回滚一天，也就是最后一天  
	    a.roll(Calendar.DATE, -1);
	    int maxDate = a.get(Calendar.DATE);  
	    return maxDate;  
	}
	
	/**
	 * 时间的计算
	 * @param preDate 计算前的时间
	 * @param count 要相加/相减的数字
	 * @return
	 */
	public static Date countDate(Date preDate,Integer count){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(preDate);
		calendar.add(calendar.DATE,count);//把日期往后增加一天.整数往后推,负数往前移动
		return calendar.getTime();
	}
	
	/**
	 * 获取本周第一天（周一）
	 */
	public static Date getCurrentWeekFirstDay(){
		Calendar c = Calendar.getInstance();

        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date first = c.getTime();
        return first;
	}
	
	/**
	 * 获取本周最后一天（周天）
	 */
	public static Date getCurrentWeekLastDay(){
		Calendar c = Calendar.getInstance();

        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        Date last = c.getTime();
        return last;
	}
    
	/**
	 * 获取本月第一天
	 * @return
	 */
	public static Date getCurrentMonthFirstDate(){
		Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
       
        return c.getTime();
	}
	
	/**
	 * 获取本月最后一天
	 * @return
	 */
	public static Date getCurrentMonthLastDate(){
		Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        return ca.getTime();
	}
	
//	/**
//	 * 获取一年前的今天
//	 * @return
//	 */
//	public static String getLastYearToday(){
//
//		Calendar calendar = new GregorianCalendar();
//		calendar.setTime(new Date());
//		calendar.add(calendar.MONTH,-12);//把日期往后增加一天.整数往后推,负数往前移动
//
//		return DateUtil.convertDateToString("yyyyMMdd", calendar.getTime());
//	}

    /**
     * 判断两个时间的大小 的d1>＝d2返回true
     *
     * @param d1
     * @param d2
     * @return
     */
    public static boolean compare(Date d1, Date d2) {
        SimpleDateFormat FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
        String str1 = FORMAT.format(d1);
        String str2 = FORMAT.format(d2);
        int result = str1.compareTo(str2);
        if (result >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isBuyTime(String buyTime){
        boolean b = false;
        try {
            Date buyDate = DateUtil.convertStringToDate("yyyyMMddHHmmss",buyTime);
            Date nowDate = new Date();
            if(compare(nowDate,subtractionMilli(buyDate,2000)) && !compare(subtractionMilli(nowDate,0),buyDate)){
                b = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static boolean isBuyTime(String buyTime,String beginTime,String endTime){
        boolean b = false;
        Integer begin = 2000;
        Integer end = -1000;
        if(StringUtils.isNotBlank(beginTime)){
            begin = Integer.valueOf(beginTime);
        }
        if(StringUtils.isNotBlank(endTime)){
            end = Integer.valueOf(endTime);
        }
        try {
            Date buyDate = DateUtil.convertStringToDate("yyyyMMddHHmmss",buyTime);
            Date nowDate = new Date();
            if(compare(nowDate,subtractionMilli(buyDate,begin)) && !compare(subtractionMilli(nowDate,-end),buyDate)){
                b = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static boolean isInitTime(String buyTime){
        boolean b = false;
        try {
            Date buyDate = DateUtil.convertStringToDate("yyyyMMddHHmmss",buyTime);
            Date nowDate = new Date();
            if(compare(nowDate,subtraction(buyDate,30)) && !compare(subtraction(nowDate,1),buyDate)){
                b = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static boolean isQueryTime(String buyTime){
        boolean b = false;
        try {
            Date buyDate = DateUtil.convertStringToDate("yyyyMMddHHmmss",buyTime);
            Date nowDate = new Date();
            if(compare(nowDate,subtraction(buyDate,9)) && !compare(subtraction(nowDate,1),buyDate)){
                b = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static boolean guajiTime(String buyTime){
        boolean b = true;
        try {
            Date buyDate = DateUtil.convertStringToDate("yyyyMMddHHmmss",buyTime);
            Date nowDate = new Date();
            if(compare(nowDate,subtraction(buyDate,120))){
                b = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * @param date
     * @param second
     * @return
     * @description 获取多少秒前的时间
     */
    public static Date subtraction(Date date, int second) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.SECOND, c.get(Calendar.SECOND) - second);
        return c.getTime();
    }

    /**
     * @param date
     * @param millliSecond
     * @return
     * @description 获取多少秒前的时间
     */
    public static Date subtractionMilli(Date date, int millliSecond) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MILLISECOND, c.get(Calendar.MILLISECOND) - millliSecond);
        return c.getTime();
    }

    public static void currentDate(){
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        logger.info(new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(date));
    }
    public static String currentDateR(){
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        return new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(date);
    }
    public static void currentDate(String step){
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        logger.info(step + "," + new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(date));
    }
	
    public static void main(String[] args) throws ParseException {

        String buyDate = "20190506100532";
        Date nowDate = new Date();

        System.out.println(nowDate);
        System.out.println(subtractionMilli(nowDate,-1000));


//        boolean b = !compare(subtractionMilli(nowDate,-1000),buyDate);


	}
    
}
