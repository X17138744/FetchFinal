package fetch.app;

/**
 *  Matthew Kearns - x17492632 / Login Features
 */

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class Login extends AppCompatActivity {

    /**
     * Currently on this version no PHP or Firebase connected just log straight in.
     */


    EditText EmailField;
    EditText PasswordField;
    Button loginBtn;
    Button createBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EmailField = findViewById(R.id.email);
        PasswordField = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        createBtn = findViewById(R.id.createBtn);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeLogin();
            }
        });
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateLogin();
            }
        });


        EmailField.addTextChangedListener(SubmitTextWatcher);
        PasswordField.addTextChangedListener(SubmitTextWatcher);

    }

    /**
     * New intents for Create Account and Home pages.
     */


    private void HomeLogin() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);

    }

    private void CreateLogin() {
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);

    }

    private TextWatcher SubmitTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String emailInput = EmailField.getText().toString().trim();
            String passwordInput = PasswordField.getText().toString().trim();

            loginBtn.setEnabled(!emailInput.isEmpty() && !passwordInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

    };

}