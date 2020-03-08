package com.tikrosoft.mcattestapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDBSecondAdapter {

    myDbHelper myhelper;
    incorrectMCQshelper mcqhelper;

    public myDBSecondAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
        mcqhelper = new incorrectMCQshelper(context);
    }

    public void saveID(String id,String value)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.PID,id);
        contentValues.put(myDbHelper.MCQID, value);
        dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        //return id;
    }

    public String getID(String ID)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.MCQID};
        String[] whereArgs ={ID};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,myDbHelper.PID+" = ?",whereArgs,null,null,null,null);

        //StringBuffer buffer= new StringBuffer();
        String id = "";
        while (cursor.moveToNext())
        {
            id =cursor.getString(cursor.getColumnIndex(myDbHelper.MCQID));
            //buffer.append(cid+ "   " + name + "   " + password +" \n");
        }
        return id;
    }

    public void deleteID()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={};
        db.delete(myDbHelper.TABLE_NAME,myDbHelper.PID,whereArgs);
        db.delete(myDbHelper.TABLE_NAME,myDbHelper.MCQID,whereArgs);
    }


    public void saveIncorrectMCQData(String id,String mcq,String cOption,String inOption)
    {
        SQLiteDatabase dbb = mcqhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(incorrectMCQshelper.ID,id);
        contentValues.put(incorrectMCQshelper.MCQ, mcq);
        contentValues.put(incorrectMCQshelper.CORRECTOPTION,cOption);
        contentValues.put(incorrectMCQshelper.INCORRECTOPTION,inOption);
        dbb.insert(incorrectMCQshelper.TABLE_NAME, null , contentValues);
    }

    public String getIncorrectMCQ(String ID)
    {
        SQLiteDatabase db = mcqhelper.getWritableDatabase();
        String[] columns = {incorrectMCQshelper.MCQ};
        String[] whereArgs ={ID};
        Cursor cursor =db.query(incorrectMCQshelper.TABLE_NAME,columns,incorrectMCQshelper.ID+" = ?",whereArgs,null,null,null,null);
        String id = "";
        while (cursor.moveToNext())
        {
            id =cursor.getString(cursor.getColumnIndex(incorrectMCQshelper.MCQ));
        }
        return id;
    }

    public String getCorrectMCQOption(String ID)
    {
        SQLiteDatabase db = mcqhelper.getWritableDatabase();
        String[] columns = {incorrectMCQshelper.CORRECTOPTION};
        String[] whereArgs ={ID};
        Cursor cursor =db.query(incorrectMCQshelper.TABLE_NAME,columns,incorrectMCQshelper.ID+" = ?",whereArgs,null,null,null,null);
        String id = "";
        while (cursor.moveToNext())
        {
            id =cursor.getString(cursor.getColumnIndex(incorrectMCQshelper.CORRECTOPTION));
        }
        return id;
    }

    public String getIncorrectMCQOption(String ID)
    {
        SQLiteDatabase db = mcqhelper.getWritableDatabase();
        String[] columns = {incorrectMCQshelper.INCORRECTOPTION};
        String[] whereArgs ={ID};
        Cursor cursor =db.query(incorrectMCQshelper.TABLE_NAME,columns,incorrectMCQshelper.ID+" = ?",whereArgs,null,null,null,null);
        String id = "";
        while (cursor.moveToNext())
        {
            id =cursor.getString(cursor.getColumnIndex(incorrectMCQshelper.INCORRECTOPTION));
        }
        return id;
    }

    public void deleteIncorrectMCQS()
    {
        SQLiteDatabase db = mcqhelper.getWritableDatabase();
        String[] whereArgs ={};
        db.delete(incorrectMCQshelper.TABLE_NAME,incorrectMCQshelper.ID,whereArgs);
        db.delete(incorrectMCQshelper.TABLE_NAME,incorrectMCQshelper.MCQ,whereArgs);
        db.delete(incorrectMCQshelper.TABLE_NAME,incorrectMCQshelper.CORRECTOPTION,whereArgs);
        db.delete(incorrectMCQshelper.TABLE_NAME,incorrectMCQshelper.INCORRECTOPTION,whereArgs);
        //return  c;
    }


    /*public int updateName(String oldName , String newName)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME,newName);
        String[] whereArgs= {oldName};
        int count =db.update(myDbHelper.TABLE_NAME,contentValues, myDbHelper.NAME+" = ?",whereArgs );
        return count;
    }*/

    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "SaveSkippedIDs";    // Database Name
        private static final String TABLE_NAME = "SkippedId";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String PID="pid";
        private static final String MCQID = "mcq_id";

        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+PID+" VARCHAR(225),"+ MCQID+" VARCHAR(225));";

        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
            }
        }
    }


    static class incorrectMCQshelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "SaveIncorrectMCQs";    // Database Name
        private static final String TABLE_NAME = "IncorrectMCQs";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String ID="id";
        private static final String MCQ = "mcq";
        private static final String CORRECTOPTION = "correctOption";
        private static final String INCORRECTOPTION = "incorrectOption";

        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+ID+" VARCHAR(225)," +
                ""+MCQ+" VARCHAR(255),"+CORRECTOPTION+" VARCHAR(255),"+ INCORRECTOPTION+" VARCHAR(225));";

        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public incorrectMCQshelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
            }
        }
    }

}

