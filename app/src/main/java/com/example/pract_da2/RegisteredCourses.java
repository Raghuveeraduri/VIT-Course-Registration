package com.example.pract_da2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RegisteredCourses extends AppCompatActivity {
    FirebaseAuth mAuth;FirebaseUser user;FirebaseFirestore db;String userId;
    RecyclerView registeredRecycler;
    ArrayList<RegisteredDataModel> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_courses);
        registeredRecycler=findViewById(R.id.registeredRecycler);
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        data=new ArrayList<RegisteredDataModel>();
        RegisteredAdapter adapter=new RegisteredAdapter(this,data);
        registeredRecycler.setAdapter(adapter);
        registeredRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
        registeredRecycler.setHasFixedSize(true);
        db=FirebaseFirestore.getInstance();
        db.collection("Users").whereEqualTo("email",""+user.getEmail()).get().addOnSuccessListener(this,new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                    userId=documentSnapshot.getId();
                    break;
                }
                db.collection("Users").document(userId).collection("Registered Courses").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null){
                            Toast.makeText(RegisteredCourses.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                        for (DocumentChange dc:value.getDocumentChanges()){
                            if(dc.getType()== DocumentChange.Type.ADDED){
                                data.add(dc.getDocument().toObject(RegisteredDataModel.class));
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
    }
}