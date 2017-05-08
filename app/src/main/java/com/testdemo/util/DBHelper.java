package com.testdemo.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 05 17:56
 * @DESC：
 */

public class DBHelper {
    private static final String SXTC_CITIES_DB = "sxtc_cities.db";
    private static final String DB_PATH = Environment.getExternalStorageDirectory() + "/sqtc/";
    private SQLiteDatabase db;

    Context context;
    private static DBHelper dbHelper = new DBHelper();
    public void init(Context context){
        this.context = context;
    }
    public static DBHelper getInstance(){
        return dbHelper;
    }

    //创建数据库
    public void createDb() {
        File dbPath = new File(DB_PATH);
        File dbFile = new File(DB_PATH + SXTC_CITIES_DB);
        if (!dbPath.exists()) {
            dbPath.mkdir();
        }
        if (!dbFile.exists()) {
            dbFile.mkdir();
        }
        if (dbPath.exists() && dbFile.exists()) {
            db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + SXTC_CITIES_DB, null);
        } else {
            Log.e("DBHelper","数据库目录创建失败");
        }
    }

    public void executeSql(String sql) {
        if (TextUtils.isEmpty(sql)) return;
        db.execSQL(sql);
    }

    public List<String> readSql() {
        List<String> sqlList = new ArrayList<String>();
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open("sqt_area.sql");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (null == inputStream) return null;
        byte[] buff = new byte[1024];
        String[] sqlArr;
        StringBuffer str = new StringBuffer();
        int count;
        try {
            while ((count = inputStream.read(buff)) != -1) {
                str.append(new String(buff, 0, count));
            }
            sqlArr = str.toString().split("(;//s*//n//r)|(;//s*//n)");
            for (int i = 0; i < sqlArr.length; i++) {
                String sql = sqlArr[i].replaceAll("--.*", "").trim();
                if (!sql.equals("")) {
                    sqlList.add(sql);
                    Log.e("DBHelper","执行SQL：" + sql);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sqlList;
    }

    /**
     * 加载解析sql文件
     * @return
     * @throws Exception
     */
    public List<String> loadSql() {
        List<String> sqlList = new ArrayList<String>();
        try {
            InputStream sqlFileIn = context.getAssets().open("sqt_area.sql");
            StringBuffer sqlSb = new StringBuffer();
            byte[] buff = new byte[1024];
            int byteRead = 0;
            while ((byteRead = sqlFileIn.read(buff)) != -1) {
                sqlSb.append(new String(buff, 0, byteRead,"utf-8"));
            }
            sqlFileIn.close();
            // Windows 下换行是 \r\n, Linux 下是 \n
            String[] sqlArr = sqlSb.toString().split("(;\\s*\\r\\n)|(;\\s*\\n)");
            for (int i = 0; i < sqlArr.length; i++) {
                String sql = sqlArr[i].replaceAll("--.*", "").trim();
                if (!sql.equals("")) {
                    sqlList.add(sql);
                }
            }
            return sqlList;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
