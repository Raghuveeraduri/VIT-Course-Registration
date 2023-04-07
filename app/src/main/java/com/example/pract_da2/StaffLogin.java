package com.example.pract_da2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class StaffLogin extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    TextInputEditText sta_username,sta_password;
    MaterialCheckBox sta_RemeberMe;
    SharedPreferences sp;Boolean result;
    MaterialButton sta_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);
        sta_username=findViewById(R.id.sta_username);
        sta_password=findViewById(R.id.sta_password);
        sta_login=findViewById(R.id.sta_login);
        sta_RemeberMe=findViewById(R.id.sta_RemeberMe);
        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        sta_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Staff").whereEqualTo("email",""+sta_username.getText()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        result=queryDocumentSnapshots.isEmpty();
                        if(!result){
                            mAuth.signInWithEmailAndPassword(sta_username.getText().toString(),sta_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        if(sta_RemeberMe.isChecked()) {
                                            sp = getSharedPreferences("sharedPrefs2", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sp.edit();
                                            editor.putString("status", "true");
                                            editor.commit();
                                        }
                                        sp = getSharedPreferences("sharedPrefs2", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putString("username",""+sta_username.getText().toString());
                                        editor.commit();
                                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),Backend.class));
                                    }
                                    else
                                        Toast.makeText(getApplicationContext(), "Check your credentials", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else
                            Toast.makeText(getApplicationContext(), "This is for staff login", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}