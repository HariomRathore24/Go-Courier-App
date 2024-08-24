package com.example.courierserviceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hbb20.CountryCodePicker;

public class LoginPage extends AppCompatActivity {

    private Button  b1;
    CountryCodePicker ccp;
    EditText t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);

        b1 = findViewById(R.id.loginBtn);
        ccp = findViewById(R.id.ccp);
        t1 = findViewById(R.id.phoneNumberEditText);

        ccp.registerCarrierNumberEditText(t1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start OTP activity
                Intent intent = new Intent(LoginPage.this, OtpVerificationActivity.class);
                intent.putExtra("mobile", ccp.getFullNumberWithPlus().replace(" ", " ") );
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}

