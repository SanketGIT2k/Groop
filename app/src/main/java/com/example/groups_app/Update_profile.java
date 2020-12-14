package com.example.groups_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Update_profile extends AppCompatActivity {

    private Button UpdateUserProfile;
    private EditText UpdateUsername;
    private EditText UpdateUserstatus;
    private ImageView UserProfilePicture;
    private String currenUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        InitializeFields();

        mAuth = FirebaseAuth.getInstance();
        currenUserID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();

        UpdateUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateProfileSettings();
            }

        });

        RetrieveUserInformation();


    }

    private void RetrieveUserInformation() {
        RootRef.child("Users").child(currenUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() && (snapshot.hasChild("name") && (snapshot.hasChild("Image")))){
                    String retreiveUsername = snapshot.child("name").getValue().toString();
                    String retreiveStatus = snapshot.child("status").getValue().toString();
                    String retreiveProfileImage = snapshot.child("Image").getValue().toString();

                    UpdateUsername.setText(retreiveUsername);
                    UpdateUserstatus.setText(retreiveStatus);


                }
                else if(snapshot.exists() && (snapshot.hasChild("name"))){
                    String retreiveUsername = snapshot.child("name").getValue().toString();
                    String retreiveStatus = snapshot.child("status").getValue().toString();

                    UpdateUsername.setText(retreiveUsername);
                    UpdateUserstatus.setText(retreiveStatus);

                }

                else{
                    Toast.makeText(Update_profile.this, "Please set and update your profile information", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void InitializeFields() {
        UpdateUserProfile = (Button) findViewById(R.id.update_account);
        UpdateUsername = (EditText) findViewById(R.id.profile_username);
        UpdateUserstatus = (EditText) findViewById(R.id.profile_user_status);
        UserProfilePicture = (ImageView) findViewById(R.id.profile_picture);

    }

    private void UpdateProfileSettings() {
        String setUsername = UpdateUsername.getText().toString();

        String setUserStatus = UpdateUserstatus.getText().toString();
        if(TextUtils.isEmpty(setUsername)){
            Toast.makeText(this, "Please write your username", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(setUserStatus)){
            Toast.makeText(this, "Please write your status", Toast.LENGTH_SHORT).show();
        }
        else{
            HashMap<String,String> profileMap = new HashMap<>();
            profileMap.put("uid",currenUserID);
            profileMap.put("name",setUsername);
            profileMap.put("status",setUserStatus);

            RootRef.child("Users").child(currenUserID).setValue(profileMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Update_profile.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                        SendUserToMainActivity();
                    }
                    else{
                        String message = task.getException().toString();
                        Toast.makeText(Update_profile.this, "Error :" + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }


    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(Update_profile.this, Main_page.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
}

}