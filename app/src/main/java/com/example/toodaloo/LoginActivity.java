package com.example.toodaloo;

import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;

        import com.parse.LogInCallback;
        import com.parse.ParseException;
        import com.parse.ParseUser;
        import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private TextView logo;
    private Button btnLogin;
    private Button btnSignUp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(ParseUser.getCurrentUser() != null){
            goMainActivity();
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        logo = findViewById(R.id.app_name);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick login button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });

        //Should move to separate activity
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick Sign up button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                signUpUser(username,password);
            }
        });

    }

    private void signUpUser(String username, String password) {

        Log.i(TAG,"Creating user: " + username);
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // Sign up successful
                    goMainActivity();
                    Toast.makeText(LoginActivity.this, "Sign up success", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.e(TAG, "Sign up error", e);
                    Toast.makeText(LoginActivity.this, "Sign up error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void loginUser(String username, String password) {
        //LogInInBackground preferred to Login because executes logic in background thread
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue with login", e);
                    Toast.makeText(LoginActivity.this, "issue with login!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Navigate to main activity if the user has signed in properly
                goMainActivity();
                Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

}
