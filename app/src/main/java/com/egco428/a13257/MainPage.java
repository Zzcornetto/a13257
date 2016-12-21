package com.egco428.a13257;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class MainPage extends AppCompatActivity {
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference().child("users");
    //    ArrayList<String> myArrList = new ArrayList<String>();
    String[] arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("Main Page");
        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_back);
        TextView title=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        Button btn=(Button) findViewById(R.id.action_bar_title_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(MainPage.this)
                        .setTitle("Logout")
                        .setMessage("Do you really want to logout?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainPage.this,MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        title.setText("Main Page");

        mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    setContentView(R.layout.activity_main_page);

                    Log.d("Count ", "" + snapshot.getChildrenCount());
                    Log.d("Firebase", snapshot.toString());
                    arr = new String[(int) snapshot.getChildrenCount()];
                    int count = 0;
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Log.d("Firebase", postSnapshot.getKey() + " " + postSnapshot.getValue().toString());
//                        myArrList.add(postSnapshot.getKey());
                        arr[count] = postSnapshot.getKey();
                        count++;
                    }

                }
                // TODO: handle the case where the data already exists
                else {
                    Log.e("Snapshot: ", "Failed");
                    // TODO: handle the case where the data does not yet exist
                }
                Log.d("Firebase", "exit");
//                Log.d("Firebase",myArrList.toString());
                Log.d("Firebase", Arrays.toString(arr));

                ListView listView = (ListView) findViewById(R.id.listView1);
                CustomAdapter adapter = new CustomAdapter(getApplicationContext(), arr);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent
                            , View view, int position, long id) {
                        Object item = parent.getItemAtPosition(position);
                        String label = item.toString();
                        ListView listView = (ListView) view.getParent();

                        if (listView.isItemChecked(position)) {
                        } else {
                            Intent intent = new Intent(MainPage.this,MapsActivity.class);
                            intent.putExtra("user",label);
                            startActivity(intent);
                        }
                    }

                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}




