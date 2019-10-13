package aspi.myclass.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import aspi.myclass.Helpers.DialogHelper;
import aspi.myclass.Helpers.MessageHelper;
import aspi.myclass.Helpers.SharedPreferencesHelper;
import aspi.myclass.Tools.Tools;
import aspi.myclass.model.AbsentPersentModel;
import aspi.myclass.R;
import aspi.myclass.activity.MainActivity;
import aspi.myclass.activity.NewClassActivity;
import aspi.myclass.Helpers.DatabasesHelper;


public class StudentViewAdapter extends RecyclerView.Adapter<StudentViewAdapter.cvh> {
    private List<AbsentPersentModel> Content_new_class;
    private Context contexts;
    private DatabasesHelper data;
    Tools om = new Tools();
    String TAG = "TAG_StudentViewAdapter";

    public StudentViewAdapter(List<AbsentPersentModel> contents, Context context) {
        this.Content_new_class = contents;
        this.contexts = context;
        data = new DatabasesHelper(context);
    }

    @Override
    public cvh onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_studentview, parent, false);
        return new cvh(view);
    }

    @Override
    public void onBindViewHolder(final cvh holder, int position) {
        final AbsentPersentModel content = Content_new_class.get(position);
        //*****************************************************************
        holder.row.setText("  " + (position + 1) + " ");
        holder.sno.setText(content.sno);
        holder.name.setText(content.name);
        holder.family.setText(content.family);
        holder.nomreh.setText(content.nomreh);

        if (content.status.equals("1")) {
            holder.status.setChecked(true);
        } else {
            holder.status.setChecked(false);
        }
        if (!content.text.isEmpty()) {
            if (content.text.replace("\n", "  ").length() > 50) {
                holder.text.setText(content.text.replace("\n", "  ").substring(0, 50) + "  توضایحات ادامه دارد...");
            } else {
                if (content.text.length() > 2) {
                    holder.text.setText(content.text);
                } else {
                    holder.text.setText("توضیحات...");
                }
            }
        }
        //*****************************************************************
        holder.status.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                String STATUS = "1";
                if (holder.status.isChecked()) {
                    STATUS = "1";
                } else {
                    STATUS = "0";
                }
                try {
                    data.open();
                    data.update_one("rollcall", "abs", STATUS, Integer.parseInt(content.id_rull));
                    data.close();
                    if (holder.status.isChecked()) {
                        content.status = "1";//حاضر
                    } else {
                        content.status = "0";//غیبت
                    }
                } catch (Exception e) {
                }

            }
        });
        //*****************************************************************
        holder.sno.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                DialogHelper.SnoStudent(contexts, "ویرایش شماره دانشجویی " + content.name + " " + content.family, holder.sno, data, content,NewClassActivity.did);

            }
        });
        //*****************************************************************
        holder.family.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogHelper.FamilyStudent(contexts, "ویرایش نام خانوادگی " + content.name + " " + content.family, holder.family, data, content);
            }
        });
        //*****************************************************************
        holder.name.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogHelper.NameStudent(contexts, "ویرایش نام " + content.name + " " + content.family, holder.name, data, content);


            }
        });
        //*****************************************************************
        holder.text.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogHelper.DescriptionStudent(contexts, "توضیحات " + content.name + " " + content.family, holder.text, data, content);
            }
        });
        //*****************************************************************
        holder.nomreh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogHelper.ScoreStudent(contexts, "نمره دانشجو " + content.name + " " + content.family, holder.nomreh, data, content);
            }
        });
        //******************************************************************************************end onBindViewHolder
    }

    @Override
    public int getItemCount() {
        return Content_new_class.size();
    }

    public class cvh extends RecyclerView.ViewHolder {

        private TextView row, sno, name, family, nomreh, text;
        private CheckBox status;

        public cvh(View itemView) {
            super(itemView);

            row = (TextView) itemView.findViewById(R.id.list_show_student_numbers);
            sno = (TextView) itemView.findViewById(R.id.list_show_student_sno);
            name = (TextView) itemView.findViewById(R.id.list_show_student_name);
            family = (TextView) itemView.findViewById(R.id.list_show_student_family);
            nomreh = (TextView) itemView.findViewById(R.id.list_show_student_nomerh);
            status = (CheckBox) itemView.findViewById(R.id.list_show_student_status);
            text = (TextView) itemView.findViewById(R.id.list_show_student_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AbsentPersentModel conlist = Content_new_class.get(getPosition());
                }
            });
        }

    }

}
