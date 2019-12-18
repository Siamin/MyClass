package aspi.myclass.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

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

import androidx.recyclerview.widget.RecyclerView;

import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.List;

import aspi.myclass.Helpers.MessageHelper;
import aspi.myclass.Helpers.SharedPreferencesHelper;
import aspi.myclass.Helpers.ValidationHelper;
import aspi.myclass.activity.EditStudentActivity;
import aspi.myclass.model.ClassModel;
import aspi.myclass.R;
import aspi.myclass.activity.AddStudentActivity;
import aspi.myclass.activity.EditClassActivity;
import aspi.myclass.activity.MainActivity;
import aspi.myclass.activity.NewClassActivity;
import aspi.myclass.activity.ReportClassActivity;
import aspi.myclass.activity.OldClassListActivity;
import aspi.myclass.activity.StatisticsActivity;
import aspi.myclass.Helpers.DatabasesHelper;


public class ClassViewAdapter extends RecyclerView.Adapter<ClassViewAdapter.cvh> {

    List<ClassModel> Model;
    Context contexts;
    DatabasesHelper data;
    Activity activity;
    String TIMEPICKER = "TimePickerDialog";

    public ClassViewAdapter(List<ClassModel> model, Context context) {
        Model = model;
        contexts = context;
        data = new DatabasesHelper(context);
        activity = ((Activity) context);
    }

