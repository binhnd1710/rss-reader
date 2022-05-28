package com.example.btl.config;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.btl.models.FeedSourceModel;

import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "RSSREADER";
    public static final String TABLE_NAME = "SOURCES";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_URL = "URL";
    public static final String COLUMN_CATEGORY = "CATEGORY";
    private SQLiteDatabase database;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null,
                DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " ( " + COLUMN_ID + " INTEGER " +
                "PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " VARCHAR, " + COLUMN_URL + " " +
                "VARCHAR, " + COLUMN_CATEGORY + " VARCHAR);");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_NAME + "(" + COLUMN_NAME + "," + COLUMN_URL +
                "," + COLUMN_CATEGORY + ") VALUES ( 'Thế giới - VNExpress'," +
                " 'https://vnexpress.net/rss/the-gioi.rss', 'Other')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertRecord(FeedSourceModel sourceModel) {
        database = this.getReadableDatabase();
        database.execSQL("INSERT INTO " + TABLE_NAME + "(" + COLUMN_NAME + "," + COLUMN_URL + "," +
                COLUMN_CATEGORY + ") VALUES ('" + sourceModel.getName() + "','" +
                sourceModel.getUrl() + "','" + sourceModel.getCategory() + "')");
        database.close();
    }

    public void deleteRecord(FeedSourceModel sourceModel) {
        database = this.getReadableDatabase();
        database.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = " + "'" +
                sourceModel.getName() + "'" + " AND " + COLUMN_URL + " = " + "'" +
                sourceModel.getUrl() + "'" + " AND " + COLUMN_CATEGORY + " = " + "'" +
                sourceModel.getCategory() + "'");
        database.close();
    }

    public ArrayList<FeedSourceModel> getAllRecords(String CATEGORY) {
        database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, null, COLUMN_CATEGORY + " LIKE ?"
                , new String[]{"%" + CATEGORY + "%"}, null, null, COLUMN_NAME
                        + " ASC ");
        ArrayList<FeedSourceModel> sourceModels = new ArrayList<>();
        FeedSourceModel feedSourceModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                feedSourceModel = new FeedSourceModel();
                feedSourceModel.setID(cursor.getString(0));
                feedSourceModel.setName(cursor.getString(1));
                feedSourceModel.setUrl(cursor.getString(2));
                feedSourceModel.setCategory(cursor.getString(3));
                sourceModels.add(feedSourceModel);
            }
        }
        cursor.close();
        database.close();
        return sourceModels;
    }
}
