package aspi.myclass.Helpers;


import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import aspi.myclass.R;


public class IndicatorHelper {

    static Dialog dialog;

    public static void IndicatorCreate(Context context, String title, String body) {
        dialog = new Dialog(context, R.style.NewDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_indicator);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        //**********************************************
        TextView _title = (TextView) dialog.findViewById(R.id.dialog_indicator_title);
        TextView _body = (TextView) dialog.findViewById(R.id.dialog_indicator_bodetxt);
        //**********************************************
        _title.setText(title);
        _body.setText(body);
    }

    public static void IndicatorDismiss() {
        dialog.dismiss();
    }
}
