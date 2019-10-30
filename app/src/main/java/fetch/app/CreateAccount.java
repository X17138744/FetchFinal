package fetch.app;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

//SHOCKINGLY, creates a new account, stores in database
//opens Family page on creation
public class CreateAccount extends AppCompatActivity {

    private EditText EmailField;
    private EditText PasswordField;
    private Button SubmitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);



        EmailField = (EditText) findViewById(R.id.email);
        PasswordField = (EditText) findViewById(R.id.password);
        SubmitBtn = (Button) findViewById(R.id.submitBtn);

        SubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateUserAccount();
            }
        });

        EmailField.addTextChangedListener(SubmitTextWatcher);
        PasswordField.addTextChangedListener(SubmitTextWatcher);

    }

    private void CreateUserAccount() {
    }

    private TextWatcher SubmitTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String emailInput = EmailField.getText().toString().trim();
            String passwordInput = PasswordField.getText().toString().trim();

            SubmitBtn.setEnabled(!emailInput.isEmpty() && !passwordInput.isEmpty());


        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    };

}

