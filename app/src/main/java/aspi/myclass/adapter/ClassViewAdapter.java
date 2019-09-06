package aspi.myclass.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.List;

import aspi.myclass.Helpers.MessageHelper;
import aspi.myclass.Helpers.SharedPreferencesHelper;
import aspi.myclass.content.ClassContent;
import aspi.myclass.Tools.Tools;
import aspi.myclass.R;
import aspi.myclass.activity.AddStudentActivity;
import aspi.myclass.activity.EditClassActivity;
import aspi.myclass.activity.MainActivity;
import aspi.myclass.activity.NewClassActivity;
import aspi.myclass.activity.OutputDataClassActivity;
import aspi.myclass.activity.OldClassListActivity;
import aspi.myclass.activity.StatisticsActivity;
import aspi.myclass.Helpers.DatabasesHelper;


public class ClassViewAdapter extends RecyclerView.Adapter<ClassViewAdapter.cvh> {

    private List<ClassContent> content_class_main_shows;
    private Context contexts;
    private DatabasesHelper data;
    Activity activity;
    Tools om = new Tools();
    String TIMEPICKER = "TimePickerDialog", DATEPICKER = "DatePickerDialog", MULTIDATEPICKER = "MultiDatePickerDialog";

    public ClassViewAdapter(List<ClassContent> contents, Context context, Activity activitys) {
        content_class_main_shows = contents;
        contexts = context;
        data = new DatabasesHelper(context);
        activity = activitys;
    }

