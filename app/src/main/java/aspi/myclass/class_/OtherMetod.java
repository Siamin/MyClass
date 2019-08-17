package aspi.myclass.class_;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;

import aspi.myclass.R;
import aspi.myclass.activity.MainActivity;

public class OtherMetod {

    public String Maile = "amin.syahi.1369@gmail.com", Pass = "942134025";
    SharedPreferences sp;
    String packages = "myclass";
    Context C;
    String[] Arry = {"A", "a", "B", "b", "C", "c", "D", "d", "E", "e", "F", "f", "G", "g", "H", "h", "I", "i", "J", "j"
            , "K", "k", "L", "l", "M", "m", "N", "n", "O", "o", "P", "P", "Q", "q", "R", "r", "S", "s", "T", "t", "U", "u"
            , "V", "v", "W", "w", "X", "x", "Y", "y", "Z", "z",};


    public String Pin_Cod(int cunt_pin) {
        Random random = new Random();
        String Resualt = "";
        for (int i = 0; i < cunt_pin; i++) {
            Resualt += (random.nextInt(9) + 0);
        }
        return Resualt;
    }

    public String Pin_Cod_en(int cunt_pin) {
        Random random = new Random();
        String Resualt = "";
        for (int i = 0; i < cunt_pin; i++) {
            if (i % 2 == 0) {
                Resualt += (random.nextInt(9) + 0);
            } else {
                int Ran = (random.nextInt(Arry.length) + 0);
                Resualt += Arry[Ran];
            }

        }
        return Resualt;
    }

