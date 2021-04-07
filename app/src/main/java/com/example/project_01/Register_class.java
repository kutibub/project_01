package com.example.project_01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register_class extends AppCompatActivity implements View.OnClickListener{
    EditText RName,RNick,RAge,REmail,RPass,RConpass;
    Button btn_cancel,btn_confirm;
    Dialog dialog;
    ProgressDialog process;

    //Firebase part
    private FirebaseAuth RAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        RAuth=FirebaseAuth.getInstance();
        init();
        btn_cancel.setOnClickListener(this);
        registration();
        dialog= new Dialog(this);
        process = new ProgressDialog(this);
    }

    private void registration() {
        RName = findViewById(R.id.edt_Name);
        RNick = findViewById(R.id.edt_nickname);
        RAge = findViewById(R.id.edt_age);
        REmail = findViewById(R.id.edt_email);
        RPass = findViewById(R.id.edt_pass);
        RConpass = findViewById(R.id.edt_conpass);
        btn_confirm = findViewById(R.id.rbtn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = REmail.getText().toString().trim();
                String pass = RPass.getText().toString().trim();
                String name = RName.getText().toString().trim();
                String nick = RNick.getText().toString().trim();
                String age = RAge.getText().toString().trim();
                String con = RConpass.getText().toString().trim();

                if (TextUtils.isEmpty(name)){
                    RName.setError("Name required");
                    return;
                }
                if (TextUtils.isEmpty(nick)){
                    RNick.setError("Nickname required");
                    return;
                }
                if (TextUtils.isEmpty(age)){
                    RAge.setError("Age required");
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    REmail.setError("E-mail required");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    RPass.setError("Password required");
                    return;
                }
                if (TextUtils.isEmpty(con)){
                    RConpass.setError("Please confirm password");
                    return;
                }
                if (!pass.equals(con)){
                    Toast.makeText(getApplicationContext(),"Please check confirm Password !",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    process.setMessage("Processing..");
                    process.show();

                    RAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                process.dismiss();
                                openconfirmdialog();
                            }
                            else {
                                process.dismiss();
                                Toast.makeText(getApplicationContext(),"Registration Failed ",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });
    }

    private void init() {
        btn_cancel=findViewById(R.id.rbtn_cancel);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.rbtn_cancel :
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("do you want to cancel");
            alertDialogBuilder
                    .setMessage("click yes to cancel !")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = null;
                            intent = new Intent(getApplicationContext(),Login_class.class);
                            Toast.makeText(getApplicationContext(),"cancel to sign up !",Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            Register_class.this.finish();
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
        }


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
                intent = new Intent(getApplicationContext(),Login_class.class);
                Toast.makeText(getApplicationContext(),"sign up successful !",Toast.LENGTH_SHORT).show();
                startActivity(intent);
                Register_class.this.finish();
            }
        });
        dialog.show();

    }
}