package com.prasadthegreat.infosnity.students;

import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prasadthegreat.infosnity.R;


public class placementFragmentStudent extends Fragment {


    SwitchCompat mSwitch;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public placementFragmentStudent() {

    }


    public static placementFragmentStudent newInstance(String param1, String param2) {
        placementFragmentStudent fragment = new placementFragmentStudent();
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

        View view=inflater.inflate(R.layout.fragment_placementstudents, container, false);

        mSwitch=(SwitchCompat) view.findViewById(R.id.placementSwitch);
        mSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mSwitch.isChecked()){
                    getFragmentManager().beginTransaction().replace(R.id.placementcontainer,new placementdataFragment()).commit();
                }else{
                    getFragmentManager().beginTransaction().replace(R.id.placementcontainer,new nonplacementFragment()).commit();
                }
            }
        });

        return view;
    }
}