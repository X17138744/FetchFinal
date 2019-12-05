package fetch.app;

/**
 * Matthew Byrne - x17138744 / PHP script
 */


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;


/**
 * This is the Create Account class. Here we have the PHP vibes sending and logging in on Workbench... The PHP files are also in the folder of this java zip file you are reading right now!
 */

public class CreateAccount extends AppCompatActivity {

    // Creating EditText.
    protected EditText FullName, Email, Password;

    // Creating buttons;
    private Button Register, Return;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;

    // Create string variable to hold the EditText Value.
    private String NameHolder, EmailHolder, PasswordHolder;

    // Creating Progress dialog.
    ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * This could be handy link to the Heroku to display our website or other info whatever our preference is.
     */

    // Storing server url into String variable.
    String HttpUrl = "https://fetch-live.herokuapp.com/index.php";

    Boolean CheckEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Assigning ID's to EditText.
        FullName = findViewById(R.id.EditTextFullName);

        Email = findViewById(R.id.EditTextEmail);

        Password = findViewById(R.id.EditTextPassword);

        // Assigning ID's to Buttons.
        Register = findViewById(R.id.ButtonRegister);

        Return = findViewById(R.id.ButtonReturn);

        /**
         * Below is the "Volley" dependency that is used to send the PHP to Workbench. It is featured as a implementation in the gradle.
         */

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(CreateAccount.this);

        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(CreateAccount.this);

        // Adding click listener to button.
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEditTextIsEmptyOrNot();

                if (CheckEditText) {

                    UserRegistration();


                } else {

                    Toast.makeText(CreateAccount.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }

            }
        });
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Return();
            }
        });

    }

    /**
     * Proceeds to the login page once PHP has been sent over!
     */


    private void Return() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void UserRegistration() {

        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing Echo Response Message Coming From Server.
                        Toast.makeText(CreateAccount.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(CreateAccount.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The first argument should be same sa your MySQL database table columns.

                /**
                 * Throws in the three strings, email, password and name onto workbench.
                 */

                params.put("email", EmailHolder);
                params.put("password", PasswordHolder);
                params.put("fName", NameHolder);
                //New Intent to Create Account Class
                Intent s = new Intent(CreateAccount.this, Home.class);
                startActivity(s);
                return params;
            }
        };

        /**
         * Volley vibes featured in the Gradle
         */

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(CreateAccount.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    public void CheckEditTextIsEmptyOrNot() {

        // Getting values from EditText.
        NameHolder = FullName.getText().toString().trim();
        EmailHolder = Email.getText().toString().trim();
        PasswordHolder = Password.getText().toString().trim();

        // Checking whether EditText value is empty or not.
        // If any of EditText is empty then set variable value as False.
        // If any of EditText is filled then set variable value as True.
        CheckEditText = !TextUtils.isEmpty(NameHolder) && !TextUtils.isEmpty(EmailHolder) && !TextUtils.isEmpty(PasswordHolder);
    }



}

