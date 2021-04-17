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

import java.util.List;

import aspi.myclass.Helpers.DateTimePickerHelper;
import aspi.myclass.Helpers.DialogHelper;
import aspi.myclass.Helpers.IndicatorHelper;
import aspi.myclass.Helpers.MessageHelper;
import aspi.myclass.Helpers.ValidationHelper;
import aspi.myclass.model.OldClassModel;
import aspi.myclass.R;
import aspi.myclass.activity.MettingLastActivity;
import aspi.myclass.activity.MettingListActivity;
import aspi.myclass.Helpers.DatabasesHelper;


public class ListCreateClassAdapter extends RecyclerView.Adapter<ListCreateClassAdapter.cvh> {

    private List<OldClassModel> Content_list_old;
    private Context contexts;
    static DatabasesHelper data;
    static String TAG = "TAG_ListCreateClassAdapter";

    public ListCreateClassAdapter(List<OldClassModel> contents, Context context) {
        this.Content_list_old = contents;
        this.contexts = context;
        this.data = new DatabasesHelper(context);

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
                EditData(content.jalase);

            }
        });


        holder.loggin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ValidationHelper.validBuyApp(contexts) || position < 3) {
                    MettingLastActivity.did_class = MettingListActivity.id_class;
                    MettingLastActivity.Name_class = MettingListActivity.Name_class;
                    MettingLastActivity.Data_class = content.DATA;
                    MettingLastActivity.Jalase = content.jalase;
                    MettingLastActivity.HOUR = content.Hour;
                    Intent old_class = new Intent(contexts, MettingLastActivity.class);
                    contexts.startActivity(old_class);
                } else {
                    MessageHelper.Toast(contexts, contexts.getResources().getString(R.string.ErrorBuyApplication));
                }
            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ValidationHelper.validBuyApp(contexts) || position < 3) {
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
                                MettingListActivity.refresh = "1";
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

    void EditData(final String jalase) {

        DateTimePickerHelper datePickerHelper = new DateTimePickerHelper();
        MettingListActivity.sessions = jalase;
        datePickerHelper.getDate(contexts);


    }


}
