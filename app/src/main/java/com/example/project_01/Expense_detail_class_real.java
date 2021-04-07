package com.example.project_01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project_01.Model.Data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Expense_detail_class_real extends Activity {
    private FirebaseAuth IAuth;
    private DatabaseReference ExpenseDatabase;

    //Recycle view
    private ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    Button btn_back;
    private TextView total_expense;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_detail_real);
        IAuth = FirebaseAuth.getInstance();
        FirebaseUser InUser = IAuth.getCurrentUser();
        String uid = InUser.getUid();
        ExpenseDatabase = FirebaseDatabase.getInstance().getReference("ExpenseData").child(uid);
        listView = findViewById(R.id.expense_list);
        total_expense = findViewById(R.id.total_expense);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        btn_back = findViewById(R.id.debtn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(getApplicationContext(), Income_class.class);
                startActivity(intent);
                Expense_detail_class_real.this.finish();
            }
        });
        ExpenseDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot datasnapshot, @Nullable String previousChildName) {
                String value = datasnapshot.getValue(Data.class).togetdata();
                arrayList.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ExpenseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int total_ex = 0;
                for (DataSnapshot mysnap:snapshot.getChildren()){
                    Data data = mysnap.getValue(Data.class);
                    total_ex += total_ex+data.getAmount();
                    String sttotal_ex = String.valueOf(total_ex);
                    total_expense.setText(sttotal_ex);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
