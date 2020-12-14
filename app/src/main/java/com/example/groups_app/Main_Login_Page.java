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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main_Login_Page extends AppCompatActivity {


    private Button loginButton, signUpButton;
    private EditText userEmailid, userPassword;
    private TextView forgotPassLink;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        mAuth = FirebaseAuth.getInstance();

        InitialzeFields();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendUserToSignUpActivity();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllowUserToLogin();
            }
        });


    }

    private void InitialzeFields() {
        loginButton = (Button) findViewById(R.id.login_button);
        signUpButton = (Button) findViewById(R.id.signup_button);
        userEmailid = (EditText) findViewById(R.id.email_address);
        userPassword = (EditText) findViewById(R.id.password_field);
        forgotPassLink = (TextView) findViewById(R.id.forgot_password);

        loadingBar = new ProgressDialog(this);

    }


    private void AllowUserToLogin(){
        String mLoginEmail = userEmailid.getText().toString();
        String mLoginPassword = userPassword.getText().toString();

        if (TextUtils.isEmpty(mLoginEmail) ||TextUtils.isEmpty(mLoginPassword)){
            Toast.makeText(getApplicationContext(), "Please enter all the detalis...", Toast.LENGTH_SHORT).show();
        }

        else{
            loadingBar.setTitle("Signing In");
            loadingBar.setMessage("Please wait while we get you into groop");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(mLoginEmail,mLoginPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){

                        SendUserToMainActivity();
                        Toast.makeText(getApplicationContext(), "Login Successful!!", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                    else {
                        String message = task.getException().toString();
                        Toast.makeText(getApplicationContext(), "Error :" + message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();

                    }
                }
            });
        }

    }

    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(Main_Login_Page.this, Main_page.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void SendUserToSignUpActivity() {
        Intent SignupIntent = new Intent(Main_Login_Page.this,signup_main.class);
        startActivity(SignupIntent);
    }


}