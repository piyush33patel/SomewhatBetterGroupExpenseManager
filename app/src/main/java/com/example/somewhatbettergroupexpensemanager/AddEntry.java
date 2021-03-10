package com.example.somewhatbettergroupexpensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        EditText piyush = findViewById(R.id.piyushET);
        EditText kishan = findViewById(R.id.kishanET);
        EditText akhilesh = findViewById(R.id.akhileshET);
        EditText ojas = findViewById(R.id.ojasET);
        EditText vishal = findViewById(R.id.vishalET);
        EditText amount = findViewById(R.id.amountET);
        EditText description = findViewById(R.id.descriptionET);

        Button done = findViewById(R.id.done);
        Button date = findViewById(R.id.date);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get ratios, amount, desciption, date, and upload
                Toast.makeText(getApplicationContext(), done.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}