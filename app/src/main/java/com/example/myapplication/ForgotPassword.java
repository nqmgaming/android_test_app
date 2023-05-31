package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Pattern;


public class ForgotPassword extends AppCompatActivity {

    private Button btnBack;
    private Button btnSend;
    private EditText txtEmail;

    //Regex Email
    private static final Pattern EMAIL_REGEX = Pattern.compile(
            "^[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]" +
                    "{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        btnBack = findViewById(R.id.btnBack);
        btnSend = findViewById(R.id.btnContinue);
        txtEmail = findViewById(R.id.edtEmail);

        btnBack.setOnClickListener(v -> {
            finish();
        });
        //check email isemty, isvalid, isexist, send email
        btnSend.setOnClickListener(v -> {
            if (checkInputValidation()) {
                //alert dialog check your email: option 1: OK, I will check my email, option 2: Check my email
                //option 1: OK, I will check my email: finish()
                //option 2: Check my email: open email app

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Check your email");
                builder.setMessage("We have sent a password recovery instruction to your email. Please check your email.");
                builder.setPositiveButton("OK, I will check my email", (dialog, which) -> {
                    finish();
                });
                builder.setNegativeButton("Check my email", (dialog, which) -> {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                    startActivity(intent);
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
    }
    private boolean checkInputValidation() {
        String email = txtEmail.getText().toString().trim();
        if (email.isEmpty()) {
            txtEmail.setError("Email is empty");
            return false;
        }
        if (!EMAIL_REGEX.matcher(email).matches()) {
            txtEmail.setError("Email is invalid");
            return false;
        }
        return true;
    }
}