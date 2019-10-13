package aspi.myclass.Helpers;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import aspi.myclass.R;
import aspi.myclass.activity.MainActivity;
import aspi.myclass.model.ReportDataModel;

public class TableHelper {

    static String TAG = "TAG_TableHelper";

    public static void creatTabel(Context context, List<ReportDataModel> model,boolean TypePage,TableLayout table) {

        try {

            List<ReportDataModel> modelCheck = new ArrayList<>();
            List<ReportDataModel> Datemodel = new ArrayList<>();

            for (int row = -1, color = -1; row < model.size(); row++) {

                TableRow tr = new TableRow(context);
                tr.setBackgroundColor(Color.BLACK);
                tr.setPadding(0, 0, 0, 2);

                TableRow.LayoutParams llp = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                llp.setMargins(0, 0, 0, 0);
                LinearLayout cell = new LinearLayout(context);
                if (color == -1) {
                    cell.setBackgroundColor(context.getResources().getColor(R.color.table1));
                } else if (color % 2 == 0) {
                    cell.setBackgroundColor(context.getResources().getColor(R.color.table2));
                } else if (color % 2 == 1) {
                    cell.setBackgroundColor(context.getResources().getColor(R.color.table3));
                }
                cell.setLayoutParams(llp);
                cell.setPadding(0, 0, 0, 0);
                boolean statusSno = (row > -1 ? ValidationHelper.validationSnoInModel(modelCheck, model.get(row).sno) : true);

                if (statusSno) {
                    color++;
                    boolean Status_Date = true;
                    for (int fild = -1; fild < model.size(); fild++) {
                        if (row == -1 && fild == -1) {
                            cell.addView(CreateTextView(context,"نام و نام خانوادگی", 300));

                        } else if (row > -1 && fild == -1) {
                            modelCheck.add(model.get(row));
                            cell.addView(CreateTextView(context,model.get(row).name + " " + model.get(row).family, 300));

                        } else if (row > -1 && fild > -1 && model.get(row).sno.equals(model.get(fild).sno)) {
                            String txt = "";
                            int CountDate = (Status_Date ? ValidationHelper.validationDateInModel(Datemodel, model.get(fild).date) : -1);
                            Status_Date = false;
                            if (CountDate > 0) {
                                for (int i = 0; i < CountDate; i++) {
                                    if (TypePage)
                                        cell.addView(CreateTextView(context,"-", 70));
                                    else
                                        cell.addView(CreateTextView(context,"", 70));
                                }
                            }

                            if (TypePage) {
                                if (model.get(fild).status.equals("1")) {
                                    txt = "√";
                                } else if (model.get(fild).status.equals("0")) {
                                    txt = "غ";
                                } else {
                                    txt = "-";
                                }
                            } else if (!TypePage) {
                                txt = model.get(fild).score;
                            }

                            cell.addView(CreateTextView(context,txt, 70));

                        } else if (row == -1 && fild > -1 && model.get(row + 1).sno.equals(model.get(fild).sno)) {
                            cell.addView(CreateTextView(context,model.get(fild).date.replace("/", "\n-\n"), 70));
                            Datemodel.add(model.get(fild));
                        }
                    }
                    tr.addView(cell);
                    table.addView(tr);
                }

            }
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }

    }

    static TextView CreateTextView(Context context,String txt, int wight) {

        TextView textView = new TextView(context);
        textView.setText(txt);
        textView.setPadding(5, 5, 5, 5);
        textView.setTextSize(15);
        textView.setTypeface(MainActivity.FONTS);
        textView.setLayoutParams(new TableRow.LayoutParams(wight, TableRow.LayoutParams.FILL_PARENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(context.getResources().getColor(R.color.white));

        return textView;
    }
}
