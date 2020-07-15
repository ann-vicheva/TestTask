package com.example.testtask;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Ann Vicheva on 14.07.2020.
 */

public class AddProfileActivity extends AppCompatActivity {

    private DatabaseHelper pDBHelper;
    private SQLiteDatabase pDb;

    Context context;

    EditText etName;
    EditText etAge;
    EditText etPhoneNumber;
    CheckBox cbMale;
    CheckBox cbFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);

        context=AddProfileActivity.this;//context for using in text hint method
        etName=(EditText)findViewById(R.id.etName);//find visual view elem
        etAge=(EditText)findViewById(R.id.etAge);
        etPhoneNumber=(EditText)findViewById(R.id.etPhoneNumber);

        //finding and handling click to checkbox(male)
        cbMale=(CheckBox)findViewById(R.id.cbMale);
        cbMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbMale.isChecked()){
                    cbFemale.setChecked(false);
                }else{
                    cbFemale.setChecked(true);
                }
            }
        });
        //finding and handling click to checkbox(female)
        cbFemale=(CheckBox)findViewById(R.id.cbFemale);
        cbFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbFemale.isChecked()){
                    cbMale.setChecked(false);
                }else{
                    cbMale.setChecked(true);
                }
            }
        });


        //--------------------------------------
        //work with db
        pDBHelper = new DatabaseHelper(this);

        try {
            pDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            pDb = pDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
        //-------------------------------------

    }


    //true - phone, age, name valid
    //false - phone or age or name don`t valid
    public boolean check_data_validation(String name, int age, String phone){
        if(name.length()==0){//check name empty
            Toast.makeText(context, R.string.name_empty_message, Toast.LENGTH_LONG).show();
        }else{//name correct
            if(age>=150){//check correct age
                Toast.makeText(context, R.string.age_corect_message, Toast.LENGTH_LONG).show();
            }else{//name & age correct
                if(phone.length()==0){//check phone empty
                    Toast.makeText(context, R.string.phone_empty_message, Toast.LENGTH_LONG).show();
                }else{
                    return true;//name & age & phone correct
                }
            }
        }
        return false;
    }


    //click handler for "add" button on AddProfile activity
    //adding record to db, clear visual field
    public void add_coworkers_profile(View view){
        String sex = "";
        if(cbMale.isChecked()){
            sex="Male";
        }else{
            sex="Female";
        }
        if(etAge.getText().toString().length()==0){//for correct parse age
            Toast.makeText(context, R.string.age_empty_message, Toast.LENGTH_LONG).show();
        }else{//age correct
            if(check_data_validation(etName.getText().toString(),
                    Integer.parseInt(etAge.getText().toString()),
                    etPhoneNumber.getText().toString())){//all data correct
                //adding new record to db
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", etName.getText().toString());
                contentValues.put("age", Integer.parseInt(etAge.getText().toString()));
                contentValues.put("phone_number", etPhoneNumber.getText().toString());
                contentValues.put("sex", sex);

                pDb.insert("bws_coworkers", null, contentValues);

                //clear user edittext & checkbox
                etName.setText("");
                etAge.setText("");
                etPhoneNumber.setText("");
                cbMale.setChecked(true);//default
                cbFemale.setChecked(false);

                Toast.makeText(context, R.string.success_add_message, Toast.LENGTH_LONG).show();
            }
        }
    }


    //click handler for "cancel" button on AddProfile activity
    //open main activity
    public void back_to_main(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
