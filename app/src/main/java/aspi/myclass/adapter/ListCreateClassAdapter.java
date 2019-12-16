package aspi.myclass.adapter;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.List;

import aspi.myclass.Helpers.DatePickerHelper;
import aspi.myclass.Helpers.DialogHelper;
import aspi.myclass.Helpers.IndicatorHelper;
import aspi.myclass.Helpers.MessageHelper;
import aspi.myclass.Helpers.ValidationHelper;
import aspi.myclass.model.OldClassModel;
import aspi.myclass.R;
import aspi.myclass.activity.MainActivity;
import aspi.myclass.activity.OldClassActivity;
import aspi.myclass.activity.OldClassListActivity;
import aspi.myclass.Helpers.DatabasesHelper;


public class ListCreateClassAdapter extends RecyclerView.Adapter<ListCreateClassAdapter.cvh> {

    private List<OldClassModel> Content_list_old;
    private Context contexts;
    static DatabasesHelper data;
    Activity activity;
    static String TAG = "TAG_ListCreateClassAdapter";

    public ListCreateClassAdapter(List<OldClassModel> contents, Context context, Activity Act) {
        this.Content_list_old = contents;
        this.contexts = context;
        data = new DatabasesHelper(context);
        activity = Act;
    }

    @Override
    public cvh onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_listcreatclass, parent, false);
        return new cvh(view);
    }

    @Override
    public void onBindViewHolder(final cvh holder, final int position) {
        final OldClassModel content = Content_list_old.get(position);


        holder.Data.setText(content.DATA);
        holder.time.setText(content.Hour);


        holder.time.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TOAST("time="+content.Hour);
            }
        });


        holder.Data.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Edit_Data(content.jalase);

            }
        });


        holder.loggin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ValidationHelper.isValidationBuyApp(contexts, "‌Buy_App") || position < 3) {
                    OldClassActivity.did_class = OldClassListActivity.id_class;
                    OldClassActivity.Name_class = OldClassListActivity.Name_class;
                    OldClassActivity.Data_class = content.DATA;
                    OldClassActivity.Jalase = content.jalase;
                    OldClassActivity.HOUR = content.Hour;
                    Intent old_class = new Intent(contexts, OldClassActivity.class);
                    contexts.startActivity(old_class);
                } else {
                    MessageHelper.Toast(contexts, contexts.getResources().getString(R.string.ErrorBuyApplication));
                }
            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ValidationHelper.isValidationBuyApp(contexts, "‌Buy_App") || position < 3) {
                    DialogHelper.DeleteOldSession(contexts, contexts.getResources().getString(R.string.doYouWantMateing) + " " + content.DATA
                            + " " + contexts.getResources().getString(R.string.inTime) + " " + content.Hour + " "
                            + contexts.getResources().getString(R.string.deleteMateing), content.jalase);
                } else {
                    MessageHelper.Toast(contexts, contexts.getResources().getString(R.string.ErrorBuyApplication));
                }
            }
        });

    }

    public int getItemCount() {
        return Content_list_old.size();
    }

    public class cvh extends RecyclerView.ViewHolder {

        private TextView Data, time;
        private Button loggin, delete;

        public cvh(View itemView) {
            super(itemView);


            Data = itemView.findViewById(R.id.list_lists_old_class_data);
            time = itemView.findViewById(R.id.list_lists_old_class_time);
            loggin = itemView.findViewById(R.id.list_lists_old_class_loggin);
            delete = itemView.findViewById(R.id.list_lists_old_class_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    OldClassModel conlist = Content_list_old.get(getPosition());
                }
            });
        }

    }

    public static void Delete_rollcall(final Context context, final String id_jalase, final Dialog dialog) {
        try {

            new Thread(new Runnable() {
                public void run() {
                    try {
                        data.open();
                        data.delete_("rollcall", "jalase=" + id_jalase);
                        data.close();
                        ((Activity) context).runOnUiThread(new Runnable() {
                            public void run() {
                                MessageHelper.Toast(context, context.getResources().getString(R.string.SuccessfullyDeleteMateing));
                                OldClassListActivity.refresh = "1";
                                dialog.dismiss();
                            }
                        });
                    } catch (Exception e) {
                        Log.i(TAG, "Error" + e.toString());
                    }


                }
            }).start();
        } catch (Exception e) {
            IndicatorHelper.IndicatorDismiss();
            Log.i(TAG, "Error :" + e.toString());
        }
    }

    void Edit_Data(final String jalase) {

        DatePickerHelper datePickerHelper = new DatePickerHelper();
        OldClassListActivity.sessions = jalase;
        datePickerHelper.getDate(contexts);


    }


}
