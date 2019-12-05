package fetch.app;

/**
 *  Matthew Kearns - x17492632 / MainActivity Features
 */

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class MainActivity extends AppCompatActivity {

    /**
     * Currently on this version no PHP or Firebase connected just log straight in.
     */

    private EditText EmailField;
    private EditText PasswordField;
    private Button loginBtn;
    private Button createBtn;
    private String uid;
    //private DocumentReference noteRef;
    private FirebaseAuth mAuth;
    private static final String TAG = MainActivity.class.getSimpleName();
   // FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        EmailField = findViewById(R.id.email);
        PasswordField = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        createBtn = findViewById(R.id.createBtn);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
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

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    /**
     * New intents for Create Account and Home pages.
     */

    private void SignIn() {

        String email, password;
        email = EmailField.getText().toString();
        password = PasswordField.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            //noteRef = db.document("Patients/"+uid);


                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(MainActivity.this, Home.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                        // ...
                    }
                });

    }


    private void GetCurrentUser() {

    }

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