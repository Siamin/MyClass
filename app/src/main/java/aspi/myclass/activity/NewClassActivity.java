package aspi.myclass.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import aspi.myclass.Helpers.DateHelper;
import aspi.myclass.Helpers.IndicatorHelper;
import aspi.myclass.Helpers.LanguageHelper;
import aspi.myclass.Helpers.MessageHelper;
import aspi.myclass.model.AbsentPersentModel;
import aspi.myclass.R;
import aspi.myclass.adapter.StudentViewAdapter;
import aspi.myclass.Helpers.DatabasesHelper;


public class NewClassActivity extends Activity {

    TextView name_class, DATA;
    public static String Name_class, did;
    DatabasesHelper data;
    RecyclerView recyclerView1;
    LinearLayoutManager linearLayoutManager;
    public static List<AbsentPersentModel> absentPersentModels = new ArrayList<>();
    int MINUTE, HOUR;
    String[] DATA_IRAN;
    String TAG = "TAG_NewClassActivity";
    public Timer time;
    int counters = 0;
    boolean view = false;
    ImageView backPage;
    EditText Search;
    boolean statusBackPage = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        LanguageHelper.loadLanguage(NewClassActivity.this);
        setContentView(R.layout.activity_newclass);
        //******************************************************************************************
        initView();
        //******************************************************************************************
        backPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        Search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch(Search.getText().toString());
                    return true;
                }
                return false;
            }

        });

        Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                performSearch(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    void performSearch(String search) {
        List<AbsentPersentModel> searchList = new ArrayList<>();
        for (int i = 0; i < absentPersentModels.size(); i++) {
            if (absentPersentModels.get(i).family.contains(search)) {
                searchList.add(absentPersentModels.get(i));
            }
        }

        ShowAdapter(searchList.size() > 0 ? searchList : absentPersentModels);

    }

    void initView() {
        data = new DatabasesHelper(this);

        backPage = findViewById(R.id.activity_newclass_back);
        name_class = (TextView) findViewById(R.id.new_class_name_class);
        DATA = (TextView) findViewById(R.id.new_class_data);
        recyclerView1 = (RecyclerView) findViewById(R.id.recyclerview_new_classsss);
        linearLayoutManager = new LinearLayoutManager(NewClassActivity.this);
        Search = findViewById(R.id.newclass_search);
        //*************************************************************
        name_class.setText(getResources().getString(R.string.Class) + " " + Name_class);
        DATA.setText(DateHelper.GetData(NewClassActivity.this));
        HOUR = Integer.parseInt(DateHelper.Get_Time().split(":")[0]);
        MINUTE = Integer.parseInt(DateHelper.Get_Time().split(":")[1]);
        //*************************************************************
        Start();
    }

    void go_main() {
        finish();
    }

    void Start() {
        final Timer time = new Timer();
        time.schedule(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        counters += 1;
                        if (counters == 1) {
                            IndicatorHelper.IndicatorCreate(NewClassActivity.this, getResources().getString(R.string.gettingData), getResources().getString(R.string.pleaseWait));
                            new Thread(new Runnable() {
                                public void run() {
                                    Data();
                                }
                            }).start();
                        }
                        if (view) {
                            view = false;
                            ShowAdapter(absentPersentModels);
                            IndicatorHelper.IndicatorDismiss();
                            time.cancel();
                        }
                    }
                });
            }
        }, 1, 1000);
    }

    void Data() {
        try {

            absentPersentModels.clear();

            data.open();
            absentPersentModels = data.NameStudentClassByModel(did);
            data.close();

            if (absentPersentModels.size() > 0) {

                DATA_IRAN = DateHelper.GetData(NewClassActivity.this).split("/");

                data.open();
                int cunt_roll = data.count("rollcall");
                int jalase = 1;
                if (cunt_roll > 0) {
                    jalase = Integer.parseInt(data.Display("rollcall", (cunt_roll - 1), 8)) + 1;
                }

                for (int i = 0; i < absentPersentModels.size(); i++) {
                    data.insert_Rollcall(absentPersentModels.get(i).sno, true, "", DATA_IRAN[0], DATA_IRAN[1], DATA_IRAN[2], did, String.valueOf(jalase), String.valueOf(HOUR) + ":" + String.valueOf(MINUTE));
                    absentPersentModels.get(i).id_rull = data.Display("rollcall", cunt_roll, 0);
                    absentPersentModels.get(i).nomreh = getResources().getString(R.string.Score);
                    cunt_roll += 1;
                }
                data.close();
                view = true;
                //**********************************************************************************
            } else {
                runOnUiThread(new Runnable() {
                    public void run() {
                        MessageHelper.Toast(NewClassActivity.this, getResources().getString(R.string.NoAddStudentToClass));
                        go_main();
                    }
                });
            }

        } catch (final Exception e) {
            runOnUiThread(new Runnable() {
                public void run() {
                    Log.i(TAG, "" + e.toString());
                }
            });
        }
    }

    void ShowAdapter(List<AbsentPersentModel> ListClass) {
        recyclerView1.setLayoutManager(linearLayoutManager);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setAdapter(new StudentViewAdapter(ListClass, NewClassActivity.this));
    }

    @Override
    public void onBackPressed() {

        if (statusBackPage) {
            super.onBackPressed();
            go_main();
        } else {
            MessageHelper.Toast(NewClassActivity.this, getResources().getString(R.string.BackPage));
            statusBackPage = true;
        }
    }


}
