package com.example.groups_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class splashFinalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_final);

        Thread thread = new Thread(){

            public void run(){
                try {
                    sleep(2000);

                }
                catch (Exception e){
                    e.printStackTrace();


                }
                finally{
                    Intent intent = new Intent(splashFinalActivity.this, Main_page.class);
                    startActivity(intent);
                }


            }


        };thread.start();

    }
}