package com.example.android_final_exam;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_final_exam.util.DatabaseHelper;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText addressET;
    Button btnAdd, btnView;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        addressET = findViewById(R.id.addressET);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String address1 = "";
                address1 = addressET.getText().toString().trim();

                if(address1.isEmpty()){
                    return;
                } else {
                        Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());

                    try {
                        List<Address> addresses = geocoder.getFromLocationName(address1,1);

                        if (addresses.size() > 0){
                            Address address = addresses.get(0);

                            Double latitude = address.getLatitude();
                            Double longitude = address.getLongitude();

                            if(databaseHelper.addLocation(latitude, longitude, address1)) {
                                alertBox("Data saved!");
                                Toast.makeText(MainActivity.this, "Location for "+ address1 +" is added", Toast.LENGTH_SHORT).show();
                            } else {
                                alertBox("Not saved!");
                                Toast.makeText(MainActivity.this, "Location is not added", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Sorry, Location not found.",Toast.LENGTH_LONG).show();
                        }
                    }
                    catch (IOException e) {
                        alertBox("catch box");
                        e.printStackTrace();
                    }
                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

    }

    public void alertBox(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Alert");
        builder.setMessage(message);

        builder.setCancelable(false);
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

}