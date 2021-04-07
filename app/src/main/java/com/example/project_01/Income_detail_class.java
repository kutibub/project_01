package com.example.project_01;

import android.app.Activity;
import android.content.Intent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Income_detail_class extends Activity  {
    //Database
    private FirebaseAuth IAuth;
    private DatabaseReference IIncomeDatabase;

    //Recycle view
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter adapter;
    Button btn_back;

    TextView income_total;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_detail);

        IAuth = FirebaseAuth.getInstance();
        FirebaseUser InUser = IAuth.getCurrentUser();
        String uid = InUser.getUid();
        IIncomeDatabase = FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid);
        income_total = findViewById(R.id.total_amount);
        recyclerView = findViewById(R.id.recycler_id_income);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        IIncomeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalvalue =0;
                for (DataSnapshot mysnapshot : snapshot.getChildren()) {
                    Data data = mysnapshot.getValue(Data.class);
                    totalvalue += totalvalue+data.getAmount();
                    String stTotal = String.valueOf(totalvalue);
                    income_total.setText(stTotal);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_back = findViewById(R.id.debtn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(getApplicationContext(), Income_class.class);
                startActivity(intent);
                Income_detail_class.this.finish();
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        ////ส่วนที่มีปัญหา
//        FirebaseRecyclerOptions<Data> options=
//                new FirebaseRecyclerOptions.Builder<Data>()
//                        .setQuery(IIncomeDatabase,Data.class)
//                        .setLifecycleOwner((LifecycleOwner) this)
//                        .build();
//
//        adapter = new FirebaseRecyclerAdapter<Data, com.example.project_01.MyViewHolder>(options) {
//
//            public com.example.project_01.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                return new com.example.project_01.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.income_data, parent, false));
//            }
//
//            protected void onBindViewHolder(com.example.project_01.MyViewHolder holder, int position, @NonNull Data model) {
//                holder.setAmount(model.getAmount());
//                holder.setType(model.getType());
//                holder.setDes(model.getDescription());
//                holder.setDate(model.getDate());
//            }
//        };
//        recyclerView.setAdapter(adapter);
//
//    }
}

    class MyViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        void setDate(String date) {
            TextView mDate = mView.findViewById(R.id.date_income_txt);
            mDate.setText(date);
        }

        void setType(String type) {
            TextView mType = mView.findViewById(R.id.type_income_txt);
            mType.setText(type);
        }

        void setDes(String des) {
            TextView mDes = mView.findViewById(R.id.des_income_txt);
            mDes.setText(des);
        }

        void setAmount(int amount) {
            TextView mAmmount = mView.findViewById(R.id.amount_income_txt);
            String stramount = String.valueOf(amount);
            mAmmount.setText(stramount);

        }
    }}




