package aspi.myclass.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import android.view.Gravity;
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
import aspi.myclass.Helpers.ValidationHelper;
import aspi.myclass.Interface.RecyclerViewInteface;
import aspi.myclass.MyActivity;
import aspi.myclass.activity.StudentEditActivity;
import aspi.myclass.model.LessonModel;
import aspi.myclass.R;
import aspi.myclass.activity.StudentAddActivity;
import aspi.myclass.activity.LessonEditActivity;
import aspi.myclass.activity.MainActivity;
import aspi.myclass.activity.MettingNewActivity;
import aspi.myclass.activity.ReportClassActivity;
import aspi.myclass.activity.MettingListActivity;
import aspi.myclass.activity.ChartActivity;
import aspi.myclass.Helpers.DatabasesHelper;


public class ClassViewAdapter extends RecyclerView.Adapter<ClassViewAdapter.cvh> {

    List<LessonModel> Model;
    Context contexts;
    DatabasesHelper data;
    Activity activity;
    String TIMEPICKER = "TimePickerDialog";

    public ClassViewAdapter(List<LessonModel> model, Context context) {
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
        final LessonModel content = Model.get(position);

        if (!content.lessonName.equals("")) {
            holder.classNumber.setText(contexts.getResources().getString(R.string.classNumber) + " :" + content.classNumber);
        }

        holder.lessonName.setText(contexts.getResources().getString(R.string.className) + " :" + content.lessonName);
        holder.startTime.setText(content.startTime);
        holder.endTime.setText(content.endTime);
        holder.education.setText(contexts.getResources().getString(R.string.classLocation) + " :" + content.education);
        if (content.description.length() > 2) holder.description.setText("" + content.description);

        //***********************************************
    }

    @Override
    public int getItemCount() {
        return Model.size();
    }

    public class cvh extends RecyclerView.ViewHolder {

        private TextView lessonName, startTime, endTime, education, classNumber, description;

