package fetch.app;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


//Central location for everything in app. Opens when user logs in or if they are already logged in.
//Displays location for currently selected child/slider to choose a different child
//dropdown menu which allows access to other areas of app (log out, settings, edit family, etc)
//Child portrait links to profile page
public class Home extends FragmentActivity implements OnMapReadyCallback {

    //GOOGLE MAPS ACTIVITY

    //Declares a variable for Google Maps
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Sets the content view for the activity. there is a automatic intent setting in the Manifest which is why its
        setContentView(R.layout.activity_home);
        // Obtain the SupportMapFragment inside the XML of "activity home" and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                //Finds the id of the home_map in the XML design
                .findFragmentById(R.id.home_map);
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

        // Add a marker in Dublin and move the camera around with a static mark. This will then be updated with real time information.
        // Longitude and Latititude
        LatLng dublin = new LatLng(+53, -6);
        mMap.addMarker(new MarkerOptions().position(dublin).title("Marker in Dublin"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(dublin));

        //GOOGLE MAPS ACTIVITY
    }
}
