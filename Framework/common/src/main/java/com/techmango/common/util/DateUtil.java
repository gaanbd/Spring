package com.techmango.common.util;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.techmango.common.exception.BusinessException;


public class DateUtil {

	public DateUtil() {
		
	}

	public enum day {
		Sun(1), Mon(2), Tue(3), Wed(4), Thu(5), Fri(6), Sat(7);
		private int value;

		private day(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	public enum Month {
		January(0), February(1), March(2), April(3), May(4), June(5), July(6), August(7), September(8), October(
				9), November(10), December(11);
		private int value;

		private Month(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	public static List<Date> getListOfDatesBetweenTwoDates(String startDateparam, String endDateParam) {
		List<Date> weekDates = new ArrayList<>();
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat outputformatter = new SimpleDateFormat(DateConversionUtil.YYYY_MM_DD);
		Date startDate;
		Date endDate;
		try {
			startDate = formatter.parse(startDateparam);
			endDate = formatter.parse(endDateParam);

			long interval = 24 * 1000 * 60 * 60l; // 1 hour in millis
			long endTime = endDate.getTime(); // create your endtime here,
												// possibly using Calendar or
												// Date
			long curTime = startDate.getTime();
			while (curTime <= endTime) {
				weekDates.add(new Date(curTime));
				curTime += interval;
			}
			for (int i = 0; i < weekDates.size(); i++) {
				Date lDate = weekDates.get(i);
				outputformatter.format(lDate);
			}
		} catch (ParseException e) {
			throw new BusinessException("Invalid Date Format");
		}
		return weekDates;
	}

	public static synchronized boolean isWithinRange(Date testDate, Date startDate, Date endDate) {
		return !(testDate.before(startDate) || testDate.after(endDate) || testDate.equals(startDate)
				|| testDate.equals(endDate));
	}

	public static Date addDaysToUtilDate(Date givenDate, int noOfDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(givenDate);
		cal.add(Calendar.DATE, noOfDays);
		return cal.getTime();
	}

	public static LocalDate addDaysToLocalDate(LocalDate givenDate, int noOfDays) {
		return givenDate.plusDays(noOfDays);
	}

	public static Date getCurrentDate() {
		return new Date();
	}

	public static boolean isThisDateValid(String dateToValidate, String dateFormat) {
		if (dateToValidate == null) {
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setLenient(false);
		try {
			sdf.parse(dateToValidate);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static Date getEndOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	public static Date getStartOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * Used to get the Years between two years
	 * 
	 * @param startYear
	 * @param endYear
	 * @return
	 */
	public static List<Integer> getInBetweenYearsExcludeStartAndEnd(int startYear, int endYear) {
		List<Integer> yearList = new ArrayList<>();
		for (int j = startYear; j < (endYear-1); j++) {
			yearList.add(j + 1);
		}
		return yearList;
	}
	
	public static List<Integer> getInBetweenYearsIncludeStartAndEnd(int startYear, int endYear) {
		List<Integer> yearList = new ArrayList<>();
		yearList.add(startYear);
		for (int j = startYear; j < endYear; j++) {
			yearList.add(j + 1);
		}
		return yearList;
	}

	/**
	 * Used to return the months for the year in the form of Jan-2017,Feb-2017,etc
	 * 
	 * @param year
	 * @return
	 */
	public static List<String> getMonthsForYear(int year) {
		List<String> monthsList = new ArrayList<>();
		int i = 1;
		for (int j = i; j <= 12; j++) {
			String monthValue = getShortMonthName(j);
			monthsList.add(monthValue.concat(" ").concat(Integer.toString(year)));
		}
		return monthsList;
	}

	/**
	 * Used to return the month short name by month number ex:Jan
	 * 
	 * @param monthNumber
	 * @return
	 */
	private static String getShortMonthName(int monthNumber) {
		String[] months = new DateFormatSymbols().getShortMonths();
		int n = monthNumber - 1;
		return (n >= 0 && n <= 11) ? months[n] : "wrong month Value";
	}
	
	private static String getEndMonthName(int monthNumber) {
		String[] months = new DateFormatSymbols().getShortMonths();
		int n = monthNumber + 1;
		return (n >= 0 && n <= 11) ? months[n] : "wrong month value";
	}

	/**
	 * Used to return the Quarters for the year as Jan 2017-Mar 2017,etc
	 * 
	 * @param year
	 * @return
	 */
	public static List<String> getQuartersForYear(int year) {
		List<String> monthsList = new ArrayList<>();
		int i = 1;
		for (int j = i; j <= 12; j = j + 3) {
			String monthValue = getShortMonthName(j);
			String endMonthValue = getEndMonthName(j);
			String startQuarter = monthValue.concat(" ").concat(Integer.toString(year));
			String endQuarter = endMonthValue.concat(" ").concat(Integer.toString(year));
			monthsList.add(startQuarter.concat("-").concat(endQuarter));
		}
		return monthsList;
	}

	/**
	 * Used to return the days list for the month
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static List<Integer> getDatesOfMonthWithYear(int year, int month) {
		LocalDate endDate = YearMonth.of(year, month).atEndOfMonth();
		List<Integer> datesList = new ArrayList<>();
		int i = 1;
		for (int j = i; j <= endDate.getDayOfMonth(); j++) {
			datesList.add(j);
		}
		return datesList;
	}

	public static Integer getIndexOfShortMonth(String shortMonth) throws ParseException {
		Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(shortMonth);
		java.time.Month month = java.time.Month.valueOf(new SimpleDateFormat("MMMM").format(date).toUpperCase());
		return month.getValue();
	}

	/**
	 * Used to return the first and last day of the year
	 * 
	 * @param year
	 * @return
	 */
	public static List<Date> getFirstAndLastDateOfYear(int year) {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		YearMonth yearMonth = YearMonth.of(year, 1);
		LocalDateTime firstOfMonthTime = yearMonth.atDay(1).atTime(0, 0, 0);
		LocalDateTime lastYear = yearMonth.atEndOfMonth().atTime(23, 59, 59);
		Date firstYearDate = Date.from(firstOfMonthTime.atZone(defaultZoneId).toInstant());
		Date lastYearDate = Date.from(lastYear.atZone(defaultZoneId).toInstant());
		return new ArrayList<>(Arrays.asList(firstYearDate, lastYearDate));
	}

	public static List<Date> getFirstAndLastDateOfMonth(int year, int month) {
		YearMonth yearMonth = YearMonth.of(year, month);
		LocalDate firstDate = yearMonth.atDay(1);
		LocalDate lastDate = yearMonth.atEndOfMonth();
		return Arrays.asList(java.sql.Timestamp.valueOf(firstDate.atStartOfDay()),
				java.sql.Timestamp.valueOf(lastDate.atTime(23, 59, 59)));
	}

	public static List<String> getWeekNumberForMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int weeks = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
		List<String> weekList = new ArrayList<>();
		for (int i = 1; i <= weeks; i++) {
			weekList.add(String.valueOf(i));
		}
		return weekList;
	}

	public static List<String> getHoursPerDay() {
		return Arrays.stream(IntStream.rangeClosed(1, 24).toArray()).boxed().map(Objects::toString)
				.collect(Collectors.toList());
	}

	public static List<String> getDurationsByLimit(int incrementNumber, int limit) {
		return Arrays.stream(IntStream.iterate(incrementNumber, i -> i + incrementNumber).limit(limit).toArray())
				.boxed().map(Objects::toString).collect(Collectors.toList());
	}

	public static String calculateAge(LocalDate birthDate, LocalDate currentDate) {
		Period timePeriod = Period.between(birthDate, currentDate);
		return timePeriod.getYears() + " years " + timePeriod.getMonths() + " months " + timePeriod.getDays() + " days ago ";
	}
}
