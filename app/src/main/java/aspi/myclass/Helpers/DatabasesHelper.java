package aspi.myclass.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import aspi.myclass.model.AbsentPersentModel;
import aspi.myclass.model.LessonModel;
import aspi.myclass.model.OldClassModel;
import aspi.myclass.model.ReportDataModel;
import aspi.myclass.model.StatisticsModel;

import static java.lang.Integer.parseInt;

public class DatabasesHelper extends SQLiteOpenHelper {

    public final String path = "data/data/aspi.myclass/databases/";
    public final String Name = "study", TAG = "TAG_DatabasesHelper";

    public SQLiteDatabase mydb;
    private Context myContext;


    //**********************************************************************************************
    public DatabasesHelper(Context context) {
        super(context, "study", null, 1);
        myContext = context;
    }

    //**********************************************************************************************
    public void onCreate(SQLiteDatabase arg0) {

    }

    //**********************************************************************************************
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }

    //**********************************************************************************************
    public void database() {
        boolean checkdb = checkdb();
        if (checkdb) {
        } else {
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch (IOException e) {
            }
        }
    }

    //**********************************************************************************************
    public void open() {
//        if (!mydb.isOpen())
            mydb = SQLiteDatabase.openDatabase(path + "study", null, SQLiteDatabase.OPEN_READWRITE);
    }

    //**********************************************************************************************
    public void close() {
//        if (mydb.isOpen())
            mydb.close();
    }

    public boolean databaseIsOpen() {
        return mydb.isOpen();
    }

    //**********************************************************************************************
    public boolean checkdb() {
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openDatabase(path + Name, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLException e) {
        }
        return db != null ? true : false;
    }

    //**********************************************************************************************
    public void copydatabase() throws IOException {
        OutputStream myoutput = new FileOutputStream(path + Name);
        byte[] buffer = new byte[1024];
        int length;
        InputStream myinput = myContext.getAssets().open("study.db");
        while ((length = myinput.read(buffer)) > 0) {
            myoutput.write(buffer, 0, length);
        }
        myinput.close();
        myoutput.flush();
        myoutput.close();
    }

    //************************************* find ***************************************************
    public Cursor findById(String table,String id) {
        Cursor cursor= mydb.rawQuery("SELECT  * FROM "+table+" WHERE id=" + id, null);
        return cursor;
    }

    public Cursor findByWhere(String table, String where) {
        Cursor cursor = mydb.rawQuery("SELECT  * FROM  "+table+"  WHERE  "+where, null);
        return cursor;
    }
    //************************************* Insert *************************************************
    public void insertDB(String table,ContentValues cv,String nullColumnHack) {
        mydb.insert(table, nullColumnHack, cv);
    }
    //************************************** update ************************************************
    public void updateById(String table,ContentValues cv,int id){
        mydb.update(table, cv, "id="+id, null);
    }
    public void updateDB(String table,ContentValues cv,String where){
        mydb.update(table, cv, where, null);
    }
    //************************************** Delete ************************************************
    public void deleteById(String table, int id) {
        mydb.delete(table, "id=" + id, null);
    }
    public void deleteAll(String table) {
        mydb.delete(table, null, null);
    }
    //**********************************************************************************************update_class
    public void update(String filde_database, String set_database, String filde_database2, String set_database2, String filde_database3, String set_database3, String where1) {
        ContentValues cv = new ContentValues();
        cv.put(filde_database, set_database);
        cv.put(filde_database2, set_database2);
        cv.put(filde_database3, set_database3);
        mydb.update("rollcall", cv, where1, null);
    }

    //**********************************************************************************************insert_class2
    public void insert_class2(String name_dars, String day, String time, String location, String Minute, String Characteristic, String text, String did, String Class) {
        ContentValues cv = new ContentValues();
        cv.put("ndars", name_dars);
        cv.put("dey", day);
        cv.put("time", time);
        cv.put("location", location);
        cv.put("Minute", Minute);
        cv.put("Characteristic", Characteristic);
        cv.put("txt", text);
        cv.put("did", did);
        cv.put("class", Class);
        mydb.insert("dars", "ndars", cv);
    }

    //**********************************************************************************************insert_student
    public boolean insert_student(String sno, String family, String name, String id_class) {

        boolean status = false;

        Cursor c = mydb.rawQuery("SELECT  *  FROM klas WHERE sno = '" + sno + "' and did = '" + id_class + "'", null);

        if (c.getCount() == 0) {
            ContentValues cv = new ContentValues();
            cv.put("sno", sno);
            cv.put("family", family);
            cv.put("name", name);
            cv.put("tx", " ");
            cv.put("did", id_class);
            cv.put("d", "0");
            cv.put("hozor", "0");
            cv.put("ghayeb", "0");
            mydb.insert("klas", "id", cv);
            status = true;
        }
        return status;
    }

    //**********************************************************************************************New Class
    public List<AbsentPersentModel> NameStudentClassByModel(String id_class) {
        List<AbsentPersentModel> studentModels = new ArrayList<>();
        Cursor cursor = mydb.rawQuery("SELECT  * FROM klas WHERE did= '" + id_class + "' ORDER BY family ASC ,name ASC ", null);

        if (cursor.moveToFirst()) {
            do {
                AbsentPersentModel _studentModel = new AbsentPersentModel();

                _studentModel.id = cursor.getString(0);
                _studentModel.sno = cursor.getString(1);
                _studentModel.family = cursor.getString(2);
                _studentModel.name = cursor.getString(3);
                _studentModel.text = cursor.getString(4);
                _studentModel.absent = cursor.getString(7);
                _studentModel.prezent = cursor.getString(8);

                studentModels.add(_studentModel);

            } while (cursor.moveToNext());

            return studentModels;
        } else {
            return studentModels;
        }
    }

    //**********************************************************************************************Class Session
    public List<OldClassModel> ListOldClssByIdClass(String id_class) {
        List<OldClassModel> oldClassModels = new ArrayList<>();
        Cursor cursor = mydb.rawQuery("SELECT  DISTINCT jalase,day,month,year,HOUR  FROM rollcall WHERE iddars= '" + id_class + "'", null);

        if (cursor.moveToFirst()) {
            do {
                OldClassModel _oldClassModel = new OldClassModel();
                _oldClassModel.DATA = cursor.getString(1) + "/" + cursor.getString(2) + "/" + cursor.getString(3);
                _oldClassModel.jalase = cursor.getString(0);
                _oldClassModel.Hour = cursor.getString(4);
                oldClassModels.add(_oldClassModel);
            } while (cursor.moveToNext());

            return oldClassModels;
        } else {
            return oldClassModels;
        }
    }

    //**********************************************************************************************insert_student2
    public void insert_student2(String sno, String family, String name, String text, String id_class) {
        ContentValues cv = new ContentValues();
        cv.put("sno", sno);
        cv.put("family", family);
        cv.put("name", name);
        cv.put("tx", text);
        cv.put("did", id_class);
        cv.put("d", "0");
        cv.put("hozor", "0");
        cv.put("ghayeb", "0");
        mydb.insert("klas", "id", cv);
    }

    //**********************************************************************************************insert_Rollcall
    public void insert_Rollcall(String sno, boolean status, String nomreh, String day, String month, String year, String id_class, String jalase, String HOUR) {
        ContentValues cv = new ContentValues();
        cv.put("sno", sno);
        cv.put("abs", status);
        cv.put("am", nomreh);
        cv.put("day", day);
        cv.put("month", month);
        cv.put("year", year);
        cv.put("iddars", id_class);
        cv.put("jalase", jalase);
        cv.put("HOUR", HOUR);
        mydb.insert("rollcall", "id", cv);
    }

    //*********************************************************************************************Display_all
    public String Display(String table, int row, int fild) {
        Cursor cu = mydb.query(table, null, null, null, null, null, null);
        cu.moveToPosition(row);
        String Return = cu.getString(fild);
        return Return;
    }

    //*********************************************************************************************Display_class_all
    public String Display_all(String table, int row, int start, int end) {
        Cursor cu = mydb.query(table, null, null, null, null, null, null);
        cu.moveToPosition(row);
        String Return = "";
        for (int i = start; i <= end; i++) {
            Return += cu.getString(i) + "~";
        }
        return Return;
    }

    //*********************************************************************************************_delete_all
    public void delete_(String table, String Where) {
        mydb.delete(table, Where, null);
    }

    //*********************************************************************************************_update_one_all
    public void update_one(String table, String fild, String text, int id) {
        ContentValues cv = new ContentValues();
        cv.put(fild, text);
        mydb.update(table, cv, "id=" + id, null);
    }

    //*********************************************************************************************_count_all
    public Integer count(String table) {
        Cursor cu = mydb.query(table, null, null, null, null, null, null);
        int cou = cu.getCount();
        return cou;
    }

    //*********************************************************************************************Get Class By Id
    public List<LessonModel> getLessonByDay(String dayOfWeek) {
        List<LessonModel> returnModel = new ArrayList<>();
        Cursor cursor = mydb.rawQuery("SELECT  * FROM dars WHERE dey='" + dayOfWeek + "'", null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    LessonModel model = new LessonModel();
                    model.id = cursor.getString(0);
                    model.lessonName = cursor.getString(1);
                    model.dayOfWeek = cursor.getString(2);
                    model.startTime = cursor.getString(3);
                    model.education = cursor.getString(4);
                    model.endTime = cursor.getString(5);
                    model.lessonCode = cursor.getString(6);
                    model.description = cursor.getString(7);
                    model.parentId = cursor.getString(8);
                    model.classNumber = cursor.getString(9);


                    returnModel.add(model);
                } while (cursor.moveToNext());
            }
        }

        return returnModel;
    }

    //*********************************************************************************************_set_id_class
    private void set_id_class() {
        int id = parseInt(Display("dars", (count("dars") - 1), 0));
        update_one("dars", "did", String.valueOf(id), id);
    }

    //*********************************************************************************************
    public int update_one1(String table, String fild, String text, String where) {
        ContentValues cv = new ContentValues();
        cv.put(fild, text);
        return mydb.update(table, cv, where, null);
    }

    //******************************************************************************* Qury Old Class
    public List<AbsentPersentModel> OldClassByQuery(String selectQuery, String score) {

        List<AbsentPersentModel> absentPersentModels = new ArrayList<>();
        Cursor cursor = mydb.rawQuery(selectQuery, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    AbsentPersentModel _absentPersentModels = new AbsentPersentModel();
                    _absentPersentModels.id_rull = cursor.getString(0);
                    _absentPersentModels.sno = cursor.getString(1);
                    _absentPersentModels.status = cursor.getString(2);
                    _absentPersentModels.nomreh = cursor.getString(3).isEmpty() ? score : cursor.getString(3);
                    //*****************************************
                    _absentPersentModels.id = cursor.getString(6);
                    _absentPersentModels.family = cursor.getString(8);
                    _absentPersentModels.name = cursor.getString(7);
                    _absentPersentModels.text = cursor.getString(10);

                    absentPersentModels.add(_absentPersentModels);
                } while (cursor.moveToNext());
            }
            return absentPersentModels;
        }
        return absentPersentModels;
    }

    //******************************************************************************* Qury Statistics Class
    public List<StatisticsModel> Statistics(String selectQuery, String did) {

        List<StatisticsModel> statisticsModels = new ArrayList<>();
        Cursor cursor = mydb.rawQuery(selectQuery, null);
        //******************************************************************************************
        Cursor c = mydb.rawQuery("SELECT  COUNT(DISTINCT jalase) FROM rollcall WHERE iddars= '" + did + "'", null);
        //******************************************************************************************
        c.moveToFirst();
        //******************************************************************************************
        String max = c.getString(0);// jalase
        String Old_sno = "";

        if (cursor != null) {
            StatisticsModel _statisticsModel = new StatisticsModel();
            if (cursor.moveToFirst()) {

                Old_sno = cursor.getString(0);

                _statisticsModel.name = cursor.getString(4);
                _statisticsModel.family = cursor.getString(5);
                _statisticsModel.max = parseInt(max);

                do {

                    if (Old_sno.equals(cursor.getString(0)) && Old_sno.equals(cursor.getString(6))) {
                        if (cursor.getString(1).equals("1")) {
                            _statisticsModel.set += 1;
                        } else {
                            _statisticsModel.per += 1;
                        }
                    } else {


                        statisticsModels.add(_statisticsModel);
                        _statisticsModel = new StatisticsModel();

                        Old_sno = cursor.getString(0);

                        _statisticsModel.name = cursor.getString(4);
                        _statisticsModel.family = cursor.getString(5);
                        _statisticsModel.max = parseInt(max);

                        if (cursor.getString(1).equals("1")) {
                            _statisticsModel.set += 1;
                        } else {
                            _statisticsModel.per += 1;
                        }
                    }

                } while (cursor.moveToNext());
            }

            statisticsModels.add(_statisticsModel);
            return statisticsModels;
        }
        return statisticsModels;
    }

    //***************************************************************************** Qury Output List
    public List<ReportDataModel> ReportCalss(String selectQuery) {

        List<ReportDataModel> returnModel = new ArrayList<>();

        Cursor cursor = mydb.rawQuery(selectQuery, null);

        //   0     1    2    3      4      5        6        7       8      9       10     11    12
        //r.sno,r.abs,r.am,r.day,r.month,r.year,r.iddars,r.jalase,r.HOUR,k.name,k.family,k.sno,k.did
        //******************************************************************************************


        if (cursor.moveToFirst()) {

            do {
                ReportDataModel reportDataModel = new ReportDataModel();
                reportDataModel.sno = cursor.getString(0);
                reportDataModel.name = cursor.getString(9);
                reportDataModel.family = cursor.getString(10);
                reportDataModel.date = cursor.getString(3) + "/" + cursor.getString(4) + "/" + cursor.getString(5);
                reportDataModel.status = cursor.getString(1);
                reportDataModel.score = cursor.getString(2);
                reportDataModel.idJalase = Integer.parseInt(cursor.getString(7));
                returnModel.add(reportDataModel);

            } while (cursor.moveToNext());

            //**************************************************************************************
        }
        return returnModel;
    }
    //**********************************************************************************************

    public boolean Update_sno(String sno, String did, String old_sno) {
        Cursor cursor = mydb.rawQuery("SELECT  * FROM klas WHERE did= '" + did + "' AND sno='" + sno + "'", null);
        if (cursor.getCount() == 0) {

            Cursor cursorClass = mydb.rawQuery("UPDATE klas SET sno = " + sno + " WHERE did= '" + did + "' AND sno='" + old_sno + "'", null);
            Log.i(TAG, "Count Class " + cursorClass.getCount());
            Cursor cursorRollcall = mydb.rawQuery("UPDATE rollcall SET sno = " + sno + " WHERE iddars= '" + did + "' AND sno='" + old_sno + "'", null);
            Log.i(TAG, "Count Rollcall" + cursorRollcall.getCount());
            return true;
        }
        return false;
    }

    //**********************************************************************************************
    public List<AbsentPersentModel> getStudentClass(String idClass, int delete) {
        List<AbsentPersentModel> returnModel = new ArrayList<>();
        Cursor cursor = mydb.rawQuery("SELECT  * FROM klas WHERE did='" + idClass + "' AND d='" + delete + "'", null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {

                    AbsentPersentModel model = new AbsentPersentModel();
                    model.id = cursor.getString(0);
                    model.sno = cursor.getString(1);
                    model.family = cursor.getString(2);
                    model.name = cursor.getString(3);
                    model.text = cursor.getString(4);
                    model.classId = cursor.getString(5);
                    model.delete = cursor.getString(6);
                    model.absent = cursor.getString(7);
                    model.prezent = cursor.getString(8);

                    returnModel.add(model);
                } while (cursor.moveToNext());
            }
        }

        return returnModel;
    }


}

				
				
	
