package com.example.yun.sqlitedbdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yun on 2018/2/6.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    public static int DB_VERSION = 1;
    public static String DATABASE_NAME = "person";
    public static String PERSON_ID = "id";
    public static String PERSON_NAME = "name";
    public static String PERSON_ADDRESS = "address";


    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        // 参数说明
        // context：上下文对象
        // name：数据库名称
        // param：一个可选的游标工厂（通常是 Null）
        // version：当前数据库的版本，值必须是整数并且是递增的状态

        super(context, name, factory, version);
    }

    /**
     * 复写onCreate（）
     * 调用时刻：当数据库第1次创建时调用
     * 作用：创建数据库 表 & 初始化数据
     * SQLite数据库创建支持的数据类型： 整型数据、字符串类型、日期类型、二进制
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 创建数据库1张表
        // 通过execSQL（）执行SQL语句（此处创建了1个名为person的表）
        String sql = "create table person(id integer primary key autoincrement,name varchar(64),address varchar(64))";
        sqLiteDatabase.execSQL(sql);

        // 注：数据库实际上是没被创建 / 打开的（因该方法还没调用）
        // 直到getWritableDatabase() / getReadableDatabase() 第一次被调用时才会进行创建 / 打开
    }

    /**
     * 复写onUpgrade（）
     * 调用时刻：当数据库升级时则自动调用（即 数据库版本 发生变化时）
     * 作用：更新数据库表结构
     * 注：创建SQLiteOpenHelper子类对象时,必须传入一个version参数，该参数 = 当前数据库版本, 若该版本高于之前版本, 就调用onUpgrade()
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // 参数说明：
        // db ： 数据库
        // oldVersion ： 旧版本数据库
        // newVersion ： 新版本数据库

        // 使用 SQL的ALTER语句
        String sql = "alter table person add sex varchar(8)";
        sqLiteDatabase.execSQL(sql);

    }



}
