package com.egco428.a13257;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
//    Button cancelBtn = (Button) findViewById(R.id.CancelBtn);
DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    private Button cancelBtn;
    private Button signUp;
    EditText user, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("EGCO392: Assignment 1");
        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        TextView title=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        title.setText("EGCO392: Assignment 1");


        cancelBtn = (Button) findViewById(R.id.cancel_btn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = (EditText) findViewById(R.id.SignInUsr);
                password = (EditText) findViewById(R.id.SignInPass);
                user.setText("");
                password.setText("");

            }
        });
        signUp =(Button) findViewById(R.id.signUpBtn);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
    }

   public void SignIn(View v){
        user = (EditText) findViewById(R.id.SignInUsr);
        password = (EditText) findViewById(R.id.SignInPass);
//
       Log.d("Signin","pressed");
//
       DatabaseReference mUsersRef = mRootRef.child("users");

       Log.d("MainActivity",user.getText().toString());
       Log.d("MainActivity",password.getText().toString());
       mUsersRef.child(user.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot snapshot) {
               if (snapshot.exists()) {
                   Log.d("Snapshot: ",snapshot.toString());
                   String getPassword = (String) snapshot.child("pass").getValue();
                   if(password.getText().toString().equals(getPassword)){
                       Log.d("Snapshot: ", "Pass");
                       SharedPreferences userDetails = getSharedPreferences("userdetails", MODE_PRIVATE);
                       SharedPreferences.Editor edit = userDetails.edit();
                       edit.clear();
                       edit.putString("username", user.getText().toString().trim());
                       edit.commit();
                       Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                       Intent intents = new Intent(MainActivity.this, MainPage.class);
                       startActivity(intents);
                   }
                   else{
                       Toast.makeText(MainActivity.this, "Login fail", Toast.LENGTH_SHORT).show();
                   }
               }
               // TODO: handle the case where the data already exists
               else {
                   Log.d("Snapshot: ","Failed");
                   // TODO: handle the case where the data does not yet exist
               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {
           }
       });

   }
}
