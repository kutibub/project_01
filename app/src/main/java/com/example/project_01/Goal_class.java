package com.example.project_01;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Goal_class extends Activity  {
    Button gbtn_cal;
    Dialog dialog;
    EditText editach;
    EditText editmont;
    TextView txtcal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal);
        init();
        gbtn_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mon_string = editmont.getText().toString().trim();
                String ach_string = editach.getText().toString().trim();
                if (TextUtils.isEmpty(ach_string) && TextUtils.isEmpty(mon_string)){
                    Toast.makeText(getApplicationContext(),"please enter data !",Toast.LENGTH_SHORT).show();
                }
                else {
                    double achive, month, ans;
                    achive = Double.parseDouble(editach.getText().toString());
                    month = Double.parseDouble(editmont.getText().toString());
                    ans = (achive / (30 * month));
                    txtcal.setText((int) ans + "/ Day");
                }
            }
        });
        dialog= new Dialog(this);
        BottomNavigationView BotNav = findViewById(R.id.btn_nav);
        BotNav.setSelectedItemId(R.id.goal);
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

    }

    public void init() {

        gbtn_cal = findViewById(R.id.gbtn_cal);
        editmont = findViewById(R.id.gedit_month);
        editach = findViewById(R.id.gedit_ach);
        txtcal = findViewById(R.id.text_cal);
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
                startActivity(intent);
                Goal_class.this.finish();
            }
        });
        dialog.show();

    }
}