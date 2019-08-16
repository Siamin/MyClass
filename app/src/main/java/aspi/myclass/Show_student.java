package aspi.myclass;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class Show_student extends Activity {


    public static String Name_class, Id_class, Did_class;
    private dbstudy data;
    private ProgressDialog progressDialog;
    private int cunters = 0;
    private RecyclerView recyclerView_show_student;
    private LinearLayoutManager linearLayoutManager_show_student;
    private boolean view = false;
    private java.util.List<Content_show_student> List = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        data = new dbstudy(this);
        Start();


    }

    void TOAST(String TEXT) {
        Toast toast = Toast.makeText(this, "" + TEXT, Toast.LENGTH_LONG);
        TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
        textView.setTextColor(getResources().getColor(R.color.toast));
        textView.setTypeface(Main.FONTS);
        textView.setTextSize(18);
        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.toast);
        toast.show();
    }

    void Get_Data_Base() {
        /*try {
            data.open();
            String name_ = "", family_ = "", old_j = "";
            int cunter = 0, cunt_student = data.count("klas"), cunt_rollcal = data.count("rollcall");
            progressDialog.setMax(cunt_student + cunt_rollcal);
            for (int i = 0; i < cunt_student; i++) {
                String Class[] = data.Display_Class("klas", i).split("~");
                if (Did_class.equals(Class[5])) {
                    family_ += Class[2] + "~";
                    name_ += Class[3] + "~";
                    cunter += 1;
                }
                progressDialog.setProgress(i);
            }
            if (cunter > 0) {
                String[] name, family;
                int[] abs, per;
                family = family_.split("~");
                name = name_.split("~");
                per = new int[name.length];
                abs = new int[name.length];
                cunter = 1;
                for (int i = 0, j = 0; i < cunt_rollcal; i++) {
                    String Class[] = data.Display_all("rollcall", i, 0, 9).split("~");
                    if (i == 0) old_j = Class[8];
                    if (Did_class.equals(Class[7])) {
                        if (!old_j.equals(Class[8]) && j != 0) {
                            j = 0;
                            cunter += 1;
                        }
                        if (Integer.parseInt(Class[2]) == 1) {
                            abs[j] += 1;
                        } else if (Integer.parseInt(Class[2]) == 0) {
                            per[j] += 1;
                        }
                        j++;
                        if (j == name.length) {
                            j = 0;
                            cunter += 1;
                        }
                        old_j = Class[8];
                    }
                    progressDialog.setProgress(cunt_student + i);
                }
                data.close();
                cunter -= 1;
                if (cunter > 0) {
                    for (int i = 0; i < name.length; i++) {
                        Content_show_student content = new Content_show_student();
                        content.name = name[i];
                        content.family = family[i];
                        content.max = cunter;
                        content.set = abs[i];
                        content.per = per[i];
                        List.add(content);
                    }
                    view = true;
                } else {
                    view = false;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            TOAST("هیچ جلسه ای برای این کلاس ثبت نشده...!");
                        }
                    });
                    finish();
                }

            } else {
                view = false;
                runOnUiThread(new Runnable() {
                    public void run() {
                        TOAST("هیچ دانشجویی در این کلاس ثبت نشده است...!");
                    }
                });
                finish();
            }
        } catch (final Exception e) {
        }
*/
    }

    void Start() {
        final Timer time = new Timer();
        time.schedule(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        cunters += 1;
                        if (cunters == 1) {
                            progressDialog = new ProgressDialog(Show_student.this);
                           /* progressDialog.setProgress(0);
                            progressDialog.setProgressDrawable(getResources().getDrawable(R.drawable.dialog));
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);*/
                            progressDialog.setTitle("درحال دریافت اطلاعات");
                            progressDialog.setCancelable(false);
                            progressDialog.setMessage("لطفا صبر کنید ...!");
                            progressDialog.show();
                            new Thread(new Runnable() {
                                public void run() {
                                    //Get_Data_Base();
                                    Get_Qury_database();
                                }
                            }).start();
                        }
                        if (view) {
                            recyclerView_show_student = (RecyclerView) findViewById(R.id.show_student_recyclerview);
                            linearLayoutManager_show_student = new LinearLayoutManager(Show_student.this);
                            recyclerView_show_student.setLayoutManager(linearLayoutManager_show_student);
                            recyclerView_show_student.setAdapter(new Recyclerview_content_show_student(List, Show_student.this));
                            time.cancel();
                            progressDialog.cancel();
                        }
                    }
                });
            }
        }, 1, 1000);
    }

    void Get_Qury_database() {
        try {
            data.open();
            String Res = data.Qury_amar_class("SELECT  r.sno,r.abs,r.iddars,r.jalase,k.name,k.family,k.sno" +
                    " FROM klas AS k,rollcall AS r WHERE r.iddars= " + Integer.parseInt(Did_class) +
                    " AND r.sno = k.sno AND r.iddars = k.did ORDER BY k.family ASC,k.name ASC,r.sno ASC ", Did_class);
            data.close();

            String name = "", family = "", per = "", abs = "",max="";
            String[] Name, Family, Per, Abs;
            int Cunt = 0;

            if(!Res.equals("")){

                for (int i = 0, j = 0, start = 0; i < Res.length(); i++) {

                    if (Res.charAt(i) == '|') {
                        String TE = Res.substring(start, i);
                        start = i + 1;

                        if (j == 0) {
                            abs = TE;
                            j += 1;
                        } else if (j == 1) {
                            per = TE;
                            j += 1;
                        } else if (j == 2) {
                            name = TE;
                            j += 1;
                        } else if (j == 3) {
                            family = TE;
                            j += 1;
                        } else if (j == 4) {
                            Cunt = Integer.parseInt(TE);
                            j += 1;
                        } else if (j == 5) {
                            max = TE;
                            j += 1;
                        }
                    }

                }

                Name=name.split("~");
                Family=family.split("~");
                Per=per.split("~");
                Abs=abs.split("~");

                for (int i = 0; i < Cunt; i++) {
                    Content_show_student content = new Content_show_student();
                    content.name = Name[i];
                    content.family = Family[i];
                    content.max = Integer.parseInt(max);
                    content.set =Integer.parseInt(Abs[i]);
                    content.per = Integer.parseInt(Per[i]);
                    List.add(content);
                }

                view = true;

            }else{

                runOnUiThread(new Runnable() {
                    public void run() {
                        TOAST(" برای آمار حضور و غیاب شما باید جلسه برای کلاس خود تشکیل دهید...! ");
                    }
                });
            }



        } catch (final Exception e) {
        }
    }
}
