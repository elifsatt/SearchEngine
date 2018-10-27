package com.elifsat.searchengine.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.datatype.XMLGregorianCalendar;

public class DateFormatUtil {
	public static final String SIMPLE_DATE = "dd/MM/yyyy";
	public static final String MONTH_DAY_YEAR_FORMAT = "MM/dd/yyyy";
	public static final String YEAR_MONTH_FORMAT = "yyyyMM";
	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd-HH:mm:ss";
	public static final String DATE_FORMAT_NOW_2 = "yyyy-MM-dd-HH-mm-ss";
	public static final String DATE_FORMAT_TIME_ZONE = "yyyy-MM-dd'T'HH:mm:ss.SSSz";
	public static final String DATE_FORMAT_NOW_CRM = "yyyy-MM-dd HH:mm:ss";
	public static final String YEAR_MONTH_DAY_FORMAT = "yyyy-MM-dd";
	public static final String YEAR_MONTH_DAY_PLAIN_FORMAT = "yyyyMMdd";
	private static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SS = "yyyyMMddHHmmssSS";
	private static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyyMMddHHmmss";

	/**
	 * This is the standard LDAP date format : yyyyMMddHHmmss.S'Z'.
	 */
	public static final String LDAP_DATE_INTERNAL_STORAGE_FORMAT = "yyyyMMddHHmmss.S'Z'";

	/**
	 * This is the simplified LDAP date format : yyyyMMddHHmmss'Z'.
	 */
	public static final String LDAP_DATE_SIMPLIFIED_STORAGE_FORMAT = "yyyyMMddHHmmss'Z'";

	/**
	 * Internal transformation object. TODO fix this if there is a performance
	 * problem, I did a small cleanup and added synchronization as a DateFormat is
	 * not threadsafe
	 */
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(LDAP_DATE_INTERNAL_STORAGE_FORMAT);

	/**
	 * Internal transformation object. TODO fix this if there is a performance
	 * problem, I did a small cleanup and added synchronization as a DateFormat is
	 * not threadsafe
	 */
	private static final SimpleDateFormat SIMPLIFIED_FORMATTER = new SimpleDateFormat(
			LDAP_DATE_SIMPLIFIED_STORAGE_FORMAT);

	private static final SimpleDateFormat SIMPLIFIED_FORMATTER_DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat(
			DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);

	/** The UTC time zone. */
	private static final TimeZone UTC_TIME_ZONE = TimeZone.getDefault();// getTimeZone("UTC");

	static {
		FORMATTER.setLenient(false);
		FORMATTER.setTimeZone(UTC_TIME_ZONE);

		SIMPLIFIED_FORMATTER_DATE_FORMAT_YYYY_MM_DD_HH_MM_SS.setLenient(false);
		SIMPLIFIED_FORMATTER_DATE_FORMAT_YYYY_MM_DD_HH_MM_SS.setTimeZone(UTC_TIME_ZONE);
	}

	private static final int MILLISECONDS_IN_DAY = 1000 * 60 * 60 * 24;

	private DateFormatUtil() {
	}

	/**
	 * Return a date object corresponding to the LDAP date string.
	 * 
	 * @param date
	 *            the date to parse
	 * @return the corresponding Java Date object
	 * @throws ParseException
	 *             thrown if an error occurs in date parsing
	 */
	public static Date parseLdap(final String date) throws ParseException {
		synchronized (FORMATTER) {
			try {
				return FORMATTER.parse(date);
			} catch (ParseException pe) {
				try {
					return SIMPLIFIED_FORMATTER.parse(date);
				} catch (ParseException pe2) {
					throw pe;
				}
			}
		}
	}

	/**
	 * Return a date object corresponding to the LDAP date string.
	 * 
	 * @param date
	 *            the date to parse
	 * @return the corresponding Java Date object
	 * @throws ParseException
	 *             thrown if an error occurs in date parsing
	 */
	public static Date parseDateFormatYYYYMMDDHHMMSSLdap(final String date) throws ParseException {
		synchronized (FORMATTER) {
			try {
				return FORMATTER.parse(date);
			} catch (ParseException pe) {
				try {
					return SIMPLIFIED_FORMATTER_DATE_FORMAT_YYYY_MM_DD_HH_MM_SS.parse(date);
				} catch (ParseException pe2) {
					throw pe;
				}
			}
		}
	}

	/**
	 * Generate a date string - synchronized call to internal formatter object to
	 * support multi-threaded calls.
	 * 
	 * @param date
	 *            date to extract
	 * @return generated date
	 */
	public static String formatLdap(final Date date) {
		synchronized (FORMATTER) {
			return FORMATTER.format(date);
		}
	}

