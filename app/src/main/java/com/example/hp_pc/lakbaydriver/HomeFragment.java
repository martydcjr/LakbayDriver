package com.example.hp_pc.lakbaydriver;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.ghyeok.stickyswitch.widget.StickySwitch;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, RoutingListener{
//, GoogleApiClient.OnConnectionFailedListener
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }

    private static final String TAG = "MapsActivity";

    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15.5f;
    //    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
//            new LatLng(-40, -168), new LatLng(71, 136)
//    );
//
//    //widgets
//    private AutoCompleteTextView nsearchtext;
<<<<<<< HEAD
    private ImageView ngps, navigation;
//
=======
    private ImageView ngps;
    //
>>>>>>> 46a3564d66924a6905da2a01568b70320dadcc11
    public FirebaseAuth nAuth;
    public DatabaseReference userdata;
    public FirebaseAuth.AuthStateListener firebaseAuthListener;
    //    //vars
    private Boolean LocationPermissionGranted = false;
    private FusedLocationProviderClient mFusedProviderClient;
    //    private PlaceAutocompleteAdapter PlaceAutocompleteAdapter;
    private GoogleMap nmap;
    GoogleApiClient GoogleApiClient;
    Location LastLocation;
    LocationRequest LocationRequest;

    private int status = 0;

    private String customerID = "", destination;
    private LatLng destinationLatLng;
    private String firstName, lastName;
    private String driverFirstName, driverLastName;
    private String current_ride_id;
    private float rideDistance, ridePrice;
    private double priceToPay;
//    private String carType;

    private String total, price;
    private TextView name, phone, ndestination, tvPricetoPay, tvwaitPay, tvwaitingtext, tvwaitmethod, workingTv;
    private ImageView userImage;
    private RelativeLayout Info, WaitPay;
    private Button rideStatus, btnWaitPaid, btnbackpaypal;
    private ProgressBar pBar;
    Marker destinationMarker, car;


    private StickySwitch mWorkingSwitch;

    private Boolean isLoggingOut = false;

    Button confirm;
//    private PlaceInfo nPlace;
//    private LocationRequest mLocationRequest;


    public HomeFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        Log.d(TAG, "initMap: Initializing Map");

        polylines = new ArrayList<>();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
//        this.getActivity().requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
//        pBar.setVisibility(View.VISIBLE);

<<<<<<< HEAD



=======
>>>>>>> 46a3564d66924a6905da2a01568b70320dadcc11
        name = v.findViewById(R.id.tvName);
        phone = v.findViewById(R.id.tvPhone);
        ndestination = v.findViewById(R.id.tvDestination);
        userImage = v.findViewById(R.id.userImage);
        rideStatus = v.findViewById(R.id.rideStatus);
        Info = v.findViewById(R.id.switch1);
        workingTv = v.findViewById(R.id.workingTv);
        WaitPay = v.findViewById(R.id.pending1);
        tvPricetoPay = v.findViewById(R.id.tvPricetoPay);
        tvwaitPay = v.findViewById(R.id.tvwaitPay);
        btnWaitPaid = v.findViewById(R.id.btnWaitPaid);
        tvwaitingtext = v.findViewById(R.id.tvwaitingtext);
        tvwaitmethod = v.findViewById(R.id.tvwaitmethod);
        btnbackpaypal = v.findViewById(R.id.btnbackpaypal);

<<<<<<< HEAD
        ngps = v.findViewById(R.id.ic_gps);
        navigation = v.findViewById(R.id.btnNav);

        mWorkingSwitch = v.findViewById(R.id.workingSwitch);
        workingTv.setText("OFFLINE");


        if (customerID.isEmpty()){
            navigation.setVisibility(View.GONE);
        }


//        mWorkingSwitch.setOnCheckedChangeListener(new MaterialAnimatedSwitch.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(boolean isChecked) {
//                if (isChecked){
//                    connectDriver();
//                    workingTv.setText("ONLINE");
//                    Snackbar.make(getView(), "You are now ONLINE", Snackbar.LENGTH_SHORT).show();
//                }else {
//                    disconnectDriver();
//                    workingTv.setText("OFFLINE");
//                    Snackbar.make(getView(), "You are now OFFLINE", Snackbar.LENGTH_SHORT).show();
//                }
//            }
//        });

        mWorkingSwitch.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
