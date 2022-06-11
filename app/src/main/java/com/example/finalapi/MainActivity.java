package com.example.finalapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //Testing GitHub

    // Create a Token > Settings > Version Control > GitHub > Log on

    // VCS > GitHub > Create a repository > Commit > Projects > Add ...

    // Fork and cloning repository by *New > Project from Verson Control > Git Accont > Select Repository > Clone


        public void TelaAPI(View view){

            Intent intent = new Intent(getApplicationContext(), APIActivity.class);
            startActivity(intent);
        }

    }

