package com.prasadthegreat.infosnity;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.ios.IosEmojiProvider;

public class EmojiLoader  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        EmojiManager.install(new IosEmojiProvider());
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
