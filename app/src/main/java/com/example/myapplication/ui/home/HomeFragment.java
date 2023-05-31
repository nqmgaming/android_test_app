package com.example.myapplication.ui.home;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private TextView clockTextView;
    private ImageView clockImageView;
    private Handler handler;
    private Runnable runnable;
    private TextView dateTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        clockTextView = root.findViewById(R.id.clockTextView);
        clockImageView = root.findViewById(R.id.clockImageView);
        handler = new Handler(Looper.getMainLooper());
        clockTextView = root.findViewById(R.id.clockTextView);
        dateTextView = root.findViewById(R.id.dateTextView);


        updateClock();

        return root;
    }

    private void updateClock() {
        runnable = new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                String currentTime = sdf.format(new Date());
                clockTextView.setText(currentTime);

                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault());
                String currentDate = dateFormat.format(new Date());
                dateTextView.setText(currentDate);

                drawClock();

                handler.postDelayed(this, 1000);
            }
        };

        handler.post(runnable);
    }

    private void drawClock() {
        int imageSize = clockImageView.getWidth(); // Lấy kích thước hình ảnh

        Bitmap bitmap = Bitmap.createBitmap(imageSize, imageSize, Bitmap.Config.ARGB_8888); // Tạo bitmap mới
        Canvas canvas = new Canvas(bitmap); // Tạo canvas để vẽ trên bitmap
        clockImageView.setImageBitmap(bitmap); // Đặt bitmap lên ImageView

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        paint.setColor(Color.BLACK);

        int centerX = imageSize / 2;
        int centerY = imageSize / 2;
        int radius = imageSize / 2 - 10;

        // Vẽ đường tròn
        canvas.drawCircle(centerX, centerY, radius, paint);

        // Vẽ kim giờ
        int hours = Integer.parseInt(new SimpleDateFormat("HH", Locale.getDefault()).format(new Date()));
        int minutes = Integer.parseInt(new SimpleDateFormat("mm", Locale.getDefault()).format(new Date()));
        int seconds = Integer.parseInt(new SimpleDateFormat("ss", Locale.getDefault()).format(new Date()));

        int hourHandLength = radius / 2;
        int minuteHandLength = radius * 3 / 4;
        int secondHandLength = radius - 20;

        drawHand(canvas, centerX, centerY, hours * 30 + (minutes / 2), hourHandLength, 6, paint);
        drawHand(canvas, centerX, centerY, minutes * 6, minuteHandLength, 4, paint);
        drawHand(canvas, centerX, centerY, seconds * 6, secondHandLength, 2, paint);
    }

    private void drawHand(Canvas canvas, int centerX, int centerY, int angle, int length, int strokeWidth, Paint paint) {
        double radian = Math.toRadians(angle - 90);
        int x = (int) (centerX + length * Math.cos(radian));
        int y = (int) (centerY + length * Math.sin(radian));

        Path path = new Path();
        path.moveTo(centerX, centerY);
        path.lineTo(x, y);

        paint.setStrokeWidth(strokeWidth);
        canvas.drawPath(path, paint);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        handler.removeCallbacks(runnable);
    }
}