    @Override
    public cvh onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_classview, parent, false);
        return new cvh(view);
    }

    @Override
    public void onBindViewHolder(cvh holder, int position) {
        final ClassContent content = content_class_main_shows.get(position);

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
                    ClassContent conlist = content_class_main_shows.get(getPosition());
                    SharedPreferences sp = contexts.getSharedPreferences("myclass", 0);
                    float amozesh = sp.getFloat("LerningActivity", 0);
                    if (conlist.APP) {
                        items(conlist.name_class, conlist.id, conlist.location, conlist.time_start, conlist.time_end, conlist.characteristic, conlist.id_class, conlist.text_class);
                    }

                    if (amozesh > 4) {
                        if (amozesh > 8) {
                            if (amozesh > 16) {
                                if (amozesh > 20) {
                                    if (amozesh > 22) {
                                        if (amozesh > 24) {
                                            if (amozesh > 26) {
                                                if (amozesh > 28) {
                                                } else if (amozesh == 27 || amozesh == 28) {
                                                    MessageHelper.Mesage(contexts, "شما میتوانید برای گرفتن مستندات از کلاس مورد نظرتان از گزینه ی خروجی گرفتن استفاده کنید .گزینه ی خروجی از کلاس را انتخاب کنید.");
                                                }
                                            } else if (amozesh == 25 || amozesh == 26) {
                                                MessageHelper.Mesage(contexts, "شما می توانید با انتخاب گزینه ی حذف کلاس اطلاعات کلاس مورد نظرتان را حذف کنید.");
                                            }
                                        } else if (amozesh == 23 || amozesh == 24) {
                                            MessageHelper.Mesage(contexts, "شما همچنین می توانید با انتخاب گزینه ی ایام هفته کلاس انتخاب شده ی خود را در دیگر روز های ایام هفته ذخیره کنید.گزینه ی ایام هفته را انتخاب کنید.");
                                        }
                                    } else if (amozesh == 21 || amozesh == 22) {
                                        MessageHelper.Mesage(contexts, "شما همچنین می توانید با انتخاب گزینه ی ویرایش کلاس، اطلاعات کلاس خود را ویرایش کنید.");
                                    }
                                } else if (amozesh == 19 || amozesh == 20) {
                                    MessageHelper.Mesage(contexts, "شما همچنین می توانید با انتخاب گزینه ی توضیحات،برای درس مورد نظرتان توضیحات وارد کنیداین توضیحات تا زمانی که کلاس درس را حذف نکنید پاک نمی شود و در قسمت توضیحات کلاس نمایش داده می شود.");
                                }
                            } else {
                                MessageHelper.Mesage(contexts, "با انتخاب گزینه جلسات گذشته شما می توانید لیست جلسات برگزار شده درس را مشاهده کنید.گزینه ی جلسات گذشته را انتخاب کنید.");
                            }
                        } else if (amozesh == 7) {
                            MessageHelper.Mesage(contexts, "با انتخاب گزینه جلسه ی جدید شما وارد کلاس می شوید و برای کلاس یک لیست حضور و غیاب تشکیل داده می شود.");
                        }
                    } else {
                        MessageHelper.Mesage(contexts, "برای تشکیل جلسات کلاس ابتدا باید با انتخاب گزینه ی دانشجوی جدید به کلاس تان دانشجو اضافه کنید.");
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
        edit.putFloat("LerningActivity", code);
        edit.commit();
    }

    void items(final String Class, final String id, final String location, final String starttime, final String endtime, final String Characteristic, final String did, final String TXT) {
        SharedPreferences sp = contexts.getSharedPreferences("myclass", 0);
        final float amozesh = sp.getFloat("LerningActivity", 0);
        final Dialog kelas = new Dialog(contexts, R.style.NewDialog);
        kelas.requestWindowFeature(Window.FEATURE_NO_TITLE);
        kelas.setTitle("درس " + Class);
        kelas.setContentView(R.layout.dialog_optionclass);
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
                if (SharedPreferencesHelper.get_Data("‌Buy_App", "NO", contexts).equals("Buy_App") || Integer.parseInt(did) <= 10) {
                    kelas.cancel();
                    Set_Of_Week(Class, location, Characteristic, did, TXT);
                    if (amozesh == 24) SetCode(25);
                } else {
                    MessageHelper.Toast(contexts, "برای استفاده از این امکانات باید نسخه ای کامل برنامه را خریداری کنید.");
                }
            }
        });
        //******************************************************************************************
        new_class.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (amozesh == 8) SetCode(9);
                kelas.cancel();
                NewClassActivity.Name_class = Class;
                NewClassActivity.did = did;
                Intent New_class = new Intent(contexts, NewClassActivity.class);
                contexts.startActivity(New_class);
            }
        });
        //******************************************************************************************
        add_student_class.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (amozesh == 4) SetCode(5);
                kelas.cancel();
                AddStudentActivity.id_class = did;
                AddStudentActivity.Name_class = Class;
                AddStudentActivity.location = location;
                AddStudentActivity.Start_time = starttime;
                Intent student = new Intent(contexts, AddStudentActivity.class);
                contexts.startActivity(student);
            }
        });
        //******************************************************************************************
        old_class.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (amozesh == 16) SetCode(17);
                OldClassListActivity.id_class = did;
                OldClassListActivity.Name_class = Class;
                Intent Show_list_old_class = new Intent(contexts, OldClassListActivity.class);
                contexts.startActivity(Show_list_old_class);
                kelas.cancel();
            }
        });
        //******************************************************************************************
        edit_class.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                kelas.dismiss();
                if (amozesh == 22) SetCode(23);
                EditClassActivity.ID_Class = id;
                EditClassActivity.Name_Class = Class;
                EditClassActivity.Location_Class = location;
                EditClassActivity.Start_Class = starttime;
                EditClassActivity.End_Class = endtime;
                Intent edit_class = new Intent(contexts, EditClassActivity.class);
                contexts.startActivity(edit_class);
            }
        });
        //******************************************************************************************
        delete_class.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialogDelete(id,kelas);
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
                dialogDescription(did,amozesh,id);
            }
        });
        //******************************************************************************************
    }

    void Set_Of_Week(final String Class, final String location, final String Characteristic, final String did, final String TXT) {
        final Dialog Week = new Dialog(contexts, R.style.NewDialog);
        Week.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Week.setContentView(R.layout.dialog_editclass);
        Week.setCancelable(false);
        Week.setCanceledOnTouchOutside(false);
        Week.show();
        //**********************************************************************************
        final TextView save_ = (TextView) Week.findViewById(R.id.class_of_week_save);
        final TextView cancel_ = (TextView) Week.findViewById(R.id.class_of_week_cancel);
        final TextView timeStart = (TextView) Week.findViewById(R.id.class_of_week_texttimestart);
        final TextView timeEnd = (TextView) Week.findViewById(R.id.class_of_week_texttimeend);
        final Spinner Spiner_ = (Spinner) Week.findViewById(R.id.class_of_week_spinner);
        final EditText room = (EditText) Week.findViewById(R.id.class_of_week_class_edit);
        final LinearLayout clickTimeStart = (LinearLayout) Week.findViewById(R.id.class_of_week_timestart);
        final LinearLayout clickTimeEnd = (LinearLayout) Week.findViewById(R.id.class_of_week_timeend);
        //**********************************************************************************
        String[] Day_of_week = {"شنبه", "یکشنبه", "دوشنبه", "سه شنبه", "چهارشنبه", "پنج شنبه", "جمعه"};

        ArrayAdapter<String> a = new ArrayAdapter<String>(contexts, android.R.layout.simple_spinner_item, Day_of_week);
        Spiner_.setAdapter(a);
        //**********************************************************************************
        save_.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int day_of = Spiner_.getSelectedItemPosition();
                try {
                    data.open();
                    data.insert_class(Class, String.valueOf(day_of), timeStart.getText().toString(), location, timeEnd.getText().toString(), Characteristic, room.getText().toString(), did);
                    data.update_one1("dars", "txt", TXT, "did=" + did);
                    data.close();
                    MainActivity.refresh = 1;
                    Week.cancel();
                } catch (Exception e) {
                }
            }
        });
        //**********************************************************************************
        clickTimeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetTimeByDialog(timeEnd);
            }
        });

        clickTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetTimeByDialog(timeStart);
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

    void statistics(final String Class, final String id, final String location, final String starttime, final String endtime, final String Characteristic, final String did) {
        final SharedPreferences sp = contexts.getSharedPreferences("myclass", 0);
        final float amozesh = sp.getFloat("LerningActivity", 0);
        final Dialog statistics_ = new Dialog(contexts, R.style.NewDialog);
        statistics_.requestWindowFeature(Window.FEATURE_NO_TITLE);
        statistics_.setTitle("درس " + Class);
        statistics_.setContentView(R.layout.dialog_optionoutputclass);
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
                if (SharedPreferencesHelper.get_Data("‌Buy_App", "NO", contexts).equals("Buy_App")) {
                    if (amozesh == 28 || amozesh == 29) SetCode(30);
                    statistics_.cancel();
                    StatisticsActivity.Name_class = Class;
                    StatisticsActivity.Id_class = id;
                    StatisticsActivity.Did_class = did;
                    contexts.startActivity(new Intent(contexts, StatisticsActivity.class));
                } else {
                    MessageHelper.Toast(contexts, "برای استفاده از این امکانات باید نسخه ای کامل برنامه را خریداری کنید.");
                }

            }
        });
        //******************************************************************************************
        absent_students.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (SharedPreferencesHelper.get_Data("‌Buy_App", "NO", contexts).equals("Buy_App")) {
                    if (amozesh == 30) SetCode(31);
                    statistics_.cancel();
                    OutputDataClassActivity.Name_class = Class;
                    OutputDataClassActivity.Id_class = id;
                    OutputDataClassActivity.Did_class = did;
                    OutputDataClassActivity.STATUS = true;
                    contexts.startActivity(new Intent(contexts, OutputDataClassActivity.class));
                } else {
                    MessageHelper.Toast(contexts, "برای استفاده از این امکانات باید نسخه ای کامل برنامه را خریداری کنید.");
                }

            }
        });
        //******************************************************************************************
        nomreh_students.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (SharedPreferencesHelper.get_Data("‌Buy_App", "NO", contexts).equals("Buy_App")) {
                    if (amozesh == 32) SetCode(33);
                    statistics_.cancel();
                    OutputDataClassActivity.Name_class = Class;
                    OutputDataClassActivity.Id_class = id;
                    OutputDataClassActivity.Did_class = did;
                    OutputDataClassActivity.STATUS = false;
                    contexts.startActivity(new Intent(contexts, OutputDataClassActivity.class));
                } else {
                    MessageHelper.Toast(contexts, "برای استفاده از این امکانات باید نسخه ای کامل برنامه را خریداری کنید.");
                }

            }
        });
        //******************************************************************************************
        if (amozesh > 29) {
            if (amozesh > 30) {
                if (amozesh == 32) {
                    MessageHelper.Mesage(contexts, "گزینه ی لیست نمرات را انتخاب کنید.");
                }
            } else if (amozesh == 30) {
                MessageHelper.Mesage(contexts, "گزینه ی لیست حضور و غیاب را انتخاب کنید.");
            }
        } else if (amozesh == 28 || amozesh == 29) {
            MessageHelper.Mesage(contexts, "می توانید در این قسمت با انتخاب گزینه های آمار حضور و غیاب بصورت آماری حضور و غیاب دانشجویان خود در کل جلسات مشاهده کنید و نیز می توانید با انتخاب گزینه ی لیست حضور و غیاب ،لیست حضور و غیاب دانشجویان خود در تاریخ های برگزاری جلسات مشاهده کنید و لیست درس خود را ذخیره کنیدو همچنین می توانید با انتخاب گزینه ی لیست نمرات،لیست نمرات دانشجویان خود را در تاریخ های برگزاری جلسات مشاهده کنید و لیست را ذخیره کنید." + "\n گزینه ی آمار حضور و غیاب را انتخاب کنید.");
            SetCode(29);
        }
    }

    void SetTimeByDialog(final TextView text) {
        PersianCalendar now = new PersianCalendar();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                (TimePickerDialog.OnTimeSetListener) contexts,
                now.get(PersianCalendar.HOUR_OF_DAY),
                now.get(PersianCalendar.MINUTE), true);
        tpd.setThemeDark(true);
        tpd.show(((Activity) contexts).getFragmentManager(), TIMEPICKER);

        tpd.setOnTimeSetListener(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                String Hour = "00", Min = "00";


                if (hourOfDay < 10) {
                    Hour = "0" + String.valueOf(hourOfDay);
                } else {
                    Hour = String.valueOf(hourOfDay);
                }

                if (minute < 10) {
                    Min = "0" + String.valueOf(minute);
                } else {
                    Min = String.valueOf(minute);
                }

                text.setText(Hour + ":" + Min);
            }
        });
    }

    void dialogDescription(final String did, final float amozesh,String id) {
        final Dialog dialog = new Dialog(contexts, R.style.NewDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_description);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        //**********************************************************************************
        final TextView save = (TextView) dialog.findViewById(R.id.dialogdes_save);
        final TextView cancle = (TextView) dialog.findViewById(R.id.dialogdes_cancle);
        final EditText description = (EditText) dialog.findViewById(R.id.dialogdes_description);
        //**********************************************************************************
        try {
            data.open();
            int cunt = data.count("dars");
            for (int i = 0; i < cunt; i++) {
                if (data.Display("dars", i, 0).equals(id)) {
                    description.setText(data.Display("dars", i, 7));
                    break;
                }
            }
            data.close();
        } catch (Exception e) {

        }
        //**********************************************************************************
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!description.getText().toString().equals("")) {
                        data.open();
                        data.update_one1("dars", "txt", description.getText().toString(), "did=" + did);
                        data.close();
                        MessageHelper.Toast(contexts, "ذخیره شد...!");
                        dialog.dismiss();
                        if (amozesh != 20) MainActivity.refresh = 1;
                    }
                } catch (Exception e) {
                }
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    void dialogDelete(final String id,final Dialog dialogCll){
        final Dialog dialog = new Dialog(contexts, R.style.NewDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_deleteclass);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        //**********************************************************************************
        final TextView save = (TextView) dialog.findViewById(R.id.dialogdelete_save);
        final TextView cancle = (TextView) dialog.findViewById(R.id.dialogdelete_cancle);
        //**********************************************************************************
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    data.open();
                    data.delete("dars", Integer.parseInt(id));
                    data.close();
                    MainActivity.refresh = 1;
                    dialog.dismiss();
                    dialogCll.dismiss();
                    MessageHelper.Toast(contexts, "کلاس حذف شد...!");
                } catch (Exception e) {
                }
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
