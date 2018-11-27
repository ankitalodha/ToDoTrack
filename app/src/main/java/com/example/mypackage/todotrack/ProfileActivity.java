package com.example.mypackage.todotrack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {


    private Button buttonLogout, buttonAddTask, buttonViewTask , buttonDeleteTask , buttonUpdateTask;
    private TextView textViewEmail;
    private FirebaseAuth firebaseauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        firebaseauth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseauth.getCurrentUser();
        if (firebaseauth.getCurrentUser()== null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonAddTask = (Button) findViewById(R.id.addTask);
        buttonViewTask = (Button) findViewById(R.id.readTask);
        buttonDeleteTask = (Button) findViewById(R.id.deleteTask);
        buttonUpdateTask = (Button) findViewById(R.id.deleteTask);
        textViewEmail = (TextView) findViewById(R.id.emailTextView);
        buttonLogout.setOnClickListener(this);
        textViewEmail.setText("Welcome " + user.getEmail());

    }

    @Override
    public void onClick(View view) {

        if(view == buttonAddTask)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),AddTask.class));

        }

        if (view == buttonLogout){
            firebaseauth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

    }
}
