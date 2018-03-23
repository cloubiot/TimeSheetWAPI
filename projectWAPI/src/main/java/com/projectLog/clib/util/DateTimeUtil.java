 package com.projectLog.clib.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtil {

	public static String getTodayString(){

		//Date date = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		String today = formatter.format(new Date());
		
		return  today;
	}
	public static Date stringToDate(String argDate) {
		Date t = null;
		try {
			//TimeZone tz = TimeZone.getDefault();
			SimpleDateFormat sdf = new SimpleDateFormat(" yyyy.MM.dd 'at' hh:mm:ss a");
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			sdf.setLenient(false);
			//sdf.setTimeZone(tz);
			t = new Date(sdf.parse(argDate).getTime());

		} catch (java.text.ParseException pe) {
			//logger_.debug("ParseException encountered in stringToTimestamp. String = " + argDate + ", Msg = " + pe.getMessage());
		}
		return t;
	}
	public  static String convertStringToDate(Date indate)
	{
	   String dateString = null;
	   SimpleDateFormat sdfr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   /*you can also use DateFormat reference instead of SimpleDateFormat 
	    * like this: DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
	    */
	   try{
		dateString = sdfr.format( indate );
	   }catch (Exception ex ){
		System.out.println(ex);
	   }
	   return dateString;
	}
	public static Date parse(String value, DateFormat... formatters) {
	    Date date = null;
	    for (DateFormat formatter : formatters) {
	      try {
	        date = formatter.parse(value);
	        break;
	      } catch (ParseException e) {
	      }
	    }
	    return date;
	  }
	public static String getDateAndTime(){
		Date dNow = new Date( );
	      SimpleDateFormat ft = 
	      new SimpleDateFormat ("yyyy-MM-dd HH:mm:s");
	      return ft.format(dNow);
	}
	
	public static String timeDifference(String checkIn,String checkOut) throws ParseException{
		String dateDiff = null;
		SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm:ss");
	       SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm:ss a");
	       System.out.println(checkIn+" nad "+checkOut);
	     //  Date startDate = parseFormat.parse(checkIn);
//	       Date endDate = parseFormat.parse(checkOut);
//	      
//	       
//	       String time1 = displayFormat.format(startDate);
//	       String time2 = displayFormat.format(endDate);
//	       
//	       System.out.println(time1+" nad "+time2);
	       SimpleDateFormat format = new SimpleDateFormat("HH:mm");
	       Date date1 = format.parse(checkIn);
	       Date date2 = format.parse(checkOut);
	       
	       long end = date2.getTime() - date1.getTime(); 
	     //  long difference = Interger.parseFormat.format(endDate) - startDate.getTime(); 
	       long finalValue = end/3600000;
	       System.out.println(end/3600000);
	       dateDiff = String.valueOf(finalValue);
	       
		return dateDiff;
	}
	public static Date getTodayAsDate(){
	 return new Date();
	}
	public static void main(String s[]) throws ParseException{
		String time = "8:30";

		DateFormat sdf = new SimpleDateFormat("HH:mm");
		Date date = sdf.parse(time);
		String datyu = sdf.format(date);

		System.out.println("Time: " + sdf.format(date));
		
		//System.out.println(getTodayAsDate());
	}
}
