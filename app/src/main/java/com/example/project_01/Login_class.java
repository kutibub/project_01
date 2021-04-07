package com.example.project_01;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_class extends Activity {
    EditText edt_email,edt_pw;
    Button btn_login;
    TextView btn_signup;
    ProgressDialog process;
    //firebase part
    private FirebaseAuth LAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        LAuth= FirebaseAuth.getInstance();
        //remember login
        if (LAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),Income_class.class));
            finish();
        }
        process = new ProgressDialog(this);
        loginDetails();

    }
    private void  loginDetails(){
        edt_email = findViewById(R.id.Ledt_mail);
        edt_pw = findViewById(R.id.Ledt_pw);
        btn_login = findViewById(R.id.Lbtn_login);
        btn_signup = findViewById(R.id.signup);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edt_email.getText().toString().trim();
                String pw =edt_pw.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    edt_email.setError("Email required ");
                    return;
                }
                if (TextUtils.isEmpty(pw)) {
                    edt_pw.setError("Password required ");
                    return;
                }
                process.setMessage("Processing..");
                process.show();
                LAuth.signInWithEmailAndPassword(email,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            process.dismiss();
                            startActivity(new Intent(getApplicationContext(),Income_class.class));
                            Toast.makeText(getApplicationContext(),"Login successful..",Toast.LENGTH_SHORT).show();

                        }
                        else {
                            process.dismiss();
                            Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(getApplicationContext(),Register_class.class);
                startActivity(intent);
                Login_class.this.finish();

            }
        });
    }
}