    public void SetCode(String name, String code, Context context) {
        sp = context.getSharedPreferences(packages, 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(name, code);
        edit.commit();
    }

    public String get_Data(String name, String Null, Context context) {
        sp = context.getSharedPreferences(packages, 0);
        return sp.getString(name, Null);
    }

    public boolean isNetworkConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE); // 1
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo(); // 2
        return networkInfo != null && networkInfo.isConnected(); // 3
    }

    public String date_iran() {
        Calendar c = Calendar.getInstance();
        int year = 0, month = 0, day = 0;
        int y = c.get(Calendar.YEAR);
        int x = c.get(Calendar.DAY_OF_YEAR);
        //*******************************
        if (x >= 0 && x <= 20) {
            year = y - 622;
        } else if (x >= 21 && x <= 50) {
            year = y - 622;
        } else if (x >= 51 && x <= 79) {
            year = y - 622;
        } else if (x >= 80 && x <= 266) {
            year = y - 621;
        } else if (x >= 267 && x <= 365) {
            year = y - 621;
        }
        int mod = year % 33, kabise = 0;
        if (mod == 1 || mod == 5 || mod == 9 || mod == 13 || mod == 17 || mod == 22 || mod == 26 || mod == 30) {
            kabise = 1;
        } else {
            kabise = 0;
        }
        //*******************************
        if (x >= 0 && x <= 20) {
            month = 10;
            day = x + 10;
        } else if (x >= 21 && x <= 50) {
            month = 11;
            day = x - 20;
        } else if (x >= 51 && x <= 79 && kabise == 0) {
            month = 12;
            day = x - 40;
        } else if (x >= 51 && x <= 80 && kabise == 1) {
            month = 12;
            day = x - 49;
        } else if (x >= 80 && x <= 266 && kabise == 0) {
            x = x - 80;
            month = (x / 31) + 1;
            day = (x % 31) + 1;
        } else if (x >= 81 && x <= 266 && kabise == 1) {
            x = x - 79;
            month = (x / 31) + 1;
            day = (x % 31);
        } else if (x >= 267 && x <= 365) {
            x = x - 266;
            month = (x / 30) + 7;
            day = (x % 30) + 1;
        }
        String data = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
        return data;
    }

    public String Get_Time() {
        Calendar c = Calendar.getInstance();
        int h = c.get(Calendar.HOUR_OF_DAY);
        String Time;
        if (h < 10) {
            Time = "0" + h + ":00";
        } else {
            Time = h + ":00";
        }

        return Time;
    }

    public String Time() {
        Calendar c = Calendar.getInstance();
        int H = c.get(Calendar.HOUR_OF_DAY);
        int M = c.get(Calendar.MINUTE);
        //**************************************
        String Hou = String.valueOf(H), Min = String.valueOf(M);
        if (H >= 0 && H < 10) {
            Hou = "0" + H;
        }

        if (M >= 0 && M < 10) {
            Min = "0" + M;
        }
        //**************************************
        return String.valueOf(Hou) + "/" + Min;
    }

    public String date_shamsi() {
        Calendar c = Calendar.getInstance();
        int year = 0, month = 0, day = 0;
        int y = c.get(Calendar.YEAR);
        int x = c.get(Calendar.DAY_OF_YEAR);
        //*******************************
        if (x >= 0 && x <= 20) {
            year = y - 622;
        } else if (x >= 21 && x <= 50) {
            year = y - 622;
        } else if (x >= 51 && x <= 79) {
            year = y - 622;
        } else if (x >= 80 && x <= 266) {
            year = y - 621;
        } else if (x >= 267 && x <= 365) {
            year = y - 621;
        }
        int mod = year % 33, kabise = 0;
        if (mod == 1 || mod == 5 || mod == 9 || mod == 13 || mod == 17 || mod == 22 || mod == 26 || mod == 30) {
            kabise = 1;

        } else {
            kabise = 0;
        }
        //*******************************
        if (x >= 0 && x <= 20) {
            month = 10;
            day = x + 10;
            Log.i("TAG_DM", "1");
        } else if (x >= 21 && x <= 50) {
            month = 11;
            day = x - 20;
            Log.i("TAG_DM", "2");
        } else if (x >= 51 && x <= 79 && kabise == 0) {
            month = 12;
            day = x - 50;
            Log.i("TAG_DM", "3");
        } else if (x >= 51 && x <= 80 && kabise == 1) {
            month = 12;
            day = x - 49;
            Log.i("TAG_DM", "4");
        } else if (x >= 80 && x <= 266 && kabise == 0) {
            x = x - 80;
            month = (x / 31) + 1;
            day = (x % 31) + 1;
            Log.i("TAG_DM", "5");
        } else if (x >= 81 && x <= 266 && kabise == 1) {
            x = x - 79;
            month = (x / 31) + 1;
            day = (x % 31);
            Log.i("TAG_DM", "6");
        } else if (x >= 267 && x <= 365) {
            x = x - 266;
            month = (x / 30) + 7;
            day = (x % 30) + 1;
            Log.i("TAG_DM", "7");
        }
        //**************************************
        String Month = String.valueOf(month), Day = String.valueOf(day);
        if (month >= 0 && month < 10) {
            Month = "0" + month;
        }

        if (day >= 0 && day < 10) {
            Day = "0" + day;
        }
        //**************************************
        return String.valueOf(year) + "/" + Month + "/" + Day;
    }

    public String GetTime() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        //*******************************
        String TimeS = "";

        if (hour >= 0 && hour < 10) {
            TimeS = "0" + hour;
        } else {
            TimeS = String.valueOf(hour);
        }

        if (min >= 0 && min < 10) {
            TimeS += ":0" + min;
        } else {
            TimeS += ":" + String.valueOf(min);
        }
        //*******************************
        return TimeS;
    }

    public Typeface SetFont(Context context, String name) {
        return Typeface.createFromAsset(context.getAssets(), "Font/" + name + ".ttf");
    }

    public String date_and_time_iran() {
        Calendar c = Calendar.getInstance();
        int year = 0, month = 0, day = 0;
        int y = c.get(Calendar.YEAR);
        int x = c.get(Calendar.DAY_OF_YEAR);
        int MINUTE = c.get(Calendar.MINUTE);
        int HOUR = c.get(Calendar.HOUR_OF_DAY);
        //*******************************
        if (x >= 0 && x <= 20) {
            year = y - 622;
        } else if (x >= 21 && x <= 50) {
            year = y - 622;
        } else if (x >= 51 && x <= 79) {
            year = y - 622;
        } else if (x >= 80 && x <= 266) {
            year = y - 621;
        } else if (x >= 267 && x <= 365) {
            year = y - 621;
        }
        int mod = year % 33, kabise = 0;
        if (mod == 1 || mod == 5 || mod == 9 || mod == 13 || mod == 17 || mod == 22 || mod == 26 || mod == 30) {
            kabise = 1;
        } else {
            kabise = 0;
        }
        //*******************************
        if (x >= 0 && x <= 20) {
            month = 10;
            day = x + 10;
        } else if (x >= 21 && x <= 50) {
            month = 11;
            day = x - 20;
        } else if (x >= 51 && x <= 79 && kabise == 0) {
            month = 12;
            day = x - 40;
        } else if (x >= 51 && x <= 80 && kabise == 1) {
            month = 12;
            day = x - 49;
        } else if (x >= 80 && x <= 266 && kabise == 0) {
            x = x - 80;
            month = (x / 31) + 1;
            day = (x % 31) + 1;
        } else if (x >= 81 && x <= 266 && kabise == 1) {
            x = x - 79;
            month = (x / 31) + 1;
            day = (x % 31);
        } else if (x >= 267 && x <= 365) {
            x = x - 266;
            month = (x / 30) + 7;
            day = (x % 30) + 1;
        }
        String data = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day) + "\n  در ساعت = "
                + String.valueOf(HOUR) + ":" + String.valueOf(MINUTE) + ":" + String.valueOf(c.get(Calendar.SECOND));
        return data;
    }

    public void Toast(Context context, String Text) {
        Toast toast = Toast.makeText(context, "" + Text, Toast.LENGTH_LONG);
        TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
        textView.setPadding(5, 3, 5, 3);
        textView.setTextColor(context.getResources().getColor(R.color.yellow));
        textView.setTypeface(MainActivity.FONTS);
        textView.setTextSize(18);
        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.toast);
        toast.show();
    }

    public String GetVer(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo("aspi.myclass", 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    void Bazar(Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("bazaar://details?id=aspi.myclass"));
            intent.setPackage("com.farsitel.bazaar");
            context.startActivity(intent);
        } catch (Exception e) {
            Toast(context, "لطفا برنامه بازار را نصب کنید...!");
        }
    }

    public void Abute(final Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo("aspi.myclass", 0);
            String version = pInfo.versionName;
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
            builder.setIcon(R.drawable.abute);

            builder.setTitle("درباره ما").setMessage("نرم افزار حضور وغیاب برای راحتی کار اساتید و معلمین گرامی طراحی و ساخته شده است برای حمایت از ما در نرم افزار بازار به ما رای بدهید" + "\n" + "\n" + "نرم افزار حضور و غیاب اساتید" + version);
            builder.setPositiveButton("امتیاز دادن به برنامه", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    Bazar(context);
                }
            });
            builder.setNegativeButton("دیگر برنامه های ما", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    Developer_app(context);
                }
            });
            AlertDialog aler = builder.create();
            aler.show();
        } catch (Exception e) {
        }
    }

    void Developer_app(Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("bazaar://collection?slug=by_author&aid=aminsyahi1369"));
            intent.setPackage("com.farsitel.bazaar");
            context.startActivity(intent);
        } catch (Exception e) {
            Toast(context, "لطفا برنامه بازار را نصب کنید...!");
        }

    }

    public void Qusins(final Context context) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
            builder.setIcon(R.drawable.abute);

            builder.setTitle("بروزرسانی").setMessage("آیا مایلید برنامه حضور و غیاب را بروزرسانی کنید ؟");
            builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    Bazar(context);
                }
            });
            builder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                }
            });
            AlertDialog aler = builder.create();
            aler.show();
        } catch (Exception e) {

        }
    }

    public void Mesage(Context context, String text) {
        final Dialog massege = new Dialog(context, R.style.NewDialog);
        massege.requestWindowFeature(Window.FEATURE_NO_TITLE);
        massege.setContentView(R.layout.dialog_message);
        massege.setCancelable(false);
        massege.setCanceledOnTouchOutside(false);
        massege.show();
        final TextView ok = (TextView) massege.findViewById(R.id.massge_btn);
        final TextView txt = (TextView) massege.findViewById(R.id.massge_text);
        //**********************************************************************
        txt.setText("" + text);
        txt.setTypeface(Typeface.createFromAsset(context.getAssets(), "Font/font2.ttf"));
        ok.setTypeface(Typeface.createFromAsset(context.getAssets(), "Font/font2.ttf"));
        //**********************************************************************
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                massege.dismiss();
            }
        });
    }

}
