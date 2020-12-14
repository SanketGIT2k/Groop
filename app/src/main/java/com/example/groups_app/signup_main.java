package com.example.groups_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class signup_main extends AppCompatActivity  {

    private Button createAccountButton;
    private EditText firstName, lastName, emailAddress, newPassword, confirmPassword;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String mVerificationId;
    private DatabaseReference Rootref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_main);
        InitializeFields();

        mAuth = FirebaseAuth.getInstance();
        Rootref = FirebaseDatabase.getInstance().getReference();




        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateNewAccount();
            }
        });


    }

    private void CreateNewAccount() {
        String mFirst_Name;
        mFirst_Name = firstName.getText().toString();
        String mLast_Name = lastName.getText().toString();
        String memail_address = emailAddress.getText().toString();
        String mnew_Password = newPassword.getText().toString();
        String mconfirm_Password = confirmPassword.getText().toString();

        if (TextUtils.isEmpty(mFirst_Name) || TextUtils.isEmpty(mLast_Name) ||TextUtils.isEmpty(memail_address) ||TextUtils.isEmpty(mnew_Password) ||TextUtils.isEmpty(mconfirm_Password)){
            Toast.makeText(getApplicationContext(), "Please enter all the detalis...", Toast.LENGTH_SHORT).show();
        }

        else{
            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please wait while we create your account");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(memail_address,mnew_Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        String currentUserID = mAuth.getCurrentUser().getUid();
                        Rootref.child("Users").child("Unique Ids").child(currentUserID).setValue("");
                        
                        SendUserToLoginActivity();
                        Toast.makeText(getApplicationContext(), "Account Created Successfully", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                    else{
                        String message = task.getException().toString();
                        Toast.makeText(getApplicationContext(), "Error :" + message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });
        }

        callbacks =new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Intent sendToOTPIntent = new Intent(signup_main.this, OTP.class);
                startActivity(sendToOTPIntent);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(getApplicationContext(), "Invalid phone number entered Enter your phone number with country code", Toast.LENGTH_SHORT).show();
            }

            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                Toast.makeText(getApplicationContext(), "OTP sent on provided phone number", Toast.LENGTH_SHORT).show();
                Intent sendToOTPIntent = new Intent(signup_main.this, OTP.class);
                startActivity(sendToOTPIntent);
            }
        };

    }

    private void SendUserToLoginActivity() {
        Intent LoginIntent = new Intent(signup_main.this, Main_Login_Page.class);
        LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(LoginIntent);
        finish();
    }


    private void InitializeFields() {
        createAccountButton = (Button) findViewById(R.id.create_account);
        firstName = (EditText) findViewById(R.id.first_name);
        lastName = (EditText) findViewById(R.id.last_name);
        emailAddress = (EditText) findViewById(R.id.user_emailAddress);
        newPassword = (EditText) findViewById(R.id.new_password);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);

        loadingBar = new ProgressDialog(this);
    }


}