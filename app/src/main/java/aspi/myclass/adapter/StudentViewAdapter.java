package aspi.myclass.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import aspi.myclass.content.AbsentPersentContent;
import aspi.myclass.R;
import aspi.myclass.activity.MainActivity;
import aspi.myclass.activity.NewClassActivity;
import aspi.myclass.class_.dbstudy;


public class StudentViewAdapter extends RecyclerView.Adapter<StudentViewAdapter.cvh> {
    private List<AbsentPersentContent> Content_new_class;
    private Typeface FONT;
    private Context contexts;
    private dbstudy data;
    public StudentViewAdapter(List<AbsentPersentContent> contents, Typeface font, Context context) {
        this.Content_new_class = contents;
        this.FONT = font;
        this.contexts = context;
        data = new dbstudy(context);
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
            if (content.text.replace("\n","  ").length()>50){
                holder.text.setText(content.text.replace("\n","  ").substring(0,50)+"  توضایحات ادامه دارد...");
            }else{
                if (content.text.length()>2) {
                    holder.text.setText(content.text);
                }else{
                    holder.text.setText("توضیحات...");
                }
            }
        }
        //*****************************************************************
        holder.row.setTypeface(FONT);
        holder.sno.setTypeface(FONT);
        holder.name.setTypeface(FONT);
        holder.family.setTypeface(FONT);
        holder.nomreh.setTypeface(FONT);
        holder.text.setTypeface(FONT);
        //*****************************************************************
        holder.status.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences sp = contexts.getSharedPreferences("myclass", 0);
                float amozesh = sp.getFloat("LerningActivity",0);

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
                if (amozesh==9) {
                    SetCode(10);
                    Mesage("و همچنین شما می توانید با انتخاب کادر سبز رنگی که روبه روی نام دانشجو قرار دارد برای دانشجو نمره کلاسی یا میان ترم وارد کنید.");
                }
            }
        });
        //*****************************************************************
        holder.sno.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    SharedPreferences sp = contexts.getSharedPreferences("myclass", 0);
                    final float amozesh = sp.getFloat("LerningActivity",0);

                    final String sno_old = holder.sno.getText().toString();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(contexts,R.style.MyAlertDialogStyle);
                    final EditText input = new EditText(contexts);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    lp.setMargins(10,10,5,0);
                    input.setLayoutParams(lp);
                    input.setTypeface(FONT);
                    input.setHint("" + sno_old);
                    input.setInputType(InputType.TYPE_CLASS_NUMBER);
                    builder1.setView(input);
                    builder1.setTitle("ویرایش شماره دانشجویی" + content.sno + " را انجام دهید." + "\n\n");
                    builder1.setPositiveButton("ثبت", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            try {
                                data.open();
                                boolean S;
                                S = data.Update_sno(input.getText().toString(), NewClassActivity.did,holder.sno.getText().toString());
                                data.close();

                                if (S) {
                                    TOAST("این شماره دانشجویی در کلاس وجود دارد.");
                                }else{
                                    holder.sno.setText(input.getText().toString());
                                }

                                if (amozesh==11) {
                                    SetCode(12);
                                    Mesage("همچنین می توانید با انتخاب نام دانشجو را ویرایش کنید.");
                                }
                            } catch (Exception e) {
                                TOAST(e.toString());
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
                    SharedPreferences sp = contexts.getSharedPreferences("myclass", 0);
                    final float amozesh = sp.getFloat("LerningActivity",0);

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(contexts,R.style.MyAlertDialogStyle);
                    final EditText input = new EditText(contexts);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    lp.setMargins(10,10,5,0);
                    input.setLayoutParams(lp);
                    input.setText("" + holder.family.getText().toString());
                    builder1.setView(input);
                    input.setTypeface(FONT);
                    builder1.setTitle("ویرایش نام خانوادگی " + content.family + " را انجام دهید." + "\n\n");
                    builder1.setPositiveButton("ثبت", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            try {
                                data.open();
                                data.update_one("klas", "family", input.getText().toString(), Integer.parseInt(content.id));
                                data.close();
                                holder.family.setText("" + input.getText().toString());
                                content.family = input.getText().toString();
                                if (amozesh==13) {
                                    SetCode(14);
                                    Mesage("شما هم می توانید با انتخاب توضیحات دانشجو برای دانشجویی انتخاب شده توضیحات لازم را به برنامه اضافه کنید.");
                                }
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
                    SharedPreferences sp = contexts.getSharedPreferences("myclass", 0);
                    final float amozesh = sp.getFloat("LerningActivity",0);

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(contexts,R.style.MyAlertDialogStyle);
                    final EditText input = new EditText(contexts);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    lp.setMargins(10,10,5,0);
                    input.setLayoutParams(lp);
                    input.setText("" + holder.name.getText().toString());
                    builder1.setView(input);
                    input.setTypeface(FONT);
                    builder1.setTitle("ویرایش نام  " + content.name + " را انجام دهید." + "\n\n");
                    builder1.setPositiveButton("ثبت", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            try {
                                data.open();
                                data.update_one("klas", "name", input.getText().toString(), Integer.parseInt(content.id));
                                data.close();
                                holder.name.setText("" + input.getText().toString());
                                content.name = input.getText().toString();
                                if (amozesh==12) {
                                    SetCode(13);
                                    Mesage("همچنین می توانید با انتخاب نام خانوادگی  دانشجو را ویرایش کنید.");
                                }
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
                    SharedPreferences sp = contexts.getSharedPreferences("myclass", 0);
                    final float amozesh = sp.getFloat("LerningActivity",0);

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(contexts,R.style.MyAlertDialogStyle);
                    final EditText input = new EditText(contexts);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    lp.setMargins(10,10,5,0);
                    input.setLayoutParams(lp);
                    input.setText("" +content.text);
                    builder1.setView(input);
                    input.setTypeface(FONT);
                    builder1.setTitle("توضیحات " + content.name + " " + content.family + " را وارد کنید." + "\n\n");
                    builder1.setPositiveButton("ثبت", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            try {
                                data.open();
                                data.update_one("klas", "tx", input.getText().toString(), Integer.parseInt(content.id));
                                data.close();
                                if (input.getText().toString().replace("\n","  ").length()>50){
                                    holder.text.setText(input.getText().toString().replace("\n","  ").substring(0,50)+"  توضایحات ادامه دارد...");
                                }else{
                                    holder.text.setText(input.getText().toString().replace("\n","  "));
                                }
                                content.text = input.getText().toString();
                                if (amozesh==14){
                                    SetCode(15);
                                }

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
                    SharedPreferences sp = contexts.getSharedPreferences("myclass", 0);
                    final float amozesh = sp.getFloat("LerningActivity",0);

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(contexts,R.style.MyAlertDialogStyle);
                    final EditText input = new EditText(contexts);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    lp.setMargins(10,10,5,0);
                    input.setLayoutParams(lp);
                    input.setHint("نمره را وارد کنید");
                    builder1.setView(input);
                    if(MainActivity.status_number.equals("on")){
                        input.setInputType(InputType.TYPE_CLASS_NUMBER);
                    }
                    input.setTypeface(FONT);
                    builder1.setTitle("نمره دانشجو " + content.name + " " + content.family + " را وارد کنید." + "\n\n");
                    builder1.setPositiveButton("ثبت", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            try {
                                data.open();
                                data.update_one("rollcall", "am", input.getText().toString(), Integer.parseInt(content.id_rull));
                                data.close();
                                holder.nomreh.setText("" + input.getText().toString());
                                content.nomreh = input.getText().toString();
                                if (amozesh==10) {
                                    SetCode(11);
                                    Mesage("همچنین می توانید با انتخاب شماره ی دانشجویی را ویرایش کنید.");
                                }
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
    void TOAST(String TEXT) {
        Toast toast = Toast.makeText(contexts, "" + TEXT, Toast.LENGTH_LONG);
        TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
        textView.setTextColor(contexts.getResources().getColor(R.color.toast));
        textView.setTypeface(MainActivity.FONTS);
        textView.setTextSize(18);
        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.toast);
        toast.show();
    }
    void SetCode( float code) {
        SharedPreferences sp = contexts.getSharedPreferences("myclass", 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putFloat("LerningActivity", code);
        edit.commit();
    }
    void Mesage(String text){
        final Dialog massege = new Dialog(contexts,R.style.MyAlertDialogStyle);
        massege.setContentView(R.layout.dialog_message);
        massege.setCancelable(false);
        massege.setCanceledOnTouchOutside(false);
        massege.show();
        final TextView ok = (TextView) massege.findViewById(R.id.massge_btn);
        final TextView txt = (TextView) massege.findViewById(R.id.massge_text);
        //**********************************************************************
        txt.setText(""+text);
        txt.setTypeface(Typeface.createFromAsset(contexts.getAssets(), "Font/font2.ttf"));
        ok.setTypeface(Typeface.createFromAsset(contexts.getAssets(), "Font/font2.ttf"));
        //**********************************************************************
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                massege.dismiss();
            }
        });
    }
}
