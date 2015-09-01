package com.hisham.sqlitedemo;

import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MyDBHelper dbHelper;
    private EditText etID;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etAddress;
    private EditText etSalary;
    private Button btnInsert;
    private Button btnUpdate;
    private Button btnDelete;
    private Button btnLoadAll;
    private TextView tvFinalData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDBHelper(MainActivity.this);
        init();

    }

    private void init() {
        etID = (EditText) findViewById(R.id.etID);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etSalary = (EditText) findViewById(R.id.etSalary);

        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnLoadAll = (Button) findViewById(R.id.btnLoadAll);

        btnInsert.setOnClickListener(dbButtonsListener);
        btnUpdate.setOnClickListener(dbButtonsListener);
        btnDelete.setOnClickListener(dbButtonsListener);
        btnLoadAll.setOnClickListener(dbButtonsListener);

        tvFinalData = (TextView) findViewById(R.id.tvData);


    }

    private View.OnClickListener dbButtonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnInsert:
                    long resultInsert = dbHelper.insert(Integer.parseInt(getValue(etID)), getValue(etFirstName),
                            getValue(etLastName), getValue(etAddress), Double.valueOf(getValue(etSalary)));
                    if(resultInsert == -1){
                        Toast.makeText(MainActivity.this, "Some error occurred while inserting", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Data inserted successfully, ID: " + resultInsert, Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.btnUpdate:
                    long resultUpdate = dbHelper.update(Integer.parseInt(getValue(etID)), getValue(etFirstName),
                            getValue(etLastName), getValue(etAddress), Double.valueOf(getValue(etSalary)));
                    if(resultUpdate == 0){
                        Toast.makeText(MainActivity.this, "Some error occurred while updating", Toast.LENGTH_SHORT).show();
                    } else if(resultUpdate == 1) {
                        Toast.makeText(MainActivity.this, "Data updated successfully, ID: " + resultUpdate, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Some error occurred, multiple records updated.", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.btnDelete:
                    long resultDelete = dbHelper.delete(Integer.parseInt(getValue(etID)));
                    if(resultDelete == 0){
                        Toast.makeText(MainActivity.this, "Some error occurred while inserting", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Data deleted successfully.", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.btnLoadAll:

                    StringBuffer finalData = new StringBuffer();
                    Cursor cursor = dbHelper.getAllRecords();
//                    Cursor cursor = dbHelper.getDataBasedOnQuery("SELECT * FROM " + MyDBHelper.TABLE_NAME
//                    + " WHERE " + MyDBHelper.ADDRESS + " = 'UK'");

                    for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                        finalData.append(cursor.getInt(cursor.getColumnIndex(MyDBHelper.ID)));
                        finalData.append(" - ");
                        finalData.append(cursor.getString(cursor.getColumnIndex(MyDBHelper.FIRST_NAME)));
                        finalData.append(" - ");
                        finalData.append(cursor.getString(cursor.getColumnIndex(MyDBHelper.LAST_NAME)));
                        finalData.append(" - ");
                        finalData.append(cursor.getString(cursor.getColumnIndex(MyDBHelper.ADDRESS)));
                        finalData.append(" - ");
                        finalData.append(cursor.getLong(cursor.getColumnIndex(MyDBHelper.SALARY)));
                        finalData.append("\n");
                    }

                    tvFinalData.setText(finalData);
                    break;

            }
        }
    };

    private String getValue(EditText editText) {
        return editText.getText().toString().trim();
    }


    @Override
    protected void onStart() {
        super.onStart();
        dbHelper.openDB();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.closeDB();
    }
}
