package com.prasadthegreat.infosnity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class signinActivity extends AppCompatActivity {


    GoogleSignInClient mSignInClient;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        firebaseAuth=FirebaseAuth.getInstance();

        progressBar = new ProgressDialog(this);
        progressBar.setTitle("Please Wait...");
        progressBar.setMessage("We are setting everything for you...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);



        GoogleSignInOptions signInOptions=new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("362881007423-nmcqcgv7jlu10rs35eviblu8ud4s8k09.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mSignInClient= GoogleSignIn.getClient(getApplicationContext(),signInOptions);

    }


    @Override
    protected void onStart() {
        super.onStart();

        Intent intent=mSignInClient.getSignInIntent();
        startActivityForResult(intent,100);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==100)
        {
            Task<GoogleSignInAccount> googleSignInAccountTask= GoogleSignIn
                    .getSignedInAccountFromIntent(data);

            if(googleSignInAccountTask.isSuccessful())
            {
                progressBar.show();
                try
                {
                    GoogleSignInAccount googleSignInAccount=googleSignInAccountTask.getResult(ApiException.class);

                    if(googleSignInAccount!=null)
                    {
                        AuthCredential authCredential= GoogleAuthProvider
                                .getCredential(googleSignInAccount.getIdToken(),null);

                        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference().child("users");
                                    HashMap<String,String> user_details=new HashMap<>();


                                    String id=googleSignInAccount.getId().toString();
                                    String name=googleSignInAccount.getDisplayName().toString();
                                    String mail=googleSignInAccount.getEmail().toString();
                                    String pic=googleSignInAccount.getPhotoUrl().toString();


                                    user_details.put("id",id);
                                    user_details.put("name",name);
                                    user_details.put("mail",mail);
                                    user_details.put("profilepic",pic);
                                    user_details.put("status","Hey,i am using Infosnity");
                                    user_details.put("mid",id);

                                    myRef.child(id).setValue(user_details).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){

                                                progressBar.cancel();
                                                Intent intent=new Intent(getApplicationContext(),roleActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }

                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}