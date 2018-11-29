package com.example.mypackage.todotrack;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTask extends AppCompatActivity {

    int PLACE_PICKER_REQUEST = 1;
    TextView setAddressView;
    Button buttonPlacePicker;
    EditText taskName , taskDescription;
    private FirebaseAuth firebaseauth;
    private FloatingActionButton addTaskButton;
    private DatabaseReference databaseReference;
    static double lat , lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        firebaseauth = FirebaseAuth.getInstance();
        if (firebaseauth.getCurrentUser()== null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();
        taskName = findViewById(R.id.taskName);
        taskDescription = findViewById(R.id.taskDesc);
        setAddressView = findViewById(R.id.setAddressView);
        addTaskButton = findViewById(R.id.floatingActionButton);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String taskNm = taskName.getText().toString().trim();
                String taskDesc = taskDescription.getText().toString().trim();
                String taskAddress = setAddressView.getText().toString().trim();
                FirebaseUser user = firebaseauth.getCurrentUser();
                firebaseauth = FirebaseAuth.getInstance();
                UserInformation userInformation = new UserInformation(taskNm,taskDesc,taskAddress,lat,lng);
                databaseReference.child(user.getUid()).setValue(userInformation);
                Toast.makeText(AddTask.this ,"Task added successfully!",Toast.LENGTH_LONG).show();

            }
        });
    }

    public void goPlacePicker(View view){

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try{

            startActivityForResult(builder.build(AddTask.this),PLACE_PICKER_REQUEST);
        }
        catch(GooglePlayServicesRepairableException e){
            e.printStackTrace();
        }catch(GooglePlayServicesNotAvailableException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){

        if(requestCode == PLACE_PICKER_REQUEST){
            if(resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(AddTask.this,data);
                setAddressView.setText(place.getAddress());
                lat = place.getLatLng().latitude;
                lng = place.getLatLng().longitude;

            }
        }
    }
}
