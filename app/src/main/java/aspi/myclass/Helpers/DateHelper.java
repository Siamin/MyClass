package aspi.myclass.Helpers;

import java.util.Calendar;

public class DateHelper {


    public static String date_iran() {
        Calendar c = Calendar.getInstance();
        int year = 0, month = 0, day = 0;
        int y = c.get(Calendar.YEAR);
        int x = c.get(Calendar.DAY_OF_YEAR);
        //*******************************
        if (x >= 0 && x <= 20) {
            year = y - 622;
        } else if (x >= 21 && x <= 50) {
            year = y - 622;
        } else if (x >= 51 && x <= 79) {
            year = y - 622;
        } else if (x >= 80 && x <= 266) {
            year = y - 621;
        } else if (x >= 267 && x <= 365) {
            year = y - 621;
        }
        int mod = year % 33, kabise = 0;
        if (mod == 1 || mod == 5 || mod == 9 || mod == 13 || mod == 17 || mod == 22 || mod == 26 || mod == 30) {
            kabise = 1;
        } else {
            kabise = 0;
        }
        //*******************************
        if (x >= 0 && x <= 20) {
            month = 10;
            day = x + 10;
        } else if (x >= 21 && x <= 50) {
            month = 11;
            day = x - 20;
        } else if (x >= 51 && x <= 79 && kabise == 0) {
            month = 12;
            day = x - 40;
        } else if (x >= 51 && x <= 80 && kabise == 1) {
            month = 12;
            day = x - 49;
        } else if (x >= 80 && x <= 266 && kabise == 0) {
            x = x - 80;
            month = (x / 31) + 1;
            day = (x % 31) + 1;
        } else if (x >= 81 && x <= 266 && kabise == 1) {
            x = x - 79;
            month = (x / 31) + 1;
            day = (x % 31);
        } else if (x >= 267 && x <= 365) {
            x = x - 266;
            month = (x / 30) + 7;
            day = (x % 30) + 1;
        }
        String data = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
        return data;
    }

    public static String Get_Time() {
        Calendar c = Calendar.getInstance();
        int h = c.get(Calendar.HOUR_OF_DAY);
        String Time;
        if (h < 10) {
            Time = "0" + h + ":00";
        } else {
            Time = h + ":00";
        }

        return Time;
    }



}
