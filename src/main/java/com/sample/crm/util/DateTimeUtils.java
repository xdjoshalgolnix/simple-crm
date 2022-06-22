
package com.sample.crm.util;

import java.lang.invoke.MethodHandles;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * provide parse/format Date
 *
 */
public class DateTimeUtils
{
  private static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
  public static final String DATE_FORMAT = "yyyy-MM-dd";
  
  public static final String SYSTEM_TZ = "Asia/Hong_Kong";

  private final static ThreadLocal<SimpleDateFormat> BASIC_DATE = new ThreadLocal<SimpleDateFormat>()
  {
    protected SimpleDateFormat initialValue()
    {
      return new SimpleDateFormat(DATE_FORMAT);
    }
  };
  private final static ThreadLocal<SimpleDateFormat> BASIC_DATE_TIME = new ThreadLocal<SimpleDateFormat>()
  {
    protected SimpleDateFormat initialValue()
    {
      return new SimpleDateFormat(DATE_TIME_FORMAT);
    }
  };

  public static String toDateString(Date date)
  {
    return new SimpleDateFormat(DATE_FORMAT).format(date);
  }

  public static boolean validateDate(String dateStr)
  {
    try
    {
      BASIC_DATE.get().parse(dateStr);
      return true;
    }
    catch ( ParseException e )
    {
      log.error("Fail to parse date: " + e.getMessage());
      return false;
    }
  }

  public static String formatDate(Date date)
  {
    return BASIC_DATE.get().format(date);
  }

  public static String formatDateTime(Date date)
  {
    return BASIC_DATE_TIME.get().format(date);
  }

  public static boolean validateDateTime(String dateStr)
  {
    try
    {
      BASIC_DATE_TIME.get().parse(dateStr);
      return true;
    }
    catch ( ParseException e )
    {
      log.error("Fail to parse date: " + e.getMessage());
      return false;
    }
  }

  public static Date parseToDate(String datestr)
  {
    try
    {
      return BASIC_DATE.get().parse(datestr);
    }
    catch ( ParseException e )
    {
      log.error("Fail to parse date: " + e.getMessage());
      return null;
    }
  }

  public static Date parseToMaxDate(String datestr)
  {
    try
    {
      Calendar cal = Calendar.getInstance();
      cal.setTime(BASIC_DATE.get().parse(datestr));
      cal.set(Calendar.HOUR_OF_DAY, 23);
      cal.set(Calendar.MINUTE, 59);
      cal.set(Calendar.SECOND, 59);
      cal.set(Calendar.MILLISECOND, 999);
      return cal.getTime();
    }
    catch ( ParseException e )
    {
      log.error("Fail to parse date: " + e.getMessage());
      return null;
    }
  }

  public static Date parseToNextDate(String datestr)
  {
    try
    {
      Calendar cal = Calendar.getInstance();
      cal.setTime(BASIC_DATE.get().parse(datestr));
      cal.add(Calendar.DAY_OF_YEAR, 1);
      return cal.getTime();
    }
    catch ( ParseException e )
    {
      log.error("Fail to parse date: " + e.getMessage());
      return null;
    }
  }

  public static String toDateTimeString(Date date)
  {
    return BASIC_DATE_TIME.get().format(date);
  }

  public static Date parseToDateTime(String datestr)
  {
    try
    {
      return BASIC_DATE_TIME.get().parse(datestr);
    }
    catch ( ParseException e )
    {
      log.error("Fail to parse date time: " + e.getMessage());
      return null;
    }
  }

  public static Date parseToDateWithNewDateFormat(String datestr)
  {
    try
    {
      DateFormat sdf = BASIC_DATE.get();
      return sdf.parse(datestr);
    }
    catch ( ParseException e )
    {
      log.error("Fail to parse date: " + e.getMessage());
      return null;
    }
  }

  public static Date parseToMaxDateWithNewDateFormat(String datestr)
  {
    try
    {
      DateFormat sdf = BASIC_DATE.get();
      Calendar cal = Calendar.getInstance();
      cal.setTime(sdf.parse(datestr));
      cal.set(Calendar.HOUR_OF_DAY, 23);
      cal.set(Calendar.MINUTE, 59);
      cal.set(Calendar.SECOND, 59);
      cal.set(Calendar.MILLISECOND, 999);
      return cal.getTime();
    }
    catch ( ParseException e )
    {
      log.error("Fail to parse date: " + e.getMessage());
      return null;
    }
  }

}