=======
        btnWaitPaid = v.findViewById(R.id.btnWaitPaid);
        btnbackpaypal.setVisibility(View.GONE);

        mWorkingSwitch = v.findViewById(R.id.workingSwitch);
<<<<<<< HEAD
        workingTv.setText("OFFLINE");
        mWorkingSwitch.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
=======
        mWorkingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
>>>>>>> 46a3564d66924a6905da2a01568b70320dadcc11
>>>>>>> master
            @Override
            public void onSelectedChange(StickySwitch.Direction direction, String s) {
                if (s.equals("on")) {
                    connectDriver();
                    workingTv.setText("ONLINE");
                    Snackbar.make(getView(), "You are now ONLINE", Snackbar.LENGTH_SHORT).show();
                }
                if(s.equals("off")) {
                    disconnectDriver();
                    workingTv.setText("OFFLINE");
                    Snackbar.make(getView(), "You are now OFFLINE", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

//        pBar = v.findViewById(R.id.pBar);

//        confirm = v.findViewById(R.id.confirm);
//        v.findViewById(R.id.viewer).setVisibility(this);

//        Toast.makeText(getContext(), "HOmeact", Toast.LENGTH_SHORT).show();

//        nsearchtext = v.findViewById(R.id.input_search);
//        ngps = v.findViewById(R.id.ic_gps);
//

//        nsearchtext = v.findViewById(R.id.input_search);
<<<<<<< HEAD
//        ngps = v.findViewById(R.id.ic_gps);
=======

>>>>>>> master
        rideStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status){
                    case 1:
                        status = 2;
                        erasePolylines();
                        if (destinationLatLng.latitude != 0.0 &&destinationLatLng.longitude != 0.0){
                            getRouteToMarker(destinationLatLng);

                            destinationMarker = nmap.addMarker(new MarkerOptions()
                                    .position(destinationLatLng)
                                    .title("Destination"));

//                            navigation.setVisibility(View.VISIBLE);

                        }
                        rideStatus.setText("DRIVE COMPLETED");

                        break;

                    case 2:
                        recordRide();
                        endRide();

                        break;
                }
            }
        });

        ngps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked gps icon");
                getDeviceLocation();
            }
        });

        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    switch (status){
                        case 1:
                            double lat = pickupLatLng.latitude;
                            double lng = pickupLatLng.longitude;

                            String format = "geo:0,0?q=" + lat + "," + lng;

                            Uri uri = Uri.parse(format);
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            break;


                        case 2:
                            double lat2 = destinationLatLng.latitude;
                            double lng2 = destinationLatLng.longitude;

                            String format2 = "geo:0,0?q=" + lat2 + "," + lng2;

                            Uri uri2= Uri.parse(format2);
                            Intent intent2 = new Intent(Intent.ACTION_VIEW, uri2);
                            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent2);
                            break;
                    }


            }
        });


        getLocationPermission();
