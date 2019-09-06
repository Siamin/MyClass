package aspi.myclass.Tools;



import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import java.util.Random;
import aspi.myclass.Helpers.MessageHelper;

public class Tools {

    String[] Arry = {"A", "a", "B", "b", "C", "c", "D", "d", "E", "e", "F", "f", "G", "g", "H", "h", "I", "i", "J", "j"
            , "K", "k", "L", "l", "M", "m", "N", "n", "O", "o", "P", "P", "Q", "q", "R", "r", "S", "s", "T", "t", "U", "u"
            , "V", "v", "W", "w", "X", "x", "Y", "y", "Z", "z",};


    public static String Pin_Cod(int counter) {
        Random random = new Random();
        String Resualt = "";
        for (int i = 0; i < counter; i++) {
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

    public Typeface SetFont(Context context, String name) {
        return Typeface.createFromAsset(context.getAssets(), "Font/" + name + ".ttf");
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

    public static void openApplicationByUriAndPackage(Context context,String uri,String Package) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(uri));
            intent.setPackage(Package);
            context.startActivity(intent);
        } catch (Exception e) {
            MessageHelper.Toast(context, "لطفا برنامه بازار را نصب کنید...!");
        }
    }

}
