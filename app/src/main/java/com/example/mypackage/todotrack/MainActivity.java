package com.example.mypackage.todotrack;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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

        firebaseauth = FirebaseAuth.getInstance();

        if (firebaseauth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }


        buttonRegister = findViewById(R.id.buttonRegister);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        textViewSignin = findViewById(R.id.textViewSignIn);
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

            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
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
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }else
                        {
                            Log.d("Ankita", task.toString());
                            Toast.makeText(MainActivity.this,"Could not register, please try again ",Toast.LENGTH_SHORT).show();
                        }
                    }


        });
    }
}