        public cvh(View itemView) {
            super(itemView);


            lessonName = itemView.findViewById(R.id.list_class_in_main_name_class);
            startTime = itemView.findViewById(R.id.list_class_in_main_start_time);
            endTime = itemView.findViewById(R.id.list_class_in_main_end_time);
            education = itemView.findViewById(R.id.list_class_in_main_location);
            classNumber = itemView.findViewById(R.id.list_class_in_main_class);
            description = itemView.findViewById(R.id.list_class_in_main_txt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LessonModel model = Model.get(getPosition());
                    Option(model);
                }
            });
        }

    }

    void Option(final LessonModel model) {

        final Dialog optionLesson = new Dialog(contexts, R.style.NewDialog);
        optionLesson.requestWindowFeature(Window.FEATURE_NO_TITLE);
        optionLesson.setTitle(contexts.getResources().getString(R.string.Class) + " " + model.lessonName);
        optionLesson.setContentView(R.layout.dialog_optionclass);
        optionLesson.setCancelable(true);
        optionLesson.setCanceledOnTouchOutside(true);
        optionLesson.show();
        //******************************************************************************************
        final Button newMetting = optionLesson.findViewById(R.id.list_class_chang_new_class);
        final Button addStudentToLesson = optionLesson.findViewById(R.id.list_class_chang_add_student_class);
        final Button listMetting = optionLesson.findViewById(R.id.list_class_chang_old_class);
        final Button editLesson = optionLesson.findViewById(R.id.list_class_chang_edit_class);
        final Button deleteLesson = optionLesson.findViewById(R.id.list_class_chang_delete_class);
        final Button exportLesson = optionLesson.findViewById(R.id.list_class_chang_output_class);
        final Button descriptionLesson = optionLesson.findViewById(R.id.list_class_chang_text_class);
        final Button setOfWeekLesson = optionLesson.findViewById(R.id.list_class_chang_add_class_of_week_class);
        final Button listStudent = optionLesson.findViewById(R.id.option_editstudent);
        //******************************************************************************************
        setOfWeekLesson.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ValidationHelper.validBuyApp(contexts) || Integer.parseInt(model.parentId) <= 10) {
                    optionLesson.cancel();
                    setOfWeek(model);
                } else {
                    MessageHelper.Toast(contexts, contexts.getResources().getString(R.string.ErrorBuyApplication));
                }
            }
        });
        //******************************************************************************************
        newMetting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                optionLesson.cancel();
                MettingNewActivity.Name_class = model.lessonName;
                MettingNewActivity.did = model.parentId;
                Intent New_class = new Intent(contexts, MettingNewActivity.class);
                contexts.startActivity(New_class);
            }
        });
        //******************************************************************************************
        addStudentToLesson.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                optionLesson.cancel();
                StudentAddActivity.id_class = model.parentId;
                StudentAddActivity.Name_class = model.lessonName;
                StudentAddActivity.location = model.education;
                StudentAddActivity.Start_time = model.startTime;
                Intent student = new Intent(contexts, StudentAddActivity.class);
                contexts.startActivity(student);
            }
        });
        //******************************************************************************************
        listMetting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MettingListActivity.id_class = model.parentId;
                MettingListActivity.Name_class = model.lessonName;
                Intent Show_list_old_class = new Intent(contexts, MettingListActivity.class);
                contexts.startActivity(Show_list_old_class);
                optionLesson.cancel();
            }
        });
        //******************************************************************************************
        editLesson.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                optionLesson.dismiss();
                Intent editLessonIntent = new Intent(contexts, LessonEditActivity.class);
                editLessonIntent.putExtra("id", model.id);
                contexts.startActivity(editLessonIntent);
            }
        });
        //******************************************************************************************
        deleteLesson.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                optionLesson.dismiss();
                dialogDelete(model);
            }
        });
        //******************************************************************************************
        exportLesson.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                optionLesson.dismiss();
                export(model);
            }
        });
        //******************************************************************************************
        descriptionLesson.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                optionLesson.dismiss();
                dialogDescription(model);
            }
        });
        //******************************************************************************************
        listStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(contexts, StudentEditActivity.class);
                intent.putExtra("idClass", model.parentId);
                ((Activity) contexts).startActivity(intent);
            }
        });
    }

    void setOfWeek(final LessonModel model) {
        final Dialog dayOfWeekDialog = new Dialog(contexts, R.style.NewDialog);
        dayOfWeekDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dayOfWeekDialog.setContentView(R.layout.dialog_editclass);
        dayOfWeekDialog.setCancelable(false);
        dayOfWeekDialog.setCanceledOnTouchOutside(false);
        dayOfWeekDialog.show();
        //**********************************************************************************
        final TextView save_ = dayOfWeekDialog.findViewById(R.id.class_of_week_save);
        final TextView cancel_ = dayOfWeekDialog.findViewById(R.id.class_of_week_cancel);
        final TextView startTime = dayOfWeekDialog.findViewById(R.id.class_of_week_texttimestart);
        final TextView endTime = dayOfWeekDialog.findViewById(R.id.class_of_week_texttimeend);
        final Spinner dayOfWeek = dayOfWeekDialog.findViewById(R.id.class_of_week_spinner);
        final EditText LessonClassNumber = dayOfWeekDialog.findViewById(R.id.class_of_week_class_edit);
        final LinearLayout clickTimeStart = dayOfWeekDialog.findViewById(R.id.class_of_week_timestart);
        final LinearLayout clickTimeEnd = dayOfWeekDialog.findViewById(R.id.class_of_week_timeend);

        //**********************************************************************************
        String[] Day_of_week = contexts.getResources().getStringArray(R.array.weekName);

        ArrayAdapter<String> a = new ArrayAdapter<String>(contexts, android.R.layout.simple_spinner_item, Day_of_week);
        dayOfWeek.setAdapter(a);
        //**********************************************************************************
        save_.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                LessonModel newModel = new LessonModel(
                        model.lessonName.replace("~", ""),
                        startTime.getText().toString(),
                        endTime.getText().toString(),
                        null,
                        model.education.replace("~", ""),
                        LessonClassNumber.getText().toString().replace("~", ""),
                        model.parentId,
                        model.lessonCode,
                        model.description,
                        String.valueOf(dayOfWeek.getSelectedItemPosition())

                );
                ((MainActivity) contexts).lessonController.insertLesson(newModel);
//                    data.update_one1("dars", "txt", TXT, "did=" + did);

                ((RecyclerViewInteface) contexts).refreshList();
                dayOfWeekDialog.cancel();

            }
        });
        //**********************************************************************************
        clickTimeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetTimeByDialog(endTime);
            }
        });

        clickTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetTimeByDialog(startTime);
            }
        });

        //**********************************************************************************
        cancel_.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dayOfWeekDialog.cancel();
                Option(model);
            }
        });
        //**********************************************************************************
    }

    void export(final LessonModel model) {

        final Dialog exportDialog = new Dialog(contexts, R.style.NewDialog);
        exportDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        exportDialog.setContentView(R.layout.dialog_optionoutputclass);
        exportDialog.setCancelable(true);
        exportDialog.setCanceledOnTouchOutside(true);
        exportDialog.show();
        final Button absent_student = exportDialog.findViewById(R.id.list_statistics_class_absent_student);
        final Button absent_students = exportDialog.findViewById(R.id.list_statistics_class_absent_students);
        final Button nomreh_students = exportDialog.findViewById(R.id.list_statistics_class_nomreh_students);
        //*************************************************************************************************

        //******************************************************************************************
        absent_student.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ValidationHelper.validBuyApp(contexts)) {
                    exportDialog.cancel();
                    ChartActivity.Name_class = model.lessonName;
                    ChartActivity.Id_class = model.id;
                    ChartActivity.Did_class = model.parentId;
                    contexts.startActivity(new Intent(contexts, ChartActivity.class));
                } else {
                    MessageHelper.Toast(contexts, contexts.getResources().getString(R.string.ErrorBuyApplication));
                }

            }
        });
        //******************************************************************************************
        absent_students.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ValidationHelper.validBuyApp(contexts)) {
                    exportDialog.cancel();

                    Intent i = new Intent(contexts, ReportClassActivity.class);
                    i.putExtra("className", model.lessonName)
                            .putExtra("classId", model.id)
                            .putExtra("classDid", model.parentId)
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
                if (ValidationHelper.validBuyApp(contexts)) {
                    exportDialog.cancel();

                    Intent i = new Intent(contexts, ReportClassActivity.class);
                    i.putExtra("className", model.lessonName)
                            .putExtra("classId", model.id)
                            .putExtra("classDid", model.parentId)
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

    void dialogDescription(final LessonModel model) {
        final Dialog descriptionDialog = new Dialog(contexts, R.style.NewDialog);
        descriptionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        descriptionDialog.setContentView(R.layout.dialog_description);
        descriptionDialog.setCancelable(true);
        descriptionDialog.setCanceledOnTouchOutside(true);
        descriptionDialog.show();
        //**********************************************************************************
        final TextView save = descriptionDialog.findViewById(R.id.dialogdes_save);
        final TextView cancle = descriptionDialog.findViewById(R.id.dialogdes_cancle);
        final EditText description = descriptionDialog.findViewById(R.id.dialogdes_description);
        //**********************************************************************************
        description.setText(model.description);
        //**********************************************************************************
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!description.getText().toString().equals("")) {
                    model.setDescription(description.getText().toString());
                    ((MyActivity) contexts).lessonController.updateLessonDescription(model);
                    descriptionDialog.dismiss();
                    ((RecyclerViewInteface) contexts).refreshList();
                }
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                descriptionDialog.dismiss();
                Option(model);
            }
        });
    }

    void dialogDelete(final LessonModel model) {
        final Dialog deleteDialog = new Dialog(contexts, R.style.NewDialog);
        deleteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        deleteDialog.setContentView(R.layout.dialog_deleteclass);
        deleteDialog.setCancelable(true);
        deleteDialog.setCanceledOnTouchOutside(true);
        deleteDialog.show();
        //**********************************************************************************
        final TextView save = deleteDialog.findViewById(R.id.dialogdelete_save);
        final TextView cancle = deleteDialog.findViewById(R.id.dialogdelete_cancle);
        //**********************************************************************************
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) contexts).lessonController.deleteLesson(Integer.parseInt(model.id));
                ((RecyclerViewInteface) contexts).refreshList();
                deleteDialog.dismiss();


            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDialog.dismiss();
                Option(model);
            }
        });
    }
}
