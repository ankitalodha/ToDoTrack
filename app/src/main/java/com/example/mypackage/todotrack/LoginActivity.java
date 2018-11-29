package com.example.mypackage.todotrack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;
    private FirebaseAuth firebaseauth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseauth = FirebaseAuth.getInstance();

        if (firebaseauth.getCurrentUser()!= null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }


        buttonSignIn = findViewById(R.id.buttonSignin);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        textViewSignUp = findViewById(R.id.textViewSignUp);
        progressDialog = new ProgressDialog(this);

        buttonSignIn.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignIn){
            userLogin();
        }

        if (view == textViewSignUp){
            startActivity(new Intent(this,MainActivity.class));
        }
    }

    private void userLogin() {

        String email = editTextEmail.getText().toString().trim();
        String pwd = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){

            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();

            return;
        }

        if (TextUtils.isEmpty(pwd)){

            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();

            return;
        }

        progressDialog.setMessage("Logging in user...");
        progressDialog.show();

        firebaseauth.signInWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }else
                        {
                            Toast.makeText(LoginActivity.this,"Could not login, please try again ",Toast.LENGTH_SHORT).show();
                        }
                    }


                });

    }


}
