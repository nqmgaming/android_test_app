package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private CheckBox rememberMeCheckBox;
    private AppCompatButton signInButton;
    private TextView signUpTextView;
    private Button signUpFacebookButton;
    private Button signUpGoogleButton;
    private TextView forgotPasswordTextView;

    private static final Pattern EMAIL_REGEX = Pattern.compile(
            "^[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]" +
                    "{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.email_editText);
        passwordEditText = findViewById(R.id.password_edittext);
        rememberMeCheckBox = findViewById(R.id.remember_me_checkbox);
        signInButton = findViewById(R.id.custom_signin_button);
        signUpTextView = findViewById(R.id.custom_signup_button);
        signUpFacebookButton = findViewById(R.id.facebook_login_button);
        signUpGoogleButton = findViewById(R.id.google_login_button);
        forgotPasswordTextView = findViewById(R.id.forgot_password_textview);

        // Creates a button that mimics a crash when pressed
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInputValidation()) {
                    String enteredEmail = emailEditText.getText().toString();
                    String enteredPassword = passwordEditText.getText().toString();

                    Intent intent = getIntent();
                    String email = intent.getStringExtra("email");
                    String password = intent.getStringExtra("password");
                    if (email.equals(enteredEmail) || password.equals(enteredPassword)) {
                        Toast.makeText(Login.this, "ok", Toast.LENGTH_SHORT).show();
                        navigateToHome();
                    } else {
                        Toast.makeText(Login.this, "wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToForgotPassword();
            }
        });
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSignUp();
            }
        });

        signUpFacebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToHome();
                Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
            }
        });

        signUpGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToHome();
                Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkInputValidation() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(Login.this, "Please enter your email!", Toast.LENGTH_SHORT).show();
            emailEditText.setError("Please enter your email!");
            return false;
        }

        if (!EMAIL_REGEX.matcher(email).matches()) {
            Toast.makeText(Login.this, "Email is not valid!", Toast.LENGTH_SHORT).show();
            emailEditText.setError("Email is not valid!");
            return false;
        }

        if (password.isEmpty()) {
            Toast.makeText(Login.this, "Please enter your password!", Toast.LENGTH_SHORT).show();
            passwordEditText.setError("Please enter your password!");
            return false;
        }

        if (password.length() < 6) {
            Toast.makeText(Login.this, "Password is too short!", Toast.LENGTH_SHORT).show();
            passwordEditText.setError("Password is too short!");
            return false;
        }

        return true;
    }

    private void navigateToSignUp() {
        Intent intent = new Intent(Login.this, SignUp.class);
        startActivity(intent);
    }

    private void navigateToHome() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
    }

    private void navigateToForgotPassword() {
        Intent intent = new Intent(Login.this, ForgotPassword.class);
        startActivity(intent);
    }


}
