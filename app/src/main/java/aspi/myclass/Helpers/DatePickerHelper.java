package aspi.myclass.Helpers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.Calendar;


public class DatePickerHelper extends DatePickerDialog {

    final String TIMEPICKER = "TimePickerDialog", DATEPICKER = "DatePickerDialog", MULTIDATEPICKER = "MultiDatePickerDialog";

    public void getDate(Context context) {

        if (LanguageHelper.loadLanguage(context).equals("fa")) {
            getDataJalali(context);
        } else {
            getDataMiladi(context);
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


        android.app.DatePickerDialog dialog = new android.app.DatePickerDialog(((Activity) context), null
                , calendar.get(Calendar.DAY_OF_MONTH)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.YEAR));


        dialog.show();


    }


}
