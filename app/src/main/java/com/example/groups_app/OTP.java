package com.example.groups_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OTP extends AppCompatActivity {

    private Button verifyButton;
    private EditText verificationCode;
    private String mVerificationId;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);
        loadingBar = new ProgressDialog(this);

        verifyButton = (Button) findViewById(R.id.verify_button);
        verificationCode = (EditText) findViewById(R.id.verification_code);

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String verification_Code =verificationCode.getText().toString();

                if (TextUtils.isEmpty(verification_Code)){
                    Toast.makeText(getApplicationContext(), "Please enter the OTP", Toast.LENGTH_SHORT).show();
                }

                else{

                    loadingBar.setTitle("Code Verification");
                    loadingBar.setMessage("Please wait while we verify the OTP");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verification_Code);
                    signInWithPhoneAuthCredential(credential);
                }

            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loadingBar.dismiss();
                            Toast.makeText(getApplicationContext(), "Account created successfully", Toast.LENGTH_SHORT).show();
                            sendUserTOLoginActivity();

                        } else {
                            String message = task.getException().toString();
                            Toast.makeText(getApplicationContext(), "Error :" + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                    }
                );}

    private void sendUserTOLoginActivity() {
        Intent otpIntent = new Intent(OTP.this, Main_Login_Page.class);
        startActivity(otpIntent);
    };
}