//        getDeviceLocation();

        getAssignedCustomer();

        return v;
    }


    private void endRide(){
        rideStatus.setText("PICK UP CUSTOMER");
        erasePolylines();

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference driverRef = FirebaseDatabase.getInstance().getReference().child("drivers").child(userId).child("client_request");
        driverRef.removeValue();

        DatabaseReference userdata = FirebaseDatabase.getInstance().getReference("clients_request");
        GeoFire geoFire = new GeoFire(userdata);
        geoFire.removeLocation(customerID);
        customerID = "";
        rideDistance = 0;

        if (pickupMarker != null){
            pickupMarker.remove();
        }
        if (destinationMarker != null){
            destinationMarker.remove();
        }
        if (assignedCustomerPickupLocationRefListener != null){
            assignedCustomerPickupLocationRef.removeEventListener(assignedCustomerPickupLocationRefListener);
        }
        Info.setVisibility(View.GONE);
        name.setText("");
        phone.setText("");
        ndestination.setText("Destination: ");
        userImage.setImageResource(R.mipmap.default_user);

    }

    private void recordRide(){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference driverRef = FirebaseDatabase.getInstance().getReference().child("drivers").child(userId).child("history");
        DatabaseReference customerRef = FirebaseDatabase.getInstance().getReference().child("clients").child(customerID).child("history");
        DatabaseReference historyRef = FirebaseDatabase.getInstance().getReference().child("history");


        String requestID = historyRef.push().getKey();
        driverRef.child(requestID).setValue(true);
        customerRef.child(requestID).setValue(true);

        DatabaseReference drivertype = FirebaseDatabase.getInstance().getReference().child("drivers").child(userId).child("car_type");
        drivertype.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String carType = dataSnapshot.getValue().toString();
                    if (carType == "single"){
                        float basePrice = 70;
                        float perKm = 10;
                        ridePrice = ((rideDistance*perKm)+basePrice);
                        total = String.format("%.2f", ridePrice);
                        price = total;
                    } else if (carType == "family"){
                        float basePrice = 110;
                        float perKm = 15;
                        ridePrice = ((rideDistance*perKm)+basePrice);
                        total = String.format("%.2f", ridePrice);
                        price = String.valueOf(total);
                    }
                    else if (carType == "barkada"){
                        float basePrice = 160;
                        float perKm = 30;
                        ridePrice = ((rideDistance*perKm)+basePrice);
                        total = String.format("%.2f", ridePrice);
                        price = String.valueOf(total);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        HashMap map = new HashMap();
        map.put("driver", userId);
        map.put("driver_name", driverFirstName +" "+ driverLastName);
        map.put("customer", customerID);
        map.put("customer_name", firstName +" "+ lastName);
        map.put("rating", 0);
        map.put("payment_status", "unpaid");
        map.put("timestamp", getCurrentTimestamp());
        map.put("destination", destination);
        map.put("location/from/lat", pickupLatLng.latitude);
        map.put("location/from/lng", pickupLatLng.longitude);
        map.put("location/to/lat", destinationLatLng.latitude);
        map.put("location/to/lng", destinationLatLng.longitude);
        map.put("distance", s);
        map.put("price", priceToPay);
        Toast.makeText(getContext(), "" + priceToPay, Toast.LENGTH_SHORT).show();

////        map.put("price", ridePrice);


        historyRef.child(requestID).updateChildren(map);

        HashMap paymentBox = new HashMap();
        paymentBox.put("d_ride_is_ended", "1");

        DatabaseReference setHistoryID = FirebaseDatabase.getInstance().getReference().child("clients").child(customerID).child("pay_in_pending");
        DatabaseReference setCurrentRideID = FirebaseDatabase.getInstance().getReference().child("clients").child(customerID).child("current_ride_id");

        HashMap setMap = new HashMap();
        setMap.put("status", "pending_payment");
        setMap.put("price", priceToPay);
        setMap.put("history_id", requestID);

        HashMap setCurrMap = new HashMap();

        setHistoryID.child(requestID).updateChildren(setMap);
        setCurrentRideID.setValue(requestID);

        current_ride_id = requestID;

        WaitPay.setVisibility(View.VISIBLE);
        tvwaitPay.setText(String.valueOf(priceToPay));

        waitForPayment();

        DatabaseReference pendingRef = FirebaseDatabase.getInstance().getReference().child("pending").child(userId).child("price_to_pay");

        pendingRef.updateChildren(paymentBox);

    }

    private void waitForPayment() {
        DatabaseReference getPayMethod = FirebaseDatabase.getInstance().getReference().child("clients").child(customerID).child("pay_in_pending").child(current_ride_id);

        getPayMethod.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("payment_method").exists()) {
                    String dpmethod = dataSnapshot.child("payment_method").getValue(String.class);
                    if(dpmethod.equals("Cash")) {
                        tvwaitmethod.setText("Cash");
                        tvwaitingtext.setText("waiting for payment from client...");
                        btnWaitPaid.setText("Payment Received");

                        confirmedPayPal(current_ride_id, dpmethod);
                    } else {
                        tvwaitmethod.setText("Paypal");
                        tvwaitingtext.setText("waiting for paypal confirmation...");
                        btnWaitPaid.setVisibility(View.GONE);
                        btnbackpaypal.setVisibility(View.GONE);

                        String pConf = dataSnapshot.child("payment_status").getValue(String.class);

                        if(pConf.equals("paid")) {
                            tvwaitmethod.setVisibility(View.GONE);
                            tvwaitingtext.setText("paid through paypal!");
                            btnbackpaypal.setVisibility(View.VISIBLE);
                            btnbackpaypal.setText("OK");

                            confirmedPayPal(current_ride_id, dpmethod);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnWaitPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference setPaid = FirebaseDatabase.getInstance().getReference().child("clients").child(customerID).child("pay_in_pending").child(current_ride_id);

                HashMap map = new HashMap();
                map.put("payment_status", "paid");

                setPaid.updateChildren(map);
            }
        });

        btnbackpaypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WaitPay.setVisibility(View.GONE);
            }
        });
    }

    private void confirmedPayPal(String current_ride_id, String dpmethod) {
        DatabaseReference setPaypalPaid = FirebaseDatabase.getInstance().getReference().child("history").child(current_ride_id);

        HashMap map = new HashMap();
        map.put("payment_status", "paid");
        map.put("payment_type", dpmethod);

        setPaypalPaid.updateChildren(map);
    }
