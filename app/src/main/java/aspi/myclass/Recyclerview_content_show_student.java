package aspi.myclass;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;


public class Recyclerview_content_show_student extends RecyclerView.Adapter<Recyclerview_content_show_student.cvh> {

    private List<Content_show_student> Content_student;
    private Context contexts;
    private dbstudy data;
    Activity activity;

    public Recyclerview_content_show_student(List<Content_show_student> contents, Context context) {
        this.Content_student = contents;
        this.contexts = context;
        data = new dbstudy(context);
    }

    @Override
    public cvh onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_student, parent, false);
        return new cvh(view);
    }

    @Override
    public void onBindViewHolder(final cvh holder, int position) {
        final Content_show_student content = Content_student.get(position);
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
        holder.row.setTypeface(Main.FONTS);
        holder.name.setTypeface(Main.FONTS);
        holder.family.setTypeface(Main.FONTS);
        holder.status.setTypeface(Main.FONTS);
        holder.all.setTypeface(Main.FONTS);
        holder.abs.setTypeface(Main.FONTS);
        holder.per.setTypeface(Main.FONTS);
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
                    Content_show_student content = Content_student.get(getPosition());
                }
            });
        }

    }

    void TOAST(String TEXT) {
        Toast toast = Toast.makeText(contexts, "" + TEXT, Toast.LENGTH_LONG);
        TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
        textView.setTextColor(contexts.getResources().getColor(R.color.toast));
        textView.setTypeface(Main.FONTS);
        textView.setTextSize(18);
        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        textView.setGravity(Gravity.CENTER);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.toast);
        toast.show();
    }
}
