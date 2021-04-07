package com.example.project_01;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.project_01.Model.Data;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Income_class extends Activity implements NavigationView.OnNavigationItemSelectedListener {
    TextView textViewDate;
    Dialog dialog;
    Button btn_Income_detail,btn_Expense_detail;

    //Floating btn
    private FloatingActionButton fb_main_btn,fb_income_btn,fb_expense_btn;
    private TextView fb_income_txt,fb_expense_txt;
    private boolean isOpen=false;
    //Anim
   private Animation fadeopen,fadeclose;
    // Firebase data
    private FirebaseAuth IAuth;
    private DatabaseReference IIncomedb;
    private  DatabaseReference IExpensedb;

    //Total
    TextView total_income,total_expense,total_amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income);
        Calendar calendar =  Calendar.getInstance();
        SimpleDateFormat format= new SimpleDateFormat("dd/MM/yyyy");
        String date = "Today : "+format.format(calendar.getTime());
        dialog = new Dialog(this);
        textViewDate = findViewById(R.id.txt_date);
        textViewDate.setText(date);
        //income_expense detail
        btn_Income_detail = findViewById(R.id.btn_indetail);
        btn_Expense_detail = findViewById(R.id.btn_exdetail);
        btn_Income_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(getApplicationContext(), Income_detail_class_real.class);
                startActivity(intent);
                Income_class.this.finish();
            }
        });
        btn_Expense_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(getApplicationContext(),Expense_detail_class_real.class);
                startActivity(intent);
                Income_class.this.finish();
            }
        });

        //Floating btn
        fb_main_btn = findViewById(R.id.fb_main_plus);
        fb_income_btn = findViewById(R.id.income_ft_btn);
        fb_expense_btn = findViewById(R.id.expense_ft_btn);
        fb_income_txt = findViewById(R.id.income_ft_txt);
        fb_expense_txt = findViewById(R.id.expense_ft_txt);

        //Anim
        fadeopen = AnimationUtils.loadAnimation(this,R.anim.fade_open);
        fadeclose = AnimationUtils.loadAnimation(this,R.anim.fade_close);

        //Database in_ex
        IAuth= FirebaseAuth.getInstance();
        FirebaseUser User = IAuth.getCurrentUser();
        String uid=User.getUid();

        IIncomedb= FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid);
        IExpensedb= FirebaseDatabase.getInstance().getReference().child("ExpenseData").child(uid);

        //total txt
        total_income = findViewById(R.id.total_income);
        total_expense = findViewById(R.id.total_expense);
        total_amount =findViewById(R.id.total_amount);



        fb_main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
                if (isOpen){
                        fb_income_btn.startAnimation(fadeclose);
                        fb_expense_btn.startAnimation(fadeclose);
                        fb_income_btn.setClickable(false);
                        fb_expense_btn.setClickable(false);

                        fb_income_txt.startAnimation(fadeclose);
                        fb_expense_txt.startAnimation(fadeclose);
                        fb_income_btn.setClickable(false);
                        fb_expense_btn.setClickable(false);
                        isOpen=false;
                }
                else {
                    fb_income_btn.startAnimation(fadeopen);
                    fb_expense_btn.startAnimation(fadeopen);
                    fb_income_btn.setClickable(true);
                    fb_expense_btn.setClickable(true);

                    fb_income_txt.startAnimation(fadeopen);
                    fb_expense_txt.startAnimation(fadeopen);
                    fb_income_btn.setClickable(true);
                    fb_expense_btn.setClickable(true);
                    isOpen=true;
                }
            }
        });
        BottomNavigationView BotNav = findViewById(R.id.btn_nav);
        BotNav.setSelectedItemId(R.id.in_ex);
        BotNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.goal:
                        startActivity(new Intent(getApplicationContext(), Goal_class.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.in_ex:
                        startActivity(new Intent(getApplicationContext(), Income_class.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.user:
                        startActivity(new Intent(getApplicationContext(), User_class.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
        //calculate total
        IIncomedb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int total_in = 0;
                for (DataSnapshot mysnap:snapshot.getChildren()){
                    Data data = mysnap.getValue(Data.class);
                    total_in += total_in+data.getAmount();
                    String sttotal_in = String.valueOf(total_in);
                    total_income.setText(sttotal_in);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        IExpensedb.addValueEventListener(new ValueEventListener() {
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private void addData(){

        //fb btn income
        fb_income_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incomeDataInsert();
            }
        });

        fb_expense_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    expenseDataInsert();
            }
        });
    }
    public void incomeDataInsert(){
        AlertDialog.Builder mydialog =new  AlertDialog.Builder(this);
        LayoutInflater inflater=LayoutInflater.from(this);
        View myview=inflater.inflate(R.layout.insert_data,null);
        mydialog.setView(myview);
        AlertDialog dialog = mydialog.create();
        dialog.setCancelable(false);
        EditText edtAmount = myview.findViewById(R.id.edt_amount);
        EditText edtdes = myview.findViewById(R.id.edt_des);
        //spinner
        Spinner type_spin = myview.findViewById(R.id.type_spin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.category_income, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_spin.setAdapter(adapter);

        type_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String type = type_spin.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button btncancel = myview.findViewById(R.id.ibtn_cancel);
        Button btnconfirm = myview.findViewById(R.id.ibtn_confirm);

        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount = edtAmount.getText().toString().trim();
                String des = edtdes.getText().toString().trim();
                String type = type_spin.getSelectedItem().toString();

                if (TextUtils.isEmpty(amount)){
                    edtAmount.setError("Required Amount ..");
                    return;
                }

                else {
                    int amout_int = Integer.parseInt(amount);
                    String id = IIncomedb.push().getKey();
                    String mDate = DateFormat.getDateInstance().format(new Date());
                    Data data=new Data(amout_int,type,des,id,mDate);
                    IIncomedb.child(id).setValue(data);
                    openconfirmdialog();
                }

            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animfloat();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void expenseDataInsert(){
        AlertDialog.Builder mydialog =new  AlertDialog.Builder(this);
        LayoutInflater inflater=LayoutInflater.from(this);
        View myview=inflater.inflate(R.layout.insert_data,null);
        mydialog.setView(myview);
        final AlertDialog dialog = mydialog.create();
        dialog.setCancelable(false);
        EditText edtAmount = myview.findViewById(R.id.edt_amount);
        EditText edtdes = myview.findViewById(R.id.edt_des);
        ///spinner
        Spinner type_spin = myview.findViewById(R.id.type_spin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.category_expense, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_spin.setAdapter(adapter);

        type_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String type = type_spin.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button btncancel = myview.findViewById(R.id.ibtn_cancel);
        Button btnconfirm = myview.findViewById(R.id.ibtn_confirm);

        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount = edtAmount.getText().toString().trim();
                String des = edtdes.getText().toString().trim();
                String type = type_spin.getSelectedItem().toString();

                if (TextUtils.isEmpty(amount)){
                    edtAmount.setError("Required Amount ..");
                    return;
                }
                else {
                    int amout_int = Integer.parseInt(amount);
                    String id = IExpensedb.push().getKey();
                    String mDate = DateFormat.getDateInstance().format(new Date());
                    Data data=new Data(amout_int,type,des,id,mDate);
                    IExpensedb.child(id).setValue(data);
                    openconfirmdialog();
                }

            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animfloat();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void openconfirmdialog() {
        dialog.setContentView(R.layout.confirmdialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btn_ok = dialog.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = null;
                intent = new Intent(getApplicationContext(),Income_class.class);
                Toast.makeText(getApplicationContext(),"save data successful !",Toast.LENGTH_SHORT).show();
                startActivity(intent);
                Income_class.this.finish();
            }
        });
        dialog.show();

    }
    private void animfloat(){
        if (isOpen){
            fb_income_btn.startAnimation(fadeclose);
            fb_expense_btn.startAnimation(fadeclose);
            fb_income_btn.setClickable(false);
            fb_expense_btn.setClickable(false);

            fb_income_txt.startAnimation(fadeclose);
            fb_expense_txt.startAnimation(fadeclose);
            fb_income_btn.setClickable(false);
            fb_expense_btn.setClickable(false);
            isOpen=false;
        }
        else {
            fb_income_btn.startAnimation(fadeopen);
            fb_expense_btn.startAnimation(fadeopen);
            fb_income_btn.setClickable(true);
            fb_expense_btn.setClickable(true);

            fb_income_txt.startAnimation(fadeopen);
            fb_expense_txt.startAnimation(fadeopen);
            fb_income_btn.setClickable(true);
            fb_expense_btn.setClickable(true);
            isOpen=true;
        }
    }
    }

