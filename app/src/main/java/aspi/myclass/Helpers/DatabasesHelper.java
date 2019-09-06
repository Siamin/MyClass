package aspi.myclass.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import aspi.myclass.activity.NewClassActivity;
import aspi.myclass.content.AbsentPersentContent;

public class DatabasesHelper extends SQLiteOpenHelper {

    public final String path = "data/data/aspi.myclass/databases/";
    public final String Name = "study";

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
        mydb = SQLiteDatabase.openDatabase(path + "study", null, SQLiteDatabase.OPEN_READWRITE);
    }

    //**********************************************************************************************
    public void close() {
        mydb.close();
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

    //**********************************************************************************************insert_class
    public void insert_class(String name_dars, String day, String time, String location, String Minute, String Characteristic, String Class, String did) {

        ContentValues cv = new ContentValues();
        cv.put("ndars", name_dars);
        cv.put("dey", day);
        cv.put("time", time);
        cv.put("location", location);
        cv.put("Minute", Minute);
        cv.put("Characteristic", Characteristic);
        cv.put("txt", "");
        cv.put("did", did);
        cv.put("class", Class);
        mydb.insert("dars", "ndars", cv);
        if (did.equals("")) {
            set_id_class();
        }
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

    //**********************************************************************************************update_class
    public void update_class(String name_dars, String day, String time, String location, String Minute, String Characteristic, int id) {
        ContentValues cv = new ContentValues();
        cv.put("ndars", name_dars);
        cv.put("dey", day);
        cv.put("time", time);
        cv.put("location", location);
        cv.put("Minute", Minute);
        cv.put("Characteristic", Characteristic);
        mydb.update("dars", cv, "id=" + id, null);
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
    public String Get_name_student_of_class(String id_class) {
        String name = "", family = "", sno = "", text = "", id = "", abs = "", per = "";
        Cursor cursor = mydb.rawQuery("SELECT  * FROM klas WHERE did= '" + id_class + "'", null);

        int cunt = 0;

        if (cursor.moveToFirst()) {
            do {
                id += cursor.getString(0) + "~";
                sno += cursor.getString(1) + "~";
                family += cursor.getString(2) + " ~";
                name += cursor.getString(3) + "~";
                text += cursor.getString(4) + " ~";
                abs += cursor.getString(7) + "~";
                per += cursor.getString(8) + "~";
                cunt += 1;
            } while (cursor.moveToNext());

            return id + "|" + sno + "|" + family + "|" + name + "|" + text + "|" + abs + "|" + per + "|" + String.valueOf(cunt) + "|";
        } else {
            return "";
        }
    }

    //**********************************************************************************************New Class
    public String Get_old_class(String id_class) {
        String DATA = "", jalase = "", HOUR = "";
        Cursor cursor = mydb.rawQuery("SELECT  DISTINCT jalase,day,month,year,HOUR  FROM rollcall WHERE iddars= '" + id_class + "'", null);

        int cunt = 0;

        if (cursor.moveToFirst()) {
            do {
                DATA += cursor.getString(1) + "/" + cursor.getString(2) + "/" + cursor.getString(3) + "~";
                jalase += cursor.getString(0) + "~";
                HOUR += cursor.getString(4) + " ~";
                cunt += 1;
            } while (cursor.moveToNext());

            return DATA + "|" + jalase + "|" + HOUR + "|" + String.valueOf(cunt) + "|";
        } else {
            return "";
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

    //*********************************************************************************************Display_class_List
    public String Display_Class(String table, int row) {
        Cursor cu = mydb.query(table, null, null, null, null, null, null);
        cu.moveToPosition(row);
        String Return = cu.getString(0) + "~";
        Return += cu.getString(1) + "~";
        Return += cu.getString(2) + "~";
        Return += cu.getString(3) + "~";
        Return += cu.getString(4) + "~";
        Return += cu.getString(5) + "~";
        Return += cu.getString(6) + "~";
        Return += cu.getString(7) + "~";
        Return += cu.getString(8) + "~";
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

    //*********************************************************************************************Display_Rullcall_list
    public String Display_Rullcall(String table, int row) {
        Cursor cu = mydb.query(table, null, null, null, null, null, null);
        cu.moveToPosition(row);
        String Return = cu.getString(8) + "~";
        Return += cu.getString(7) + "~";
        Return += cu.getString(4) + "~";
        Return += cu.getString(5) + "~";
        Return += cu.getString(6) + "~";
        Return += cu.getString(9) + "~";
        Return += cu.getString(0) + "~";
        return Return;
    }

    //*********************************************************************************************Display_Rullcall2old_list
    public String Display_Rullcall2old(int row) {
        Cursor cu = mydb.query("rollcall", null, null, null, null, null, null);
        cu.moveToPosition(row);
        String Return = cu.getString(0) + "~";
        Return += cu.getString(1) + "~";
        Return += cu.getString(2) + "~";
        Return += cu.getString(3) + "~";
        Return += cu.getString(4) + "/" + cu.getString(5) + "/" + cu.getString(6) + "~";
        Return += cu.getString(7) + "~";
        Return += cu.getString(8) + "~";
        Return += cu.getString(9) + "~";
        return Return;
    }

    //*********************************************************************************************_delete_all
    public void delete(String table, int id) {
        mydb.delete(table, "id=" + id, null);
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

    //*********************************************************************************************_set_id_class
    private void set_id_class() {
        int id = Integer.parseInt(Display("dars", (count("dars") - 1), 0));
        update_one("dars", "did", String.valueOf(id), id);
    }

    //*********************************************************************************************
    public void update_one1(String table, String fild, String text, String where) {
        ContentValues cv = new ContentValues();
        cv.put(fild, text);
        mydb.update(table, cv, where, null);
    }

    //******************************************************************************* Qury New Class
    public boolean Qury(String selectQuery, String[] DATA_IRAN, String did, int HOUR, int MINUTE) {
        boolean Status = false;
        Cursor cursor = mydb.rawQuery(selectQuery, null);
        int cunt_roll = count("rollcall");
        int jalase = 1;
        if (cunt_roll > 0) {
            jalase = Integer.parseInt(Display("rollcall", (cunt_roll - 1), 8)) + 1;
        }
        if (cursor.moveToFirst()) {
            do {
                AbsentPersentContent content = new AbsentPersentContent();
                content.name = cursor.getString(3);
                content.family = cursor.getString(2);
                content.sno = cursor.getString(1);
                content.id = cursor.getString(0);
                content.text = cursor.getString(4);
                content.status = "1";
                content.absent = cursor.getString(7);
                content.prezent = cursor.getString(8);
                insert_Rollcall(cursor.getString(1), true, "", DATA_IRAN[0], DATA_IRAN[1], DATA_IRAN[2], did, String.valueOf(jalase), String.valueOf(HOUR) + ":" + String.valueOf(MINUTE));
                content.id_rull = Display("rollcall", cunt_roll, 0);
                cunt_roll += 1;
                NewClassActivity.List.add(content);
                Status = true;
            } while (cursor.moveToNext());
        }

        return Status;
    }

    //******************************************************************************* Qury Old Class
    public String Qury_old_class(String selectQuery) {

        String id_rull = "", sno = "", status = "", nomreh = "", id = "", family = "", name = "", text = "";
        Cursor cursor = mydb.rawQuery(selectQuery, null);
        int Cunter = 0;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    //r.id,r.sno,r.abs,r.am,r.iddars,r.jalase,k.id6,k.name,k.family,k.sno,k.tx,k.did

                    id_rull += cursor.getString(0) + "~";
                    sno += cursor.getString(1) + "~";
                    status += cursor.getString(2) + "~";
                    nomreh += cursor.getString(3) + " " + "~";
                    //*****************************************
                    id += cursor.getString(6) + "~";
                    family += cursor.getString(8) + "~";
                    name += cursor.getString(7) + "~";
                    text += cursor.getString(10) + " " + "~";
                    Cunter += 1;
                } while (cursor.moveToNext());
            }
            return id_rull + "|" + sno + "|" + status + "|" + nomreh + "|" + id + "|" + family + "|" + name + "|" + text + "|" + String.valueOf(Cunter) + "|";
        }
        return "";
    }

    //******************************************************************************* Qury Amar Class
    public String Qury_amar_class(String selectQuery, String did) {

        String abs = "", per = "", name = "", family = "", Old_sno = "";
        Cursor cursor = mydb.rawQuery(selectQuery, null);
        //******************************************************************************************
        Cursor c = mydb.rawQuery("SELECT  COUNT(DISTINCT jalase) FROM rollcall WHERE iddars= '" + did + "'", null);
        //******************************************************************************************
        c.moveToFirst();
        //******************************************************************************************
        String max = c.getString(0);// jalase
        int Cunter = 1, Abs = 0, Per = 0;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                Old_sno = cursor.getString(0);
                name = cursor.getString(4) + "~";
                family = cursor.getString(5) + "~";
                do {

                    if (Old_sno.equals(cursor.getString(0)) && Old_sno.equals(cursor.getString(6))) {
                        if (cursor.getString(1).equals("1")) {
                            Abs += 1;
                        } else {
                            Per += 1;
                        }
                    } else {
                        Cunter += 1;
                        Old_sno = cursor.getString(0);
                        name += cursor.getString(4) + "~";
                        family += cursor.getString(5) + "~";
                        abs += String.valueOf(Abs) + "~";
                        per += String.valueOf(Per) + "~";
                        Abs = 0;
                        Per = 0;
                        if (cursor.getString(1).equals("1")) {
                            Abs += 1;
                        } else {
                            Per += 1;
                        }
                    }

                } while (cursor.moveToNext());
            }
            abs += String.valueOf(Abs) + "~";
            per += String.valueOf(Per) + "~";
            return abs + "|" + per + "|" + name + "|" + family + "|" + String.valueOf(Cunter) + "|" + max + "|";
        }
        return "";
    }

    //***************************************************************************** Qury Output List
    public String Qury_output_list(String selectQuery, String did) {
        String R = "", R1 = "", R2 = "", R3 = "", jal = "", data = "", data1 = "", data2 = "";
        int cunt_jalase, cunt_stud_jalase, cunt_stud, cunt_qury;
        //******************************************************************************************
        Cursor c = mydb.rawQuery("SELECT   COUNT(DISTINCT jalase) FROM rollcall WHERE iddars= " + Integer.parseInt(did), null);
        Cursor c0 = mydb.rawQuery("SELECT  COUNT(DISTINCT sno) FROM rollcall WHERE iddars= " + Integer.parseInt(did), null);
        Cursor c1 = mydb.rawQuery("SELECT  COUNT(DISTINCT id) FROM klas WHERE did= " + Integer.parseInt(did), null);
        Cursor cursor = mydb.rawQuery(selectQuery, null);
        //******************************************************************************************
        c1.moveToFirst();
        c0.moveToFirst();
        c.moveToFirst();
        //******************************************************************************************
        cunt_jalase = Integer.parseInt(c.getString(0));// jalase
        cunt_stud_jalase = Integer.parseInt(c0.getString(0));//Student_jalase
        cunt_stud = Integer.parseInt(c1.getString(0));//Student
        cunt_qury = cursor.getCount();//Cunt Qury
        //   0     1    2    3      4      5        6        7       8      9       10     11    12
        //r.sno,r.abs,r.am,r.day,r.month,r.year,r.iddars,r.jalase,r.HOUR,k.name,k.family,k.sno,k.did
        //******************************************************************************************
        String sno = "";
        int jalase = 0, jalase1 = 9, jalase2 = 99;

        if (cursor.moveToFirst()) {

            do {
                int new_jal = Integer.parseInt(cursor.getString(7));
                if (!sno.equals(cursor.getString(0))) {
                    R += cursor.getString(0) + "|" + cursor.getString(9) + "^" + cursor.getString(10) + "*" + cursor.getString(1) + "$" + cursor.getString(2) + "#";
                    sno = cursor.getString(0);
                } else {
                    R += cursor.getString(1) + "$" + cursor.getString(2) + "#";
                }


                if (new_jal > jalase && new_jal < 10) {
                    data += cursor.getString(3).substring(2, cursor.getString(3).length()) + "/" + cursor.getString(4) + "/" + cursor.getString(5) + "^";
                    jalase = new_jal;
                } else if (new_jal > jalase1 && new_jal < 100 && new_jal > 9) {
                    data += cursor.getString(3).substring(2, cursor.getString(3).length()) + "/" + cursor.getString(4) + "/" + cursor.getString(5) + "^";
                    jalase1 = new_jal;
                } else if (new_jal > jalase2 && new_jal < 1000 && new_jal > 99) {
                    data += cursor.getString(3).substring(2, cursor.getString(3).length()) + "/" + cursor.getString(4) + "/" + cursor.getString(5) + "^";
                    jalase2 = new_jal;
                }


            } while (cursor.moveToNext());
            data += data1;
            data += data2;
            R = String.valueOf(cunt_jalase) + "|" + String.valueOf(cunt_stud) + "|" + data + "|" + R;
            //**************************************************************************************
        }
        return R;
    }
    //**********************************************************************************************

    public boolean Update_sno(String sno, String did, String old_sno) {
        Cursor cursor = mydb.rawQuery("SELECT  * FROM klas WHERE did= '" + did + "' AND sno='" + sno + "'", null);
        if (cursor.getCount() == 0) {
            update_one1("klas", "sno", sno, "did= '" + did + "' AND sno='" + old_sno + "'");
            update_one1("rollcall", "sno", sno, "iddars= '" + did + "' AND sno='" + old_sno + "'");
            return false;
        }
        return true;
    }
    //**********************************************************************************************


}

				
				
	
