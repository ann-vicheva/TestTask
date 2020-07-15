package com.example.testtask;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ann Vicheva on 14.07.2020.
 */

public class MainActivity extends AppCompatActivity {

    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;


    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    public List common_list = new ArrayList();//list for all coworkers from db


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init_recycler_view();
        //--------------------------------------
        //work with db
        mDBHelper = new DatabaseHelper(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
        //-------------------------------------
        list_from_db();
    }


    //initialization RecyclerView
    private void init_recycler_view(){
        recyclerView=(RecyclerView)findViewById(R.id.rvCommon);//find visual view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//set manager
        adapter = new RecyclerViewAdapter(this, common_list);
        recyclerView.setAdapter(adapter);//set adapter
    }


    //filling in the common_list from the database
    public void list_from_db(){
        common_list.clear();
        String request_table="SELECT * FROM bws_coworkers";
        Cursor cursor = mDb.rawQuery(request_table, null);//query to table
        cursor.moveToFirst();//cursor on first element
        while (!cursor.isAfterLast()) {//while not last element
            Profile profile = new Profile();
            profile.setId(cursor.getInt(0));
            profile.setName(cursor.getString(1));
            profile.setAge(cursor.getInt(2));
            profile.setPhone_number(cursor.getString(3));
            profile.setSex(cursor.getString(4));

            common_list.add(profile);//adding to list

            cursor.moveToNext();//move to next element
        }
        cursor.close();//close db-connection
    }


    //click handler for "add" button on main activity
    //open adding activity
    public void add_coworkers_main(View view){
        Intent intent = new Intent(this, AddProfileActivity.class);
        startActivity(intent);
    }
}
