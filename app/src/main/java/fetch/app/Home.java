package fetch.app;

/**
 * Matthew Byrne - x17138744 / Google API with Firebase, Carosel and Dropdown
 */

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * This is the meat and bones of the application. Our Dropdown, Maps API and Carosel vibes feature here.
 */

public class Home extends FragmentActivity implements MyRecyclerViewAdapter.ItemClickListener, OnMapReadyCallback {

    //Recycle View for Carosel
    MyRecyclerViewAdapter adapter;

    //Dropdown
    Spinner spinner;
    List<String> list = new ArrayList<>();
    ArrayAdapter<String> spinnerAdapter;

    //Google API
    public static final String TAG = Home.class.getSimpleName();
    public HashMap<String, Marker> mMarkers = new HashMap<>();
    public GoogleMap mMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.home_map);
        mapFragment.getMapAsync(this);
    }

    /**
     * The next sections of code comprise of Google Maps API. Auth to Firebase and using Strings.xml page finds credentials and then links with Firebase accordingly.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Authenticate with Firebase when the Google map is loaded
        mMap = googleMap;
        mMap.setMaxZoomPreference(18);
        loginToFirebase();
    }

    public void loginToFirebase() {
        String email = getString(R.string.firebase_email);
        String password = getString(R.string.firebase_password);
        // Authenticate with Firebase and subscribe to updates
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
                email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    subscribeToUpdates();
                    Log.d(TAG, "firebase auth success");
                } else {
                    Log.d(TAG, "firebase auth failed");
                }
            }
        });
    }


    public void subscribeToUpdates() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(getString(R.string.firebase_path));
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                setMarker(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                setMarker(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void setMarker(DataSnapshot dataSnapshot) {
        // When a location update is received, put or update
        // its value in mMarkers, which contains all the markers
        // for locations received, so that we can build the
        // boundaries required to show them all on the map at once
        String key = dataSnapshot.getKey();
        HashMap<String, Object> value = (HashMap<String, Object>) dataSnapshot.getValue();
        double lat = Double.parseDouble(value.get("latitude").toString());
        double lng = Double.parseDouble(value.get("longitude").toString());
        LatLng location = new LatLng(lat, lng);
        if (!mMarkers.containsKey(key)) {
            mMarkers.put(key, mMap.addMarker(new MarkerOptions().title(key).position(location)));
        } else {
            mMarkers.get(key).setPosition(location);
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : mMarkers.values()) {
            builder.include(marker.getPosition());
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 300));







        /**
         * This is the Spinner features or "Dropdown" to show "Add Child", "Add Parent" and customizable toggle settings.
         */

        //Define Spinner
        spinner = findViewById(R.id.spinner_dropdown);
        spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        //Applying Listener on Spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Adding items to list :
        list.add("Menu");
        list.add("Add Child");
        list.add("Add Parent");
        list.add("Settings");

        //Adding list to Adapter and Notify Spinner to change its content :
        spinnerAdapter.addAll(list);
        spinnerAdapter.notifyDataSetChanged();










        /**
         * This is the Carosel section. This rotates through profiles and then proceeds to click through the main profile with more information.
         */

        // data to populate the RecyclerView with
        ArrayList<Integer> imageBg = new ArrayList<>();
        imageBg.add(Color.WHITE);
        imageBg.add(Color.WHITE);
        imageBg.add(Color.WHITE);
        imageBg.add(Color.WHITE);
        imageBg.add(Color.WHITE);

        ArrayList<String> kidsNames = new ArrayList<>();
        kidsNames.add("Matt");
        kidsNames.add("Keith");
        kidsNames.add("Ben");
        kidsNames.add("Kearnz");

        // Set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.kidsScroll);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        adapter = new MyRecyclerViewAdapter(Home.this, imageBg, kidsNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * This is the click intent to proceed onto the selected Profile.
     */
    @Override
    public void onItemClick(View View, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " Profile! ", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
}

