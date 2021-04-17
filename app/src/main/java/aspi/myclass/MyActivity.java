package aspi.myclass;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import aspi.myclass.Helpers.DatabasesHelper;
import aspi.myclass.Helpers.LanguageHelper;
import aspi.myclass.Helpers.NewMessageHelper;
import aspi.myclass.controllers.LessonController;

public class MyActivity extends AppCompatActivity {


    public DatabasesHelper data;
    public NewMessageHelper messageHelper;
    public LessonController lessonController;
    public Intent backPage = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        LanguageHelper.loadLanguage(this);

        data = new DatabasesHelper(this);
        data.database();

        messageHelper = new NewMessageHelper(this);
        lessonController = new LessonController(data, messageHelper, this);

    }

    public void GoPage(Intent intent) {
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (backPage != null) {
            startActivity(backPage);
        }
        finish();

    }
}
