package com.folioreader.model.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DbWordLearn extends SQLiteOpenHelper {

    private static final String TAG = DbWordLearn.class.getSimpleName();

    public static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "wordLearn";
    private static final String TABLE_NAME = "wordLearnData";
    private static final String KEY_ID = "id";
    private static final String KEY_WORD = "word";
    private static final String KEY_LEARN = "learn";
    private static final String KEY_LEARNED = "learned";

    public DbWordLearn(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = " CREATE TABLE " + " " + TABLE_NAME + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_WORD + " TEXT, "
                + KEY_LEARN + " INTEGER DEFAULT 0, "
                + KEY_LEARNED + " INTEGER DEFAULT 0 "
                + " )";

        sqLiteDatabase.execSQL(CREATE_TABLE);
        Log.d("Veritabanı Kuruldu", "Tablo Oluşturuldu.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" drop table if exists  " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addWord(Context context, String wordd, String learn, String learned) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        boolean bla = existsColumnInTable(db,TABLE_NAME,KEY_WORD,wordd);

        if(bla){
            //word Found
            values.put(KEY_LEARN, learn);
            values.put(KEY_LEARNED, learned);
            Toast.makeText(context, "Kelime oluşturuldu:  " + wordd + " :  " + learn + "  :  " + learned, Toast.LENGTH_LONG).show();
            db.update(TABLE_NAME,values,"word= "+wordd,null);
        }else{
            //word Not Found
            values.put(KEY_WORD, wordd);
            values.put(KEY_LEARN, learn);
            values.put(KEY_LEARNED, learned);
            db.insert(TABLE_NAME, null, values);
            Toast.makeText(context, "Kelime oluşturuldu:  " + wordd + " :  " + learn + "  :  " + learned, Toast.LENGTH_LONG).show();
        }
      //  Boolean exist= IsProductExist(wordd);
        db.close();
    }

    private boolean existsColumnInTable(SQLiteDatabase inDatabase, String inTable, String columnToCheck,String words) {
        Cursor mCursor = null;
        try {
            // Query 1 row
            mCursor = inDatabase.rawQuery("SELECT * FROM " + inTable +" WHERE "+ columnToCheck+" ="+words +" LIMIT 1", null);

            // getColumnIndex() gives us the index (0 to ...) of the column - otherwise we get a -1
            if (mCursor.getColumnIndex(columnToCheck) != -1)
                return true;
            else
                return false;

        } catch (Exception Exp) {
            // Something went wrong. Missing the database? The table?
            Log.d("existsColumnInTable", "When checking whether a column exists in the table, an error occurred: " + Exp.getMessage());
            return false;
        } finally {
            if (mCursor != null) mCursor.close();
        }
    }


}
