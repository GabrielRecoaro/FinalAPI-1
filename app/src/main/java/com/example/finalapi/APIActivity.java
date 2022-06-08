package com.example.finalapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class APIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apiactivity);
    }

    public void TelaMenu(View view){

        Intent intent = new Intent(getApplicationContext(), APIActivity.class);
        startActivity(intent);
    }

}