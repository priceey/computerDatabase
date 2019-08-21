package Utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;  

public class DateUtils {
	
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * Generates a random date. 
	 * @param str_start_date
	 * @return
	 * @throws ParseException
	 */
	public static String GetRandomDate(String str_start_date) throws ParseException {
		
		Calendar cal = Calendar.getInstance();
		
		if (str_start_date == null || str_start_date.isEmpty()) {
			str_start_date = "1970-01-01";
		}
  
		String str_end_date = dateFormat.format(Calendar.getInstance().getTime());
		
		cal.setTime(dateFormat.parse(str_start_date));
        Long value1 = cal.getTimeInMillis();

        cal.setTime(dateFormat.parse(str_end_date));
        Long value2 = cal.getTimeInMillis();

        long value3 = (long)(value1 + Math.random()*(value2 - value1));
        cal.setTimeInMillis(value3);
        
        return dateFormat.format(cal.getTime());
	}
	
	
	/**
	 * Takes a string date and using the current format and a format you woul like
	 * a new string date format is returned.
	 * @param dateString
	 * @param inputDateFormat
	 * @param outputDateFormat
	 * @return
	 * @throws ParseException
	 */
	public static String DateFormatter(String dateString, String inputDateFormat, String outputDateFormat) throws ParseException {
		
		DateFormat initialDateFormat = new SimpleDateFormat(inputDateFormat);
        Date date = initialDateFormat.parse(dateString);
        String parsedDate = new SimpleDateFormat(outputDateFormat).format(date);
        
        return parsedDate;
	}
	
}
