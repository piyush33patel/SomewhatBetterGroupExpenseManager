package com.example.somewhatbettergroupexpensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addEntry = findViewById(R.id.addEntry);
        Button viewLogs = findViewById(R.id.viewLogs);
        Button payMe = findViewById(R.id.pay_me);

        addEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Adding new Entry", Toast.LENGTH_SHORT);
                Intent intent = new Intent(MainActivity.this, AddEntry.class);
                startActivity(intent);
            }
        });

        viewLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "View Logs", Toast.LENGTH_SHORT);
                Intent intent = new Intent(MainActivity.this, ViewLogs.class);
                startActivity(intent);
            }
        });

        payMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PayMe.class);
                startActivity(intent);
            }
        });
    }
}