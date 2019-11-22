package fetch.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class Home extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    //Carosel and Dropdown Declarations
    private MyRecyclerViewAdapter adapter;
    Spinner spinner;
    List<String> list  = new ArrayList<>();
    ArrayAdapter<String> spinnerAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Define spinner
        spinner=(Spinner) findViewById(R.id.spinner_services);
        spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        //Applying Listener on Spinner :
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedPosition= position;
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
        adapter = new MyRecyclerViewAdapter(Home.this, imageBg, kidsNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View View, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " Profile " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
}

