package com.prasadthegreat.infosnity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vanniktech.emoji.EmojiPopup;

public class chatActivity extends AppCompatActivity {

    ImageView mEmoji;
    TextView mName;
    EditText mMsgBox;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        String name=getIntent().getExtras().getString("name");
        mName=(TextView)findViewById(R.id.name);
        mEmoji=(ImageView)findViewById(R.id.emojiImg);
        mMsgBox=(EditText)findViewById(R.id.msgBox);
        linearLayout=(LinearLayout)findViewById(R.id.LinearLayout);
        mName.setText(name);


        EmojiPopup popup=EmojiPopup.Builder.fromRootView(findViewById(R.id.Root_Layout)).build(mMsgBox);

        mEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popup.toggle();
            }
        });

    }
}