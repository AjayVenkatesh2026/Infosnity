package com.prasadthegreat.infosnity.students;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideExtension;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.prasadthegreat.infosnity.R;
import com.prasadthegreat.infosnity.chatActivity;


public class messageFragmentStudent extends Fragment {


    RecyclerView mRecyclerview;
    studentmessageAdapter adapter;
    EditText mSearchbar;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public messageFragmentStudent() {

    }


    public static messageFragmentStudent newInstance(String param1, String param2) {
        messageFragmentStudent fragment = new messageFragmentStudent();
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
        View view=inflater.inflate(R.layout.fragment_messagestudents, container, false);

        mRecyclerview=(RecyclerView)view.findViewById(R.id.recyclerViewMessageStudent);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mSearchbar=(EditText)view.findViewById(R.id.searchMsgStudent);



        FirebaseRecyclerOptions<studentModel> options =
                new FirebaseRecyclerOptions.Builder<studentModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users"),studentModel.class)
                        .build();

        adapter=new studentmessageAdapter(options);
        mRecyclerview.setAdapter(adapter);


        mSearchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchtxt=mSearchbar.getText().toString();
                FirebaseRecyclerOptions<studentModel> options =
                        new FirebaseRecyclerOptions.Builder<studentModel>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("users").orderByChild("name").startAt(searchtxt).endAt(searchtxt+"\uf8ff"),studentModel.class)
                                .build();

                adapter=new studentmessageAdapter(options);
                adapter.startListening();
                mRecyclerview.setAdapter(adapter);

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchtxt=mSearchbar.getText().toString();
                FirebaseRecyclerOptions<studentModel> options =
                        new FirebaseRecyclerOptions.Builder<studentModel>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("users").orderByChild("name").startAt(searchtxt).endAt(searchtxt+"\uf8ff"),studentModel.class)
                                .build();

                adapter=new studentmessageAdapter(options);
                adapter.startListening();
                mRecyclerview.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String charSequence=editable.toString();
                FirebaseRecyclerOptions<studentModel> options =
                        new FirebaseRecyclerOptions.Builder<studentModel>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("users").orderByChild("name").startAt(charSequence).endAt(charSequence+"\uf8ff"),studentModel.class)
                                .build();

                adapter=new studentmessageAdapter(options);
                adapter.startListening();
                mRecyclerview.setAdapter(adapter);

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}

class studentmessageAdapter extends FirebaseRecyclerAdapter<studentModel,studentmessageAdapter.studentViewholder>
{


    public studentmessageAdapter(@NonNull FirebaseRecyclerOptions<studentModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull studentViewholder holder, int position, @NonNull studentModel model)
    {

        String data=model.getName().toString();
        holder.mTextview.setText(model.getName().toString());
       Glide.with(holder.mImg.getContext()).load(model.getProfilepic()).into(holder.mImg);


       holder.mTextview.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Context context=view.getContext();
               chat_method(data,context);
           }


       });

        holder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context=view.getContext();
                chat_method(data,context);
            }
        });

    }



    private void chat_method(String data,Context context) {
        Intent intent = new Intent(context, chatActivity.class);
        intent.putExtra("name",data);
        context.startActivity(intent);
    }


    @NonNull
    @Override
    public studentViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user_message,parent,false);
        return  new studentViewholder(view);
    }

    class studentViewholder extends RecyclerView.ViewHolder
    {
        ImageView mImg;
        TextView mTextview;
        public studentViewholder(@NonNull View itemView) {
            super(itemView);

            mImg=(ImageView)itemView.findViewById(R.id.img_message);
            mTextview=(TextView)itemView.findViewById(R.id.name_message);
        }
    }

}