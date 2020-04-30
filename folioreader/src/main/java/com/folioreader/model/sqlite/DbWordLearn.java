package com.folioreader.model.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DbWordLearn extends SQLiteOpenHelper {

    private static final String TAG = DbWordLearn.class.getSimpleName();

    public static final int DATABASE_VERSION = 3;
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
        try {
            String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                    + KEY_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_WORD + "TEXT NOT NULL,"
                    + KEY_LEARN + "INTEGER DEFAULT 0,"
                    + KEY_LEARNED + "INTEGER DEFAULT 0)";

            sqLiteDatabase.execSQL(CREATE_TABLE);
            Log.d("Veritabanı Kuruldu", "Tablo Oluşturuldu.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addWord(Context context,String word,String learn,String learned) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_WORD,word);
        values.put(KEY_LEARN,learn);
        values.put(KEY_LEARNED,learned);
        db.close();
        Toast.makeText(context, "Addword durumu: "+word+" :  "+ learn +"  :  "+learned, Toast.LENGTH_LONG).show();
    }

}
