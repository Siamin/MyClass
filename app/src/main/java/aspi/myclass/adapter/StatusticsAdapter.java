package aspi.myclass.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import aspi.myclass.model.StatisticsModel;
import aspi.myclass.R;
import aspi.myclass.activity.MainActivity;
import aspi.myclass.Helpers.DatabasesHelper;


public class StatusticsAdapter extends RecyclerView.Adapter<StatusticsAdapter.cvh> {

    private List<StatisticsModel> Content_student;

    public StatusticsAdapter(List<StatisticsModel> contents) {
        this.Content_student = contents;
    }

    @Override
    public cvh onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_statistics, parent, false);
        return new cvh(view);
    }

    @Override
    public void onBindViewHolder(final cvh holder, int position) {
        final StatisticsModel content = Content_student.get(position);

        holder.name.setText(content.name);
        holder.family.setText(content.family);
        holder.all.setText(String.valueOf(content.max));
        holder.abs.setText(String.valueOf(content.set));
        holder.per.setText(String.valueOf(content.per));
        float status_ = ((float) content.set / content.max) * 100;
        holder.status.setText("%" + String.valueOf((int) status_));

        holder.progressDialog.setProgress(content.set);
        holder.progressDialog.setMax(content.max);
        //*************************************************************************
    }

    public int getItemCount() {
        return Content_student.size();
    }

    public class cvh extends RecyclerView.ViewHolder {

        private TextView name, family, status, abs, per, all;
        private ProgressBar progressDialog;

        public cvh(View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.show_student_name);
            family = itemView.findViewById(R.id.show_student_family);
            status = itemView.findViewById(R.id.show_student_status);
            all = itemView.findViewById(R.id.show_student_all);
            abs = itemView.findViewById(R.id.show_student_absent);
            per = itemView.findViewById(R.id.show_student_present);
            progressDialog = itemView.findViewById(R.id.show_student_progressbar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatisticsModel content = Content_student.get(getPosition());
                }
            });
        }

    }


}
