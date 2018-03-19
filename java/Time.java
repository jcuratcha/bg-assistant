import java.util.*;

public class Time {
    Date time;
    Calendar cal;

    public Time() {
        time = new Date();
        cal = Calendar.getInstance(TimeZone.getTimeZone("CST"));
    }

    public int getHour() {
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute() {
        return cal.get(Calendar.MINUTE);
    }

    public int getSecond() {
        return cal.get(Calendar.SECOND);
    }

    public int getMillisecond() {
        return cal.get(Calendar.MILLISECOND);
    }

    public String getTime() {
        String time = Integer.toString(getHour()) + ":";
        time += Integer.toString(getMinute()) + ":";
        time += Integer.toString(getSecond()) + ".";
        time += Integer.toString(getMillisecond());
        return time;
    }

    public String getDateTimeFileFormat() {
        String time = getDate() + " -- ";
        time += Integer.toString(getHour()) + "-";
        time += Integer.toString(getMinute()) + "-";
        time += Integer.toString(getSecond());
        return time;
    }

    public String getDate() {
        String date = Integer.toString(cal.get(Calendar.MONTH)+1) + "-";  // +1 to Month because Jan = 0 and Dec = 11, Why???
        date += Integer.toString(cal.get(Calendar.DATE)) + "-";
        date += Integer.toString(cal.get(Calendar.YEAR));
        return date;
    }

    // assumes the caller of this method is later than time2
    public String getDifference(Time time2) {
        // convert difference into milliseconds
        int difference = 0;
        if (this.getHour() != time2.getHour()) {
            difference += (this.getHour() - time2.getHour()) * 3600000;
        }
        if (this.getMinute() != time2.getMinute()) {
            difference += (this.getMinute() - time2.getMinute()) * 60000;
        }
        if (this.getSecond() != time2.getSecond()) {
            difference += (this.getSecond() - time2.getSecond()) * 1000;
        }
        if (this.getMillisecond() != time2.getMillisecond()) {
            difference += this.getMillisecond() - time2.getMillisecond();
        }

        // convert total milliseconds difference into readable time string
        int hours = (int)((difference / 3600000) % 24);
        int minutes = (int)(((difference - hours*3600000) / 60000) % 60);
        int seconds = (int)(((difference - minutes*60000 - hours*3600000 ) / 1000) % 60);
        int milliseconds = (int)(difference - seconds*1000 - minutes*60000 -  hours*3600000);
        String strDiff = Integer.toString(hours) + ":" + Integer.toString(minutes) + ":" + Integer.toString(seconds) + "." + Integer.toString(milliseconds);

        return strDiff;
    }
}