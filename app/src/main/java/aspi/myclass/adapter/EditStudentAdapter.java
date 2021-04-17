package aspi.myclass.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import aspi.myclass.Helpers.DatabasesHelper;
import aspi.myclass.Helpers.DialogHelper;
import aspi.myclass.R;
import aspi.myclass.activity.MettingNewActivity;
import aspi.myclass.model.AbsentPersentModel;

public class EditStudentAdapter extends RecyclerView.Adapter<EditStudentAdapter.cvh> {

    private List<AbsentPersentModel> model;
    private Context context;
    private DatabasesHelper data;
    String TAG = "TAG_StudentViewAdapter";

    public EditStudentAdapter(List<AbsentPersentModel> model, Context context) {
        this.model = model;
        this.context = context;
        data = new DatabasesHelper(context);
    }

    @Override
    public cvh onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_editstudent, parent, false);
        return new cvh(view);
    }

    @Override
    public void onBindViewHolder(final cvh holder, int position) {

        final AbsentPersentModel studentModel = model.get(position);

        holder.sno.setText(context.getResources().getString(R.string.studentCode)+" : " + studentModel.sno);
        holder.name.setText(context.getResources().getString(R.string.studentName)+" : "  + studentModel.name);
        holder.family.setText(context.getResources().getString(R.string.studentFamily)+" : "  + studentModel.family);
        holder.description.setText(studentModel.text.length() > 0 ? studentModel.text : "");

        holder.titleDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.description.getVisibility() == View.VISIBLE) {
                    holder.description.setVisibility(View.GONE);
                } else {
                    holder.description.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.sno.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogHelper.SnoStudent(context, context.getResources().getString(R.string.EditStudentCode)+" "+ studentModel.name + " " + studentModel.family, holder.sno, data, studentModel, MettingNewActivity.did);

            }
        });
        //*****************************************************************
        holder.family.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogHelper.FamilyStudent(context, context.getResources().getString(R.string.EditStudentFamily)+" " + studentModel.name + " " + studentModel.family, holder.family, data, studentModel);
            }
        });
        //*****************************************************************
        holder.name.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogHelper.NameStudent(context, context.getResources().getString(R.string.EditStudentName)+" "+ studentModel.name + " " + studentModel.family, holder.name, data, studentModel);


            }
        });
        //*****************************************************************
        holder.description.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogHelper.DescriptionStudent(context, context.getResources().getString(R.string.SetStudentDescription)+" " + studentModel.name + " " + studentModel.family, holder.description, data, studentModel);
            }
        });

    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class cvh extends RecyclerView.ViewHolder {

        TextView sno, name, family, titleDescription, description;

        public cvh(View itemView) {
            super(itemView);

            sno = itemView.findViewById(R.id.adapter_editestudent_sno);
            name = itemView.findViewById(R.id.adapter_editestudent_name);
            family = itemView.findViewById(R.id.adapter_editestudent_family);
            description = itemView.findViewById(R.id.adapter_editestudent_description);
            titleDescription = itemView.findViewById(R.id.adapter_editestudent_titledescription);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }

    }

}

