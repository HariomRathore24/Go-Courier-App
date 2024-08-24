package com.example.courierserviceapp;

import static com.example.courierserviceapp.R.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OtpPage extends AppCompatActivity {
    private Button verifyOTPButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otp_page);
        verifyOTPButton = findViewById(R.id.buttonVerifyOTP);

        verifyOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start MainActivity
                Intent intent = new Intent(OtpPage.this, MainActivity.class);
                startActivity(intent);

                // Finish OTPActivity if you don't want to keep it in the back stack
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}