	/**
	 * Generate a date string - synchronized call to internal formatter object to
	 * support multi-threaded calls.
	 * 
	 * This uses the simplified format: yyyyMMddHHmmss'Z'
	 * 
	 * @param date
	 *            date to extract
	 * @return generated date
	 */
	public static String simpleFormatLdap(final Date date) {
		synchronized (SIMPLIFIED_FORMATTER) {
			return SIMPLIFIED_FORMATTER.format(date);
		}
	}

	/**
	 * Formats from text to simple Date (dd/MM/yyyy)
	 *
	 * @param source
	 *            Text
	 * @return Date
	 * @throws ParseException
	 */
	public synchronized static Date parseYearMonthDayFormat(String source) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(YEAR_MONTH_DAY_FORMAT);

		sdf.setTimeZone(UTC_TIME_ZONE);

		return sdf.parse(source);
	}

	/**
	 * Formats from text to simple Date(yyyyMMddHHmmssSS)
	 * 
	 * @param source
	 *            text
	 */
	public synchronized static Date parseYearMonthDayHourMinuteSecondMsFormat(String source) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SS);

		sdf.setTimeZone(UTC_TIME_ZONE);

		return sdf.parse(source);

	}

	/**
	 * Formats from text to simple Date(yyyyMMddHHmmss)
	 * 
	 * @param source
	 *            text
	 */
	public synchronized static Date parseYearMonthDayHourMinuteSecondFormat(String source) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);

		sdf.setTimeZone(UTC_TIME_ZONE);

		return sdf.parse(source);

	}

	/**
	 * Formats from text to simple Date (dd/MM/yyyy)
	 *
	 * @param source
	 *            Text
	 * @return Date
	 * @throws ParseException
	 */
	public synchronized static Date parseSimpleDate(String source) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_DATE);

		sdf.setTimeZone(UTC_TIME_ZONE);

		return sdf.parse(source);

	}

	public static Date convertCalendarToDate(XMLGregorianCalendar cal) {
		return cal.toGregorianCalendar().getTime();
	}

	/**
	 * Format date to string (dd/MM/yyyy)
	 * 
	 * @param date
	 *            Date object to be formatted
	 * @return formatted string
	 */
	public synchronized static String format(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_DATE);

		sdf.setTimeZone(UTC_TIME_ZONE);

		return sdf.format(date);
	}

	/**
	 * Format date to year and month (yyyyMM)
	 * 
	 * @param date
	 *            Date object to be formatted
	 * @return formatted string
	 */
	public synchronized static String getYearAndMonth(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(YEAR_MONTH_FORMAT);

		sdf.setTimeZone(UTC_TIME_ZONE);

		return sdf.format(date);
	}

	/**
	 * Format the current time to string (yyyy-MM-dd-HH:mm:ss)
	 * 
	 * @return formatted time string
	 */
	public synchronized static String getCurrentTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

		sdf.setTimeZone(UTC_TIME_ZONE);

		return sdf.format(cal.getTime());
	}
	
	public synchronized static String getCurrentTime2() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW_2);

		sdf.setTimeZone(UTC_TIME_ZONE);

		return sdf.format(cal.getTime());
	}

	/**
	 * Format the current time to string (yyyy-MM-dd HH:mm:ss)
	 * 
	 * @return formatted time string
	 */
	public synchronized static String getCurrentTime_CRM() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW_CRM);

		sdf.setTimeZone(UTC_TIME_ZONE);

		return sdf.format(cal.getTime());
	}

	/**
	 * Format the current time to string (yyyy-MM-dd-HH:mm:ss)
	 * 
	 * @return formatted time string
	 */
	public synchronized static String getTime(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

		sdf.setTimeZone(UTC_TIME_ZONE);

		return sdf.format(date.getTime());

	}
	
	/**
	 * Format the current time to string (yyyy-MM-dd'T'HH:mm:ss.SSSz)
	 * 
	 * @return formatted time string
	 */
	public synchronized static String getDatetime(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_TIME_ZONE);

		sdf.setTimeZone(UTC_TIME_ZONE);

		return sdf.format(date.getTime());

	}

	/**
	 * Format a Date to string (MM/dd/yyyy)
	 * 
	 * @param date
	 *            Date object to be formatted
	 * @return
	 */
	public synchronized static String formatAsMonthDayYear(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(MONTH_DAY_YEAR_FORMAT);

		sdf.setTimeZone(UTC_TIME_ZONE);

		return sdf.format(date);
	}

	/**
	 * Format a Date to string (yyyy-MM-dd)
	 * 
	 * @param date
	 *            Date object to be formatted
	 * @return
	 */
	public synchronized static String formatAsYearMonthDay(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(YEAR_MONTH_DAY_FORMAT);

		sdf.setTimeZone(UTC_TIME_ZONE);

		return sdf.format(date);
	}

	/**
	 * Format date to string (yyyyMMdd)
	 * 
	 * @param date
	 * @return
	 */
	public synchronized static String formatAsYearMonthDayPlain(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(YEAR_MONTH_DAY_PLAIN_FORMAT);

		sdf.setTimeZone(UTC_TIME_ZONE);

		return sdf.format(date);
	}

	/**
	 * Clone the date object
	 * 
	 * @param date
	 *            Date object to be cloned
	 * @return cloned date
	 */
	public synchronized static Date getClonedDate(Date date) {

		if (date != null) {
			return (Date) date.clone();
		}
		return null;

	}

	/**
	 * Adds n month to the argument date
	 * 
	 * @param date
	 * @param n
	 *            number of the months to be added
	 * @return n months after the argument date
	 */
	public synchronized static Date getNextNMonth(Date date, int n) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, n);
		return (Date) calendar.getTime().clone();
	}

	/**
	 * Adds n year to the argument date
	 * 
	 * @param date
	 * @param n
	 *            number of the months to be added
	 * @return n months after the argument date
	 */
	public synchronized static Date getNextNYear(Date date, int n) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, n);
		return (Date) calendar.getTime().clone();
	}

	/**
	 * Finds date-daysBefore
	 * 
	 * @param date
	 * @param daysBefore
	 *            number of days to be substracted.(Must be positive)
	 * @return
	 * 
	 */
	public synchronized static Date getDaysBefore(Date date, int daysBefore) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -daysBefore);
		return calendar.getTime();
	}

	/**
	 * Add n day(s) to date
	 * 
	 * @param date
	 * @param n
	 * @return
	 */
	public synchronized static Date getNextNDay(Date date, int n) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, n);
		return (Date) calendar.getTime().clone();
	}

	/**
	 * Finds and return # of months between startDate and endDate
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */

	public synchronized static int monthsBetween(Date startDate, Date endDate) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(startDate);
		int startMonth = calendar.get(Calendar.MONTH);
		int startYear = calendar.get(Calendar.YEAR);
		calendar.setTime(endDate);
		int endMonth = calendar.get(Calendar.MONTH);
		int endYear = calendar.get(Calendar.YEAR);
		return (endYear - startYear) * 12 + endMonth - startMonth;
	}

	/**
	 * Finds and return number of days between startDate and endDate ignoring hours,
	 * minutes, seconds and milliseconds
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public synchronized static int daysBetween(Date startDate, Date endDate) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(startDate);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MILLISECOND, 0);
		long startTime = c1.getTimeInMillis() + c1.get(Calendar.DST_OFFSET);

		c2.setTime(endDate);
		c2.set(Calendar.HOUR_OF_DAY, 0);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.SECOND, 0);
		c2.set(Calendar.MILLISECOND, 0);
		long endTime = c2.getTimeInMillis() + c2.get(Calendar.DST_OFFSET);

		return (int) ((endTime - startTime) / MILLISECONDS_IN_DAY);
		// return (int)( (endDate.getTime() - startDate.getTime()) /
		// (MILLISECONDS_IN_DAY));
	}

	/**
	 * Finds and return number of days between early and late
	 * 
	 * @param early
	 * @param late
	 * @return days between late-early
	 */
	public synchronized static int daysBetween(Calendar early, Calendar late) {
		return (int) (toJulian(late) - toJulian(early));
	}

	/**
	 * Return the julian representation of the calendar
	 * 
	 * @param calendar
	 * @return
	 */
	public synchronized static float toJulian(Calendar calendar) {
		int y = calendar.get(Calendar.YEAR);
		int m = calendar.get(Calendar.MONTH);
		int d = calendar.get(Calendar.DATE);
		int a = y / 100;
		int b = a / 4;
		int c = 2 - a + b;
		float e = (int) (365.25f * (y + 4716));
		float f = (int) (30.6001f * (m + 1));
		return c + d + e + f - 1524.5f;
	}

	/**
	 * Returns the current time.
	 * 
	 * @return the current time.
	 */
	public synchronized static Date getNow() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * Return the start of current day (00.00.00)
	 * 
	 * @return
	 * 
	 */
	public synchronized static Date getCurrentDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * Return 1 if first date is later than second date, 0 when equals, -1 when
	 * first date is earlier than second date
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public synchronized static int compare(Date date1, Date date2) {
		if (date1.before(date2)) {
			return -1;
		} else if (date1.after(date2)) {
			return 1;
		} else {
			return 0;
		}
	}

	public synchronized static String formatDate(Date dt) {
		if (dt == null) {
			return "";
		}
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/YYYY");

		fmt.setLenient(false);
		fmt.setTimeZone(UTC_TIME_ZONE);
		try {
			return fmt.format(dt);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
