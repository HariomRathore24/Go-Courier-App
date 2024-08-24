package com.example.courierserviceapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpVerificationActivity extends AppCompatActivity {

    private EditText[] otpBoxes = new EditText[6];
    private Button buttonVerifyOTP;
    private String phoneNumber;
    private String verificationId;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize EditText fields
        otpBoxes[0] = findViewById(R.id.editTextOTP1);
        otpBoxes[1] = findViewById(R.id.editTextOTP2);
        otpBoxes[2] = findViewById(R.id.editTextOTP3);
        otpBoxes[3] = findViewById(R.id.editTextOTP4);
        otpBoxes[4] = findViewById(R.id.editTextOTP5);
        otpBoxes[5] = findViewById(R.id.editTextOTP6);

        buttonVerifyOTP = findViewById(R.id.buttonVerifyOTP);
        phoneNumber = getIntent().getStringExtra("mobile");

        sendOtpToUser(phoneNumber);

        // Handle verify button click
        buttonVerifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = "";
                for (EditText editText : otpBoxes) {
                    otp += editText.getText().toString().trim();
                }
                if (!TextUtils.isEmpty(otp)) {
                    verifyOTP(otp);
                } else {
                    Toast.makeText(OtpVerificationActivity.this, "Please enter valid OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendOtpToUser(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        // This callback will be invoked in two situations:
                        // 1 - Instant verification. In some cases the phone number can be instantly
                        //     verified without needing to send or enter the verification code.
                        // 2 - Auto-retrieval. On some devices Google Play services can automatically
                        //     detect the incoming verification SMS and perform verification without
                        //     user action.
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        // This callback is invoked in an invalid request for verification is made,
                        // for instance if the the phone number format is not valid.
                        Toast.makeText(OtpVerificationActivity.this, "Verification Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        // The SMS verification code has been sent to the provided phone number.
                        // You would typically now need to ask the user to enter the code and then
                        // construct a credential by combining the code with a verification ID.
                        verificationId = s;
                    }
                });
    }

    private void verifyOTP(String otp) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Verification successful
                            Toast.makeText(OtpVerificationActivity.this, "Verification successful!", Toast.LENGTH_SHORT).show();
                            openHomeScreen();
                        } else {
                            // Verification failed
                            Toast.makeText(OtpVerificationActivity.this, "Verification failed! Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void openHomeScreen() {
        Intent intent = new Intent(OtpVerificationActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity so user can't navigate back to the OTP screen
    }
}
