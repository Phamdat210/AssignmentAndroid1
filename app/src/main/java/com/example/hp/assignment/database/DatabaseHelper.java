package com.example.hp.assignment.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hp.assignment.model.Lop;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "DBCLass";
    public static final int VERSION = 1;
    public final String CREATE_TABLE = "CREATE TABLE Class (id VARCHAR PRIMARY KEY, name NVARCHAR)";
    public final String COLUMN_ID = "id";
    public final String COLUMN_NAME = "name";
    public final String TABLE_NAME_CLASS = "Class";
    public DatabaseHelper dbHelper;
    public SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CLASS);
    }

    public long insertClass(Lop lop){
        //xin quyền
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //ghép cặp dữ liệu
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID,lop.getId());
        contentValues.put(COLUMN_NAME,lop.getName());

        //sử dụng lệnh insert
        long result = sqLiteDatabase.insert(TABLE_NAME_CLASS,null,contentValues);

        //đóng kết nối
        sqLiteDatabase.close();

        return result;
    }

    public int deleteClass(String id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        int result = sqLiteDatabase.delete(TABLE_NAME_CLASS,COLUMN_ID + "=?",new String[]{id});
        sqLiteDatabase.close();
        return result;
    }

    public long updateClass(Lop lop){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID,lop.getId());
        values.put(COLUMN_NAME,lop.getName());

        long result = sqLiteDatabase.update(TABLE_NAME_CLASS,values,COLUMN_ID+"=?", new String[]{lop.getId()});
        sqLiteDatabase.close();
        return result;
    }

    public List<Lop> getAllClass(){
        List<Lop> lopList = new ArrayList<>();
        //Xin quyền
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        //viết câu lệnh select
        String select = "SELECT * FROM " + TABLE_NAME_CLASS;

        Cursor cursor = sqLiteDatabase.rawQuery(select,null);
        if (cursor.moveToFirst()){
            do {
                Lop lop = new Lop();
                lop.setId(cursor.getString(0));
                lop.setName(cursor.getString(1));

                lopList.add(lop);
            } while (cursor.moveToNext());

            cursor.close();
        }
        //đóng kết nối DB
        sqLiteDatabase.close();
        return lopList;
    }

}
