package com.mate.olabhau.fragments;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.mate.olabhau.R;

import java.util.Random;

public class HomeFragment extends Fragment implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final String LOGTAG = HomeFragment.class.getSimpleName();

    private ImageView callPoliceIV;
    private ImageView callAmbulanceIV;
    private Button askBT;
    private EditText queryET;
    private String queryText;
    private LinearLayout conversationLL;
    private Context context;

    private String[] congraulatoryWords = {"Hi", "Hey", "Wassup", "How re you", "what's going on"};

    private TextView fareTV, speedTV, distanceTV;


    /**
     * LocationManager related constants
     */

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private Location mLastLocation, mNewLocation;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;

    private LocationRequest mLocationRequest;

    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 1; // 10 meters

    //distance calculators
    private double kms = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        context = getActivity().getApplicationContext();

        initializeUI(view);


        // First we need to check availability of play services
        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();

            createLocationRequest();
        }

        return view;
    }

    /**
     * Initialize all UI widgets
     */
    private void initializeUI(View view){
        callPoliceIV = (ImageView)view.findViewById(R.id.callPoliceIV);
        callAmbulanceIV = (ImageView)view.findViewById(R.id.callAmbulanceIV);
        askBT = (Button)view.findViewById(R.id.askBT);
        queryET = (EditText)view.findViewById(R.id.queryET);
        conversationLL = (LinearLayout)view.findViewById(R.id.conversationLL);

        callPoliceIV.setOnClickListener(this);
        callAmbulanceIV.setOnClickListener(this);
        askBT.setOnClickListener(this);

        fareTV = (TextView)view.findViewById(R.id.fareTV);
        speedTV = (TextView)view.findViewById(R.id.speedTV);
        distanceTV = (TextView)view.findViewById(R.id.distanceTV);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.callPoliceIV:
                Log.d(LOGTAG, "callPoliceBT");

                break;
            case R.id.callAmbulanceIV:
                Log.d(LOGTAG, "callAmbulanceBT");

                break;
            case R.id.askBT:
                Log.d(LOGTAG, "askBT");

                queryText = queryET.getText().toString();

                if(queryText != null){
                    if(!queryText.isEmpty()){
                        //Do something with this query
                        Log.d(LOGTAG, queryText+"");

                        processQuery(queryText);

                        queryET.setText("");

                    }else{
                        Log.d(LOGTAG, "Query is empty");
                        Toast.makeText(context, "Query is empty", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Log.d(LOGTAG, "Query is null");
                }

                break;
        }
    }


    private void processQuery(String queryText){
        //Steps

        //Hi Ola, Ola bhau, Ola bro, hey ola
        //book, book a cab, book a taxi, get a taxi, get a cab

        addAChildView(queryText, "SENDER");

        String lcqueryText = queryText.toLowerCase();

        if(lcqueryText.contains("hi ola")
                || lcqueryText.contains("hey ola") || lcqueryText.contains("ola bhau") || lcqueryText.contains("ola bro")){
            if(lcqueryText.equals("hi ola")
                    || lcqueryText.equals("hey ola") || lcqueryText.equals("ola bhau") || lcqueryText.equals("ola bro")){

                addAChildView(congraulatoryWords[new Random().nextInt(6)] + " Rahul", "OLABHAU");

                Log.d(LOGTAG, "Matched");

            }else{
                if((lcqueryText.contains("hi ola") && lcqueryText.contains("book a cab")) ||
                        (lcqueryText.contains("hi ola") && lcqueryText.contains("book a taxi"))
                        || (lcqueryText.contains("hi ola") && lcqueryText.contains("get a cab"))
                        || (lcqueryText.contains("hi ola") && lcqueryText.contains("get a taxi"))
                        || (lcqueryText.contains("hi ola") && lcqueryText.contains("book"))
                        || (lcqueryText.contains("hi ola") && lcqueryText.contains("take me"))
                        || (lcqueryText.contains("hi ola") && lcqueryText.contains("want to go"))

                        || (lcqueryText.contains("hey ola") && lcqueryText.contains("book a cab"))
                        || (lcqueryText.contains("hey ola") && lcqueryText.contains("book a taxi"))
                        || (lcqueryText.contains("hey ola") && lcqueryText.contains("get a cab"))
                        || (lcqueryText.contains("hey ola") && lcqueryText.contains("get a taxi"))
                        || (lcqueryText.contains("hey ola") && lcqueryText.contains("book"))
                        || (lcqueryText.contains("hey ola") && lcqueryText.contains("take me"))
                        || (lcqueryText.contains("hey ola") && lcqueryText.contains("want to go"))

                        || (lcqueryText.contains("ola bhau") && lcqueryText.contains("book a cab"))
                        || (lcqueryText.contains("ola bhau") && lcqueryText.contains("get a cab"))
                        || (lcqueryText.contains("ola bhau") && lcqueryText.contains("get a taxi"))
                        || (lcqueryText.contains("ola bhau") && lcqueryText.contains("book"))
                        || (lcqueryText.contains("ola bhau") && lcqueryText.contains("book a taxi"))
                        || (lcqueryText.contains("ola bhau") && lcqueryText.contains("take me"))
                        || (lcqueryText.contains("ola bhau") && lcqueryText.contains("want to go"))

                        || (lcqueryText.contains("ola bro") && lcqueryText.contains("book a cab"))
                        || (lcqueryText.contains("ola bro") && lcqueryText.contains("get a cab"))
                        || (lcqueryText.contains("ola bro") && lcqueryText.contains("get a taxi"))
                        || (lcqueryText.contains("ola bro") && lcqueryText.contains("book"))
                        || (lcqueryText.contains("ola bro") && lcqueryText.contains("book a taxi"))
                        || (lcqueryText.contains("ola bro") && lcqueryText.contains("take me"))
                        || (lcqueryText.contains("ola bro") && lcqueryText.contains("want to go"))){
                    //Identify location intent as well as other part
                    if(lcqueryText.contains("cab to") || lcqueryText.contains("taxi to")){

                        int index = lcqueryText.indexOf("to");
                        if(index < lcqueryText.length()+2){
                            //Extract destination from string
                            String destination = lcqueryText.substring(index+2, lcqueryText.length());

                            Log.d(LOGTAG, "Destination:"+destination);

                            addAChildView("We are booking a cab to " + destination, "OLABHAU");

                        }else{
                            //There is no destination included
                            Log.d(LOGTAG, "No destination included");
                            //Ask him to include a destination

                            addAChildView("Please select a drop-off location", "OLABHAU");

                        }
                    }else{
                        addAChildView("Please select a drop-off location", "OLABHAU");
                    }
                }
            }
        }else{

            //Identify intent of user
            if(lcqueryText.contains("book") || lcqueryText.contains("book a cab") || lcqueryText.contains("book a taxi")
                    || lcqueryText.contains("get a taxi") || lcqueryText.equals("get a cab") || lcqueryText.equals("want to go")
                    || lcqueryText.equals("take me")){

                //Identify location intent as well as other part
                if(lcqueryText.contains("cab to") || lcqueryText.contains("taxi to")){

                    int index = lcqueryText.indexOf("to");
                    if(index < lcqueryText.length()+2){
                        //Extract destination from string
                        String destination = lcqueryText.substring(index+2, lcqueryText.length());

                        Log.d(LOGTAG, "Destination:"+destination);

                        addAChildView("We are booking a cab to "+destination, "OLABHAU");

                    }else{
                        //There is no destination included
                        Log.d(LOGTAG, "No destination included");
                        //Ask him to include a destination

                        addAChildView("Please select a drop-off location", "OLABHAU");

                    }
                }else{
                    addAChildView("Please select a drop-off location", "OLABHAU");
                }
            }else{
                //SOme other query

                Log.d(LOGTAG, "Sorry, didn't get you!");

                addAChildView("Sorry, didn't get you!", "OLABHAU");

            }
        }
    }

    private void addAChildView(String text, String type){
        TextView childTV = new TextView(context);
        childTV.setText(text);
        childTV.setPadding(2, 5, 2, 5);
        childTV.setTextSize(20);
        if(type.equals("SENDER")){
            childTV.setTextColor(Color.DKGRAY);
            childTV.setPadding(200, 0, 0, 0);
        }else if(type.equals("OLABHAU")){
            childTV.setTextColor(Color.BLUE);
            childTV.setPadding(0, 0, 200, 0);
        }

        conversationLL.addView(childTV);
    }

    public static HomeFragment newInstance(String text) {
        HomeFragment f = new HomeFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPlayServices();

        // Resuming the periodic location updates
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Method to display the location on UI
     * */
    private void displayLocation(Location location) {

        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            Toast.makeText(context, "Location is :"+latitude+"-"+longitude, Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(context, "Location is null", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Location Related methods
     * @param bundle
     */
    /**
     * Method to verify google play services on the device
     * */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(context,
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
            }
            return false;
        }
        return true;
    }


    /**
     * Creating google api client object
     * */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Creating location request object
     * */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    /**
     * Starting the location updates
     * */
    protected void startLocationUpdates() {

        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, locationCallback, null);
    }

    /**
     * Stopping location updates
     */
    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, locationCallback);
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult result) {
            super.onLocationResult(result);
        }

        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability) {
            super.onLocationAvailability(locationAvailability);
        }
    };


    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(LOGTAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        mNewLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        // Once connected with google api, get the location
        displayLocation(mNewLocation);

        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        // Assign the new location
        mLastLocation = mNewLocation;

        mNewLocation = location;

        Log.e(LOGTAG, "Start_location: " + mLastLocation.getLatitude() + " , " + mLastLocation.getLongitude());
        Log.e(LOGTAG, "End Location: " + location.getLatitude() + " , " + location.getLongitude());

        kms = kms + (calculateDistance(mLastLocation.getLatitude(), mLastLocation.getLongitude(),
                mNewLocation.getLatitude(), mNewLocation.getLongitude()));

        //distance.setText(String.valueOf(kms));

        distanceTV.setText(String.valueOf(kms));

        Toast.makeText(context, "Location changed!",
                Toast.LENGTH_SHORT).show();

        // Displaying the new location on UI
        displayLocation(mNewLocation);
    }

    public static double calculateDistance(double startLatitude, double startLongitude, double endLatitude, double endLongitude) {
        float[] results = new float[3];
        Location.distanceBetween(startLatitude, startLongitude, endLatitude, endLongitude, results);
        return results[0];
    }

}
