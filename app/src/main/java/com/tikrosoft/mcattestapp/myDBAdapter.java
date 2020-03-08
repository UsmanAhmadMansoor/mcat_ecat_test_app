package com.tikrosoft.mcattestapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDBAdapter {

    myDbHelper myhelper;
    mcqOptionHelper optionHelper;
    CorrectMCQoptionHelper correctMCQHelper;
    skippedHelper skipHelper;
    CategoriesHelper categoriesHelper;

    public myDBAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
        optionHelper = new mcqOptionHelper(context);
        correctMCQHelper = new CorrectMCQoptionHelper(context);
        skipHelper = new skippedHelper(context);
        categoriesHelper = new CategoriesHelper(context);
    }

    public void saveTimer(String value,String mcqPointer,String paperId,String correct,String incorrect,String cMarks,String unanswered,String userSkip,String skipAttempt,String skipPointer,String sPointer)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.TIMERVALUE, value);
        contentValues.put(myDbHelper.MCQPOINTER, mcqPointer);
        contentValues.put(myDbHelper.PAPERID, paperId);
        contentValues.put(myDbHelper.CORRECT, correct);
        contentValues.put(myDbHelper.INCORRECT, incorrect);
        contentValues.put(myDbHelper.CORRECTMARKS, cMarks);
        contentValues.put(myDbHelper.SKIPPEDATTEMPT, skipAttempt);
        contentValues.put(myDbHelper.UNANSWERED, unanswered);
        contentValues.put(myDbHelper.USERSKIPPED, userSkip);
        //contentValues.put(myDbHelper.SKIPPEDATTEMPT, skipAttempt);
        contentValues.put(myDbHelper.SKIPPOINTER, skipPointer);
        contentValues.put(myDbHelper.SPOINTER, sPointer);
        dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        //return id;
    }

    public String getTimer()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.TIMERVALUE};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        //StringBuffer buffer= new StringBuffer();
        String timerValue = "";
        while (cursor.moveToNext())
        {
            timerValue =cursor.getString(cursor.getColumnIndex(myDbHelper.TIMERVALUE));
            //buffer.append(cid+ "   " + name + "   " + password +" \n");
        }
        return timerValue;
    }

    public String getPointer()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.MCQPOINTER};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        String pointer = "";
        while (cursor.moveToNext())
        {
            pointer =cursor.getString(cursor.getColumnIndex(myDbHelper.MCQPOINTER));
        }
        return pointer;
    }

    public String getPaperId()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.PAPERID};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        String id = "";
        while (cursor.moveToNext())
        {
            id =cursor.getString(cursor.getColumnIndex(myDbHelper.PAPERID));
        }
        return id;
    }

    public String getCorrect()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.CORRECT};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        String id = "";
        while (cursor.moveToNext())
        {
            id =cursor.getString(cursor.getColumnIndex(myDbHelper.CORRECT));
        }
        return id;
    }

    public String getIncorrect()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.INCORRECT};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        String id = "";
        while (cursor.moveToNext())
        {
            id =cursor.getString(cursor.getColumnIndex(myDbHelper.INCORRECT));
        }
        return id;
    }

    public String getUnanswered()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UNANSWERED};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        String id = "";
        while (cursor.moveToNext())
        {
            id =cursor.getString(cursor.getColumnIndex(myDbHelper.UNANSWERED));
        }
        return id;
    }

    public String getUserSkiped()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.USERSKIPPED};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        String id = "";
        while (cursor.moveToNext())
        {
            id =cursor.getString(cursor.getColumnIndex(myDbHelper.USERSKIPPED));
        }
        return id;
    }

    public String getSkipAttempt()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.SKIPPEDATTEMPT};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        String id = "";
        while (cursor.moveToNext())
        {
            id =cursor.getString(cursor.getColumnIndex(myDbHelper.SKIPPEDATTEMPT));
        }
        return id;
    }

    public String getSkipPointer()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.SKIPPOINTER};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        String id = "";
        while (cursor.moveToNext())
        {
            id =cursor.getString(cursor.getColumnIndex(myDbHelper.SKIPPOINTER));
        }
        return id;
    }

    public String getCorrectMarks()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.CORRECTMARKS};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        String id = "";
        while (cursor.moveToNext())
        {
            id =cursor.getString(cursor.getColumnIndex(myDbHelper.CORRECTMARKS));
        }
        return id;
    }


    public void deleteTimer()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={};

        db.delete(myDbHelper.TABLE_NAME ,myDbHelper.TIMERVALUE,whereArgs);
        db.delete(myDbHelper.TABLE_NAME ,myDbHelper.CORRECT,whereArgs);
        db.delete(myDbHelper.TABLE_NAME ,myDbHelper.CORRECTMARKS,whereArgs);
        db.delete(myDbHelper.TABLE_NAME ,myDbHelper.INCORRECT,whereArgs);
        db.delete(myDbHelper.TABLE_NAME ,myDbHelper.MCQPOINTER,whereArgs);
        db.delete(myDbHelper.TABLE_NAME ,myDbHelper.PAPERID,whereArgs);
        db.delete(myDbHelper.TABLE_NAME ,myDbHelper.SKIPPEDATTEMPT,whereArgs);
        db.delete(myDbHelper.TABLE_NAME ,myDbHelper.SKIPPOINTER,whereArgs);
        db.delete(myDbHelper.TABLE_NAME ,myDbHelper.SPOINTER,whereArgs);
        db.delete(myDbHelper.TABLE_NAME ,myDbHelper.UNANSWERED,whereArgs);
        db.delete(myDbHelper.TABLE_NAME ,myDbHelper.USERSKIPPED,whereArgs);
        //db.delete(myDbHelper.TABLE_NAME ,myDbHelper.CORRECT,whereArgs);

    }


    public void deleteMCQHelper()
    {
        SQLiteDatabase db = optionHelper.getWritableDatabase();
        String[] whereArgs ={};

        db.delete(mcqOptionHelper.TABLE_NAME ,mcqOptionHelper.OID,whereArgs);
        db.delete(mcqOptionHelper.TABLE_NAME ,mcqOptionHelper.MCQID,whereArgs);
        db.delete(mcqOptionHelper.TABLE_NAME ,mcqOptionHelper.OPTIONNAME,whereArgs);

    }

    public void deleteCorrectOptionHelper()
    {
        SQLiteDatabase db = correctMCQHelper.getWritableDatabase();
        String[] whereArgs ={};

        db.delete(CorrectMCQoptionHelper.TABLE_NAME ,CorrectMCQoptionHelper.MCQID,whereArgs);
        db.delete(CorrectMCQoptionHelper.TABLE_NAME ,CorrectMCQoptionHelper.OPTIONNAME,whereArgs);

    }

    public void deleteSkipedMCQHelper()
    {
        SQLiteDatabase db = skipHelper.getWritableDatabase();
        String[] whereArgs ={};

        db.delete(skippedHelper.TABLE_NAME ,skippedHelper.SID,whereArgs);
        db.delete(skippedHelper.TABLE_NAME ,skippedHelper.MCQID,whereArgs);
        db.delete(skippedHelper.TABLE_NAME ,skippedHelper.MCQ,whereArgs);
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

    public void saveMCQoptions(String optionNo,String id,String optionName)
    {
        SQLiteDatabase dbb = optionHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(mcqOptionHelper.OID,optionNo);
        contentValues.put(mcqOptionHelper.MCQID, id);
        contentValues.put(mcqOptionHelper.OPTIONNAME, optionName);
        dbb.insert(mcqOptionHelper.TABLE_NAME, null , contentValues);
        //return id;
    }

    public String getTestData()
    {
        SQLiteDatabase db = optionHelper.getWritableDatabase();
        String[] columns = {mcqOptionHelper.OID,mcqOptionHelper.MCQID,mcqOptionHelper.OPTIONNAME};
        Cursor cursor =db.query(mcqOptionHelper.TABLE_NAME,columns, null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        //String id = "";
        while (cursor.moveToNext())
        {
            String cid =cursor.getString(cursor.getColumnIndex(mcqOptionHelper.OID));
            String name =cursor.getString(cursor.getColumnIndex(mcqOptionHelper.MCQID));
            String  password =cursor.getString(cursor.getColumnIndex(mcqOptionHelper.OPTIONNAME));
            buffer.append(cid+ "   " + name + "   " + password +" \n");
        }
        return buffer.toString();
    }

    public String getMCQOption(String pid,String ID)
    {
        SQLiteDatabase db = optionHelper.getWritableDatabase();
        String[] columns = {mcqOptionHelper.OPTIONNAME};
        String[] whereArgs ={pid,ID};
        Cursor cursor =db.query(mcqOptionHelper.TABLE_NAME,columns, mcqOptionHelper.OID+" = ?" +" AND "+ mcqOptionHelper.MCQID+" = ?",whereArgs,null,null,null,null);
        String id = "";
        while (cursor.moveToNext())
        {
            id =cursor.getString(cursor.getColumnIndex(mcqOptionHelper.OPTIONNAME));
        }
        return id;
    }








    public void saveCorrectMCQoption(String id,String optionName)
    {
        SQLiteDatabase dbb = correctMCQHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CorrectMCQoptionHelper.MCQID, id);
        contentValues.put(CorrectMCQoptionHelper.OPTIONNAME, optionName);
        dbb.insert(CorrectMCQoptionHelper.TABLE_NAME, null , contentValues);
        //return id;
    }

    public String getCorrectMCQOption(String ID)
    {
        SQLiteDatabase db = correctMCQHelper.getWritableDatabase();
        String[] columns = {CorrectMCQoptionHelper.OPTIONNAME};
        String[] whereArgs ={ID};
        Cursor cursor =db.query(CorrectMCQoptionHelper.TABLE_NAME,columns, CorrectMCQoptionHelper.MCQID+" = ?",whereArgs,null,null,null,null);
        String id = "";
        while (cursor.moveToNext())
        {
            id =cursor.getString(cursor.getColumnIndex(CorrectMCQoptionHelper.OPTIONNAME));
        }
        return id;
    }


    public void saveSkippedMCQ(String sid,String id,String mcq)
    {
        SQLiteDatabase dbb = skipHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(skippedHelper.SID, sid);
        contentValues.put(skippedHelper.MCQID, id);
        contentValues.put(skippedHelper.MCQ, mcq);
        dbb.insert(skippedHelper.TABLE_NAME, null , contentValues);
    }

    public String getSkipMCQ(String ID)
    {
        SQLiteDatabase db = skipHelper.getWritableDatabase();
        String[] columns = {skippedHelper.MCQ};
        String[] whereArgs ={ID};
        Cursor cursor =db.query(skippedHelper.TABLE_NAME,columns, skippedHelper.SID+" = ?",whereArgs,null,null,null,null);
        String id = "";
        while (cursor.moveToNext())
        {
            id =cursor.getString(cursor.getColumnIndex(skippedHelper.MCQ));
        }
        return id;
    }

    public String getSkipMCQid(String ID)
    {
        SQLiteDatabase db = skipHelper.getWritableDatabase();
        String[] columns = {skippedHelper.MCQID};
        String[] whereArgs ={ID};
        Cursor cursor =db.query(skippedHelper.TABLE_NAME,columns, skippedHelper.SID+" = ?",whereArgs,null,null,null,null);
        String id = "";
        while (cursor.moveToNext())
        {
            id =cursor.getString(cursor.getColumnIndex(skippedHelper.MCQID));
        }
        return id;
    }



    public String getSkipTestData()
    {
        SQLiteDatabase db = skipHelper.getWritableDatabase();
        String[] columns = {skippedHelper.SID,skippedHelper.MCQID,skippedHelper.MCQ};
        Cursor cursor =db.query(skippedHelper.TABLE_NAME,columns, null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        //String id = "";
        while (cursor.moveToNext())
        {
            String cid =cursor.getString(cursor.getColumnIndex(skippedHelper.SID));
            String name =cursor.getString(cursor.getColumnIndex(mcqOptionHelper.MCQID));
            String  password =cursor.getString(cursor.getColumnIndex(skippedHelper.MCQ));
            buffer.append(cid+  "   " + name + "   " + password +" \n");
        }
        return buffer.toString();
    }


    public void saveCatogary(String id)
    {
        SQLiteDatabase dbb = categoriesHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CategoriesHelper.CAT, id);
        dbb.insert(CategoriesHelper.TABLE_NAME, null , contentValues);
    }

    public String getCatogary()
    {
        SQLiteDatabase db = categoriesHelper.getWritableDatabase();
        String[] columns = {CategoriesHelper.CAT};
        //String[] whereArgs ={ID};
        Cursor cursor =db.query(CategoriesHelper.TABLE_NAME,columns, null,null,null,null,null,null);
        String id = "";
        while (cursor.moveToNext())
        {
            id =cursor.getString(cursor.getColumnIndex(CategoriesHelper.CAT));
        }
        return id;
    }




    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "SaveTimer";    // Database Name
        private static final String TABLE_NAME = "timer";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        //private static final String UID="_id";     // Column I (Primary Key)
        private static final String TIMERVALUE = "TimerValue";    //Column II

        private static final String MCQPOINTER = "McqPointer";
        private static final String PAPERID = "PaperId";
        private static final String CORRECT = "Correct";
        private static final String INCORRECT = "Incorrect";
        private static final String CORRECTMARKS = "CorrectMarks";
        private static final String UNANSWERED = "Unanswered";
        private static final String USERSKIPPED = "UserSkipped";
        private static final String SKIPPEDATTEMPT = "SkippedAttempt";
        private static final String SKIPPOINTER = "SkipPointer";
        private static final String SPOINTER = "Spointer";


        //private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
        //      " ("+TIMERVALUE+" VARCHAR(225));";

        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+TIMERVALUE+" VARCHAR(225),"+PAPERID+" VARCHAR(255),"+CORRECT+" VARCHAR(255)," +
                ""+INCORRECT+" VARCHAR(255),"+CORRECTMARKS+" VARCHAR(255),"+UNANSWERED+" VARCHAR(255),"+USERSKIPPED+" VARCHAR(255)," +
                ""+SKIPPEDATTEMPT+" VARCHAR(255),"+SKIPPOINTER+" VARCHAR(255),"+SPOINTER+" VARCHAR(255),"+ MCQPOINTER+" VARCHAR(225));";

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
                //Message.message(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                //Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                //Message.message(context,""+e);
            }
        }
    }

    static class mcqOptionHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "Options";
        private static final String TABLE_NAME = "option";

        private static final int DATABASE_Version = 1;
        private static final String OID="Id";     // Column I (Primary Key)
        private static final String MCQID = "mcq_id";
        private static final String OPTIONNAME = "option_name";


        //private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
           //     " ("+MCQID+" VARCHAR(225),"+ OPTIONNAME+" VARCHAR(225));";

        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+OID+" VARCHAR(225)," +
                ""+MCQID+" VARCHAR(255),"+ OPTIONNAME+" VARCHAR(225));";


        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public mcqOptionHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) { }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) { }
        }
    }


    static class CorrectMCQoptionHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "CorrectOption";
        private static final String TABLE_NAME = "correctOption";
        private static final int DATABASE_Version = 1;
        //private static final String UID="_id";     // Column I (Primary Key)
        private static final String MCQID = "mcq_id";
        private static final String OPTIONNAME = "option_name";

        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
            " ("+MCQID+" VARCHAR(225),"+ OPTIONNAME+" VARCHAR(225));";

        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public CorrectMCQoptionHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) { }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) { }
        }
    }


    static class skippedHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "Skip";
        private static final String TABLE_NAME = "skip";

        private static final int DATABASE_Version = 1;
        private static final String SID="Id";     // Column I (Primary Key)
        private static final String MCQID = "mcq_id";
        private static final String MCQ = "mcq";


        //private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
        //     " ("+MCQID+" VARCHAR(225),"+ OPTIONNAME+" VARCHAR(225));";

        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+SID+" VARCHAR(225)," +
                ""+MCQID+" VARCHAR(255),"+ MCQ+" VARCHAR(225));";


        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public skippedHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) { }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) { }
        }
    }


    static class CategoriesHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "Catogries";
        private static final String TABLE_NAME = "cat";
        private static final int DATABASE_Version = 1;
        private static final String CAT = "cat_id";

        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+CAT+" VARCHAR(225));";

        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public CategoriesHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) { }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) { }
        }
    }


}


