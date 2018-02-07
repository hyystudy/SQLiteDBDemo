package com.example.yun.sqlitedbdemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.amitshekhar.DebugDB;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private DataBaseHelper person;

    @OnClick(R.id.insert)
    public void onInsertBtnClicked(){
        //真正创建打开数据库
        //可增 删 改 的数据库
        SQLiteDatabase writableDatabase = person.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.PERSON_ID, 2);
        contentValues.put(DataBaseHelper.PERSON_NAME, "zal");
        contentValues.put(DataBaseHelper.PERSON_ADDRESS, "beijing");

        writableDatabase.insert(DataBaseHelper.DATABASE_NAME, null, contentValues);
        writableDatabase.close();

    }

    @OnClick(R.id.query)
    public void onQueryBtnClicked(){
        //可查询的数据库
        SQLiteDatabase readableDatabase = person.getReadableDatabase();

        Cursor cursor = readableDatabase.query(DataBaseHelper.DATABASE_NAME,
                new String[]{DataBaseHelper.PERSON_ID, DataBaseHelper.PERSON_NAME},//查询的列名
                "id=?",//查询条件
                new String[]{"2"},//查询条件的参数
                null,//对查询结果进行分组
                null, //对查询结果进行限制
                null//对查询结果进行排序
        );

        cursor.moveToFirst();
        while (!cursor.moveToLast()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            Log.d(TAG, "onCreate: name" + name);
        }

        cursor.close();

        readableDatabase.close();
    }

    @OnClick(R.id.update)
    public void onUpdateBtnClicked(){
        SQLiteDatabase writableDatabase = person.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.PERSON_NAME, "hyy");

        writableDatabase.update(DataBaseHelper.DATABASE_NAME, contentValues, "id=?", new String[]{"2"});

        writableDatabase.close();
    }

    @OnClick(R.id.delete)
    public void onDeleteBtnClicked(){
        SQLiteDatabase writableDatabase = person.getWritableDatabase();
        writableDatabase.delete(DataBaseHelper.DATABASE_NAME, "id=?", new String[]{"2"});

        writableDatabase.close();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //还没创建数据库
        person = new DataBaseHelper(this, "person", null, DataBaseHelper.DB_VERSION);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
