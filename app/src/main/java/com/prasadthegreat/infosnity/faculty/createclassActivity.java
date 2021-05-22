package com.prasadthegreat.infosnity.faculty;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prasadthegreat.infosnity.R;


import org.jetbrains.annotations.NotNull;

import java.util.Base64;
import java.util.HashMap;

public class createclassActivity extends AppCompatActivity {

    private Button createClass;
    private DatabaseReference ref;
    private DatabaseReference classReference;
    private EditText newClassName;
    private String id;
    private User currentUser;
    HashMap<String, Object> students = new HashMap<>();
    HashMap<String, Object> teachers = new HashMap<>();
    private String classId;
//    private String newClassId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createclass);

        createClass = findViewById(R.id.createClassButton);
        newClassName = findViewById(R.id.newClassName);

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        id=googleSignInAccount.getId();

        ref = FirebaseDatabase.getInstance().getReference().child("users").child(id);
        classReference = FirebaseDatabase.getInstance().getReference().child("classes");

        readData(new MyCallback() {
            @Override
            public void addMemberToMap(HashMap<String, Object> hash, User user) {
                hash.put(user.getId(), user.getMail());
            }
        });

        createClass.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                classId = encrypt(newClassName.getText().toString() + currentUser.getMail());
                Log.d("encrypted value", classId);
                if(currentUser.getRole() != null && currentUser.getRole().equals("faculty")){
                    createNewClass(newClassName.getText().toString());
                    System.out.println("You are not faculty");
                }
            }
        });
    }

    private void createNewClass(String className) {
        System.out.println("In create New Class Function");
        classReference.child(classId).child("admin").setValue(currentUser.getMail());
        classReference.child(classId).child("className").setValue(className);
        classReference.child(classId).child("adminId").setValue(id);
        classReference.child(classId).child("students").updateChildren(students, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull @NotNull DatabaseReference ref) {
                Log.d("Upload Status", "In students");
                if(error == null)
                    Log.d("Upload Status", "Uploaded Students");
                else
                    Log.d("Error Message: ", error.getMessage());
            }
        });
        classReference.child(classId).child("teachers").updateChildren(teachers, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull @NotNull DatabaseReference ref) {
                if(error == null)
                    Log.d("Upload Status", "Uploaded Teachers");
                else
                    Log.d("Error Message",error.getMessage());
            }
        });

        HashMap<String, Object> createdClass = new HashMap<>();
        createdClass.put("classId", classId);
        createdClass.put("className", className);
        createdClass.put("admin", currentUser.getMail());
        createdClass.put("adminId", currentUser.getId());
        ref.child("createdClasses").child(classId).setValue(createdClass);
        startActivity(new Intent(getApplicationContext(), MainActivityfaculty.class));
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String encrypt(String value) {
        String result;
        try {
            result = Base64.getEncoder().encodeToString(value.getBytes());
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "nothing";
    }


    private interface MyCallback{
        void addMemberToMap(HashMap<String, Object> hash, User user);
    }

    public void readData(MyCallback myCallback){
        ref.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    currentUser = snapshot.getValue(User.class);
                    myCallback.addMemberToMap(students, currentUser);
                    myCallback.addMemberToMap(teachers, currentUser);
                }
            }
            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                Log.d("DatabaseError Message", error.getMessage());
            }
        });
    }

}