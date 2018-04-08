package com.hishixi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by seamus on 2018/3/19 11:21
 */

public class DBManager {
    private String DB_NAME = "Dyeing.db";
    private Context mContext;

    public DBManager(Context mContext) {
        this.mContext = mContext;
    }

    //把assets目录下的db文件复制到dbpath下
    public void copyDB(String packName) {
        String mDbPath = "data/data/" + packName + "/" + DB_NAME;
        if (!new File(mDbPath).exists()) {
            try {
                FileOutputStream out = new FileOutputStream(mDbPath);
                InputStream in = mContext.getAssets().open("Dyeing.db");
                byte[] buffer = new byte[1024];
                int readBytes = 0;
                while ((readBytes = in.read(buffer)) != -1)
                    out.write(buffer, 0, readBytes);
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public SQLiteDatabase initDB(String packName) {
        String path = "data/data/" + packName + "/" + DB_NAME;
        return SQLiteDatabase.openOrCreateDatabase(path, null);
    }


    /**
     * 获取全部产品信息
     *
     * @param sqliteDB
     * @param columns
     * @param selection
     * @param selectionArgs
     * @return
     */
    public List<DyeEntity> query(SQLiteDatabase sqliteDB, String[] columns, String selection,
                                 String[]
                                         selectionArgs) {
        DyeEntity dyeEntity = null;
        List<DyeEntity> list = new ArrayList<>();
        try {
            String table = "lotmanage";
            Cursor cursor = sqliteDB.query(table, columns, selection, selectionArgs, null, null,
                    null);
            if (cursor.moveToFirst()) {
                do {
                    String lotid = cursor.getString(cursor.getColumnIndex("lotid"));
                    String lotcus = cursor.getString(cursor.getColumnIndex("lotcus"));
                    String lotweight = cursor.getString(cursor.getColumnIndex("lotweight"));
                    String lotlength = cursor.getString(cursor.getColumnIndex("lotlength"));
                    String lotweightscale = cursor.getString(cursor.getColumnIndex
                            ("lotweightscale"));
                    String lotwidth = cursor.getString(cursor.getColumnIndex("lotwidth"));

                    String lotroll = cursor.getString(cursor.getColumnIndex("lotroll"));
                    String lottype = cursor.getString(cursor.getColumnIndex("lottype"));
                    String dyegroup = cursor.getString(cursor.getColumnIndex("dyegroup"));
                    String quality = cursor.getString(cursor.getColumnIndex("quality"));
                    String shade = cursor.getString(cursor.getColumnIndex("shade"));
                    String lotmachgroup = cursor.getString(cursor.getColumnIndex("lotmachgroup"));
                    String dyeno = cursor.getString(cursor.getColumnIndex("dyeno"));
                    String lotmach = cursor.getString(cursor.getColumnIndex("lotmach"));
                    String secondes = cursor.getString(cursor.getColumnIndex("secondes"));
                    String addquality = cursor.getString(cursor.getColumnIndex("addquality"));
                    String lothandler = cursor.getString(cursor.getColumnIndex("lothandler"));
                    String lotdeliver = cursor.getString(cursor.getColumnIndex("lotdeliver"));
                    String lotown = cursor.getString(cursor.getColumnIndex("lotown"));
                    String remarks = cursor.getString(cursor.getColumnIndex("remarks"));
                    String starttime = cursor.getString(cursor.getColumnIndex("starttime"));
                    dyeEntity = new DyeEntity(lotid, lotcus, lotweight, lotlength, lotweightscale,
                            lotwidth, lotroll, lottype, dyegroup, quality, shade, lotmachgroup,
                            dyeno, lotmach, secondes, addquality, lothandler,lotdeliver,lotown,remarks,starttime);
                    list.add(dyeEntity);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String[] getNumList(SQLiteDatabase sqliteDB) {
        String[] array = null;
        int i = 1;
        try {
            String table = "lotmanage";
            Cursor cursor = sqliteDB.query(true, table, new String[]{"lotid"}, null, null, null,
                    null, null, null);
            array = new String[cursor.getCount() + 1];
            array[0] = "不限";
            if (cursor.moveToFirst()) {
                do {
                    String lotid = cursor.getString(cursor.getColumnIndex("lotid"));
                    array[i] = lotid;
                    i++;
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    /**
     * 获取温度曲线
     * @param sqliteDB sqliteDB
     * @param columns columns
     * @param tableName tableName
     *
     * @return
     */
    public ArrayList<Point> queryTemp(SQLiteDatabase sqliteDB, String[] columns,String tableName) {
        DyeEntity dyeEntity = null;
        ArrayList<Point> pointArrayList = new ArrayList<>();
        try {
            String table = tableName;
            Cursor cursor = sqliteDB.query(table, columns, null, null, null, null,
                    null);
            if (cursor.moveToFirst()) {
                do {
                    int time = cursor.getInt(cursor.getColumnIndex("time"));
                    int data = (int) (cursor.getFloat(cursor.getColumnIndex("data")) * 10);
                    Point point = new Point(time, data);
                    pointArrayList.add(point);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pointArrayList;
    }
}
