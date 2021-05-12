package com.prasadthegreat.infosnity.faculty;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prasadthegreat.infosnity.R;
import com.prasadthegreat.infosnity.students.homeFragmentStudent;

public class homeFragmentfaculty extends Fragment {

    TextView mCreateClasstxt;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public homeFragmentfaculty() {

    }

    public static homeFragmentfaculty newInstance(String param1, String param2) {
        homeFragmentfaculty fragment = new homeFragmentfaculty();
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

        View view= inflater.inflate(R.layout.fragment_home_faculty, container, false);

        mCreateClasstxt=(TextView)view.findViewById(R.id.createclassroomtxt);
        mCreateClasstxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),createclassActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}