//    DatabaseReference paypalConfirmRef;
//    ValueEventListener getConfirmationListener;
//    private void checkPaypalPaid() {
//        paypalConfirmRef = FirebaseDatabase.getInstance().getReference().child("clients").child(customerID).child("pay_in_pending").child(current_ride_id);
//
//        getConfirmationListener = paypalConfirmRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String pConf = dataSnapshot.child("payment_status").getValue(String.class);
//                if(pConf.equals("Paypal")) {
//                    tvwaitingtext.setText("paid through paypal!");
//                    btnbackpaypal.setVisibility(View.VISIBLE);
//                    btnbackpaypal.setText("<- Back");
//                } else {
//                    tvwaitmethod.setText("Paypal");
//                    tvwaitingtext.setText("waiting for paypal confirmation...");
//                    btnWaitPaid.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
////        paypalConfirm.addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(DataSnapshot dataSnapshot) {
////
////            }
////
////            @Override
////            public void onCancelled(DatabaseError databaseError) {
////
////            }
////        });
//
//        btnbackpaypal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                WaitPay.setVisibility(View.GONE);
//            }
//        });
//    }

    private Long getCurrentTimestamp() {
        Long timestamp = System.currentTimeMillis()/1000;
        return timestamp;
    }


    private void getAssignedCustomer(){
        String driverID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference assignedCustomerRef = FirebaseDatabase.getInstance().getReference().child("drivers").child(driverID).child("client_request").child("customerRideID");
        assignedCustomerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
//                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
//                    if (map.get("customerRideID") != null){
//                        customerID = map.get("customerRideID").toString();
                    Intent intent = new Intent(getContext(), RideAlert.class);
                    startActivity(intent);

                    status = 1;
                    customerID = dataSnapshot.getValue().toString();
                    getAssignedCustomerPickupLocation();
                    getAssignedCustomerInfo();
                    getDriverInfo();
                    getAssignedCustomerDestination();
<<<<<<< HEAD
                    navigation.setVisibility(View.VISIBLE);
=======
>>>>>>> 46a3564d66924a6905da2a01568b70320dadcc11
//                    }
                } else {
                    endRide();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getDriverInfo(){
        String driverID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userdata = FirebaseDatabase.getInstance().getReference().child("drivers").child(driverID);
        userdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
//                    pBar.setVisibility(View.GONE);
                    Info.setVisibility(View.VISIBLE);
                    if (map.get("user_firstname") != null && map.get("user_lastname") != null){
                        driverFirstName = map.get("user_firstname").toString();
                        driverLastName = map.get("user_lastname").toString();

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference ridePendingPrice = FirebaseDatabase.getInstance().getReference().child("pending").child(driverID).child("price_to_pay");

        ridePendingPrice.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String pricetoPay = dataSnapshot.child("c_ride_price").getValue().toString();

                    tvPricetoPay.setText("Price: " + pricetoPay);

                    priceToPay = Double.parseDouble(pricetoPay);
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
//                    pBar.setVisibility(View.GONE);
                    Info.setVisibility(View.VISIBLE);
                    if (map.get("user_firstname") != null && map.get("user_lastname") != null){
                        firstName = map.get("user_firstname").toString();
                        lastName = map.get("user_lastname").toString();
                        name.setText(firstName + " " + lastName);
                    }
                    if (map.get("user_mobile") != null){
                        phone.setText(map.get("user_mobile").toString());
                    }
                    if (map.get("profile_image_url") != null){
                        Glide.with(getActivity().getApplication()).load(map.get("profile_image_url").toString()).into(userImage);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getAssignedCustomerDestination(){
        String driverID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference assignedCustomerRef = FirebaseDatabase.getInstance().getReference().child("drivers").child(driverID).child("client_request");
        assignedCustomerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if (map.get("destination") != null){
                        destination = map.get("destination").toString();
                        ndestination.setText("Destination: " + destination);
                    } else {
                        ndestination.setText("Destination: ");
                    }

                    Double destinationLat = 0.0;
                    Double destinationLng = 0.0;

                    if (map.get("destinationLat") != null){
                        destinationLat = Double.valueOf(map.get("destinationLat").toString());
                    }
                    if (map.get("destinationLng") != null){
                        destinationLng = Double.valueOf(map.get("destinationLng").toString());
                        destinationLatLng = new LatLng(destinationLat, destinationLng);
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    LatLng pickupLatLng;
    Marker pickupMarker;
    private DatabaseReference assignedCustomerPickupLocationRef;
    private ValueEventListener assignedCustomerPickupLocationRefListener;
    private void getAssignedCustomerPickupLocation(){
        assignedCustomerPickupLocationRef = FirebaseDatabase.getInstance().getReference().child("clients_request").child(customerID).child("l");
        assignedCustomerPickupLocationRefListener = assignedCustomerPickupLocationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && !customerID.equals("")){
                    List<Object> map = (List<Object>) dataSnapshot.getValue();
                    double locationLat = 0;
                    double locationLng = 0;
                    if (map.get(0) != null){
                        Object latObject = map.get(0);
                        locationLat = Double.parseDouble(latObject.toString());
//                        locationLat = Double.parseDouble(map.get(0).toString());
                    }
                    if (map.get(1) != null){
                        Object lngObject = map.get(1);
                        locationLng = Double.parseDouble(lngObject.toString());
//                        locationLng = Double.parseDouble(map.get(0).toString());
                    }
                    pickupLatLng = new LatLng(locationLat, locationLng);
                    if(pickupMarker != null){
                        pickupMarker.remove();
                    }
                    pickupMarker = nmap.addMarker(new MarkerOptions()
                            .position(pickupLatLng)
                            .title("Pickup Location")
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_pickup)));

                    getRouteToMarker(pickupLatLng);

//                    clientMarker = nmap.addMarker(new MarkerOptions()
//                            .position(clientLatLng)
//                            .title("Pickup Location"));
//                    nmap.addMarker(new MarkerOptions().position(clientLatLng).title("Pickup Location"));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getRouteToMarker(LatLng pickupLatLng) {
        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(false)
                .waypoints(new LatLng(LastLocation.getLatitude(), LastLocation.getLongitude()), pickupLatLng)
                .build();
        routing.execute();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        Toast.makeText(getContext(), "Map is ready", Toast.LENGTH_SHORT).show();
//        Log.d(TAG, "onMapReady: Map is Ready");
        nmap = googleMap;
//
//        if (mLocationPermissionGranted) {
//            getDeviceLocation();

<<<<<<< HEAD
=======
<<<<<<< HEAD
            if (ActivityCompat.checkSelfPermission(getActivity(), FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(), COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
               return;
            }
            buildGoogleApiClient();
            nmap.setMyLocationEnabled(true);
            nmap.getUiSettings().setMyLocationButtonEnabled(false);
<<<<<<< HEAD
                nmap.getUiSettings().setMapToolbarEnabled(false);
=======
            nmap.getUiSettings().setMapToolbarEnabled(false);
=======
>>>>>>> master
        if (ActivityCompat.checkSelfPermission(getActivity(), FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        buildGoogleApiClient();
        nmap.setMyLocationEnabled(true);
        nmap.getUiSettings().setMyLocationButtonEnabled(false);
<<<<<<< HEAD
=======
>>>>>>> jerald-branch
>>>>>>> 46a3564d66924a6905da2a01568b70320dadcc11
>>>>>>> master
//            map.getUiSettings().setCompassEnabled(true);

//            init();

//        }


    }

    protected synchronized void buildGoogleApiClient(){
        GoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        GoogleApiClient.connect();
    }

    private String s;

    @Override
    public void onLocationChanged(Location location) {
        if (getContext() != null){

            if (!customerID.equals("")){
//                rideDistance += LastLocation.distanceTo(location)/1000;

                Location loc1 = new Location("");
                loc1.setLatitude(pickupLatLng.latitude);
                loc1.setLongitude(pickupLatLng.longitude);

                Location loc2 = new Location("");
                loc2.setLatitude(destinationLatLng.latitude);
                loc2.setLongitude(destinationLatLng.longitude);

                float adistance = loc1.distanceTo(loc2)/1000;
//                s = String.format("%.2f", distance);

                rideDistance = adistance;
            }

            LastLocation = location;

//        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
//        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            moveCamera(new LatLng(location.getLatitude(), location.getLongitude()),
                    DEFAULT_ZOOM,
                    "My Location");

            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference refAvailable = FirebaseDatabase.getInstance().getReference("drivers_available");
            DatabaseReference refWorking = FirebaseDatabase.getInstance().getReference("drivers_working");
            DatabaseReference refLocation = FirebaseDatabase.getInstance().getReference("drivers_location");
            GeoFire geoFireAvailable = new GeoFire(refAvailable);
            GeoFire geoFireWorking = new GeoFire(refWorking);
            GeoFire geoFireLocation = new GeoFire(refLocation);

            if (!customerID.isEmpty()){
                geoFireAvailable.removeLocation(userId);
                geoFireWorking.setLocation(userId, new GeoLocation(location.getLatitude(), location.getLongitude()));
                geoFireLocation.setLocation(userId, new GeoLocation(location.getLatitude(), location.getLongitude()));
            } else if (customerID.isEmpty()){
                if (mWorkingSwitch.getText().equals("off")){
                    geoFireAvailable.removeLocation(userId);
                    geoFireWorking.removeLocation(userId);
                    geoFireLocation.setLocation(userId, new GeoLocation(location.getLatitude(), location.getLongitude()));
                } else{
                    geoFireAvailable.setLocation(userId, new GeoLocation(location.getLatitude(), location.getLongitude()));
                    geoFireWorking.removeLocation(userId);
                    geoFireLocation.setLocation(userId, new GeoLocation(location.getLatitude(), location.getLongitude()));
                }
            }

//            switch (customerID){
//                case "":
//                    geoFireWorking.removeLocation(userId);
//                    geoFireAvailable.setLocation(userId, new GeoLocation(location.getLatitude(), location.getLongitude()));
//                    break;
//
//                default:
//                    geoFireAvailable.removeLocation(userId);
//                    geoFireWorking.setLocation(userId, new GeoLocation(location.getLatitude(), location.getLongitude()));
//                    break;
//            }

        }
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest = new LocationRequest();
        LocationRequest.setInterval(3000);
        LocationRequest.setFastestInterval(3000);
        LocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: Moving the Camera to Lat" + latLng.latitude + ", lng" + latLng.longitude);
//        nmap.setMinZoomPreference(5.5f);
        nmap.setMaxZoomPreference(19.0f);
        nmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
//
//        if (!title.equals("My Location")){
//            MarkerOptions options = new MarkerOptions()
//                    .position(latLng)
//                    .title(title)
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_end));
//
//
//            nmap.addMarker(options);
//        }
//        hideSoftKeyboard();
        if (car != null) {
            car.remove();
        }

        car = nmap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("My Location")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
                .anchor(0.5f, 0.5f)
                .flat(true));

        rotateMarker(car, -360, nmap);

    }
    private void rotateMarker(final Marker car, final float i, GoogleMap nmap) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final float startRotation = car.getRotation();
        final long duration = 1300;
        final Interpolator interpolator = new LinearInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float)elapsed/duration);
                float rot = t*i+(1-t)*startRotation;
                car.setRotation(-rot > 120 ? rot : rot);
                if (t<1.0){
                    handler.postDelayed(this, 16);
                }
            }
        });
    }



    private void hideSoftKeyboard(){
        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: Getting the Device Current Location");

        mFusedProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        try {
            if (LocationPermissionGranted) {

                final Task location = mFusedProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Log.d(TAG, "onComplete: found location!");
                            final Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM,
                                    "My Location");


                        } else {
                            Log.d(TAG, "onComplete: Current Location is Null");
                            FancyToast.makeText(getContext(), "Unable to get Current Location", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.d(TAG, "getDeviceLocation: Security Exception" + e.getMessage());
        }
    }
//
//    private void init(){
//        Log.d(TAG, "init: initializing");
//
//        GoogleApiClient = new GoogleApiClient
//                .Builder(getActivity())
//                .addApi(Places.GEO_DATA_API)
//                .addApi(Places.PLACE_DETECTION_API)
//                .enableAutoManage(getActivity(), this)
//                .build();
//
//        nsearchtext.setOnItemClickListener(nAutocompleteClickListener);
//
//        //COUNTRY FILTER
//        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder()
//                .setTypeFilter(Place.TYPE_COUNTRY)
//                .setCountry("PH")
//                .build();
//
//
//
//
//        PlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(getContext(),
//                GoogleApiClient, LAT_LNG_BOUNDS, autocompleteFilter);
//
//        nsearchtext.setAdapter(PlaceAutocompleteAdapter);
//
//        nsearchtext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
//                if(actionId == EditorInfo.IME_ACTION_SEARCH
//                        || actionId == EditorInfo.IME_ACTION_DONE
//                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
//                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER){
//
//                    //execute our method for searching
//
//                    geoLocate();
//                    hideSoftKeyboard();
//                }
//
//                return false;
//            }
//        });
//        ngps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: clicked gps icon");
//                getDeviceLocation();
//            }
//        });
//        hideSoftKeyboard();
//    }
//
//    private void geoLocate(){
//        Log.d(TAG, "geoLocate: geolocating");
//
//        String searchString = nsearchtext.getText().toString();
//
//        Geocoder geocoder = new Geocoder(getContext());
//        List<Address> list = new ArrayList<>();
//        try{
//            list = geocoder.getFromLocationName(searchString, 1);
//        }catch (IOException e){
//            Log.d(TAG, "geoLocate: IOException: " + e.getMessage());
//        }
//        if (list.size() > 0){
//            Address address = list.get(0);
//
//            Log.d(TAG, "geoLocate: found a location: " + address.toString());
//            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();
//            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
//                    address.getAddressLine(0));
//        }
//    }
//
//    private void getDeviceLocation() {
//        Log.d(TAG, "getDeviceLocation: Getting the Device Current Location");
//
//        mFusedProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
//
//        try {
//            if (mLocationPermissionGranted) {
//
//                Task location = mFusedProviderClient.getLastLocation();
//                location.addOnCompleteListener(new OnCompleteListener() {
//                    @Override
//                    public void onComplete(@NonNull Task task) {
//                        if (task.isSuccessful() && task.getResult() != null) {
//                            Log.d(TAG, "onComplete: found location!");
//                            currentLocation = (Location) task.getResult();
//
//                            mLocationRequest = new LocationRequest();
//                            mLocationRequest.setInterval(100);
//                            mLocationRequest.setFastestInterval(100);
//                            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
//                                    DEFAULT_ZOOM,
//                                    "My Location");
//
//                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                            DatabaseReference userdata = FirebaseDatabase.getInstance().getReference("drivers_available");
//
//                            GeoFire geoFire = new GeoFire(userdata);
//                            geoFire.setLocation(userId, new GeoLocation(currentLocation.getLatitude(), currentLocation.getLongitude()));
//
//                        } else {
//                            Log.d(TAG, "onComplete: Current Location is Null");
//                            Toast.makeText(getContext(), "Unable to get Current Location", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        } catch (SecurityException e) {
//            Log.d(TAG, "getDeviceLocation: Security Exception" + e.getMessage());
//        }
//    }
//


    private void initMap(){
        Log.d(TAG, "initMap: Initializing Map");
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: Getting Location Permissions");
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getActivity(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            if (ContextCompat.checkSelfPermission(this.getActivity(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                LocationPermissionGranted = true;
                initMap();
            }else {
                ActivityCompat.requestPermissions(getActivity(),
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }else {
            ActivityCompat.requestPermissions(getActivity(),
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: Called.");
        LocationPermissionGranted = false;
        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if (grantResults.length > 0){
                    for (int i = 0; i < grantResults.length; i++){
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            LocationPermissionGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: Permission Failed!");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: Permission Granted");
                    LocationPermissionGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }

    }

    private List<Polyline> polylines;
    private static final int[] COLORS = new int[]{R.color.colorAccent};

    @Override
    public void onRoutingFailure(RouteException e) {
        if(e != null) {
            FancyToast.makeText(getContext(), "Routing Failed", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
        }else {
            FancyToast.makeText(getContext(), "Something went wrong, Try again", FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
        }
    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int i) {

        if(polylines.size()>0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int e = 0; e <route.size(); e++) {

            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(15 + e * 3);
            polyOptions.addAll(route.get(e).getPoints());
            Polyline polyline = nmap.addPolyline(polyOptions);
            polylines.add(polyline);

            FancyToast.makeText(getContext(),"Route "+ (e+1) +", " + "distance: "+ route.get(e).getDistanceValue()/1000+"Km/s, " +
                            "duration: "+ route.get(e).getDurationValue()/60 + "Min/s",
                    FancyToast.LENGTH_LONG, FancyToast.INFO, false).show();
        }

    }

    @Override
    public void onRoutingCancelled() {

    }

    private void erasePolylines(){
        for (Polyline line : polylines){
            line.remove();
        }
        polylines.clear();
    }





    @Override
    public void onPause() {
        super.onPause();
//        GoogleApiClient.stopAutoManage(getActivity());
//        GoogleApiClient.disconnect();
//        nAuth.addAuthStateListener(firebaseAuthListener);
    }

//    private void disconnectDriver(){
//        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        DatabaseReference userdata = FirebaseDatabase.getInstance().getReference("drivers_available");
//
//        GeoFire geoFire = new GeoFire(userdata);
//        geoFire.removeLocation(userId);
//    }

//    @Override
//    public void onResume() {
//        super.onResume();
////        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
////        DatabaseReference userdata = FirebaseDatabase.getInstance().getReference("drivers_available");
////
////        GeoFire geoFireAvailable = new GeoFire(userdata);
////        geoFireAvailable.setLocation(userId, new GeoLocation(LastLocation.getLatitude(), LastLocation.getLongitude()));
//
//
//
//    }

    @Override
    public void onStop() {
        super.onStop();
        if (GoogleApiClient != null && GoogleApiClient.isConnected()) {
//            GoogleApiClient.stopAutoManage(getActivity());
            GoogleApiClient.disconnect();
//            nAuth.removeAuthStateListener(firebaseAuthListener);
        }



    }

    private void connectDriver(){
        if (ActivityCompat.checkSelfPermission(getContext(), FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(GoogleApiClient, LocationRequest, this);
    }


    public void disconnectDriver(){

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userdata = FirebaseDatabase.getInstance().getReference("drivers_available");

        GeoFire geoFire = new GeoFire(userdata);
        geoFire.removeLocation(userId);
    }



}














