package com.example.hp_pc.lakbaydriver;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RideAlert extends AppCompatActivity {


    private String customerID = "";
    DatabaseReference userdata;

    private TextView eta, distance, destination;
    private CircleImageView userImage;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_alert);


        eta = findViewById(R.id.tvEta);
        distance = findViewById(R.id.tvDistance);
        destination = findViewById(R.id.tvPickup);
        userImage = findViewById(R.id.userImage);

        mediaPlayer = MediaPlayer.create(this,R.raw.ringtone);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        getAssignedCustomer();

    }

    private void getAssignedCustomer(){
        String driverID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference assignedCustomerRef = FirebaseDatabase.getInstance().getReference().child("drivers").child(driverID).child("client_request").child("customerRideID");
        assignedCustomerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    customerID = dataSnapshot.getValue().toString();
//                    getAssignedCustomerPickupLocation();
                    getAssignedCustomerInfo();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getAssignedCustomerInfo(){
        userdata = FirebaseDatabase.getInstance().getReference().child("clients").child(customerID);
        userdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if (map.get("profile_image_url") != null){
                        Glide.with(getApplication()).load(map.get("profile_image_url").toString()).into(userImage);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onStop() {
        mediaPlayer.release();
        super.onStop();
    }

    @Override
    protected void onPause() {
        mediaPlayer.release();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();

    }
}
