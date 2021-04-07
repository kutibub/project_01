package com.example.project_01;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_01.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Expense_detail_class extends Activity {
    //Database
    private FirebaseAuth EAuth;
    private DatabaseReference ExpenseDatabase;

    //Recycle view
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter adapter;
    Button btn_back;

    TextView income_total;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_detail);

        EAuth = FirebaseAuth.getInstance();
        FirebaseUser InUser = EAuth.getCurrentUser();
        String uid = InUser.getUid();
        ExpenseDatabase = FirebaseDatabase.getInstance().getReference().child("ExpenseData").child(uid);

        recyclerView = findViewById(R.id.recycler_expense);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    }
    @Override
    public void onStart() {
        super.onStart();
//        FirebaseRecyclerOptions<Data> options=
//                new FirebaseRecyclerOptions.Builder<Data>()
//                        .setQuery(ExpenseDatabase,Data.class)
//                        .setLifecycleOwner((LifecycleOwner) this)
//                        .build();
//
//        adapter = new FirebaseRecyclerAdapter<Data, com.example.project_01.MyViewHolder>(options) {
//
//            public com.example.project_01.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                return new com.example.project_01.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_data, parent, false));
//            }
//
//            protected void onBindViewHolder(com.example.project_01.MyViewHolder holder, int position, @NonNull Data model) {
//
//            }
//        };
//        recyclerView.setAdapter(adapter);
    }
    }

