package CustomObject;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import aspi.myclass.Helpers.LanguageHelper;


public class ImageViewCustom extends AppCompatImageView {


    public ImageViewCustom(Context context, AttributeSet attis) {
        super(context, attis);

        String lang = LanguageHelper.loadLanguage(context);
        if (lang.equals("en")) this.setRotation(180);
    }

}