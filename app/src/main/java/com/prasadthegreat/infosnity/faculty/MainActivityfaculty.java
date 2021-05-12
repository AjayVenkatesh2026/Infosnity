package com.prasadthegreat.infosnity.faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.prasadthegreat.infosnity.R;
import com.prasadthegreat.infosnity.students.filesFragmentStudent;
import com.prasadthegreat.infosnity.students.homeFragmentStudent;
import com.prasadthegreat.infosnity.students.messageFragmentStudent;
import com.prasadthegreat.infosnity.students.placementFragmentStudent;
import com.prasadthegreat.infosnity.students.profileFragmentStudents;

public class MainActivityfaculty extends AppCompatActivity {

    BottomNavigationView mbottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainfaculty);


        mbottomNavigationView=(BottomNavigationView)findViewById(R.id.bottomNavView_faculty);
        mbottomNavigationView.setBackground(null);
        Menu menuNav=mbottomNavigationView.getMenu();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new homeFragmentStudent()).commit();
        mbottomNavigationView.setOnNavigationItemSelectedListener(bottomnavMethod);
    }

    private  BottomNavigationView.OnNavigationItemSelectedListener bottomnavMethod=new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment fragment=null;
                    switch (item.getItemId()){

                        case R.id.home_faculty:
                            fragment=new homeFragmentfaculty();
                            break;
                        case R.id.dms_faculty:
                            fragment=new messageFragmentfaculty();
                            break;
                        case R.id.files_faculty:
                            fragment=new fileFragmentfaculty();
                            break;

                        case R.id.profile_faculty:
                            fragment=new profileFragmentfaculty();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_faculty,fragment).commit();
                    return true;
                }
            };
}

