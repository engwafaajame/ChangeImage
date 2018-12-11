package com.ahamedads.myrecyclerdatabase;

/*
//package com.smarthome.isko.iskosmarthome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class
DeviceActivity extends AppCompatActivity {


    //    Recycler
    ArrayList<recyclerDevice> list;
    private RecyclerView mRecyclerView;

    //    getLocal
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String username;
    String clusterPosition;
    String clusterPosition_icon;



    //    read Db
    DatabaseReference dbDeviceName;
    DatabaseReference dbDeviceStatus;
    DatabaseReference dbElectricDevice;

    //    position
    String devicePosition;
    String devicePosition_icon;


    //    mqtt
    String savedMQTTHOST;
    String savedUSERNAME;
    String savedPASSWORD;
    String MQTTHOST;
    String USERNAME;
    String PASSWORD;
    String on = "ON";
    String off = "OFF";
    String val_topic;
    String val_message;
    String topic;


    //    topic
    String top_device;
    String sub_top_device;
    String sub_top_statElectric_device;

    //    vibrator
    Vibrator vibrator;
    Ringtone myRingtone;
    int timeVibrate = 100;

    //    device
    String deviceStatus;
    String serialNumber;
    String electricDevice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        getLocal();
        realtimeDb();
        // connectMqtt();
        btnAddDevice();

        readDeviceStatus();

//        readSerialNumber();

//        getSubscribe();

//        publish();

    }

    private void getLocal() {
        sharedPreferences = getSharedPreferences("localDb",0);
        editor = sharedPreferences.edit();

//        get Username & Cluster position
        username = sharedPreferences.getString("username","");
        clusterPosition = sharedPreferences.getString("clusterPosition","");
        clusterPosition_icon = sharedPreferences.getString("clusterPosition_icon","");
        Log.d("dekuz","DeviceActivity - cache username : " + username);
        Log.d("dekuz","DeviceActivity - cache clusterPosition : " + clusterPosition);
        Log.d("dekuz","DeviceActivity - cache clusterPosition_icon : " + clusterPosition_icon);


//        get MQTT
        savedMQTTHOST = sharedPreferences.getString("savedMQTTHOST","");
        savedUSERNAME = sharedPreferences.getString("savedUSERNAME","");
        savedPASSWORD = sharedPreferences.getString("savedPASSWORD","");
        MQTTHOST = savedMQTTHOST;
        USERNAME = savedUSERNAME;
        PASSWORD = savedPASSWORD;
        Log.d("dekuz","DeviceActivity - MQTTHOST : "+MQTTHOST);
        Log.d("dekuz","DeviceActivity - USERNAME : "+USERNAME);
        Log.d("dekuz","DeviceActivity - PASSWORD : "+PASSWORD);

    }

    private void realtimeDb() {
        dbDeviceName = FirebaseDatabase.getInstance().getReference("USER/cluster/cluster/device");
        dbDeviceName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list = new ArrayList<recyclerDevice>();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                    recyclerDevice value = dataSnapshot1.getValue(recyclerDevice.class);
                    String DeviceName = value.getDeviceName();
                    String deviceStatus = value.getDeviceStatus();
                    Toast.makeText(DeviceActivity.this, ""+DeviceName, Toast.LENGTH_SHORT).show();
                    Toast.makeText(DeviceActivity.this, ""+deviceStatus, Toast.LENGTH_SHORT).show();


                    recyclerDevice recyclerDevice = new recyclerDevice();
                    recyclerDevice.setDeviceName(DeviceName);
                    recyclerDevice.setDeviceStatus(deviceStatus);
                    Toast.makeText(DeviceActivity.this, ""+DeviceName, Toast.LENGTH_SHORT).show();

                    list.add(recyclerDevice);
                    realtimeResponse();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Hello", "Failed to read value.", error.toException());
            }
        });
    }

    private void realtimeResponse() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerDeviceAdapter recyclerDeviceAdapter = new recyclerDeviceAdapter(list,DeviceActivity.this);
        RecyclerView.LayoutManager recyce = new GridLayoutManager(DeviceActivity.this,2);
        /// RecyclerView.LayoutManager recyce = new LinearLayoutManager(MainActivity.this);
        // recycle.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        mRecyclerView.setLayoutManager(recyce);
        mRecyclerView.setItemAnimator( new DefaultItemAnimator());
        mRecyclerView.setAdapter(recyclerDeviceAdapter);

        recyclerDeviceAdapter.setOnItemClickListener(new recyclerDeviceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int x = position + 1;
                String sPosition =  Integer.toString (x);
                editor.putString("devicePosition",sPosition);
                editor.commit();
                devicePosition = sharedPreferences.getString("devicePosition","");
                Log.d("dekuz","DeviceActivity - cache Position devicePosition onItem : "+devicePosition);

//                toDevice();
            }

            @Override
            public void onDeleteClick(int position) {
                int x = position + 1;
                String sPosition =  Integer.toString (x);
                editor.putString("devicePosition_icon",sPosition);
                editor.commit();
                devicePosition_icon = sharedPreferences.getString("devicePosition_icon","");
                Log.d("dekuz","DeviceActivity - cache Position devicePosition onDelete : "+devicePosition_icon);
            }
        });
    }



    private void readSerialNumber() {
        DatabaseReference dbSerialNumber = FirebaseDatabase.getInstance().getReference("USER/"+username+"/cluster/cluster/"+clusterPosition+"/device"+devicePosition+"serialNumber");
        dbSerialNumber.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String valdbSerialNumber = dataSnapshot.getValue(String.class);
                editor.putString("serialNumber",valdbSerialNumber);
                editor.commit();
                serialNumber = sharedPreferences.getString("serialNumber","");
                Log.d("dekuz","DeviceActivity - Read Serial Number : " + serialNumber);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private void readDeviceStatus() {
        dbDeviceStatus = FirebaseDatabase.getInstance().getReference("USER/"+username+"/cluster/cluster/"+clusterPosition+"/device"+devicePosition+"deviceStatus");
        dbDeviceStatus.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String valdbDeviceStatus = dataSnapshot.getValue(String.class);
                editor.putString("deviceStatus",valdbDeviceStatus);
                editor.commit();
                deviceStatus = sharedPreferences.getString("deviceStatus","");
                Log.d("dekuz","DeviceActivity - Read deviceStatus : " + deviceStatus);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }








    private void btnAddDevice() {
        ImageButton btnAddDevice = findViewById(R.id.btnAddDevice);
        btnAddDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                toAddDevice();







            }
        });
    }


    private void setvibrator() {
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        myRingtone = RingtoneManager.getRingtone(getApplicationContext(),uri);
    }

}*/

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeviceActivity extends AppCompatActivity {


    //    Recycler
    ArrayList<recyclerDevice> list;
    private RecyclerView mRecyclerView;

//    getLocal
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String username;
    String clusterPosition;
    String clusterPosition_icon;

//    read Db
    DatabaseReference dbDeviceName;
    private String userId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

       getLocal();


        readDeviceDb();

//        button to AddDevice
        ImageButton btnAddDevice = findViewById(R.id.btnAddDevice);
        btnAddDevice.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
//                toAddDevice();
            }
        });


    }






    private void getLocal() {
        sharedPreferences = getSharedPreferences("localDb",0);
        editor = sharedPreferences.edit();
        username = sharedPreferences.getString("username","");
        clusterPosition = sharedPreferences.getString("clusterPosition","");
        clusterPosition_icon = sharedPreferences.getString("clusterPosition_icon","");
        Log.d("dekuz","DeviceActivity - cache username : " + username);
        Log.d("dekuz","DeviceActivity - cache clusterPosition : " + clusterPosition);
        Log.d("dekuz","DeviceActivity - cache clusterPosition_icon : " + clusterPosition_icon);
    }



    private void readDeviceDb() {
        dbDeviceName = FirebaseDatabase.getInstance().getReference("test");
        dbDeviceName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                list = new ArrayList<recyclerDevice>();

                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    recyclerDevice value = dataSnapshot1.getValue(recyclerDevice.class);

                    recyclerDevice fire = new recyclerDevice();
                    String DeviceName = value.getDeviceName();

                    fire.setDeviceName(DeviceName);

                    list.add(fire);
                    Toast.makeText(DeviceActivity.this, ""+DeviceName, Toast.LENGTH_SHORT).show();

                    realtime();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Hello", "Failed to read value.", error.toException());
            }
        });
    }


    private void realtime() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerDeviceAdapter recyclerDeviceAdapter = new recyclerDeviceAdapter(list,DeviceActivity.this);
        RecyclerView.LayoutManager recyce = new GridLayoutManager(DeviceActivity.this,2);
        /// RecyclerView.LayoutManager recyce = new LinearLayoutManager(MainActivity.this);
        // recycle.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        mRecyclerView.setLayoutManager(recyce);
        mRecyclerView.setItemAnimator( new DefaultItemAnimator());
        mRecyclerView.setAdapter(recyclerDeviceAdapter);

        recyclerDeviceAdapter.setOnItemClickListener(new recyclerDeviceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

              // Toast.makeText(DeviceActivity.this, ""+list.get(position).getDeviceName()., Toast.LENGTH_SHORT).show();
                int x = position + 1;
                String sPosition =  Integer.toString (x);
                editor.putString("devicePosition",sPosition);
                editor.commit();
                String devicePosition = sharedPreferences.getString("devicePosition","");
                Log.d("dekuz","DeviceActivity - cache Position recyclerDeviceAdapter onItem : "+devicePosition);

//                toDevice();
            }

            @Override
            public void onDeleteClick(int position) {
                int x = position + 1;
                String sPosition =  Integer.toString (x);
                editor.putString("devicePosition_icon",sPosition);
                editor.commit();
                String devicePosition_icon = sharedPreferences.getString("devicePosition_icon","");
                Log.d("dekuz","DeviceActivity - cache Position recyclerDeviceAdapter onDelete : "+devicePosition_icon);
            }
        });
    }

//    private void toAddDevice() {
//        Intent intent = new Intent(this, AddDeviceActivity.class);
//        startActivity(intent);
//    }
}
