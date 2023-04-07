package com.example.pract_da2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {
    MaterialButton register,logout,see_registered;
    RadioGroup rg;
    String option;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        register=findViewById(R.id.register);
        logout=findViewById(R.id.logout);
        see_registered=findViewById(R.id.see_registered);
        rg=findViewById(R.id.radioGroup);
        mAuth=FirebaseAuth.getInstance();
        see_registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisteredCourses.class));
            }
        });
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                option="";
                switch (checkedId){
                    case R.id.pc:
                        option="Programme Core";
                        break;
                    case R.id.pe:
                        option="Programme Elective";
                        break;
                    case R.id.uc:
                        option="University Core";
                        break;
                    case R.id.ue:
                        option="University Elective";
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),CoursePage.class);
                i.putExtra("selected",option);
                startActivity(i);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp=getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("status","");
                editor.commit();
                Toast.makeText(Registration.this, "Signed Out", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}