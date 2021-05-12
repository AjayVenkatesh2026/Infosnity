package com.prasadthegreat.infosnity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.prasadthegreat.infosnity.students.filesFragmentStudent;
import com.prasadthegreat.infosnity.students.homeFragmentStudent;
import com.prasadthegreat.infosnity.students.messageFragmentStudent;
import com.prasadthegreat.infosnity.students.placementFragmentStudent;
import com.prasadthegreat.infosnity.students.profileFragmentStudents;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView mbottomNavigationView;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        }

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

