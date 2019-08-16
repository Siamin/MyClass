package aspi.myclass;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class Amozesh extends Activity {

    LinearLayout linearLayout;
    int w, h;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lerning);
        linearLayout = (LinearLayout) findViewById(R.id.amozesh_linear);
        Mode();
    }
    void text(String text, float size,int p) {
        TextView tv = new TextView(Amozesh.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 10, 0, 10);
        tv.setPadding(15, 20, 15, 10);
        tv.setTextColor(getResources().getColor(R.color.toast));
        tv.setTextSize(size);
        lp.gravity = p;
        tv.setTypeface(Typeface.createFromAsset(getAssets(), "Font/font2.ttf"));
        tv.setText(text);
        linearLayout.addView(tv, lp);

    }
    void line() {
        TextView tv = new TextView(Amozesh.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,2);
        lp.setMargins(0, 0, 0, 0);
        tv.setPadding(5, 0, 5, 0);
        tv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        lp.gravity = Gravity.CENTER;
        linearLayout.addView(tv, lp);

    }
    void img(String name,int p) {
        ImageView img = new ImageView(Amozesh.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(p, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        AssetManager am = getAssets();
        InputStream is = null;
        try {
            is = am.open("image/" + name);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap bi = BitmapFactory.decodeStream(is);

        if (bi != null) {
            img.setImageBitmap(bi);
            linearLayout.addView(img, lp);
        }

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    void Mode() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        final TextView input = new TextView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setText("آموزش مورد نظرتان را انتخاب کنید.");
        input.setTypeface(Main.FONTS);
        input.setTextSize(15);
        input.setPadding(10, 10, 5, 0);
        builder1.setView(input);
        builder1.setTitle("آموزش");
        builder1.setPositiveButton("آموزش text", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                LerningText();
            }
        });
        builder1.setNegativeButton("آموزش Excel",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                LerningExcel();
            }
        });
        AlertDialog aler1 = builder1.create();
        aler1.show();
    }
    void LerningText(){
        text("آموزش اضافه کردن دانشجویان بصورت لیستی", 30,Gravity.CENTER);
        line();
        text("گام اول:", 30,Gravity.RIGHT);
        text("ابتدا در کامپیوتر خود یک فایل txt را باز کنید.",25,Gravity.RIGHT);
        line();
        text("گام دوم:", 30,Gravity.RIGHT);
        text("در این قسمت شما باید نام دانشجویان خود را وارد کنید بدین صورت که اول شماره ی دانشجوی و سپس نام دانشجو وبعد نام خانوادگی بین شماره ، نام ، نام خانوادگی دانشجو باید از - استفاده کرد و در خط بعد اسامی دیگر دانشجویان را به همین ترتیب وارد کنید.",25,Gravity.RIGHT);
        text("نمونه وارد کردن اسامی دانشجویان:"+"\n 9191-امین-سیاهی\n 9192-رضا-محمدیان\n9193-حسین-مرادی", 25,Gravity.RIGHT);
        img("A1.png",LinearLayout.LayoutParams.MATCH_PARENT);
        text("\n",10,Gravity.RIGHT);
        line();
        text("گام سوم:",30,Gravity.RIGHT);
        text("در این قسمت نام فایل txt رابه IO.txt تغییر دهید چون نرم افزار این نام را جستجو می کند و پس از ذخیره ی اطلاعات فایل را در داخل حافظه ی گوشی خود و در پوشه ی App_class قرار دهید.",30,Gravity.RIGHT);
        img("A2.png",LinearLayout.LayoutParams.WRAP_CONTENT);
        text("\n",10,Gravity.RIGHT);
        img("A3.png",LinearLayout.LayoutParams.WRAP_CONTENT);
        text("\n",10,Gravity.RIGHT);
        line();

        text("گام چهارم:",30,Gravity.RIGHT);
        text("وارد برنامه شوید و به قسمت دانشجوی جدید بروید",30,Gravity.RIGHT);
        img("A4.png",LinearLayout.LayoutParams.WRAP_CONTENT);
        text("\n",10,Gravity.RIGHT);
        text("\n",10,Gravity.RIGHT);
        img("A5.png",LinearLayout.LayoutParams.WRAP_CONTENT);
        text("\n",10,Gravity.RIGHT);
        text("و سپس گزینه ی سبز رنگ بالای صفحه را انتخاب کنید.با انتخاب گزینه ی سبز رنگ بالای صفحه برای شما پیغام زیر نمایش داده می شود.",30,Gravity.RIGHT);
        img("A6.png",LinearLayout.LayoutParams.WRAP_CONTENT);
        text("\n",10,Gravity.RIGHT);
        text("با انتخاب گزینه ی فایل Text در پنجره ی بازشده اسامی دانشجویان برای شما نمایش دارده می شود اگر لیست اسامی دانشجویان شما بیشتر از 20 نفر باشد برای شما گزینه ی زیر نمایش داده می شود که به شما این امکان را می دهد با ذخیره ی 20 نفر اول شما بقیه لیست را در صفحه مشاهده کنید و زمانی لیست ذخیره تمام شود لیست حذف می شود.",30,Gravity.RIGHT);
        img("A7.png",LinearLayout.LayoutParams.WRAP_CONTENT);
        text("\n",10,Gravity.RIGHT);
        line();
    }
    void LerningExcel(){
        text("آموزش اضافه کردن دانشجویان بصورت لیستی", 30,Gravity.CENTER);
        line();
        text("گام اول:", 30,Gravity.RIGHT);
        text("ابتدا در کامپیوتر خود یک فایل اکسل(Excel)  باز کنید.",25,Gravity.RIGHT);
        line();
        text("گام دوم:", 30,Gravity.RIGHT);
        text("اسمامی دانشجویان را بصورت در فایل اکسل وارد کنید که در ستون A شماره دانشجویی تمام دانشجویان و در ستون B نام دانشجویان و در ستون C نام خانوادگی دانشجویان را بصورت زیر وارد کنید",25,Gravity.RIGHT);
        img("E1.png",LinearLayout.LayoutParams.WRAP_CONTENT);
        text("\n",10,Gravity.RIGHT);
        line();
        text("گام سوم:",30,Gravity.RIGHT);
        text("در این قسمت نام فایل اکسل(Excel) رابه IO.xls تغییر دهید چون نرم افزار این نام را جستجو می کند و پس از ذخیره ی اطلاعات فایل را در داخل حافظه ی گوشی خود و در پوشه ی App_class قرار دهید.",30,Gravity.RIGHT);
        img("A2.png",LinearLayout.LayoutParams.WRAP_CONTENT);
        text("\n",10,Gravity.RIGHT);
        img("E2.png",LinearLayout.LayoutParams.WRAP_CONTENT);
        text("\n",10,Gravity.RIGHT);
        line();
        text("گام چهارم:",30,Gravity.RIGHT);
        text("وارد برنامه شوید و به قسمت دانشجوی جدید بروید",30,Gravity.RIGHT);
        img("A4.png",LinearLayout.LayoutParams.WRAP_CONTENT);
        text("\n",10,Gravity.RIGHT);
        text("\n",10,Gravity.RIGHT);
        img("A5.png",LinearLayout.LayoutParams.WRAP_CONTENT);
        text("\n",10,Gravity.RIGHT);
        text("\n",10,Gravity.RIGHT);
        text("و سپس گزینه ی سبز رنگ بالای صفحه را انتخاب کنید.با انتخاب گزینه ی سبز رنگ بالای صفحه برای شما پیغام زیر نمایش داده می شود.",30,Gravity.RIGHT);
        img("A6.png",LinearLayout.LayoutParams.WRAP_CONTENT);
        text("\n",10,Gravity.RIGHT);
        text("با انتخاب گزینه ی فایل Excal در پنجره ی بازشده اسامی دانشجویان برای شما نمایش دارده می شود اگر لیست اسامی دانشجویان شما بیشتر از 20 نفر باشد برای شما گزینه ی زیر نمایش داده می شود که به شما این امکان را می دهد با ذخیره ی 20 نفر اول شما بقیه لیست را در صفحه مشاهده کنید و زمانی لیست ذخیره تمام شود لیست حذف می شود.",30,Gravity.RIGHT);
        img("A7.png",LinearLayout.LayoutParams.WRAP_CONTENT);
        text("\n",10,Gravity.RIGHT);
        line();

    }
}
