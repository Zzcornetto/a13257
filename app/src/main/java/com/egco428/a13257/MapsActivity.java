package com.egco428.a13257;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.Objects;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    double latitude,longtitude;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        getSupportActionBar().setTitle("Main Page");
        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_back);
        TextView title=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        Button btn=(Button) findViewById(R.id.action_bar_title_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this,MainPage.class);
                startActivity(intent);
            }
        });
        Intent temp = getIntent();
        username = temp.getStringExtra("user");
        title.setText(username+"'s Location");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Intent intent = getIntent();
        username = intent.getStringExtra("user");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("users").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot i:dataSnapshot.getChildren()){
                        Log.d("MapsFirebase",i.getKey().toString());
                        if (Objects.equals(i.getKey().toString(), "lat")){
                            Log.d("MapsFirebase","lat "+i.getValue().toString());
                            latitude = Double.valueOf(i.getValue().toString());
                        }
                        if (Objects.equals(i.getKey().toString(), "lon"))
                        {
                            Log.d("MapsFirebase","lon "+i.getValue().toString());
                            longtitude =Double.valueOf(i.getValue().toString());
                        }
                    }
                    Log.d("Position out",latitude+" "+longtitude);
                    LatLng sydney = new LatLng(latitude, longtitude);
                    mMap.addMarker(new MarkerOptions().position(sydney).title(username));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        // Add a marker in Sydney and move the camera

    }



}
