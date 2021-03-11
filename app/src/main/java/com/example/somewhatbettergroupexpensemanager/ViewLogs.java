package com.example.somewhatbettergroupexpensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewLogs extends AppCompatActivity {
    static class Details{
        String id, date, description, amount;
        public Details(String id, String date, String description, String amount){
            this.id = id;
            this.date = date;
            this.description = description;
            this.amount = amount;
        }
    }

    public ArrayList<Details> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_logs);
        ListView listView = findViewById(R.id.list_view);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("group-expense").child("transaction");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList = new ArrayList<>();
                arrayList.add(new Details("ID", "DATE", "DESCRIPTION", "AMOUNT"));
                arrayList.add(new Details("", "", "", ""));
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String id = snapshot.child("ID").getValue().toString();
                    String date = snapshot.child("Date").getValue().toString();
                    String description = snapshot.child("Description").getValue().toString();
                    String amount = snapshot.child("Amount").getValue().toString();
                    arrayList.add(new Details(id, date, description, amount));
                }
                CustomSimpleAdapter arrayAdapter = new CustomSimpleAdapter(getApplicationContext(), arrayList);
                listView.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}