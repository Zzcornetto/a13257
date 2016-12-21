package com.egco428.a13257;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class SignupActivity extends AppCompatActivity implements SensorEventListener {
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference().child("users");

    long actualTime;
    EditText latitude, longtitude;
    String finalLatitude, finalLongtitude;
    Double rndlatitude, rndlongtitude;
    private long lastUpdate;
    private SensorManager sensorManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();

//        Intent intent = getIntent();
//        String data = intent.getStringExtra("user");
//
//        Log.d("user","aaa"+data);
    }


    public void randomClick(View view) {
        latitude = (EditText) findViewById(R.id.SignUpLat);
        longtitude = (EditText) findViewById(R.id.SignUpLong);

        rndlatitude = getRandomCoordinate(-85.000000, 85.000000);
        rndlongtitude = getRandomCoordinate(-179.999989, 179.999989);

        finalLatitude = String.format(Locale.ENGLISH, "%.6f", rndlatitude);
        finalLongtitude = String.format(Locale.ENGLISH, "%.6f", rndlongtitude);

        latitude.setText(finalLatitude);
        longtitude.setText(finalLongtitude);
    }

    public double getRandomCoordinate(Double from, Double to) {
        return Math.random() * ((from) - (to)) - from;
    }

    @Override
    public void onSensorChanged(final SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }
    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;

        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        actualTime = System.currentTimeMillis();

        if (accelationSquareRoot > 3) {
            if (actualTime - lastUpdate < 600) {
                return;
            }
            lastUpdate = actualTime;
            randomShake();
        }
    }

    public void randomShake(){
        latitude = (EditText) findViewById(R.id.SignUpLat);
        longtitude = (EditText) findViewById(R.id.SignUpLong);

        rndlatitude = getRandomCoordinate(-85.000000, 85.000000);
        rndlongtitude = getRandomCoordinate(-179.999989, 179.999989);

        finalLatitude = String.format(Locale.ENGLISH, "%.6f", rndlatitude);
        finalLongtitude = String.format(Locale.ENGLISH, "%.6f", rndlongtitude);

        latitude.setText(finalLatitude);
        longtitude.setText(finalLongtitude);
    }

    @Override
    public void onAccuracyChanged (Sensor sensor,int accuracy){

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener
                (this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        //will not get value from phone
    }
    public void addnewUser(View v){
        EditText user = (EditText) findViewById(R.id.signUpUsr);
        String username = user.getText().toString();
        EditText pass = (EditText) findViewById(R.id.signUpPass);
        String password = pass.getText().toString();
        EditText lat = (EditText) findViewById(R.id.SignUpLat);
        String latitude = lat.getText().toString();
        EditText lon = (EditText) findViewById(R.id.SignUpLong);
        String longtitude = lon.getText().toString();

        mRootRef.child(username).child("pass").setValue(password);
        mRootRef.child(username).child("lat").setValue(latitude);
        mRootRef.child(username).child("lon").setValue(longtitude);

        Log.d("Firebase",user.getText().toString());
        Intent intent = new Intent(SignupActivity.this,MainActivity.class);
        startActivity(intent);
    }
}



