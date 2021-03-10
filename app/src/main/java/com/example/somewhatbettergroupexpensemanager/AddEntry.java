package com.example.somewhatbettergroupexpensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("group-expense");
                reference.child("transaction").push().child("Number").setValue("ONE");

            }
        });
    }
}