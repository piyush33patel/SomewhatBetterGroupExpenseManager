package com.example.somewhatbettergroupexpensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TreeSet;

public class AddEntry extends AppCompatActivity {

    static final String names[] = {"Piyush", "Kishan", "Akhilesh", "Ojas", "Vishal"};
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EditText ratios[] = new EditText[5];
        setContentView(R.layout.activity_add_entry);
        ratios[0] = findViewById(R.id.piyushET);
        ratios[1] = findViewById(R.id.kishanET);
        ratios[2] = findViewById(R.id.akhileshET);
        ratios[3] = findViewById(R.id.ojasET);
        ratios[4] = findViewById(R.id.vishalET);
        EditText amount = findViewById(R.id.amountET);
        EditText description = findViewById(R.id.descriptionET);

        Button done = findViewById(R.id.done);
        Button date = findViewById(R.id.date);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(AddEntry.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String tareek = day + "/" + month + "/" + year;
                date.setText(tareek);
            }
        };

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference transaction = FirebaseDatabase.getInstance().getReference().child("group-expense").child("transaction");
                transaction.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int count = 0;
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            count++;
                        }
                        Toast.makeText(getApplicationContext(), count+"", Toast.LENGTH_SHORT).show();
                        HashMap<String, String> map = new HashMap<>();
                        map.put("ID", count+"");
                        map.put("Date", date.getText().toString());
                        map.put("Amount", amount.getText().toString());
                        map.put("Description", description.getText().toString());
                        for(int i = 0; i < names.length; i++){
                            if(ratios[i].getText().toString().trim().length()==0)
                                map.put(names[i], "0");
                            else
                                map.put(names[i], ratios[i].getText().toString());
                        }
                        transaction.push().setValue(map);
                        // second branch
                        DatabaseReference individual = FirebaseDatabase.getInstance().getReference().child("group-expense").child("individual");
                        for(int i = 0; i < names.length; i++){
                            if(ratios[i].getText().toString().trim().length()==0 || Double.valueOf(ratios[i].getText().toString()) == 0.0)
                                continue;
                            individual.child(names[i]).push().setValue(count);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

}