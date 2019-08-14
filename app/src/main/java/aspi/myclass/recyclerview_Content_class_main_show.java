package aspi.myclass;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.List;


public class recyclerview_Content_class_main_show extends RecyclerView.Adapter<recyclerview_Content_class_main_show.cvh> {

    private List<Content_class_main_show> content_class_main_shows;
    private Context contexts;
    private dbstudy data;
    Activity activity;
    OtherMetod om = new OtherMetod();

    public recyclerview_Content_class_main_show(List<Content_class_main_show> contents, Context context, Activity activitys) {
        this.content_class_main_shows = contents;

        this.contexts = context;
        data = new dbstudy(context);
        activity = activitys;
    }

    @Override
    public cvh onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_class_in_main, parent, false);
        return new cvh(view);
    }

    @Override
    public void onBindViewHolder(cvh holder, int position) {
        final Content_class_main_show content = content_class_main_shows.get(position);

        if (!content.name_class.equals("")) {
            holder.row.setText("" + (position + 1) + "");
            holder.class_.setText("کلاس: " + content.Class_);
        } else {
            holder.row.setVisibility(View.INVISIBLE);
            content.name_class = "هیچ کلاسی در این روز ثبت نشده";
            holder.class_.setText("  ");

        }
        holder.name_class.setText("" + content.name_class);
        holder.time_start.setText("" + content.time_start);
        holder.time_end.setText("" + content.time_end);
        holder.location.setText("" + content.location);
        if (content.text_class.length() > 2) holder.txt.setText("" + content.text_class);

        //***********************************************
    }

    @Override
    public int getItemCount() {
        return content_class_main_shows.size();
    }

    public class cvh extends RecyclerView.ViewHolder {

        private TextView row, name_class, time_start, time_end, location, class_, txt;

        public cvh(View itemView) {
            super(itemView);

            row = (TextView) itemView.findViewById(R.id.list_class_in_main_row);
            name_class = (TextView) itemView.findViewById(R.id.list_class_in_main_name_class);
            time_start = (TextView) itemView.findViewById(R.id.list_class_in_main_start_time);
            time_end = (TextView) itemView.findViewById(R.id.list_class_in_main_end_time);
            location = (TextView) itemView.findViewById(R.id.list_class_in_main_location);
            class_ = (TextView) itemView.findViewById(R.id.list_class_in_main_class);
            txt = (TextView) itemView.findViewById(R.id.list_class_in_main_txt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Content_class_main_show conlist = content_class_main_shows.get(getPosition());
                    SharedPreferences sp = contexts.getSharedPreferences("myclass", 0);
                    float amozesh = sp.getFloat("Amozesh", 0);
                    if (conlist.APP) {
                        items(conlist.name_class, conlist.id, conlist.location, conlist.time_start, conlist.time_end, conlist.characteristic, conlist.id_class,conlist.text_class);
                    }

                    if (amozesh > 4) {
                        if (amozesh > 8) {
                            if (amozesh > 16) {
                                if (amozesh >20) {
                                    if (amozesh >22) {
                                        if (amozesh >24) {
                                            if (amozesh >26) {
                                                if (amozesh >28) {
                                                }else if (amozesh ==27 || amozesh ==28){
                                                    Mesage("شما میتوانید برای گرفتن مستندات از کلاس مورد نظرتان از گزینه ی خروجی گرفتن استفاده کنید .گزینه ی خروجی از کلاس را انتخاب کنید.");
                                                }
                                            }else if (amozesh ==25 || amozesh ==26){
                                               Mesage("شما می توانید با انتخاب گزینه ی حذف کلاس اطلاعات کلاس مورد نظرتان را حذف کنید.");
                                            }
                                        }else if (amozesh ==23 || amozesh ==24){
                                            Mesage("شما همچنین می توانید با انتخاب گزینه ی ایام هفته کلاس انتخاب شده ی خود را در دیگر روز های ایام هفته ذخیره کنید.گزینه ی ایام هفته را انتخاب کنید.");
                                        }
                                    }else if (amozesh ==21 || amozesh ==22){
                                        Mesage("شما همچنین می توانید با انتخاب گزینه ی ویرایش کلاس، اطلاعات کلاس خود را ویرایش کنید.");
                                    }
                                }else if (amozesh ==19 || amozesh ==20){
                                    Mesage("شما همچنین می توانید با انتخاب گزینه ی توضیحات،برای درس مورد نظرتان توضیحات وارد کنیداین توضیحات تا زمانی که کلاس درس را حذف نکنید پاک نمی شود و در قسمت توضیحات کلاس نمایش داده می شود.");
                                }
                            } else {
                                Mesage("با انتخاب گزینه جلسات گذشته شما می توانید لیست جلسات برگزار شده درس را مشاهده کنید.گزینه ی جلسات گذشته را انتخاب کنید.");
                            }
                        } else if (amozesh == 7) {
                            Mesage("با انتخاب گزینه جلسه ی جدید شما وارد کلاس می شوید و برای کلاس یک لیست حضور و غیاب تشکیل داده می شود.");
                        }
                    } else {
                        Mesage("برای تشکیل جلسات کلاس ابتدا باید با انتخاب گزینه ی دانشجوی جدید به کلاس تان دانشجو اضافه کنید.");
                    }
                    if (amozesh == 3) SetCode(4);
                    else if (amozesh == 7) SetCode(8);

                }
            });
        }

    }

    void SetCode(float code) {
        SharedPreferences sp = contexts.getSharedPreferences("myclass", 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putFloat("Amozesh", code);
        edit.commit();
    }

    void Mesage(String text) {
        final Dialog massege = new Dialog(contexts, R.style.MyAlertDialogStyle);
        massege.setContentView(R.layout.massge);
        massege.setCancelable(false);
        massege.setCanceledOnTouchOutside(false);
        massege.show();
        final TextView ok = (TextView) massege.findViewById(R.id.massge_btn);
        final TextView txt = (TextView) massege.findViewById(R.id.massge_text);
        //**********************************************************************
        txt.setText("" + text);
        txt.setTypeface(Typeface.createFromAsset(contexts.getAssets(), "Font/font2.ttf"));
        ok.setTypeface(Typeface.createFromAsset(contexts.getAssets(), "Font/font2.ttf"));
        //**********************************************************************
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                massege.dismiss();
            }
        });
    }

    void items(final String Class, final String id, final String location, final String starttime, final String endtime, final String Characteristic, final String did,final String TXT) {
        SharedPreferences sp = contexts.getSharedPreferences("myclass", 0);
        final float amozesh = sp.getFloat("Amozesh", 0);
        final Dialog kelas = new Dialog(contexts, R.style.MyAlertDialogStyle);
        kelas.setTitle("درس " + Class);
        kelas.setContentView(R.layout.list_class_chang);
        kelas.setCancelable(true);
        kelas.setCanceledOnTouchOutside(true);
        kelas.show();
        final Button new_class = (Button) kelas.findViewById(R.id.list_class_chang_new_class);
        final Button add_student_class = (Button) kelas.findViewById(R.id.list_class_chang_add_student_class);
        final Button old_class = (Button) kelas.findViewById(R.id.list_class_chang_old_class);
        final Button edit_class = (Button) kelas.findViewById(R.id.list_class_chang_edit_class);
        final Button delete_class = (Button) kelas.findViewById(R.id.list_class_chang_delete_class);
        final Button output_class = (Button) kelas.findViewById(R.id.list_class_chang_output_class);
        final Button text_class = (Button) kelas.findViewById(R.id.list_class_chang_text_class);
        final Button week_class = (Button) kelas.findViewById(R.id.list_class_chang_add_class_of_week_class);
        //******************************************************************************************



        //******************************************************************************************
        week_class.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (om.get_Data("‌Buy_App","NO",contexts).equals("Buy_App") || Integer.parseInt(did) <= 10) {
                    kelas.cancel();
                    Set_Of_Week(Class, location, Characteristic, did,TXT);
                    if (amozesh ==24) SetCode(25);
                } else {
                    TOAST("برای استفاده از این امکانات باید نسخه ای کامل برنامه را خریداری کنید.");
                }
            }
        });
        //******************************************************************************************
        new_class.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (amozesh == 8) SetCode(9);
                kelas.cancel();
                New_class.Name_class = Class;
                New_class.did = did;
                Intent New_class = new Intent(contexts, New_class.class);
                contexts.startActivity(New_class);
            }
        });
        //******************************************************************************************
        add_student_class.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (amozesh == 4) SetCode(5);
                kelas.cancel();
                Add_student.id_class = did;
                Add_student.Name_class = Class;
                Add_student.location = location;
                Add_student.Start_time = starttime;
                Intent student = new Intent(contexts, Add_student.class);
                contexts.startActivity(student);
            }
        });
        //******************************************************************************************
        old_class.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (amozesh == 16) SetCode(17);
                Show_list_old_class.id_class = did;
                Show_list_old_class.Name_class = Class;
                Intent Show_list_old_class = new Intent(contexts, Show_list_old_class.class);
                contexts.startActivity(Show_list_old_class);
                kelas.cancel();
            }
        });
        //******************************************************************************************
        edit_class.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                kelas.dismiss();
                if (amozesh == 22) SetCode(23);
                Edit_class.ID_Class = id;
                Edit_class.Name_Class = Class;
                Edit_class.Location_Class = location;
                Edit_class.Start_Class = starttime;
                Edit_class.End_Class = endtime;
                Intent edit_class = new Intent(contexts, Edit_class.class);
                contexts.startActivity(edit_class);
            }
        });
        //******************************************************************************************
        delete_class.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (amozesh == 26) SetCode(27);
                kelas.cancel();
                AlertDialog.Builder builder = new AlertDialog.Builder(contexts, R.style.MyAlertDialogStyle);
                builder.setIcon(R.drawable.delete);
                builder.setTitle("حذف درس " + Class).setMessage("شما با حذف درس " + Class + " " + "تمامی کلاس های برگزار شده این درس نیز حذف می شوند.");
                builder.setPositiveButton("حذف", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {
                            data.open();
                            data.delete("dars", Integer.parseInt(id));
                            data.close();
                            Main.refresh = 1;
                            TOAST("کلاس حذف شد...!");
                        } catch (Exception e) {
                        }
                    }
                });
                builder.setNegativeButton("انصراف", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (amozesh == 27) Main.refresh = 1;
                    }
                });
                AlertDialog aler = builder.create();
                aler.show();
            }
        });
        //******************************************************************************************
        output_class.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                kelas.dismiss();
                statistics(Class, id, location, starttime, endtime, Characteristic, did);
            }
        });
        //******************************************************************************************
        text_class.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    kelas.dismiss();
                    final AlertDialog.Builder builder1 = new AlertDialog.Builder(contexts, R.style.MyAlertDialogStyle);
                    final EditText input = new EditText(contexts);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(10, 10, 5, 0);
                    input.setLayoutParams(lp);
                    try {
                        data.open();
                        int cunt = data.count("dars");
                        for (int i = 0; i < cunt; i++) {
                            if (data.Display("dars", i, 0).equals(id)) {
                                input.setText(data.Display("dars", i, 7));
                                break;
                            }
                        }
                        data.close();
                    } catch (Exception e) {

                    }
                    input.setHint("توضیحات را بنویسید...!" + "\n\n");
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder1.setView(input);
                    builder1.setTitle("توضیحات درس" + " " + Class);
                    builder1.setPositiveButton("ذخیره", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            try {
                                if (!input.getText().toString().equals("")) {
                                    data.open();
                                    data.update_one1("dars", "txt", input.getText().toString(), "did="+did);
                                    data.close();
                                    TOAST("ذخیره شد...!");
                                    if (amozesh != 20) Main.refresh = 1;
                                }
                            } catch (Exception e) {
                            }
                        }
                    });
                    builder1.setNegativeButton("انصراف", null);
                    AlertDialog aler1 = builder1.create();
                    aler1.show();
                    if (amozesh == 20){
                        SetCode(21);
                        Mesage("شما می توانید در این پنجره توضیحات را نوشته و سپس گزینه ی ذخیره را انتخاب کنید.");
                    }
                } catch (Exception e) {

                }
            }
        });
        //******************************************************************************************
    }

    void Set_Of_Week(final String Class, final String location, final String Characteristic, final String did,final String TXT) {
        final Dialog Week = new Dialog(contexts);
        Week.setTitle("اضافه کردن به ایام هفته");
        Week.setContentView(R.layout.class_of_week);
        Week.setCancelable(false);
        Week.setCanceledOnTouchOutside(false);
        Week.show();
        //**********************************************************************************
        final Button save_ = (Button) Week.findViewById(R.id.class_of_week_save);
        final Button cancel_ = (Button) Week.findViewById(R.id.class_of_week_cancel);
        final Spinner Spiner_ = (Spinner) Week.findViewById(R.id.class_of_week_spinner);
        final EditText room = (EditText) Week.findViewById(R.id.class_of_week_class_edit);
        final TimePicker start_time = (TimePicker) Week.findViewById(R.id.class_of_week_start_time_picker);
        final TimePicker end_time = (TimePicker) Week.findViewById(R.id.class_of_week_end_time_picker);
        final TextView t1 = (TextView) Week.findViewById(R.id.class_of_week_class_text);
        final TextView t2 = (TextView) Week.findViewById(R.id.class_of_week_day_text);
        //**********************************************************************************
        String[] Day_of_week = {"شنبه", "یکشنبه", "دوشنبه", "سه شنبه", "چهارشنبه", "پنج شنبه", "جمعه"};

        ArrayAdapter<String> a = new ArrayAdapter<String>(contexts, android.R.layout.simple_spinner_item, Day_of_week);
        Spiner_.setAdapter(a);

        start_time.setIs24HourView(true);
        end_time.setIs24HourView(true);
        //**********************************************************************************
        save_.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int day_of = Spiner_.getSelectedItemPosition();
                int hour_start = start_time.getCurrentHour();
                int minute_start = start_time.getCurrentMinute();
                int hour_end = end_time.getCurrentHour();
                int minute_end = end_time.getCurrentMinute();
                String HS = String.valueOf(hour_start);
                String MS = String.valueOf(minute_start);
                String HE = String.valueOf(hour_end);
                String ME = String.valueOf(minute_end);
                if (HE.length() == 1) {
                    HE = "0" + HE;
                }
                if (HS.length() == 1) {
                    HS = "0" + HS;
                }
                if (MS.length() == 1) {
                    MS = "0" + MS;
                }
                if (ME.length() == 1) {
                    ME = "0" + ME;
                }
                try {
                    data.open();
                    data.insert_class(Class, String.valueOf(day_of), String.valueOf(HS) + ":" + String.valueOf(MS), location, String.valueOf(HE) + ":" + String.valueOf(ME), Characteristic, room.getText().toString(), did);
                    data.update_one1("dars", "txt", TXT, "did="+did);
                    data.close();
                    Main.refresh = 1;
                    Week.cancel();
                } catch (Exception e) {
                }
            }
        });
        //**********************************************************************************
        cancel_.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Week.cancel();
            }
        });
        //**********************************************************************************
    }

    void TOAST(String TEXT) {
        Toast toast = Toast.makeText(contexts, "" + TEXT, Toast.LENGTH_LONG);
        TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
        textView.setTextColor(contexts.getResources().getColor(R.color.toast));
        textView.setTypeface(Main.FONTS);
        textView.setTextSize(18);
        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.toast);
        toast.show();
    }

    void statistics(final String Class, final String id, final String location, final String starttime, final String endtime, final String Characteristic, final String did) {
        final SharedPreferences sp = contexts.getSharedPreferences("myclass", 0);
        final float amozesh = sp.getFloat("Amozesh", 0);
        final Dialog statistics_ = new Dialog(contexts, R.style.MyAlertDialogStyle);
        statistics_.setTitle("درس " + Class);
        statistics_.setContentView(R.layout.list_statistics_class);
        statistics_.setCancelable(true);
        statistics_.setCanceledOnTouchOutside(true);
        statistics_.show();
        final Button absent_student = (Button) statistics_.findViewById(R.id.list_statistics_class_absent_student);
        final Button absent_students = (Button) statistics_.findViewById(R.id.list_statistics_class_absent_students);
        final Button nomreh_students = (Button) statistics_.findViewById(R.id.list_statistics_class_nomreh_students);
        //*************************************************************************************************

        //******************************************************************************************
        absent_student.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (om.get_Data("‌Buy_App","NO",contexts).equals("Buy_App")) {
                    if (amozesh ==28 || amozesh ==29) SetCode(30);
                    statistics_.cancel();
                    Show_student.Name_class = Class;
                    Show_student.Id_class = id;
                    Show_student.Did_class = did;
                    contexts.startActivity(new Intent(contexts, Show_student.class));
                } else {
                    TOAST("برای استفاده از این امکانات باید نسخه ای کامل برنامه را خریداری کنید.");
                }

            }
        });
        //******************************************************************************************
        absent_students.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (om.get_Data("‌Buy_App","NO",contexts).equals("Buy_App")) {
                    if (amozesh ==30) SetCode(31);
                    statistics_.cancel();
                    Output_list.Name_class = Class;
                    Output_list.Id_class = id;
                    Output_list.Did_class = did;
                    Output_list.STATUS = true;
                    contexts.startActivity(new Intent(contexts, Output_list.class));
                } else {
                    TOAST("برای استفاده از این امکانات باید نسخه ای کامل برنامه را خریداری کنید.");
                }

            }
        });
        //******************************************************************************************
        nomreh_students.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (om.get_Data("‌Buy_App","NO",contexts).equals("Buy_App")) {
                    if (amozesh ==32) SetCode(33);
                    statistics_.cancel();
                    Output_list.Name_class = Class;
                    Output_list.Id_class = id;
                    Output_list.Did_class = did;
                    Output_list.STATUS = false;
                    contexts.startActivity(new Intent(contexts, Output_list.class));
                } else {
                    TOAST("برای استفاده از این امکانات باید نسخه ای کامل برنامه را خریداری کنید.");
                }

            }
        });
        //******************************************************************************************
        if (amozesh >29) {
            if (amozesh>30){
                if (amozesh ==32){
                    Mesage("گزینه ی لیست نمرات را انتخاب کنید.");
                }
            }else if (amozesh ==30){
                Mesage("گزینه ی لیست حضور و غیاب را انتخاب کنید.");
            }
        }else if (amozesh ==28 || amozesh ==29){
            Mesage("می توانید در این قسمت با انتخاب گزینه های آمار حضور و غیاب بصورت آماری حضور و غیاب دانشجویان خود در کل جلسات مشاهده کنید و نیز می توانید با انتخاب گزینه ی لیست حضور و غیاب ،لیست حضور و غیاب دانشجویان خود در تاریخ های برگزاری جلسات مشاهده کنید و لیست درس خود را ذخیره کنیدو همچنین می توانید با انتخاب گزینه ی لیست نمرات،لیست نمرات دانشجویان خود را در تاریخ های برگزاری جلسات مشاهده کنید و لیست را ذخیره کنید."+"\n گزینه ی آمار حضور و غیاب را انتخاب کنید.");
            SetCode(29);
        }
    }

}
