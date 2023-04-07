package com.example.pract_da2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class CoursePageAdapter extends RecyclerView.Adapter<CoursePageAdapter.ViewHolder> {
    Context context;
    FirebaseFirestore db;
    FirebaseAuth mAuth;Boolean result;
    FirebaseUser user;String userId;String docId;
    ArrayList<CoursePageDataModel> data;
    String option;
    CoursePageAdapter(Context context,ArrayList<CoursePageDataModel> data,String option){
        this.context=context;this.data=data;this.option=option;
    }
    @NonNull
    @Override
    public CoursePageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.coursepage_item_design,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursePageAdapter.ViewHolder holder, int position) {
        CoursePageDataModel obj=data.get(position);
        holder.code.setText(obj.code);
        holder.title.setText(obj.title);
        holder.slot.setText(obj.slot);
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        db=FirebaseFirestore.getInstance();
        holder.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Users").whereEqualTo("email",user.getEmail()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                            userId=documentSnapshot.getId();
                            break;
                        }
                        HashMap<String,Object> course=new HashMap<>();
                        course.put("code",obj.code);
                        course.put("title",obj.title);
                        course.put("slot",obj.slot);
                        db.collection("Users").document(userId).collection("Registered Courses").whereEqualTo("slot",""+obj.slot).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                result=queryDocumentSnapshots.isEmpty();
                                if(result){
                                    db.collection("Users").document(userId).collection("Registered Courses").whereEqualTo("code",""+obj.code).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            result=queryDocumentSnapshots.isEmpty();
                                            if(result){
                                                db.collection("Users").document(userId).collection("Registered Courses").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                        docId= String.valueOf(queryDocumentSnapshots.size()+1);
                                                        db.collection("Users").document(userId).collection("Registered Courses").document(docId).set(course);
                                                    }
                                                });
                                            }
                                            else{
                                                Toast.makeText(context, "Already Registered", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                                else
                                    Toast.makeText(context, "Clash", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView code,title,slot;
        MaterialButton register;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            code=itemView.findViewById(R.id.code);
            title=itemView.findViewById(R.id.title);
            slot=itemView.findViewById(R.id.slot);
            register=itemView.findViewById(R.id.register);
        }
    }
}
