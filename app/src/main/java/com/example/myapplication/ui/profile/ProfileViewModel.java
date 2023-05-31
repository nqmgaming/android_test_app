package com.example.myapplication.ui.profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.example.myapplication.Login;

public class ProfileViewModel extends ViewModel {

    private ImageView mImageViewProfile;
    private Button mButtonEditProfile;
    private Button mButtonLogout;
    private Button mSecurity;

    public void initializeViews(ImageView imageViewProfile, Button buttonEditProfile, Button buttonLogout, Button buttonSecurity) {
        mImageViewProfile = imageViewProfile;
        mButtonEditProfile = buttonEditProfile;
        mButtonLogout = buttonLogout;
        mSecurity = buttonSecurity;

        // Set sự kiện click cho nút Logout
        mButtonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý đăng xuất hỏi option người dùng có thực sự muốn đăng xuất hay không
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Đăng xuất");

            builder.setMessage("Bạn có chắc chắn muốn đăng xuất?");
            builder.setPositiveButton("Có", (dialog, which) -> {
                // Xử lý sự kiện khi người dùng chọn Có
                Toast.makeText(v.getContext(), "Đăng xuất", Toast.LENGTH_SHORT).show();
                navigateToSignIn(v);
            });
            builder.setNegativeButton("Không", (dialog, which) -> {
                // Xử lý sự kiện khi người dùng chọn Không
                Toast.makeText(v.getContext(), "Không đăng xuất", Toast.LENGTH_SHORT).show();
            });
            AlertDialog dialog = builder.create();
            dialog.show();

            }
        });
    }

    private void navigateToSignIn(View view) {
        Intent intent = new Intent(view.getContext(), Login.class);
        view.getContext().startActivity(intent);
    }
}
