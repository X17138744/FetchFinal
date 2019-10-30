package fetch.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

//This will open when user clicks child portrait in home page. Displays profile page of whichever kid it was.
//Heartbeat, general info, etc

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }
}
