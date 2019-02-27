package com.techmango.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

import com.techmango.common.exception.BusinessException;


public class DateConversionUtil {

	public static final String MM_DD_YYYY = "MM/dd/yyyy";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String UI_DATE_FORMAT = "MM-dd-yyyy";
	public static final String ZONE_FORMAT_OF_YYYYMMDDHHMMSSZ = "yyyy-MM-dd HH:mm:ss z";
	public static final String DEFAULT_TIME_STAMP = " 00:00:00";
	public static final String DEFAULT_END_TIME_STAMP = " 23:59:59";
	public static final String INVALID_DATE_FORMAT = "Invalid Date Format";
	public static final String MMM_D_YYYY = "MMM d, yyyy";
	public static final String MMM_DD_YYYY = "MMM dd, yyyy";
	public static final String YYYY_DD_MM = "yyyy-dd-MM";
	public static final String MMM_D_YYYY_HH_MM_SS_AAA = "MMM d, yyyy hh:mm:ss aaa";
	public static final String MMM_DD_YYYY_HH_MM_SS_AAA = "MMM dd, yyyy hh:mm:ss aaa";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	
	private DateConversionUtil() {
		simpleMysqlDateFormatTime.get().setTimeZone(TimeZone.getTimeZone("UTC"));
		simpleMysqlLocalDateFormatTime.get().setTimeZone(TimeZone.getTimeZone("UTC"));
	}
	
	private static final ThreadLocal<SimpleDateFormat> simpleMysqlDateFormat = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(DateConversionUtil.YYYY_MM_DD);
		}
	};

	private static final ThreadLocal<SimpleDateFormat> simpleMysqlDateFormatTime = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		}
	};

	private static final ThreadLocal<SimpleDateFormat> simpleMysqlLocalDateFormatTime = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		}
	};

	public static Date convertToDate(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(MM_DD_YYYY);
		return sdf.parse(date);
	}

	public static String convertToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(MM_DD_YYYY);
		return sdf.format(date);
	}

	public static Date convertToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate convertToLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static String convertToUtilDate(String date) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD);
		Date tempDate = simpleDateFormat.parse(date);
		SimpleDateFormat outputDateFormat = new SimpleDateFormat(MM_DD_YYYY);
		return outputDateFormat.format(tempDate);
	}

	public static LocalDate convertStringToISOLocalDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MM_DD_YYYY);
		return LocalDate.parse(date, formatter);
	}

	public static Date checkconvertStringToISODate(String date) {
		DateFormat df = new SimpleDateFormat(MM_DD_YYYY);
		Date convertUtilDate = null;
		if (!StringUtils.isEmpty(date)) {
			try {
				convertUtilDate = df.parse(date);
			} catch (ParseException e) {
				throw new BusinessException(INVALID_DATE_FORMAT);
			}
		}
		return convertUtilDate;
	}
	
	public static Date convertStringToISODate(String date) {
		DateFormat df = new SimpleDateFormat(DateConversionUtil.UI_DATE_FORMAT);
		Date convertUtilDate = null;
		if (StringUtils.isNotBlank(date)) {
			try {
				convertUtilDate = df.parse(date);
			} catch (ParseException e) {
				throw new BusinessException(INVALID_DATE_FORMAT);
			}
		}
		return convertUtilDate;
	}

	public static Date checkconvertStringToDate(String date) {
		DateFormat df = new SimpleDateFormat(YYYY_MM_DD);
		try {
			return df.parse(date);
		} catch (ParseException e) {
			throw new BusinessException(INVALID_DATE_FORMAT);
		}
	}

	public static Date checkconvertStringToDateFormat(String date) {
		DateFormat df = new SimpleDateFormat(YYYY_DD_MM);
		try {
			return df.parse(date);
		} catch (ParseException e) {
			throw new BusinessException(INVALID_DATE_FORMAT);
		}
	}
	
	public static String convertDateToStringFormat(Date date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		String convertUtilDate = "";
		if (StringUtils.isNotBlank(format)) {
			convertUtilDate = df.format(date);
		}
		return convertUtilDate;
	}
	
	public static Date convertStringToDateWithZone(String date) {
		DateFormat df = new SimpleDateFormat(DateConversionUtil.ZONE_FORMAT_OF_YYYYMMDDHHMMSSZ);
		try {
			return df.parse(date);
		} catch (ParseException e) {
			throw new BusinessException(INVALID_DATE_FORMAT);
		}
	}

	public static String convertDateToStringWithZone(Date date) {
		DateFormat df = new SimpleDateFormat(DateConversionUtil.ZONE_FORMAT_OF_YYYYMMDDHHMMSSZ);
		try {
			return df.format(date);
		} catch (Exception e) {
			throw new BusinessException(INVALID_DATE_FORMAT);
		}
	}
	
	public static Date parseEndDateFormatWithDefaultTime(Date givenDate) throws ParseException {
		String convertDefaultTimeFormat = simpleMysqlDateFormat.get().format(givenDate)
				+ DateConversionUtil.DEFAULT_END_TIME_STAMP;
		return simpleMysqlDateFormatTime.get().parse(convertDefaultTimeFormat);
	}
	
	public static Date parseUtilDateFormatWithDefaultTime(Date givenDate) throws ParseException {
		String convertDefaultTimeFormat = simpleMysqlDateFormat.get().format(givenDate) + DEFAULT_TIME_STAMP;
		return simpleMysqlDateFormatTime.get().parse(convertDefaultTimeFormat);
	}

	public static Date parseLocalDateFormatWithDefaultTime(LocalDate givenDate) throws ParseException {
		String convertDefaultTimeFormat = simpleMysqlDateFormat.get()
				.format(DateConversionUtil.convertToDate(givenDate)) + DEFAULT_TIME_STAMP;
		return simpleMysqlLocalDateFormatTime.get().parse(convertDefaultTimeFormat);
	}

	public static String parseWordDateToString(Date dateAsString) {
		DateFormat df = new SimpleDateFormat(MMM_D_YYYY);
		String convertDateString = null;
		if (dateAsString != null) {
			convertDateString = df.format(dateAsString);
		}
		return convertDateString;
	}

	public static String parseDateToString(Date dateAsString) {
		DateFormat df = new SimpleDateFormat(YYYY_MM_DD);
		String convertDateString = null;
		if (dateAsString != null) {
			convertDateString = df.format(dateAsString);
		}
		return convertDateString;
	}
	
	public static String parseWordDateFormatString(Date dateAsString) {
		DateFormat df = new SimpleDateFormat(MMM_DD_YYYY);
		String convertDateString = null;
		if (dateAsString != null) {
			convertDateString = df.format(dateAsString);
		}
		return convertDateString;
	}
	
	public static String parseDateToStringWithTime(Date dateAsString) {
		DateFormat df = new SimpleDateFormat(MMM_D_YYYY_HH_MM_SS_AAA);
		String convertDateString = null;
		if (dateAsString != null) {
			convertDateString = df.format(dateAsString);
		}
		return convertDateString;
	}
	
	public static String convertDateFormatWithTime(Date date) {
		SimpleDateFormat dateformater = new SimpleDateFormat(MMM_DD_YYYY_HH_MM_SS_AAA);
			return dateformater.format(date);
	}
}
