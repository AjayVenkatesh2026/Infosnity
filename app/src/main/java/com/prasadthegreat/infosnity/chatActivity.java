package com.prasadthegreat.infosnity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class chatActivity extends AppCompatActivity {




    TextView mName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        String name=getIntent().getExtras().getString("name");
        mName=(TextView)findViewById(R.id.name);

        mName.setText(name);

    }
}