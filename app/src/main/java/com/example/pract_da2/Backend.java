package com.example.pract_da2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Backend extends AppCompatActivity {
    FirebaseFirestore db;
    MaterialButton sta_logout;
    Spinner sp;long id;
    TextInputEditText code;TextInputEditText title;TextInputEditText slot;
    String type;
    MaterialButton save;AutoCompleteTextView editTextFilledExposedDropdown;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backend);
        db = FirebaseFirestore.getInstance();
        sta_logout=findViewById(R.id.sta_logout);
        mAuth=FirebaseAuth.getInstance();
        save=findViewById(R.id.save);code=findViewById(R.id.code);title=findViewById(R.id.title);slot=findViewById(R.id.slot);
        String[] types={"Programme Core","Programme Elective","University Core","University Elective"};
        ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),R.layout.forspinner,types);
        editTextFilledExposedDropdown = findViewById(R.id.filled_exposed_dropdown);
        editTextFilledExposedDropdown.setAdapter(adapter);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValues();
            }
        });
        sta_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp=getSharedPreferences("sharedPrefs2", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("status","");
                editor.commit();
                Toast.makeText(Backend.this, "Signed Out", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
    }
    void setValues(){
        type=editTextFilledExposedDropdown.getText().toString();
        Course c=new Course(""+code.getText().toString(),""+title.getText().toString(),""+slot.getText().toString());
        db.collection(type).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                id=queryDocumentSnapshots.size()+1;
                db.collection(type).document(""+id).set(c);
                Toast.makeText(Backend.this, "Added", Toast.LENGTH_SHORT).show();
            }
        });
        code.setText("");title.setText("");slot.setText("");
    }
}
class Course{
    public String code,title,slot;
    Course(){}
    Course(String code,String title,String slot){
        this.code=code;this.title=title;this.slot=slot;
    }
}