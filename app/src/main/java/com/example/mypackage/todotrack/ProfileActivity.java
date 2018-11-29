package com.example.mypackage.todotrack;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {


    private Button buttonLogout, buttonAddTask, buttonViewTask , buttonDeleteTask , buttonUpdateTask;
    private TextView textViewEmail;
    private FirebaseAuth firebaseauth;
    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;

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

        buttonLogout = findViewById(R.id.buttonLogout);
        buttonAddTask = findViewById(R.id.addTask);
        buttonViewTask = findViewById(R.id.readTask);
        buttonDeleteTask = findViewById(R.id.deleteTask);
        buttonUpdateTask = findViewById(R.id.updateTask);
        textViewEmail = findViewById(R.id.emailTextView);
        buttonLogout.setOnClickListener(this);
        buttonAddTask.setOnClickListener(this);
        buttonViewTask.setOnClickListener(this);
        buttonDeleteTask.setOnClickListener(this);
        buttonUpdateTask.setOnClickListener(this);
        //textViewEmail.setText("Welcome " + user.getEmail());
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        getLastLocation();

    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();
                            mLastLocation.getLatitude();
                            mLastLocation.getLongitude();

                            Toast.makeText(ProfileActivity.this, "Location " + mLastLocation.getLongitude() + " " + mLastLocation.getLatitude(), Toast.LENGTH_SHORT).show();
                            textViewEmail.setText(String.format(Locale.ENGLISH, "%s: %f",
                                    "Latitutde",
                                    mLastLocation.getLatitude()));
                        } else {
                            Toast.makeText(ProfileActivity.this, "Location " + mLastLocation.getLongitude() + " " + mLastLocation.getLatitude(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {

        if(view == buttonAddTask)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),AddTask.class));

        }

        if(view == buttonViewTask)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),ViewTask.class));

        }

        if (view == buttonLogout){
            firebaseauth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }



    }
}
