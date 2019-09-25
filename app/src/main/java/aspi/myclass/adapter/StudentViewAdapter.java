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

import aspi.myclass.Helpers.MessageHelper;
import aspi.myclass.Tools.Tools;
import aspi.myclass.content.AbsentPersentContent;
import aspi.myclass.R;
import aspi.myclass.activity.MainActivity;
import aspi.myclass.activity.NewClassActivity;
import aspi.myclass.Helpers.DatabasesHelper;


public class StudentViewAdapter extends RecyclerView.Adapter<StudentViewAdapter.cvh> {
    private List<AbsentPersentContent> Content_new_class;
    private Context contexts;
    private DatabasesHelper data;
    Tools om = new Tools();
    String TAG = "TAG_StudentViewAdapter";

    public StudentViewAdapter(List<AbsentPersentContent> contents, Context context) {
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
        final AbsentPersentContent content = Content_new_class.get(position);
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
        if (!content.text.equals(" ")) {
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
                try {


                    final String sno_old = holder.sno.getText().toString();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(contexts, R.style.MyAlertDialogStyle);
                    final EditText input = new EditText(contexts);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    lp.setMargins(10, 10, 5, 0);
                    input.setLayoutParams(lp);
                    input.setHint("" + sno_old);
                    input.setInputType(InputType.TYPE_CLASS_NUMBER);
                    builder1.setView(input);
                    builder1.setTitle("ویرایش شماره دانشجویی" + content.sno + " را انجام دهید." + "\n\n");
                    builder1.setPositiveButton("ثبت", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            try {
                                data.open();
                                boolean S;
                                S = data.Update_sno(input.getText().toString(), NewClassActivity.did, holder.sno.getText().toString());
                                data.close();

                                if (S) {
                                    MessageHelper.Toast(contexts, "این شماره دانشجویی در کلاس وجود دارد.");
                                } else {
                                    holder.sno.setText(input.getText().toString());
                                }


                            } catch (Exception e) {
                                Log.i(TAG, "Error" + e.toString());
                            }
                        }
                    });
                    builder1.setNegativeButton("انصراف", null);
                    AlertDialog aler1 = builder1.create();
                    aler1.show();
                } catch (Exception e) {
                }
            }
        });
        //*****************************************************************
        holder.family.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {


                    AlertDialog.Builder builder1 = new AlertDialog.Builder(contexts, R.style.MyAlertDialogStyle);
                    final EditText input = new EditText(contexts);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    lp.setMargins(10, 10, 5, 0);
                    input.setLayoutParams(lp);
                    input.setText("" + holder.family.getText().toString());
                    builder1.setView(input);
                    builder1.setTitle("ویرایش نام خانوادگی " + content.family + " را انجام دهید." + "\n\n");
                    builder1.setPositiveButton("ثبت", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            try {
                                data.open();
                                data.update_one("klas", "family", input.getText().toString(), Integer.parseInt(content.id));
                                data.close();
                                holder.family.setText("" + input.getText().toString());
                                content.family = input.getText().toString();

                            } catch (Exception e) {
                            }
                        }
                    });
                    builder1.setNegativeButton("انصراف", null);
                    AlertDialog aler1 = builder1.create();
                    aler1.show();

                } catch (Exception e) {

                }
            }
        });
        //*****************************************************************
        holder.name.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {


                    AlertDialog.Builder builder1 = new AlertDialog.Builder(contexts, R.style.MyAlertDialogStyle);
                    final EditText input = new EditText(contexts);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    lp.setMargins(10, 10, 5, 0);
                    input.setLayoutParams(lp);
                    input.setText("" + holder.name.getText().toString());
                    builder1.setView(input);
                    builder1.setTitle("ویرایش نام  " + content.name + " را انجام دهید." + "\n\n");
                    builder1.setPositiveButton("ثبت", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            try {
                                data.open();
                                data.update_one("klas", "name", input.getText().toString(), Integer.parseInt(content.id));
                                data.close();
                                holder.name.setText("" + input.getText().toString());
                                content.name = input.getText().toString();

                            } catch (Exception e) {
                            }
                        }
                    });
                    builder1.setNegativeButton("انصراف", null);
                    AlertDialog aler1 = builder1.create();
                    aler1.show();

                } catch (Exception e) {
                }
            }
        });
        //*****************************************************************
        holder.text.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {


                    AlertDialog.Builder builder1 = new AlertDialog.Builder(contexts, R.style.MyAlertDialogStyle);
                    final EditText input = new EditText(contexts);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    lp.setMargins(10, 10, 5, 0);
                    input.setLayoutParams(lp);
                    input.setText("" + content.text);
                    builder1.setView(input);
                    builder1.setTitle("توضیحات " + content.name + " " + content.family + " را وارد کنید." + "\n\n");
                    builder1.setPositiveButton("ثبت", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            try {
                                data.open();
                                data.update_one("klas", "tx", input.getText().toString(), Integer.parseInt(content.id));
                                data.close();
                                if (input.getText().toString().replace("\n", "  ").length() > 50) {
                                    holder.text.setText(input.getText().toString().replace("\n", "  ").substring(0, 50) + "  توضایحات ادامه دارد...");
                                } else {
                                    holder.text.setText(input.getText().toString().replace("\n", "  "));
                                }
                                content.text = input.getText().toString();


                            } catch (Exception e) {
                            }
                        }
                    });
                    builder1.setNegativeButton("انصراف", null);
                    AlertDialog aler1 = builder1.create();
                    aler1.show();
                } catch (Exception e) {
                }
            }
        });
        //*****************************************************************
        holder.nomreh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {


                    AlertDialog.Builder builder1 = new AlertDialog.Builder(contexts, R.style.MyAlertDialogStyle);
                    final EditText input = new EditText(contexts);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    lp.setMargins(10, 10, 5, 0);
                    input.setLayoutParams(lp);
                    input.setHint("نمره را وارد کنید");
                    builder1.setView(input);
                    if (MainActivity.status_number.equals("on")) {
                        input.setInputType(InputType.TYPE_CLASS_NUMBER);
                    }
                    builder1.setTitle("نمره دانشجو " + content.name + " " + content.family + " را وارد کنید." + "\n\n");
                    builder1.setPositiveButton("ثبت", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            try {
                                data.open();
                                data.update_one("rollcall", "am", input.getText().toString(), Integer.parseInt(content.id_rull));
                                data.close();
                                holder.nomreh.setText("" + input.getText().toString());
                                content.nomreh = input.getText().toString();

                            } catch (Exception e) {
                            }
                        }
                    });
                    builder1.setNegativeButton("انصراف", null);
                    AlertDialog aler1 = builder1.create();
                    aler1.show();

                } catch (Exception e) {
                }
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
                    AbsentPersentContent conlist = Content_new_class.get(getPosition());
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
}
