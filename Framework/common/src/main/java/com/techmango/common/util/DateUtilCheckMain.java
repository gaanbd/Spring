package com.techmango.common.util;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;

public class DateUtilCheckMain
{
  public DateUtilCheckMain() {}
  
  /*public static void main(String[] args)
  throws ParseException
{
	System.out.println("Convert Date to String with time(MMM dd, yyyy hh:mm:ss aaa) : " + DateConversionUtil.convertDateFormatWithTime(new Date()));//Convert Date to String with time AM/PM
	
  System.out.println("Convert Date to String with given format(MM/dd/yyyy) : " + DateConversionUtil.convertDateToStringFormat(new Date(), "MM/dd/yyyy"));//Convert Date to String with Date and DateFormat input
  
  System.out.println("Convert Date to String with given format(yyyy-MM-dd) : " + DateConversionUtil.convertDateToStringFormat(new Date(), "yyyy-MM-dd"));
  
  System.out.println("Convert Date to String with given format(MM-dd-yyyy) : " + DateConversionUtil.convertDateToStringFormat(new Date(), "MM-dd-yyyy"));
  
	System.out.println("Convert Date to String with Zone(yyyy-MM-dd HH:mm:ss z) : " + DateConversionUtil.convertDateToStringWithZone(new Date()));//Convert Date to String with Zone(yyyy-MM-dd HH:mm:ss z)
	
	System.out.println("Convert date to String(MM/dd/yyyy) : " + DateConversionUtil.convertToString(new Date()));//Convert date to String(MM/dd/yyyy)
	
	System.out.println("Convert String(yyyy-MM-dd) to Date(MM/dd/yyyy) : " + DateConversionUtil.convertToUtilDate("2018-10-12"));//Convert String(yyyy-MM-dd) to date(MM/dd/yyyy)
	
	System.out.println("Convert String(yyyy-MM-dd HH:mm:ss z) to date with Zone : " + DateConversionUtil.convertStringToDateWithZone("2018-10-12 00:00:00 IST"));//Convert String(yyyy-MM-dd HH:mm:ss z) to date with Zone
	
  System.out.println("Convert String(MM-dd-yyyy) to ISO date : " + DateConversionUtil.convertStringToISODate("10-12-2018"));//Convert String(MM-dd-yyyy) to ISO date
  
  System.out.println("Convert String(MM/dd/yyyy) to ISO Local date : " + DateConversionUtil.convertStringToISOLocalDate("10/12/2018"));//Convert String(MM/dd/yyyy) to ISO Local date
  
  System.out.println("Convert Local date to Date : " + DateConversionUtil.convertToDate(LocalDate.now()));//Convert Local date to Date
  
	System.out.println("Convert String(MM/dd/yyyy) to date : " + DateConversionUtil.convertToDate("10/12/2018"));//Convert String(MM/dd/yyyy) to date

  System.out.println("Convert Date to LocalDate : " + DateConversionUtil.convertToLocalDate(new Date()));//Convert Date to LocalDate
  
  System.out.println("Convert String(yyyy-MM-dd) to date : " + DateConversionUtil.checkconvertStringToDate("2018-10-12"));//Convert String(yyyy-MM-dd) to date
  
  System.out.println("Convert String(yyyy-dd-MM) to date : " + DateConversionUtil.checkconvertStringToDateFormat("2018-12-10"));//Convert String(yyyy-dd-MM) to date
  
  System.out.println("Convert String(MM-dd-yyyy) to ISO date : " + DateConversionUtil.convertStringToISODate("10-12-2018"));//Convert String(MM-dd-yyyy) to ISO date
  
  System.out.println("Parse Date To String(yyyy-MM-dd) : " + DateConversionUtil.parseDateToString(new Date()));//Parse Date To String(yyyy-MM-dd)
  
  System.out.println("Parse Date to String(MMM d, yyyy hh:mm:ss aaa) : " + DateConversionUtil.parseDateToStringWithTime(new Date()));//Parse Date to String(MMM d, yyyy hh:mm:ss aaa)
  
  System.out.println("Parse Date To String(MMM dd, yyyy) : " + DateConversionUtil.parseWordDateFormatString(new Date()));//Parse Date To String(MMM dd, yyyy)
  
  System.out.println("Parse Date To String(MMM d, yyyy) : " + DateConversionUtil.parseWordDateToString(new Date()));//Parse Date To String(MMM d, yyyy)
  
  System.out.println("Parse Date To Date with End Time : " + DateConversionUtil.parseEndDateFormatWithDefaultTime(new Date()));//Parse Date To Date with End Time
  
  System.out.println("Parse LocalDate To Date with Start Time : " + DateConversionUtil.parseLocalDateFormatWithDefaultTime(LocalDate.now()));//Parse LocalDate To Date with Start Time
  
  System.out.println("Parse Date To Date with Start Time : " + DateConversionUtil.parseUtilDateFormatWithDefaultTime(new Date()));//Parse Date To Date with Start Time
  
  System.out.println("Get Current Date : " + DateUtil.getCurrentDate());//Get Current Date
  
  System.out.println("Get Index of short month : " + DateUtil.getIndexOfShortMonth("Jan"));//Get Index of short month
  
  System.out.println("Add days to Local date : " + DateUtil.addDaysToLocalDate(LocalDate.now(), 4));//Add days to Local date
  
  System.out.println("Add days to date : " + DateUtil.addDaysToUtilDate(new Date(), 3));//Add days to date
  
  System.out.println("Get Dates of month with year : " + DateUtil.getDatesOfMonthWithYear(2018, 2));//Get Dates of month with year
  
  System.out.println("Duration of limits : " + DateUtil.getDurationsByLimit(1, 5));//Duration of limits
  
  System.out.println("End of the day with time : " + DateUtil.getEndOfDay(new Date()));//End of the day with time
  
  System.out.println("First and last date of month : " + DateUtil.getFirstAndLastDateOfMonth(2010, 1));//First and last date of month
  
  System.out.println("First and last day of year : " + DateUtil.getFirstAndLastDateOfYear(2010));//First and last day of year
  
  System.out.println("List of Hours per day : " + DateUtil.getHoursPerDay());//List of Hours per day
  
  System.out.println("In-between years exclude start/end dates : " + DateUtil.getInBetweenYearsExcludeStartAndEnd(2010, 2020));//In-between years exclude start/end dates 
  
  System.out.println("In-between years include start/end dates : " + DateUtil.getInBetweenYearsIncludeStartAndEnd(2010, 2020));//In-between years include start/end dates
  
  System.out.println("Get List of Dates between two dates : " + DateUtil.getListOfDatesBetweenTwoDates("10/12/2018", "10/20/2018"));//Get List of Dates between two dates
  
  System.out.println("Get List of Short months with year(Jan 2020) : " + DateUtil.getMonthsForYear(2020));//Get List of Short months with year(Jan 2020)
  
  System.out.println("Get Quarter months : " + DateUtil.getQuartersForYear(2010));//Results arryas of quarter month-year([Jan 2018-Mar 2018],[Apr 2018-Jun 2018]...)
  
  System.out.println("Start of the day with time : " + DateUtil.getStartOfDay(new Date()));//Start of the day with time
  
  System.out.println("Get Number of Weeks in a month of Year : " + DateUtil.getWeekNumberForMonth(2010, 1));//Get Number of Weeks in a month of Year
  
  System.out.println("Is a valid date : " + DateUtil.isThisDateValid("2018-10-01", "yyyy-MM-dd"));//Check A Valid Date
  
  System.out.println("Check given date with in date range : " + DateUtil.isWithinRange(new Date("10/24/2018"), new Date("10/10/2018"), new Date("10/25/2018")));
  
}*/
}
