package com.example.pract_da2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    MaterialButton student,staff;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        check();check2();
        staff=findViewById(R.id.staff);
        student=findViewById(R.id.student);
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),StudentLogin.class));
                finish();
            }
        });
        staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),StaffLogin.class));
                finish();
            }
        });
    }
    void check(){
        SharedPreferences sp=getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        String status=sp.getString("status","");
        if(status.equals("true")){
            startActivity(new Intent(getApplicationContext(),Registration.class));
        }
    }
    void check2(){
        SharedPreferences sp=getSharedPreferences("sharedPrefs2",MODE_PRIVATE);
        String status=sp.getString("status","");
        if(status.equals("true")){
            startActivity(new Intent(getApplicationContext(),Backend.class));
        }
    }
}