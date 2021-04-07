package com.example.project_01;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class User_class extends Activity implements View.OnClickListener {
    FirebaseAuth uAuth;
    Button btn_logout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        //fire base
        uAuth=  FirebaseAuth.getInstance();
        init();
        btn_logout.setOnClickListener(this);



        //navbar
        BottomNavigationView BotNav = findViewById(R.id.btn_nav);
        BotNav.setSelectedItemId(R.id.user);
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

    private void init() {
        btn_logout=findViewById(R.id.btn_logout);
    }


    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_logout :
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setTitle("do you want to exit");
                    alertDialogBuilder
                            .setMessage("click yes to exit !")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    uAuth.signOut();
                                    Toast.makeText(getApplicationContext(),"Log out...",Toast.LENGTH_LONG).show();
                                    Intent intent = null;
                                    intent = new Intent(getApplicationContext(),Login_class.class);
                                    startActivity(intent);
                                    User_class.this.finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();

                                }

                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    break;
            }
        }
}



