package aspi.myclass.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import aspi.myclass.Helpers.DialogHelper;
import aspi.myclass.model.AbsentPersentModel;
import aspi.myclass.R;
import aspi.myclass.activity.MettingNewActivity;
import aspi.myclass.Helpers.DatabasesHelper;


public class StudentViewAdapter extends RecyclerView.Adapter<StudentViewAdapter.cvh> {
    private List<AbsentPersentModel> Content_new_class;
    private Context contexts;
    private DatabasesHelper data;
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
        holder.sno.setText(content.sno);
        holder.name.setText(content.name);
        holder.family.setText(content.family);
        holder.score.setText(content.nomreh);

        if (content.status.equals("1")) {
            holder.status.setChecked(true);
        } else {
            holder.status.setChecked(false);
        }
        if (!content.text.isEmpty()) {
            if (content.text.replace("\n", "  ").length() > 50) {
                holder.text.setText(content.text.replace("\n", "  ").substring(0, 50) + "...");
            } else {
                if (content.text.length() > 2) {
                    holder.text.setText(content.text);
                } else {
                    holder.text.setText(contexts.getResources().getString(R.string.studentDescription)+"...");
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
                DialogHelper.SnoStudent(contexts, contexts.getResources().getString(R.string.EditStudentCode)+" "+ content.name + " " + content.family, holder.sno, data, content, MettingNewActivity.did);

            }
        });
        //*****************************************************************
        holder.family.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogHelper.FamilyStudent(contexts, contexts.getResources().getString(R.string.EditStudentFamily)+" " + content.name + " " + content.family, holder.family, data, content);
            }
        });
        //*****************************************************************
        holder.name.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogHelper.NameStudent(contexts, contexts.getResources().getString(R.string.EditStudentName)+" " + content.name + " " + content.family, holder.name, data, content);


            }
        });
        //*****************************************************************
        holder.text.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogHelper.DescriptionStudent(contexts, contexts.getResources().getString(R.string.SetStudentDescription)+" " + content.family, holder.text, data, content);
            }
        });
        //*****************************************************************
        holder.score.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogHelper.ScoreStudent(contexts, contexts.getResources().getString(R.string.studentScore)+" " + content.name + " " + content.family, holder.score, data, content);
            }
        });
        //******************************************************************************************end onBindViewHolder
    }

    @Override
    public int getItemCount() {
        return Content_new_class.size();
    }

    public class cvh extends RecyclerView.ViewHolder {

        private TextView  sno, name, family, score, text;
        private CheckBox status;

        public cvh(View itemView) {
            super(itemView);

            sno =  itemView.findViewById(R.id.list_show_student_sno);
            name =  itemView.findViewById(R.id.list_show_student_name);
            family =  itemView.findViewById(R.id.list_show_student_family);
            score =  itemView.findViewById(R.id.list_show_student_nomerh);
            status =  itemView.findViewById(R.id.list_show_student_status);
            text =  itemView.findViewById(R.id.list_show_student_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AbsentPersentModel conlist = Content_new_class.get(getPosition());
                }
            });
        }

    }

}
