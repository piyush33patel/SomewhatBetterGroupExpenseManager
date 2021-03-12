package com.example.somewhatbettergroupexpensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.TreeSet;

public class UserTransactions extends AppCompatActivity {

    ArrayList<Transaction> credits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_transactions);
        String userName = getIntent().getStringExtra("name");
        ListView listView = findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ShowTransaction.class);
                intent.putExtra("ID", credits.get(position).id+"");
                startActivity(intent);
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("group-expense").child("transactions");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                credits = new ArrayList<>();
                Transaction transaction = new Transaction();
                transaction.id = "ID";
                transaction.date = "DATE";
                transaction.description = "DESCRIPTION";
                transaction.amount = "AMOUNT";
                credits.add(transaction);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    transaction = new Transaction();
                    String deleted = snapshot.child("Deleted").getValue().toString();
                    String date = snapshot.child("Date").getValue().toString();
                    String description = snapshot.child("Person").child(userName).child("specific-description").getValue().toString();
                    String amount = snapshot.child("Person").child(userName).child("share").getValue().toString();
                    if(description.length()==0)
                        description = snapshot.child("Description").getValue().toString();
                    transaction.id = snapshot.child("ID").getValue().toString();
                    transaction.date = date;
                    transaction.description = description;
                    transaction.amount = amount;
                    if(Double.parseDouble(amount) > 0.0 && deleted.equals("0"))
                        credits.add(transaction);
                }
                CustomSimpleAdapter arrayAdapter = new CustomSimpleAdapter(getApplicationContext(), credits);
                listView.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}