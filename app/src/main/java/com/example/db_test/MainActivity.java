package com.example.db_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    String nameSt = null;
    int ageInt = 0;
    String depSt = null;
    int count = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText name = findViewById(R.id.Name);
        EditText age = findViewById(R.id.Age);
        EditText department = findViewById(R.id.Department);

        Button parseBtn = findViewById(R.id.button3);
        Button addBtn = findViewById(R.id.button2);
        Button selBtn = findViewById(R.id.button4);
        Button delBtn = findViewById(R.id.button);
        MyDbHelper myDbHelper = new MyDbHelper(getApplicationContext());


        parseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String nameSt = String.valueOf(name.getText());
                try {
                    nameSt = name.getText().toString();
                    ageInt = Integer.parseInt(String.valueOf(age.getText()));
                    depSt = department.getText().toString();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"빈칸을 채워주세요", Toast.LENGTH_SHORT).show();
                }

                //Toast.makeText(getApplicationContext(), "" + nameSt + ageInt + depSt, Toast.LENGTH_LONG).show();

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();

                values.put(TableInfo.COLUMN_NAME_NAME, nameSt);
                values.put(TableInfo.COLUMN_NAME_AGE, ageInt);
                values.put(TableInfo.COLUMN_NAME_DEP, depSt);

                SQLiteDatabase db = myDbHelper.getWritableDatabase();
                long newRowId = db.insert(TableInfo.TABLE_NAME, null, values);
                Log.i(TAG, "new row ID : " + newRowId);

                db.close();

            }
        });

        selBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i(TAG, "sel btn clicked");
                SQLiteDatabase db = myDbHelper.getReadableDatabase();

                count++;
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                int clickTime = sharedPref.getInt("clickTime",0);
                editor.putInt("clickTime", count+clickTime);
                editor.commit();

                Log.i(TAG, "조회 버튼 클릭 수 : "+ sharedPref.getInt("clickTime",0));

                nameSt = name.getText().toString();
                try {
                    ageInt = Integer.parseInt(String.valueOf(age.getText()));
                }catch (Exception e){
                    ageInt = 0;
                }
                depSt = department.getText().toString();

                Cursor c;

                if(nameSt.length() != 0 && ageInt ==0 && depSt.length()==0){ //이름 검색
                    //Log.i(TAG, "이름 ");
                    c = db.rawQuery("SELECT * FROM " + TableInfo.TABLE_NAME + " WHERE name = '"+nameSt+"' "  , null);
                    if(c.moveToFirst()){
                        do {
                            String col1 = c.getString(1);
                            int col2 = c.getInt(2);
                            String col3 = c.getString(3);
                            Log.i(TAG, "READ, 이름 : " + col1 + ", 나이 : " + col2 + ", 학부 : " + col3);

                        }while (c.moveToNext());
                    }

                }

                else if(nameSt.length() != 0 && ageInt !=0 && depSt.length()==0){ //이름, 나이 검색
                    //Log.i(TAG, "이름나이 ");
                    c = db.rawQuery("SELECT * FROM " + TableInfo.TABLE_NAME + " WHERE name = '"+nameSt+"' AND age = " + ageInt  , null);
                    if(c.moveToFirst()){
                        do {
                            String col1 = c.getString(1);
                            int col2 = c.getInt(2);
                            String col3 = c.getString(3);
                            Log.i(TAG, "READ, 이름 : " + col1 + ", 나이 : " + col2 + ", 학부 : " + col3);

                        }while (c.moveToNext());
                    }
                }

                else if(nameSt.length() != 0 && ageInt !=0 && depSt.length() != 0){ //이름, 나이, 학부 검색
                    //Log.i(TAG, "이름나이학부 ");
                    c = db.rawQuery("SELECT * FROM " + TableInfo.TABLE_NAME + " WHERE name = '"+nameSt+"' AND age = " + ageInt + " AND department = '"+depSt+"' " , null);
                    if(c.moveToFirst()){
                        do {
                            String col1 = c.getString(1);
                            int col2 = c.getInt(2);
                            String col3 = c.getString(3);
                            Log.i(TAG, "READ, 이름 : " + col1 + ", 나이 : " + col2 + ", 학부 : " + col3);

                        }while (c.moveToNext());
                    }
                }

                else if(nameSt.length() == 0 && ageInt !=0 && depSt.length()==0){ //나이 검색
                    //Log.i(TAG, "나이 ");
                    c = db.rawQuery("SELECT * FROM " + TableInfo.TABLE_NAME + " WHERE age = " + ageInt  , null);
                    if(c.moveToFirst()){
                        do {
                            String col1 = c.getString(1);
                            int col2 = c.getInt(2);
                            String col3 = c.getString(3);
                            Log.i(TAG, "READ, 이름 : " + col1 + ", 나이 : " + col2 + ", 학부 : " + col3);

                        }while (c.moveToNext());
                    }

                }

                else if(nameSt.length() == 0 && ageInt !=0 && depSt.length() != 0){ //나이, 학부 검색
                    //Log.i(TAG, "나이학부 ");
                    c = db.rawQuery("SELECT * FROM " + TableInfo.TABLE_NAME + " WHERE age = " + ageInt + " AND department = '"+depSt+"' " , null);
                    if(c.moveToFirst()){
                        do {
                            String col1 = c.getString(1);
                            int col2 = c.getInt(2);
                            String col3 = c.getString(3);
                            Log.i(TAG, "READ, 이름 : " + col1 + ", 나이 : " + col2 + ", 학부 : " + col3);

                        }while (c.moveToNext());
                    }

                }

                else if(nameSt.length() == 0 && ageInt == 0 && depSt.length()!=0){ //학부 검색
                    //Log.i(TAG, "학부 ");
                    c = db.rawQuery("SELECT * FROM " + TableInfo.TABLE_NAME + " WHERE department = '"+depSt+"' " , null);
                    if(c.moveToFirst()){
                        do {
                            String col1 = c.getString(1);
                            int col2 = c.getInt(2);
                            String col3 = c.getString(3);
                            Log.i(TAG, "READ, 이름 : " + col1 + ", 나이 : " + col2 + ", 학부 : " + col3);

                        }while (c.moveToNext());
                    }

                }

                else if(nameSt.length() != 0 && ageInt == 0 && depSt.length() != 0){ //이름, 학부 검색
                    //Log.i(TAG, "이름학부 ");
                    c = db.rawQuery("SELECT * FROM " + TableInfo.TABLE_NAME + " WHERE name = '"+nameSt+"' AND department = '"+depSt+"' " , null);
                    if(c.moveToFirst()){
                        do {
                            String col1 = c.getString(1);
                            int col2 = c.getInt(2);
                            String col3 = c.getString(3);
                            Log.i(TAG, "READ, 이름 : " + col1 + ", 나이 : " + col2 + ", 학부 : " + col3);

                        }while (c.moveToNext());
                    }

                }

                else{                                                                 // 전체조회
                    c = db.rawQuery("SELECT * FROM " + TableInfo.TABLE_NAME, null);
                    if(c.moveToFirst()){
                        do{
                            int col1 = c.getInt(0);
                            String col2 = c.getString(1);
                            int col3 = c.getInt(2);
                            String col4 = c.getString(3);
                            Log.i(TAG, "READ, col1:" + col1 + ", col2 : " + col2 + ", col3 : " + col3 + ", col4: " + col4);

                        }while(c.moveToNext());
                    }


                }


            }
        });



        /*
        department.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //depSt = department.getText().toString();
            }
            @Override
            public void afterTextChanged(Editable s) {
                depSt = department.getText().toString();
                Log.i(TAG, "depSt : " + depSt);

            }
        });*/
    }

    public class TableInfo {
        public static final String TABLE_NAME = "ourclass";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_AGE = "age";
        public static final String COLUMN_NAME_DEP = "department";

    }


    public static class MyDbHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public  static final String DATABASE_NAME = "ClassGroup.db";

        private static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TableInfo.TABLE_NAME + " (" +
                        TableInfo.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                        TableInfo.COLUMN_NAME_NAME + " TEXT, " +
                        TableInfo.COLUMN_NAME_AGE + " INTEGER, " +
                        TableInfo.COLUMN_NAME_DEP + " TEXT)";

        private  static  final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TableInfo.TABLE_NAME;

        public MyDbHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db){
            db.execSQL(SQL_CREATE_TABLE);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL(SQL_DELETE_TABLE);
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
            onUpgrade(db, oldVersion, newVersion);
        }

    }

}