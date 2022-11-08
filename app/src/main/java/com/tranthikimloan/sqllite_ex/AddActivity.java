package com.tranthikimloan.sqllite_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tranthikimloan.sqllite_ex.databinding.ActivityAddBinding;
import com.tranthikimloan.sqllite_ex.databinding.ActivityMainBinding;

public class AddActivity extends AppCompatActivity {
    ActivityAddBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvent();


    }

    private void addEvent() {
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values=new ContentValues();
                values.put(Utils.COL_NAME,binding.edtName.getText().toString());
                values.put(Utils.COL_PRICE,Double.parseDouble(binding.edtPrice.getText().toString()));

                long numb0fRows= MainActivity.db.insert(Utils.TBL_NAME,null,values);
                if (numb0fRows >0){
                    Toast.makeText(AddActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(AddActivity.this, "Fail!", Toast.LENGTH_SHORT).show();
                }
                finish();
            }


        });
    }

}