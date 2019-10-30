package fetch.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

// Loads on opening of app
// Checks if user is logged in/offers log in functionality if not
// create account link
public class MainActivity extends AppCompatActivity {
    private Button CreateAccountBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CreateAccountBtn = (Button) findViewById(R.id.CreateAccountBtn);

        initializeUI();

        CreateAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateAccount();
            }
        });

    }

    private void initializeUI() {

        CreateAccountBtn = findViewById(R.id.CreateAccountBtn);
    }

    public void openCreateAccount() {
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }
}
