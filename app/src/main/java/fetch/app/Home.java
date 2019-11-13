package fetch.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static fetch.app.R.id;
import static fetch.app.R.layout;

///Class

//Central location for everything in app. Opens when user logs in or if they are already logged in.
//Displays location for currently selected child/slider to choose a different child
//dropdown menu which allows access to other areas of app (log out, settings, edit family, etc)
//Child portrait links to profile page



public class Home extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    private MyRecyclerViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //NEEDS WORK SAMPLE BUTTON
        ImageButton button=findViewById(id.backBtnHome);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // data to populate the RecyclerView with
        ArrayList<Integer> imageBg = new ArrayList<>();
        imageBg.add(Color.BLACK);
        imageBg.add(Color.BLACK);
        imageBg.add(Color.BLACK);
        imageBg.add(Color.BLACK);
        imageBg.add(Color.BLACK);

        ArrayList<String> kidsNames = new ArrayList<>();
        kidsNames.add("Megan");
        kidsNames.add("Keith");
        kidsNames.add("Steve");
        kidsNames.add("Harry");
        kidsNames.add("Ben");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.kidsScroll);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        adapter = new MyRecyclerViewAdapter(this, imageBg, kidsNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " Profile " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }


    /**
     * This is the section where Google Maps is gonna sort itself out and all and link with the bottom section.
     */

    class Maps extends AppCompatActivity implements OnMapReadyCallback {

        //Declares a variable for Google Maps
        private GoogleMap mMap;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            //Sets the content view for the activity. there is a automatic intent setting in the Manifest which is why its
            setContentView(layout.activity_home);
            // Obtain the SupportMapFragment inside the XML of "activity home" and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    //Finds the id of the home_map in the XML design
                    .findFragmentById(id.home_map);
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
            //GOOGLE MAPS ACTIVITY
            LatLng dublin = new LatLng(+53, -6);
            mMap.addMarker(new MarkerOptions().position(dublin).title("Marker in Dublin"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(dublin));
        }


        class Dropdown extends Home implements MenuItem.OnMenuItemClickListener {

            Button showMenu;

            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_home);

                showMenu = findViewById(id.show_dropdown_menu);
                showMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupMenu dropDownMenu = new PopupMenu(getApplicationContext(), showMenu);
                        dropDownMenu.getMenuInflater().inflate(R.menu.drop_down_menu, dropDownMenu.getMenu());
                        showMenu.setText("DropDown Menu");
                        dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                Toast.makeText(getApplicationContext(), "You have clicked " + menuItem.getTitle(), Toast.LENGTH_LONG).show();
                                return true;
                            }
                        });
                        dropDownMenu.show();
                    }
                });
            }

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        }
    }
}