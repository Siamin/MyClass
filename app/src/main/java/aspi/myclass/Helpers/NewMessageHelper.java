package aspi.myclass.Helpers;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import aspi.myclass.R;

public class NewMessageHelper {

    private Context context;

    public NewMessageHelper(Context context) {
        this.context = context;
    }

    public void Toast(String Text) {
        Toast toast = Toast.makeText(context, "" + Text, Toast.LENGTH_LONG);
        TextView textView =  toast.getView().findViewById(android.R.id.message);
        textView.setPadding(5, 3, 5, 3);
        textView.setTextColor(context.getResources().getColor(R.color.white));
        textView.setTextSize(18);
        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.toast);
        toast.show();
    }

    public void Mesage(String text) {

        final Dialog massege = new Dialog(context, R.style.NewDialog);
        massege.requestWindowFeature(Window.FEATURE_NO_TITLE);
        massege.setContentView(R.layout.dialog_message);
        massege.setCancelable(false);
        massege.setCanceledOnTouchOutside(false);
        massege.show();

        final TextView ok = massege.findViewById(R.id.massge_btn);
        final TextView txt = massege.findViewById(R.id.massge_text);


        txt.setText("" + text);


        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                massege.dismiss();
            }
        });
    }

}
