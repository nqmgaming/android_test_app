package com.example.myapplication;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText emailSignupEditText;
    private EditText passwordSignupEditText;
    private EditText passwordSignupAgainEditText;
    private CheckBox checkbox_signup_terms_and_policy;
    private Button customSignupButton;
    private TextView signInTextView;
    private Button facebookSignupButton;
    private Button googleSignupButton;

    private static final Pattern EMAIL_REGEX = Pattern.compile(
            "^[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]" +
                    "{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameEditText = findViewById(R.id.username_editText);
        emailSignupEditText = findViewById(R.id.email_signup_editText);
        passwordSignupEditText = findViewById(R.id.password_signup_edittext);
        passwordSignupAgainEditText = findViewById(R.id.password_again_signup_edittext);
        checkbox_signup_terms_and_policy = findViewById(R.id.checkbox_signup_terms_and_policy);
        customSignupButton = findViewById(R.id.custom_signup_button);
        signInTextView = findViewById(R.id.custom_signin_text);
        facebookSignupButton = findViewById(R.id.facebook_signup_button);
        googleSignupButton = findViewById(R.id.google_signup_button);

        // Đặt OnClickListener cho nút đăng ký
        customSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInputValidation() == true) {
                    // Xử lý đăng ký
                    Toast.makeText(SignUp.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                    navigateToSignIn();

                    String username = usernameEditText.getText().toString();
                    String email = emailSignupEditText.getText().toString();
                    String password = passwordSignupEditText.getText().toString();
                    String passwordAgain = passwordSignupAgainEditText.getText().toString();
                    User user = new User(username, email, password);
                    user.saveUser(user, SignUp.this);

                    // Chuyển dữ liệu sang màn hình Login và lưu thông tin vào SharedPreferences
                    Intent intent = new Intent(SignUp.this, Login.class);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    startActivity(intent);
                } else {
                    Toast.makeText(SignUp.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Đặt OnClickListener cho TextView để chuyển sang màn hình đăng nhập
        signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSignIn();
            }
        });

        // Đặt OnClickListener cho nút đăng ký Facebook và Google
        facebookSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);


            }
        });

        googleSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đoạn mã để xử lý đăng ký bằng Google
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }

    private void navigateToSignIn() {
        Intent intent = new Intent(SignUp.this, Login.class);
        startActivity(intent);
    }

    private boolean checkInputValidation() {
        String username = usernameEditText.getText().toString();
        String email = emailSignupEditText.getText().toString();
        String password = passwordSignupEditText.getText().toString();
        String passwordAgain = passwordSignupAgainEditText.getText().toString();

        if (username.isEmpty()) {
            Toast.makeText(SignUp.this, "Please enter your username!", Toast.LENGTH_SHORT).show();
            usernameEditText.setError("Please enter your username!");
            return false;
        }

        if (username.length() < 6) {
            Toast.makeText(SignUp.this, "Username is too short!", Toast.LENGTH_SHORT).show();
            usernameEditText.setError("Username is too short!");
            return false;
        }
        if (email.isEmpty()) {
            Toast.makeText(SignUp.this, "Please enter your email!", Toast.LENGTH_SHORT).show();
            emailSignupEditText.setError("Please enter your email!");
            return false;
        }

        if (!EMAIL_REGEX.matcher(email).matches()) {
            Toast.makeText(SignUp.this, "Email is not valid!", Toast.LENGTH_SHORT).show();
            emailSignupEditText.setError("Email is not valid!");
            return false;
        }

        //check password doesn't match
        if (!password.equals(passwordAgain)) {
            Toast.makeText(SignUp.this, "Password doesn't match!", Toast.LENGTH_SHORT).show();
            passwordSignupAgainEditText.setError("Password doesn't match!");
            return false;
        }
        if (password.isEmpty() || passwordAgain.isEmpty()) {
            Toast.makeText(SignUp.this, "Please enter your password!", Toast.LENGTH_SHORT).show();
            passwordSignupAgainEditText.setError("Please enter your password!");
            passwordSignupEditText.setError("Please enter your password!");
            return false;
        }

        if (password.length() < 6 || passwordAgain.length() < 6) {
            Toast.makeText(SignUp.this, "Password is too short!", Toast.LENGTH_SHORT).show();
            passwordSignupAgainEditText.setError("Password is too short!");
            passwordSignupEditText.setError("Password is too short!");
            return false;
        }

        //checkbox_signup_terms_and_policy is not checked
        if (!checkbox_signup_terms_and_policy.isChecked()) {
            Toast.makeText(SignUp.this, "Please agree Terms of Service and Privacy Policy", Toast.LENGTH_SHORT).show();
            checkbox_signup_terms_and_policy.setError("Please agree Terms of Service and Privacy Policy");
            return false;
        }
        //check đã tồn tại
        if (checkUserExist(username, email)) {
            Toast.makeText(SignUp.this, "Username or email already exists!", Toast.LENGTH_SHORT).show();
            usernameEditText.setError("Username or email already exists!");
            return false;
        }
        // Đoạn mã để xử lý đăng ký thông thường
        return true;
    }

    private boolean checkUserExist(String username, String email) {
        boolean userExists = false;

        if (username.equals("nqmgaming") || email.equals("nguyenquangminh570@gmail.com")) {
            userExists = true;

        }

        return userExists;
    }

    private void setErrorBackground(EditText editText, boolean isError) {
        if (isError) {
            // Đặt màu nền hoặc viền của EditText thành màu đỏ khi nhập sai
            editText.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        } else {
            editText.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#6200EE"))); // Đổi thành màu bạn muốn
        }
    }

    private void setFocusChangeListener(EditText editText) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    setErrorBackground(editText, false);
                }
            }
        });
    }
}