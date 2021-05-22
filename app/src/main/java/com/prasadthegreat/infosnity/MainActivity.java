package com.prasadthegreat.infosnity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prasadthegreat.infosnity.faculty.MainActivityfaculty;
import com.prasadthegreat.infosnity.students.filesFragmentStudent;
import com.prasadthegreat.infosnity.students.homeFragmentStudent;
import com.prasadthegreat.infosnity.students.messageFragmentStudent;
import com.prasadthegreat.infosnity.students.placementFragmentStudent;
import com.prasadthegreat.infosnity.students.profileFragmentStudents;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView mbottomNavigationView;
    FirebaseAuth mAuth;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTheme(R.style.Infosnity);
        setContentView(R.layout.activity_mainstudents);

        mbottomNavigationView=(BottomNavigationView)findViewById(R.id.bottomNavView);
        mbottomNavigationView.setBackground(null);
        Menu menuNav=mbottomNavigationView.getMenu();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new homeFragmentStudent()).commit();
        mbottomNavigationView.setOnNavigationItemSelectedListener(bottomnavMethod);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser mUser =FirebaseAuth.getInstance().getCurrentUser();
        if(mUser==null){
            Intent intent=new Intent(MainActivity.this,startingActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            startActivity(new Intent(MainActivity.this, MainActivityfaculty.class));
            finish();
        }

//
//        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
//        String id=googleSignInAccount.getId();
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("users").child(id);
//
//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange( DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    role = snapshot.child("role").getValue(String.class);
//                    if(role.equals("faculty")){
//                        Intent intent=new Intent(getApplicationContext(), MainActivityfaculty.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intent);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled( DatabaseError error) {
////                Log.d("ValueListner", "Cancelled");
//            }
//        });

    }

    private  BottomNavigationView.OnNavigationItemSelectedListener bottomnavMethod=new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment fragment=null;
                    switch (item.getItemId()){

                        case R.id.home:
                            fragment=new homeFragmentStudent();
                            break;
                        case R.id.dms:
                            fragment=new messageFragmentStudent();
                            break;
                        case R.id.files:
                            fragment=new filesFragmentStudent();
                            break;
                        case R.id.placement:
                            fragment=new placementFragmentStudent();
                            break;
                        case R.id.profile:
                            fragment=new profileFragmentStudents();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
                    return true;
                }
            };
}

