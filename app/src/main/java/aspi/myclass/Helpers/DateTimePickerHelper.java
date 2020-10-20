package aspi.myclass.Helpers;


import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.Calendar;



public class DateTimePickerHelper extends DatePickerDialog {

    final String TIMEPICKER = "TimePickerDialog", DATEPICKER = "DatePickerDialog", MULTIDATEPICKER = "MultiDatePickerDialog";

    public void getDate(Context context) {

        if (LanguageHelper.loadLanguage(context).equals("fa")) {
            getDataJalali(context);
        } else {
            getDataMiladi(context);
        }

    }

    public void getTime(Context context) {

        if (LanguageHelper.loadLanguage(context).equals("fa")) {
            getTimePersian(context);
        } else {
            getTimeEnglish(context);
        }

    }

    void getDataJalali(Context context) {

        final PersianCalendar now = new PersianCalendar();

        final DatePickerDialog dpd = DatePickerDialog.newInstance(
                (DatePickerDialog.OnDateSetListener) context,
                now.getPersianYear(),
                now.getPersianMonth(),
                now.getPersianDay()
        );


        dpd.show(((Activity) context).getFragmentManager(), TIMEPICKER);

    }

    void getDataMiladi(Context context) {

        Calendar calendar = Calendar.getInstance();


        android.app.DatePickerDialog dialog = new android.app.DatePickerDialog(((Activity) context)
                , (android.app.DatePickerDialog.OnDateSetListener) context
                , calendar.get(Calendar.DAY_OF_MONTH)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.YEAR));


        dialog.show();


    }

    void getTimePersian(Context context) {
        PersianCalendar now = new PersianCalendar();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                (TimePickerDialog.OnTimeSetListener) context,
                now.get(PersianCalendar.HOUR_OF_DAY),
                now.get(PersianCalendar.MINUTE),
                true);

        tpd.setThemeDark(true);
        tpd.show(((Activity) context).getFragmentManager(), TIMEPICKER);

    }

    void getTimeEnglish(Context context) {
        Calendar calendar = Calendar.getInstance();

        android.app.TimePickerDialog dialog = new android.app.TimePickerDialog(((Activity) context)
                , (android.app.TimePickerDialog.OnTimeSetListener) context
                , calendar.get(Calendar.HOUR)
                , calendar.get(Calendar.MINUTE)
                , true);

        dialog.show();

    }

}
