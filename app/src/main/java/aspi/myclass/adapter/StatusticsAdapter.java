package aspi.myclass.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import aspi.myclass.model.StatisticsModel;
import aspi.myclass.R;
import aspi.myclass.activity.MainActivity;
import aspi.myclass.Helpers.DatabasesHelper;


public class StatusticsAdapter extends RecyclerView.Adapter<StatusticsAdapter.cvh> {

    private List<StatisticsModel> Content_student;
    private Context contexts;
    private DatabasesHelper data;
    Activity activity;

    public StatusticsAdapter(List<StatisticsModel> contents, Context context) {
        this.Content_student = contents;
        this.contexts = context;
        data = new DatabasesHelper(context);
    }

    @Override
    public cvh onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_statistics, parent, false);
        return new cvh(view);
    }

    @Override
    public void onBindViewHolder(final cvh holder, int position) {
        final StatisticsModel content = Content_student.get(position);
        //*************************************************************************
        if (position<9)holder.row.setText("  " + (position + 1)+" ");
        if (position>9)holder.row.setText(" " + (position + 1)+" ");
        if (position>99)holder.row.setText("" + (position + 1)+" ");
        holder.name.setText(""+content.name);
        holder.family.setText(""+content.family);
        holder.all.setText("تعداد کل جلسات "+content.max);
        holder.abs.setText(content.set+" حضور");
        holder.per.setText(content.per+" غیبت");
        float status_=((float)content.set/content.max)*100;
        holder.status.setText("%"+String.valueOf((int)status_));
        //*************************************************************************
        holder.row.setTypeface(MainActivity.FONTS);
        holder.name.setTypeface(MainActivity.FONTS);
        holder.family.setTypeface(MainActivity.FONTS);
        holder.status.setTypeface(MainActivity.FONTS);
        holder.all.setTypeface(MainActivity.FONTS);
        holder.abs.setTypeface(MainActivity.FONTS);
        holder.per.setTypeface(MainActivity.FONTS);
        holder.progressDialog.setProgress(content.set);
        holder.progressDialog.setMax(content.max);
        //*************************************************************************
    }

    public int getItemCount() {
        return Content_student.size();
    }

    public class cvh extends RecyclerView.ViewHolder {

        private TextView row, name, family, status,abs,per,all;
        private ProgressBar progressDialog;

        public cvh(View itemView) {
            super(itemView);

            row = (TextView) itemView.findViewById(R.id.show_student_number);
            name = (TextView) itemView.findViewById(R.id.show_student_name);
            family = (TextView) itemView.findViewById(R.id.show_student_family);
            status = (TextView) itemView.findViewById(R.id.show_student_status);
            all= (TextView) itemView.findViewById(R.id.show_student_all);
            abs= (TextView) itemView.findViewById(R.id.show_student_absent);
            per= (TextView) itemView.findViewById(R.id.show_student_present);
            progressDialog= (ProgressBar) itemView.findViewById(R.id.show_student_progressbar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatisticsModel content = Content_student.get(getPosition());
                }
            });
        }

    }


}
