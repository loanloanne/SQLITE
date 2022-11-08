package com.tranthikimloan.sqllite_ex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.tranthikimloan.models.Product;
import com.tranthikimloan.sqllite_ex.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ArrayAdapter<Product> adapter;
    ArrayList<Product> products;
    public  static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        copyDB();
        //loadData();

    }

    private void loadData() {
        products=new ArrayList<>();
        //Init sample data

       // products.add(new Product(1, "Henieken", 19000));
       // products.add(new Product(2, "Tiger", 17000));
// cach 1
        db = openOrCreateDatabase(Utils.DB_NAME, MODE_PRIVATE, null);
//        //Cursor c=db.rawQuery("SELECT * FROM"+ Utils.TBL_NAME,null);
//        Cursor c=db.rawQuery(" SELECT * FROM " + Utils.TBL_NAME + " WHERE "
//        + Utils.COL_PRICE +">=?", new String[]{"16000"});
        //cach 2
        Cursor c=db.query(Utils.TBL_NAME,null,null,null, null,null,null);
        //Cursor c=db.query(Utils.TBL_NAME, null,Utils.COL_PRICE +">=?"
        //, new String[]{"16000"},null,null,null);

        while (c.moveToNext()) {
            products.add(new Product(c.getInt(0),c.getString(1),c.getDouble(2)));
        }
        c.close();

        adapter=new ArrayAdapter<Product>(MainActivity.this, android.R.layout.simple_list_item_1,products);
        binding.lvProduct.setAdapter(adapter);


    }

    private void copyDB() {
        File dbPath = getDatabasePath(Utils.DB_NAME);
        if (!dbPath.exists()){
            if (copyDBFromAssets()){
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
            }

        }
    };

    private boolean copyDBFromAssets() {

        String dbPath = getApplicationInfo().dataDir +Utils.DB_PATH_SUFFIX + Utils.DB_NAME;
        try {
            InputStream inputStream = getAssets().open(Utils.DB_NAME); File f = new File(getApplicationInfo().dataDir + Utils.DB_PATH_SUFFIX); if(!f.exists()){
                f.mkdir(); }
            OutputStream outputStream = new FileOutputStream(dbPath); byte[] buffer = new byte[1024]; int length; while((length=inputStream.read(buffer))>0){
                outputStream.write(buffer,0, length); }
            outputStream.flush(); outputStream.close(); inputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        return false;
    }
}

    @Override
    protected void onResume() {
        loadData();
        super.onResume();
    }

    //MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.mn_Add){
            Intent intent=new Intent(MainActivity.this,AddActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}