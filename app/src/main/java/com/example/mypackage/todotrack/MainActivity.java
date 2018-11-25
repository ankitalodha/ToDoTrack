package com.example.mypackage.todotrack;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;
    private FirebaseAuth firebaseauth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignIn);
        progressDialog = new ProgressDialog(this);
        firebaseauth = FirebaseAuth.getInstance();
        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == buttonRegister){
            registerUser();
        }

        if (view == textViewSignin){


        }

    }

    private void registerUser() {

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

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        firebaseauth.createUserWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){

                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Successfully registered ",Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Toast.makeText(MainActivity.this,"Could not register, please try again ",Toast.LENGTH_SHORT).show();
                        }
                    }


        });
    }
}
