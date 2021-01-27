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
        String Query = "select * from wordLearnData where word = ? LIMIT 1";
        Cursor cursor = db.rawQuery(Query, new String[]{wordd});
        if (cursor.getCount() <= 0) {
            //word Not Found
            cursor.close();
            values.put(KEY_WORD, wordd);
            values.put(KEY_LEARN, learn);
            values.put(KEY_LEARNED, learned);
            db.insert(TABLE_NAME, null, values);
            Toast.makeText(context, "Word Added:  " + wordd , Toast.LENGTH_LONG).show();

        } else {
            try {
                //word Found
                cursor.close();
                values.put(KEY_LEARN, learn);
                values.put(KEY_LEARNED, learned);
                db.update(TABLE_NAME, values, "word= ?", new String[]{wordd});
                Toast.makeText(context, "Word Update:  " + wordd , Toast.LENGTH_LONG).show();
            } catch (Exception e) {e.printStackTrace(); }
        }
        db.close();
    }


}
