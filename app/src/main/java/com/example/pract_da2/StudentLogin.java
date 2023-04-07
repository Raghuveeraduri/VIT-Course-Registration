package com.example.pract_da2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class StudentLogin extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextInputEditText stu_username,stu_password;
    MaterialCheckBox RemeberMe;SharedPreferences sp;
    MaterialButton stu_login;Button register;ImageView iv1;TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        stu_username=findViewById(R.id.stu_username);
        stu_password=findViewById(R.id.stu_password);
        stu_login=findViewById(R.id.stu_login);
        register=findViewById(R.id.register);
        RemeberMe=findViewById(R.id.RemeberMe);
        tv1=findViewById(R.id.tv1);
        iv1=findViewById(R.id.imageView2);
        mAuth=FirebaseAuth.getInstance();
        stu_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signInWithEmailAndPassword(stu_username.getText().toString(),stu_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            if(RemeberMe.isChecked()) {
                                sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("status", "true");
                                editor.commit();
                            }
                            sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("username",""+stu_username.getText().toString());
                            editor.commit();
                            Toast.makeText(StudentLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Registration.class));
                            finish();
                        }
                        else
                            Toast.makeText(StudentLogin.this, "Check your credentials", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Signup.class);
                Pair[] pairs=new Pair[6];
                pairs[0] =new Pair<View,String>(iv1,"logo_image");
                pairs[1] =new Pair<View,String>(tv1,"logo_text");
                pairs[2] =new Pair<View,String>(register,"login_signup_trans");
                pairs[3] =new Pair<View,String>(stu_login,"signup_trans");
                pairs[4] =new Pair<View,String>(stu_username,"username_trans");
                pairs[5] =new Pair<View,String>(stu_password,"password_trans");
                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(StudentLogin.this,pairs);
                startActivity(i,options.toBundle());
                finish();
            }
        });
    }
}