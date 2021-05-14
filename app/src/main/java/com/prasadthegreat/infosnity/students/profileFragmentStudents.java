package com.prasadthegreat.infosnity.students;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prasadthegreat.infosnity.R;
import com.prasadthegreat.infosnity.startingActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class profileFragmentStudents extends Fragment {


    Button mSignout;
    TextView mUsername;
    CircleImageView mUserpic;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public profileFragmentStudents() {

    }


    public static profileFragmentStudents newInstance(String param1, String param2) {
        profileFragmentStudents fragment = new profileFragmentStudents();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_profilestudents, container, false);

        mUsername=(TextView)view.findViewById(R.id.usernameStudent);
        mUserpic=(CircleImageView)view.findViewById(R.id.userProfileStudent);

        String id = GoogleSignIn.getLastSignedInAccount(getActivity()).getId();


        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("users").child(id);
        mSignout=(Button)view.findViewById(R.id.signOutbtnStudent);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                studentModel model=snapshot.getValue(studentModel.class);

                Glide.with(getContext()).load(model.getProfilepic()).into(mUserpic);
                mUsername.setText(model.getName());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                GoogleSignInOptions gso = new GoogleSignInOptions.
                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                        build();

                GoogleSignInClient googleSignInClient=GoogleSignIn.getClient(getActivity(),gso);
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            FirebaseAuth.getInstance().signOut();
                            Intent intent=new Intent(getContext(), startingActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }

                    }
                });

            }
        });


        return view;
    }
}