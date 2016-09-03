package utils;

import java.util.Calendar;

public class DateAndTime {
	
	public static int getCurrentHour() {
		Calendar time = Calendar.getInstance();
		int hour = time.get(Calendar.HOUR_OF_DAY);
		return hour;
	}

}
