package com.prasadthegreat.infosnity.students;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prasadthegreat.infosnity.MainActivity;
import com.prasadthegreat.infosnity.R;
import com.prasadthegreat.infosnity.faculty.Classroom;
import com.prasadthegreat.infosnity.faculty.User;
import com.prasadthegreat.infosnity.faculty.createclassActivity;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class JoinClassActivity extends AppCompatActivity {

    private Button joinClass;
    private EditText classId;
    User currentUser;
    private DatabaseReference ref;
    private DatabaseReference classRef;
    String id;
    HashMap<String, Object> joinedClasses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_class);

        joinClass = findViewById(R.id.join);
        classId = findViewById(R.id.idClassId);

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        id=googleSignInAccount.getId();

        ref = FirebaseDatabase.getInstance().getReference().child("users").child(id);
        classRef = FirebaseDatabase.getInstance().getReference().child("classes");

        readData(new MyCallback() {
            @Override
            public void getCurrentUser(HashMap<String, Object> hash, User user) {

            }
        });


        joinClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String joinClassId = classId.getText().toString().trim();
                if(currentUser.getRole() != null && currentUser.getRole().equals("student")){
                    if(joinClassId != null)
                        joinClassWithId(joinClassId, currentUser);
                    else
                        System.out.println("classId is null");
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    finish();
                }
            }
        });
    }

    private void joinClassWithId(@NonNull String joinClassId, User currentUser) {

        classRef.child(joinClassId).child("students").child(currentUser.getId()).setValue(currentUser.getMail())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if(task.isSuccessful()){
                            System.out.println("Successfully joined class");

                            classRef.child(joinClassId).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        HashMap<String, Object> classToJoin = new HashMap<>();
                                        classToJoin.put("admin", snapshot.child("admin").getValue());
                                        classToJoin.put("classId", joinClassId);
                                        classToJoin.put("adminId", snapshot.child("adminId").getValue());
                                        classToJoin.put("className", snapshot.child("className").getValue());
                                        ref.child("joinedClasses").child(joinClassId).setValue(classToJoin);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                    System.out.println("Error uploading or retrieving data from firebase");
                                    System.out.println(error.getMessage());
                                }
                            });
                        }
                        else{
                            System.out.println("There is problem joining the class");
                        }
                    }
                });
    }


    private interface MyCallback{
        void getCurrentUser(HashMap<String, Object> hash, User user);
    }

    public void readData(MyCallback myCallback){
        ref.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    currentUser = snapshot.getValue(User.class);
//                    joinedClasses = snapshot.child("joinedClasses").getValue();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("DatabaseError Message", error.getMessage());
            }
        });

    }
}