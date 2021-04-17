package aspi.myclass.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import aspi.myclass.Helpers.DatabasesHelper;
import aspi.myclass.Helpers.NewMessageHelper;
import aspi.myclass.R;
import aspi.myclass.model.LessonModel;
import aspi.myclass.model.databaseModels.LessonDatabaseModel;

public class LessonController {

    private DatabasesHelper databasesHelper;
    private NewMessageHelper messageHelper;
    private Context context;
    private String table = "dars";
    private LessonDatabaseModel databaseModel;


    public LessonController(DatabasesHelper databasesHelper, NewMessageHelper messageHelper, Context context) {
        this.databasesHelper = databasesHelper;
        this.messageHelper = messageHelper;
        this.context = context;
        this.databaseModel = new LessonDatabaseModel();
    }

    public List<LessonModel> findByDayOfWeek(String dayOfWeek){
        databasesHelper.open();
        List<LessonModel> returnModel = databasesHelper.getLessonByDay(String.valueOf(dayOfWeek));
        databasesHelper.close();
        return returnModel;

    }

    public LessonModel findByid(String id){
        databasesHelper.open();
        Cursor cursor = databasesHelper.findById(table,id);
        LessonModel returnModel = new LessonModel();
        if (cursor!=null){
            cursor.moveToFirst();
            returnModel.id = cursor.getString(0);
            returnModel.lessonName = cursor.getString(1);
            returnModel.dayOfWeek = cursor.getString(2);
            returnModel.startTime = cursor.getString(3);
            returnModel.education = cursor.getString(4);
            returnModel.endTime = cursor.getString(5);
            returnModel.lessonCode = cursor.getString(6);
            returnModel.description = cursor.getString(7);
            returnModel.parentId = cursor.getString(8);
            returnModel.classNumber = cursor.getString(9);
        }

        databasesHelper.close();
        return returnModel;

    }

    public boolean insertLesson(LessonModel model) {

        if (validationLesson(model)) {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(databaseModel.getLessonName(), model.lessonName);
                contentValues.put(databaseModel.getDayOfWeek(), model.dayOfWeek);
                contentValues.put(databaseModel.getStartTime(), model.startTime);
                contentValues.put(databaseModel.getEducation(), model.education);
                contentValues.put(databaseModel.getEnsTime(), model.endTime);
                contentValues.put(databaseModel.getLessonCode(), model.lessonCode);
                contentValues.put(databaseModel.getDescription(), model.description);
                contentValues.put(databaseModel.getParentId(), model.parentId);
                contentValues.put(databaseModel.getClassNumber(), model.classNumber);

                databasesHelper.open();
                databasesHelper.insertDB(table, contentValues, databaseModel.getLessonName());
                databasesHelper.close();

                messageHelper.Toast(context.getResources().getString(R.string.Saved));
                return true;
            } catch (Exception e) {
                Log.i("TAG_", e.toString());
                messageHelper.Toast(context.getResources().getString(R.string.Error));
            }
        }
        return false;
    }

    public boolean updateLesson(LessonModel model) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(databaseModel.getLessonName(), model.lessonName);
            contentValues.put(databaseModel.getDayOfWeek(), model.dayOfWeek);
            contentValues.put(databaseModel.getStartTime(), model.startTime);
            contentValues.put(databaseModel.getEducation(), model.education);
            contentValues.put(databaseModel.getEnsTime(), model.endTime);
            contentValues.put(databaseModel.getLessonCode(), model.lessonCode);
            contentValues.put(databaseModel.getDescription(), model.description);
            contentValues.put(databaseModel.getParentId(), model.parentId);
            contentValues.put(databaseModel.getClassNumber(), model.classNumber);

            databasesHelper.open();
            databasesHelper.updateById(table, contentValues, Integer.parseInt(model.id));
            databasesHelper.close();

            messageHelper.Toast(context.getResources().getString(R.string.editingDoneSuccessfully));
            return true;
        } catch (Exception e) {
            Log.i("TAG_", e.toString());
            messageHelper.Toast(context.getResources().getString(R.string.Error));
        }
        return false;
    }

    public boolean updateLessonDescription(LessonModel model) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(databaseModel.getDescription(), model.description);

            databasesHelper.open();
            String parentId = model.parentId.isEmpty() ? model.id : model.parentId;
            databasesHelper.updateDB(table, contentValues, databaseModel.getParentId() + "=" + parentId);
            databasesHelper.close();

            messageHelper.Toast(context.getResources().getString(R.string.editingDoneSuccessfully));
            return true;
        } catch (Exception e) {
            Log.i("TAG_", e.toString());
            messageHelper.Toast(context.getResources().getString(R.string.Error));
        }
        return false;
    }

    public boolean deleteLesson(int id) {
        try {
            databasesHelper.open();
            databasesHelper.deleteById(table, id);
            databasesHelper.close();
            messageHelper.Toast(context.getResources().getString(R.string.ClassDeleted));
            return true;
        } catch (Exception e) {
            messageHelper.Toast(context.getResources().getString(R.string.Error));
            return false;
        }
    }

    private boolean validationLesson(LessonModel model) {
        if (!model.lessonName.isEmpty()) {
            if (!model.lessonCode.isEmpty()) {
                if (!model.education.isEmpty()) {
                    if (!model.classNumber.isEmpty()) {
                        return true;
                    } else {
                        messageHelper.Toast(context.getResources().getString(R.string.PleaseEnterClassNumber));
                    }
                } else {
                    messageHelper.Toast(context.getResources().getString(R.string.PleaseEnterClassLocation));
                }
            } else {
                messageHelper.Toast(context.getResources().getString(R.string.PleaseEnterClassAttribute));
            }
        } else {
            messageHelper.Toast(context.getResources().getString(R.string.PleaseEnterClassName));
        }
        return false;
    }


}
