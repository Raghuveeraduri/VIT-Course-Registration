package com.example.pract_da2;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CoursePage extends AppCompatActivity {
    public String option;private CoursePageAdapter adapter;
    ArrayList<CoursePageDataModel> dataList;
    FirebaseFirestore db;
    TextView coursecode;
    RecyclerView courseRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_page);
        courseRecycler=findViewById(R.id.courseRecycler);
        Bundle extras=getIntent().getExtras();
        option=extras.getString("selected");
        db=FirebaseFirestore.getInstance();
        courseRecycler.setHasFixedSize(true);
        courseRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        dataList=new ArrayList<CoursePageDataModel>();
        adapter=new CoursePageAdapter(this,dataList,option);
        courseRecycler.setAdapter(adapter);
        db.collection(""+option).addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error !=null){
                    Log.e("Firebase error",error.getMessage());
                    return;
                }
                for(DocumentChange dc: value.getDocumentChanges()){
                    if(dc.getType()== DocumentChange.Type.ADDED){
                        dataList.add(dc.getDocument().toObject(CoursePageDataModel.class));
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}