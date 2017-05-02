package dev.linhnv.location2_2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//1
public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener{
    //2. khai bao anh xa view
    //chó linh ~~
    //ggrt
    //ko push

    private final String TAG = "LaurenTestApp";
    private TextView txtLatitude;
    private TextView txtLongtitude;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    //7
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseReference;
    private String android_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();

        //3
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    //4.

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {

        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    public void anhXa() {
        txtLatitude = (TextView) findViewById(R.id.txtLatitude);
        txtLongtitude = (TextView) findViewById(R.id.txtLongtitude);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //5
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(2000); //Update location every second
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //cho nay bi loi thi dung alt+enter de fix
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLastLocation != null){
            txtLatitude.setText("Latitude: "+ String.valueOf(mLastLocation.getLatitude()));
            txtLongtitude.setText("Longtitude: "+ String.valueOf(mLastLocation.getLongitude()));

            Log.d(TAG, String.valueOf(mLastLocation.getLatitude())+"---"+ String.valueOf(mLastLocation.getLongitude()));
            wirtePosition(android_id, txtLatitude.getText().toString(), txtLongtitude.getText().toString());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection fail " + connectionResult.getErrorCode());
    }
    //6
    public void onDisconnected(){
        Log.i(TAG, "Disconnected");
    }

    @Override
    public void onLocationChanged(Location location) {
        txtLatitude.setText("Latitude: "+ Double.toString(location.getLatitude()));
        txtLongtitude.setText("Longtitude: "+ Double.toString(location.getLongitude()));
        writeNewPosition(txtLatitude.toString(), txtLongtitude.toString());
    }
    //8.
    public void wirtePosition(String id, String latitude, String longtitude){
        Position position = new Position(latitude, longtitude);
        mDatabaseReference.child("position").child(id).setValue(position);
    }
    //9.
    public void writeNewPosition(String latitude, String longtitude){
        Position position = new Position(latitude, longtitude);
        mDatabaseReference = database.getReference(android_id);
        mDatabaseReference.setValue(position);
    }
}
