package aspi.myclass.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.List;

import aspi.myclass.content.OldClassContent;
import aspi.myclass.R;
import aspi.myclass.activity.MainActivity;
import aspi.myclass.activity.OldClassActivity;
import aspi.myclass.activity.OldClassListActivity;
import aspi.myclass.class_.dbstudy;


public class ListCreateClassAdapter extends RecyclerView.Adapter<ListCreateClassAdapter.cvh> {

    private List<OldClassContent> Content_list_old;
    private Context contexts;
    private dbstudy data;
    Activity activity;

    public ListCreateClassAdapter(List<OldClassContent> contents, Context context, Activity Act) {
        this.Content_list_old = contents;
        this.contexts = context;
        data = new dbstudy(context);
        activity = Act;
    }

    @Override
    public cvh onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_listcreatclass, parent, false);
        return new cvh(view);
    }

    @Override
    public void onBindViewHolder(final cvh holder, final int position) {
        final OldClassContent content = Content_list_old.get(position);
        //*************************************************************************
        holder.row.setText("" + (position + 1));
        holder.Data.setText("" + content.DATA);
        holder.time.setText("" + content.Hour);
        //*************************************************************************
        holder.row.setTypeface(MainActivity.FONTS);
        holder.Data.setTypeface(MainActivity.FONTS);
        holder.loggin.setTypeface(MainActivity.FONTS);
        holder.delete.setTypeface(MainActivity.FONTS);
        holder.time.setTypeface(MainActivity.FONTS);
        //*************************************************************************
        holder.time.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              // TOAST("time="+content.Hour);
            }
        });
        //*************************************************************************
        holder.Data.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Edit_Data(content.DATA.split("/"),content.jalase);

            }
        });
        //*************************************************************************
        holder.loggin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MainActivity.BUYAPP.equals("Buy_App") || position<3) {
                    SharedPreferences sp = contexts.getSharedPreferences("myclass", 0);
                    float amozesh = sp.getFloat("LerningActivity", 0);
                    if (amozesh == 17)SetCode(18);

                    OldClassActivity.did_class= OldClassListActivity.id_class;
                    OldClassActivity.Name_class= OldClassListActivity.Name_class;
                    //OldClassActivity.Row=Integer.parseInt(content.Row_rollcall);
                    OldClassActivity.Data_class=content.DATA;
                    OldClassActivity.Jalase=content.jalase;
                    OldClassActivity.HOUR=content.Hour;
                    Intent old_class=new Intent(contexts, OldClassActivity.class);
                    contexts.startActivity(old_class);
                }else{
                    TOAST("برای استفاده از این امکانات باید نسخه ای کامل برنامه را خریداری کنید.");
                }
            }
        });
        //*************************************************************************
        holder.delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MainActivity.BUYAPP.equals("Buy_App") || position<3) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(contexts,R.style.MyAlertDialogStyle);
                    final TextView input = new TextView(contexts);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);
                    input.setText("آیا میخواهید جلسه"+content.DATA+" در ساعت "+content.Hour+" را حذف کنید؟");
                    input.setTypeface(MainActivity.FONTS);
                    input.setTextSize(15);
                    input.setPadding(10,10,5,0);
                    input.setTextColor(contexts.getResources().getColor(R.color.toast));
                    builder1.setView(input);
                    builder1.setTitle("حذف جلسه ");
                    builder1.setPositiveButton("حذف", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            Delete_rollcall(content.jalase);
                        }
                    });
                    builder1.setNegativeButton("انصراف", null);
                    AlertDialog aler1 = builder1.create();
                    aler1.show();
                }else{
                    TOAST("برای استفاده از این امکانات باید نسخه ای کامل برنامه را خریداری کنید.");
                }
            }
        });
    }

    public int getItemCount() {
        return Content_list_old.size();
    }

    public class cvh extends RecyclerView.ViewHolder {

        private TextView row, Data, time;
        private Button loggin, delete;

        public cvh(View itemView) {
            super(itemView);

            row = (TextView) itemView.findViewById(R.id.list_lists_old_class_row);
            Data = (TextView) itemView.findViewById(R.id.list_lists_old_class_data);
            time = (TextView) itemView.findViewById(R.id.list_lists_old_class_time);
            loggin = (Button) itemView.findViewById(R.id.list_lists_old_class_loggin);
            delete = (Button) itemView.findViewById(R.id.list_lists_old_class_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    OldClassContent conlist = Content_list_old.get(getPosition());
                }
            });
        }

    }

    void Delete_rollcall(final String id_jalase) {
        try {
            final ProgressDialog progressDialog = new ProgressDialog(contexts, R.style.NewDialog);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressDialog.setProgress(0);
            progressDialog.setProgressDrawable(contexts.getResources().getDrawable(R.drawable.dialog));
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setTitle("حذف جلسه");
            progressDialog.setCancelable(false);
            progressDialog.setMessage("لطفا صبر کنید ...!");
            progressDialog.show();

            new Thread(new Runnable() {
                public void run() {
                    try {
                        data.open();
                        data.delete_("rollcall","jalase="+id_jalase);
                        data.close();
                        activity.runOnUiThread(new Runnable() {
                            public void run() {
                                TOAST("جلسه با موفقیت حذف شد..!");
                                OldClassListActivity.refresh = "1";
                            }
                        });
                    } catch (Exception e) {
                        TOAST(e.toString());
                    }
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.cancel();
                        }
                    });

                }
            }).start();
        } catch (Exception e) {
           // TOAST("THREAD \n" + e.toString());
        }
    }

    void TOAST(String TEXT) {
        Toast toast = Toast.makeText(contexts, "" + TEXT, Toast.LENGTH_LONG);
        TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
        textView.setTextColor(contexts.getResources().getColor(R.color.toast));
        textView.setTypeface(MainActivity.FONTS);
        textView.setTextSize(18);
        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.toast);
        toast.show();
    }

    void SetCode(float code) {
        SharedPreferences sp = contexts.getSharedPreferences("myclass", 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putFloat("LerningActivity", code);
        edit.commit();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    void Edit_Data(String[] Datas, final String jalase){
        final Dialog edit = new Dialog(contexts, R.style.NewDialog);
        edit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        edit.setContentView(R.layout.dialog_editdatecreatclass);
        edit.setCancelable(true);
        edit.setTitle("ویرایش تاریخ");
        edit.setCanceledOnTouchOutside(true);
        edit.show();
        final Button save = (Button) edit.findViewById(R.id.edit_data_save);
        final Button cancel = (Button) edit.findViewById(R.id.edit_data_cancel);
        final Spinner yaer= (Spinner) edit.findViewById(R.id.edit_data_y);
        final Spinner month= (Spinner) edit.findViewById(R.id.edit_data_m);
        final Spinner day= (Spinner) edit.findViewById(R.id.edit_data_d);
        //******************************************************************************************
        final String[] Yaer,Month,Day;
        String Y="",M="",D="";
        Calendar c = Calendar.getInstance();
        int yy = 0;
        int y = c.get(Calendar.YEAR);
        int x = c.get(Calendar.DAY_OF_YEAR);
        //*******************************
        if (x >= 0 && x <= 20) {
            yy = y - 622;
        } else if (x >= 21 && x <= 50) {
            yy = y - 622;
        } else if (x >= 51 && x <= 79) {
            yy = y - 622;
        } else if (x >= 80 && x <= 266) {
            yy = y - 621;
        } else if (x >= 267 && x <= 365) {
            yy = y - 621;
        }
        for (int i=0;i<30;i++)
            Y+=String.valueOf(yy-i)+"~";

        for (int i=1;i<=12;i++)
            M+=String.valueOf(i)+"~";

        for (int i=1;i<=31;i++)
            D+=String.valueOf(i)+"~";

        Yaer=Y.split("~");
        Month=M.split("~");
        Day=D.split("~");
        ArrayAdapter<String> yaers=new  ArrayAdapter<String>(contexts,android.R.layout.simple_spinner_item,Yaer);
        yaer.setAdapter(yaers);
        ArrayAdapter<String> months=new  ArrayAdapter<String>(contexts,android.R.layout.simple_spinner_item,Month);
        month.setAdapter(months);
        ArrayAdapter<String> days=new  ArrayAdapter<String>(contexts,android.R.layout.simple_spinner_item,Day);
        day.setAdapter(days);

        //******************************************************************************************
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
        try{
            data.open();
            data.update("day",Yaer[yaer.getSelectedItemPosition()],"month",Month[month.getSelectedItemPosition()],"year",Day[day.getSelectedItemPosition()],"jalase="+jalase);
            data.close();
            TOAST("ویرایش انجام شد.");
            edit.dismiss();
            OldClassListActivity.refresh = "1";
        }catch (Exception e){
           // TOAST(e.toString());
        }

            }
        });
        //******************************************************************************************
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                edit.dismiss();
            }
        });
        //******************************************************************************************

    }
}
