package com.example.groups_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main_page extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager mView_pager;
    private TabLayout mTabLayout;
    private tab_accessor mtabAccessorAdapter;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;


//VIDEO 5
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_app);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        RootRef = FirebaseDatabase.getInstance().getReference();

        InitializeFields();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Groop");




    }

    private void InitializeFields() {
        mView_pager = (ViewPager) findViewById(R.id.main_tab_pager);
        mtabAccessorAdapter = new tab_accessor(getSupportFragmentManager());
        mView_pager.setAdapter(mtabAccessorAdapter);
        mTabLayout = (TabLayout) findViewById(R.id.main_tabBar);
        mTabLayout.setupWithViewPager(mView_pager);
        mToolbar = (Toolbar) findViewById(R.id.main_Toolbar);

    }



    @Override
    protected void onStart() {
        super.onStart();
        if (currentUser == null){
            SendUserToLoginActivity();
        }
        else{
            VerifyUserExistance();
        }

    }

    private void VerifyUserExistance() {
        String currentUserId = mAuth.getCurrentUser().getUid();

        RootRef.child("Users").child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("name").exists()){
                    Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();
                }
                else{
                    SendUserToUpdateProfileActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void SendUserToLoginActivity() {
        Intent LoginIntent = new Intent(Main_page.this, Main_Login_Page.class);
        LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(LoginIntent);
        finish();
    }

    private void SendUserToUpdateProfileActivity() {
        Intent UpdateProfileIntent = new Intent(Main_page.this, Update_profile.class);
        UpdateProfileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(UpdateProfileIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.logout){
            mAuth.signOut();
            SendUserToLoginActivity();
        }

        if(item.getItemId() == R.id.update_profile_option){
            SendUserToUpdateProfileActivity();
        }

        if(item.getItemId() == R.id.main_find_friends_option){

        }

        return true;

    }


}