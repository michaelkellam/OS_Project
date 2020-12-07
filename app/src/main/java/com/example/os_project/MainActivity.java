package com.example.os_project;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.os_project.sql.SQLOps;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.opencsv.CSVWriter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.*;
import androidx.gridlayout.widget.GridLayout;

import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static MainActivity instance;

    public String ip;
    public String db;
    public String user;
    public String pass;

    public Connection con;
    public SQLOps sql;

    public String currentTable = "";
    public TableLayout table;

    public boolean success = false;

    public int numRows = 0;
    public int numCols = 0;

    public FirstFragment f1;
    public SecondFragment f2;
    public TableFragment tf;
    public BlankFragment bf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        instance = this;

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public static MainActivity getInstance() {
        return instance;
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

    public void displayTables(ListView lv) {
        try {
            ArrayList<String> tables = sql.getTableList(con);
            ArrayAdapter<String> arrAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tables);
            lv.setAdapter(arrAdapter);
        } catch (SQLException e) {

        }

    }

    public ArrayList<String> getData() {
        try {
            return sql.getEntireTable(con, currentTable);
        } catch(SQLException e) {

        }

        return null;
    }

    public void displayData() {
        try {
            ArrayList<String> data = sql.getEntireTable(con, currentTable);
            Log.d(null, data.toString());

            numRows = 0;
            numCols = 0;

            try {

                ResultSet rs = null;

                try {
                    rs = con.prepareStatement("SELECT * FROM " + currentTable).executeQuery();
                } catch(SQLException e) {

                }

                numCols = con.prepareStatement("SELECT * FROM " + currentTable).getMetaData().getColumnCount();

                while (rs.next()) {
                    numRows++;
                }
            } catch(Exception e) {

            }

            Log.d(null, "Rows: " + numRows + ", Cols: " + numCols);

            if (currentTable.length() > 0) {
                for (int i = 0; i < numRows; i++) {
                    TableRow row = new TableRow(MainActivity.this);
                    for (int j = 0; j < numCols; j++) {
                        String value = "Testing.";
                        TextView tv = new TextView(MainActivity.this);
                        tv.setText(value);
                        row.addView(tv);
                    }
                    table.addView(row);
                }
            }


        } catch(SQLException e) {

        }

    }

    public ArrayList<String> columnNames() {
        ArrayList<String> names = new ArrayList<String>();

        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM " + currentTable);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int colCount = rsmd.getColumnCount();

            for (int i = 1; i <= colCount; i++) {
                names.add(rsmd.getColumnName(i));
            }
        } catch(SQLException e) {
            Log.d(null, e.toString());
        }
        return names;
    }

    public void drawTable(View view) {
        TableLayout tl = new TableLayout(this);
        TableRow tr = new TableRow(this);

        TextView tv = new TextView(this);
        tv.setText("TTTest");
        tr.addView(tv);

        TableLayout.LayoutParams lp = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        tl.setLayoutParams(lp);
        tl.addView(tr);
        setContentView(view);
    }

    public void startCon() {
        Task task = new Task();
        task.ip = ip;
        task.db = db;
        task.user = user;
        task.pass = pass;
        try {
            task.execute().get();
        } catch(Exception e) {

        }
    }

    public void saveToCsv(ArrayList<String[]> data, String title) {

        String csv = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + title + ".csv");

        CSVWriter writer = null;
        try {
            writer = new CSVWriter(new FileWriter(csv));
            writer.writeAll(data); // data is adding to csv

            writer.close();

            MediaScannerConnection.scanFile(MainActivity.this,
                    new String[] { Environment.getExternalStorageDirectory().toString() },
                    null,
                    new MediaScannerConnection.OnScanCompletedListener() {

                        public void onScanCompleted(String path, Uri uri) {

                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        }
                    });

        } catch (IOException e) {
            Log.d(null, e.toString());
        }
    }


    class Task extends AsyncTask<Void, Void, Void> {

        public String ip;
        public String db;
        public String user;
        public String pass;

        @Override
        protected Void doInBackground(Void... voids) {

            sql = new SQLOps(ip, db, user, pass);
            con = sql.startConnection(ip, db, user, pass);

            if (con == null) {
                success = false;
            } else {
                success = true;
                try {
                    Log.d(null, sql.getTableList(con).toString());
                } catch(Exception e) {

                }
            }
            return null;
        }
    }

}