    @Override
    public cvh onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_classview, parent, false);
        return new cvh(view);
    }

    @Override
    public void onBindViewHolder(cvh holder, int position) {
        final ClassModel content = Model.get(position);

        if (!content.name_class.equals("")) {
            holder.class_.setText(contexts.getResources().getString(R.string.classNumber) + " :" + content.Class_);
        }

        holder.name_class.setText(contexts.getResources().getString(R.string.className) + " :" + content.name_class);
        holder.time_start.setText(content.time_start);
        holder.time_end.setText(content.time_end);
        holder.location.setText(contexts.getResources().getString(R.string.classLocation) + " :" + content.location);
        if (content.text_class.length() > 2) holder.txt.setText("" + content.text_class);

        //***********************************************
    }

    @Override
    public int getItemCount() {
        return Model.size();
    }

    public class cvh extends RecyclerView.ViewHolder {

        private TextView name_class, time_start, time_end, location, class_, txt;

        public cvh(View itemView) {
            super(itemView);


            name_class = itemView.findViewById(R.id.list_class_in_main_name_class);
            time_start = itemView.findViewById(R.id.list_class_in_main_start_time);
            time_end = itemView.findViewById(R.id.list_class_in_main_end_time);
            location = itemView.findViewById(R.id.list_class_in_main_location);
            class_ = itemView.findViewById(R.id.list_class_in_main_class);
            txt = itemView.findViewById(R.id.list_class_in_main_txt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClassModel conlist = Model.get(getPosition());

                    if (conlist.APP) {
                        Option(conlist.name_class, conlist.id, conlist.location, conlist.time_start, conlist.time_end, conlist.characteristic, conlist.id_class, conlist.text_class);
                    }

                }
            });
        }

    }

    void Option(final String Class, final String id, final String location, final String starttime, final String endtime, final String Characteristic, final String did, final String TXT) {

        final Dialog kelas = new Dialog(contexts, R.style.NewDialog);
        kelas.requestWindowFeature(Window.FEATURE_NO_TITLE);
        kelas.setTitle(contexts.getResources().getString(R.string.Class) + " " + Class);
        kelas.setContentView(R.layout.dialog_optionclass);
        kelas.setCancelable(true);
        kelas.setCanceledOnTouchOutside(true);
        kelas.show();
        final Button new_class = kelas.findViewById(R.id.list_class_chang_new_class);
        final Button add_student_class = kelas.findViewById(R.id.list_class_chang_add_student_class);
        final Button old_class = kelas.findViewById(R.id.list_class_chang_old_class);
        final Button edit_class = kelas.findViewById(R.id.list_class_chang_edit_class);
        final Button delete_class = kelas.findViewById(R.id.list_class_chang_delete_class);
        final Button output_class = kelas.findViewById(R.id.list_class_chang_output_class);
        final Button text_class = kelas.findViewById(R.id.list_class_chang_text_class);
        final Button week_class = kelas.findViewById(R.id.list_class_chang_add_class_of_week_class);
        final Button editStudent = kelas.findViewById(R.id.option_editstudent);
        //******************************************************************************************


        //******************************************************************************************
        week_class.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ValidationHelper.isValidationBuyApp(contexts, "‌Buy_App") || Integer.parseInt(did) <= 10) {
                    kelas.cancel();
                    Set_Of_Week(Class, location, Characteristic, did, TXT);
                } else {
                    MessageHelper.Toast(contexts, contexts.getResources().getString(R.string.ErrorBuyApplication));
                }
            }
        });
        //******************************************************************************************
        new_class.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
                dialogDelete(id, kelas);
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
                dialogDescription(did, id);
            }
        });
        //******************************************************************************************
        editStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(contexts, EditStudentActivity.class);
                intent.putExtra("idClass", did);
                ((Activity) contexts).startActivity(intent);
            }
        });
    }

    void Set_Of_Week(final String Class, final String location, final String Characteristic, final String did, final String TXT) {
        final Dialog Week = new Dialog(contexts, R.style.NewDialog);
        Week.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Week.setContentView(R.layout.dialog_editclass);
        Week.setCancelable(false);
        Week.setCanceledOnTouchOutside(false);
        Week.show();
        //**********************************************************************************
        final TextView save_ = Week.findViewById(R.id.class_of_week_save);
        final TextView cancel_ = Week.findViewById(R.id.class_of_week_cancel);
        final TextView timeStart = Week.findViewById(R.id.class_of_week_texttimestart);
        final TextView timeEnd = Week.findViewById(R.id.class_of_week_texttimeend);
        final Spinner Spiner_ = Week.findViewById(R.id.class_of_week_spinner);
        final EditText room = Week.findViewById(R.id.class_of_week_class_edit);
        final LinearLayout clickTimeStart = Week.findViewById(R.id.class_of_week_timestart);
        final LinearLayout clickTimeEnd = Week.findViewById(R.id.class_of_week_timeend);

        //**********************************************************************************
        String[] Day_of_week = contexts.getResources().getStringArray(R.array.weekName);

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

        final Dialog statistics_ = new Dialog(contexts, R.style.NewDialog);
        statistics_.requestWindowFeature(Window.FEATURE_NO_TITLE);
        statistics_.setContentView(R.layout.dialog_optionoutputclass);
        statistics_.setCancelable(true);
        statistics_.setCanceledOnTouchOutside(true);
        statistics_.show();
        final Button absent_student = statistics_.findViewById(R.id.list_statistics_class_absent_student);
        final Button absent_students = statistics_.findViewById(R.id.list_statistics_class_absent_students);
        final Button nomreh_students = statistics_.findViewById(R.id.list_statistics_class_nomreh_students);
        //*************************************************************************************************

        //******************************************************************************************
        absent_student.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ValidationHelper.isValidationBuyApp(contexts, "‌Buy_App")) {
                    statistics_.cancel();
                    StatisticsActivity.Name_class = Class;
                    StatisticsActivity.Id_class = id;
                    StatisticsActivity.Did_class = did;
                    contexts.startActivity(new Intent(contexts, StatisticsActivity.class));
                } else {
                    MessageHelper.Toast(contexts, contexts.getResources().getString(R.string.ErrorBuyApplication));
                }

            }
        });
        //******************************************************************************************
        absent_students.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (SharedPreferencesHelper.get_Data("‌Buy_App", "NO", contexts).equals("Buy_App")) {
                    statistics_.cancel();

                    Intent i = new Intent(contexts, ReportClassActivity.class);
                    i.putExtra("className", Class)
                            .putExtra("classId", id)
                            .putExtra("classDid", did)
                            .putExtra("status", true);

                    contexts.startActivity(i);
                } else {
                    MessageHelper.Toast(contexts, contexts.getResources().getString(R.string.ErrorBuyApplication));
                }

            }
        });
        //******************************************************************************************
        nomreh_students.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (SharedPreferencesHelper.get_Data("‌Buy_App", "NO", contexts).equals("Buy_App")) {
                    statistics_.cancel();

                    Intent i = new Intent(contexts, ReportClassActivity.class);
                    i.putExtra("className", Class)
                            .putExtra("classId", id)
                            .putExtra("classDid", did)
                            .putExtra("status", false);

                    contexts.startActivity(i);
                } else {
                    MessageHelper.Toast(contexts, contexts.getResources().getString(R.string.ErrorBuyApplication));
                }

            }
        });
        //******************************************************************************************

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

    void dialogDescription(final String did, String id) {
        final Dialog dialog = new Dialog(contexts, R.style.NewDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_description);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        //**********************************************************************************
        final TextView save = dialog.findViewById(R.id.dialogdes_save);
        final TextView cancle = dialog.findViewById(R.id.dialogdes_cancle);
        final EditText description = dialog.findViewById(R.id.dialogdes_description);
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
                        MessageHelper.Toast(contexts, contexts.getResources().getString(R.string.Saved));
                        dialog.dismiss();
                        MainActivity.refresh = 1;
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

    void dialogDelete(final String id, final Dialog dialogCll) {
        final Dialog dialog = new Dialog(contexts, R.style.NewDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_deleteclass);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        //**********************************************************************************
        final TextView save = dialog.findViewById(R.id.dialogdelete_save);
        final TextView cancle = dialog.findViewById(R.id.dialogdelete_cancle);
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
                    MessageHelper.Toast(contexts, contexts.getResources().getString(R.string.ClassDeleted));
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
