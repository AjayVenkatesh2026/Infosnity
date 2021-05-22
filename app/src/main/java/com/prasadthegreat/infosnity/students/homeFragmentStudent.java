package com.prasadthegreat.infosnity.students;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prasadthegreat.infosnity.MainActivity;
import com.prasadthegreat.infosnity.R;
import com.prasadthegreat.infosnity.faculty.Classroom;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class homeFragmentStudent extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private ArrayList<Classroom> classroomArrayList;
    private RecyclerView recyclerView;
    private DatabaseReference myRef;
    private String id = "";
    private com.prasadthegreat.infosnity.students.classRoomAdapter adapter;


    public homeFragmentStudent() {

    }


    public static homeFragmentStudent newInstance(String param1, String param2) {
        homeFragmentStudent fragment = new homeFragmentStudent();
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

        View view=inflater.inflate(R.layout.fragment_homestudents, container, false);

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getActivity().getApplicationContext());
        if(googleSignInAccount != null)
            id=googleSignInAccount.getId();

        TextView joinClass = view.findViewById(R.id.invitation);
        recyclerView =  view.findViewById(R.id.showJoinedClasses);
        myRef = FirebaseDatabase.getInstance().getReference().child("users").child(id);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        classroomArrayList = new ArrayList<>();


        joinClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), JoinClassActivity.class));
            }
        });

        clearAll();

        getData();

        return view;
    }

    private void getData() {

        myRef.child("joinedClasses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(classroomArrayList != null){
                    classroomArrayList.clear();
                }
                classroomArrayList = new ArrayList<>();
                for (DataSnapshot data: snapshot.getChildren()) {
                    Classroom newClass = new Classroom();
                    if(data.child("className").getValue().toString() != null)
                        newClass.setClassName(data.child("className").getValue().toString());
                    else
                        newClass.setClassName("none");
                    if(data.child("admin").getValue().toString() != null)
                        newClass.setAdmin(data.child("admin").getValue().toString());
                    else
                        newClass.setAdmin("none");

                    classroomArrayList.add(newClass);
                }

                adapter = new com.prasadthegreat.infosnity.students.classRoomAdapter(getContext(), classroomArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


    public void clearAll(){
        if(classroomArrayList != null){
            classroomArrayList.clear();
        }
        if(adapter != null){
            adapter.notifyDataSetChanged();
        }
        classroomArrayList = new ArrayList<>();
    }

}


class classRoomAdapter extends RecyclerView.Adapter<com.prasadthegreat.infosnity.students.classRoomAdapter.classViewHolder>{


    private Context mContext;
    private ArrayList<Classroom> classroomArrayList;

    public classRoomAdapter(Context mContext, ArrayList<Classroom> classroomArrayList) {
        this.mContext = mContext;
        this.classroomArrayList = classroomArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public com.prasadthegreat.infosnity.students.classRoomAdapter.classViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.classroom_layout, parent, false);
        return new com.prasadthegreat.infosnity.students.classRoomAdapter.classViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull com.prasadthegreat.infosnity.students.classRoomAdapter.classViewHolder holder, int position) {
        holder.mClassName.setText(classroomArrayList.get(position).getClassName());
        holder.mAdminName.setText(classroomArrayList.get(position).getAdmin());
    }

    @Override
    public int getItemCount() {
        return classroomArrayList.size();
    }

    public class classViewHolder extends RecyclerView.ViewHolder{
        TextView mClassName, mAdminName;

        public classViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mClassName = (TextView) itemView.findViewById(R.id.textClassName);
            mAdminName = (TextView) itemView.findViewById(R.id.textAdmin);
        }
    }
}