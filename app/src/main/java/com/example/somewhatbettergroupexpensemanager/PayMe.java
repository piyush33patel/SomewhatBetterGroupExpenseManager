package com.example.somewhatbettergroupexpensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class PayMe extends AppCompatActivity {
    static final String names[] = {"Piyush", "Kishan", "Akhilesh", "Ojas", "Vishal"};
    ArrayList<Transaction> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_me);
        HashMap<String, TreeSet<String>> map = new HashMap<>();
        ListView listView = findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), UserTransactions.class);
                intent.putExtra("name", arrayList.get(position).description+"");
                startActivity(intent);
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("group-expense").child("individual");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(int i = 0; i < names.length; i++) {
                    TreeSet<String> tree = new TreeSet<>();
                    for (DataSnapshot snapshot : dataSnapshot.child(names[i]).getChildren()) {
                        tree.add(snapshot.getValue().toString());
                    }
                    map.put(names[i], tree);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Person totalPayPerPerson[] = new Person[5];
        for(int i = 0; i < names.length; i++){
            totalPayPerPerson[i] = new Person();
            totalPayPerPerson[i].name = names[i];
        }
        reference = FirebaseDatabase.getInstance().getReference().child("group-expense").child("transaction");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for(int i = 0; i < names.length; i++) {
                        if(snapshot.child("Deleted").getValue().equals("0")) {
                            double newValue = Double.parseDouble(totalPayPerPerson[i].share);
                            newValue += Double.parseDouble(snapshot.child("Person").child(names[i]).child("share").getValue().toString());
                            totalPayPerPerson[i].share = newValue + "";
                        }
                    }
                }
                arrayList = new ArrayList<>();
                Transaction output = new Transaction();
                output.id = "";
                output.date = "ID";
                output.description = "DESCRIPTION";
                output.amount =  "AMOUNT TO PAY";
                arrayList.add(output);
                for(int i = 0; i < names.length; i++){
                    output = new Transaction();
                    output.id = "";
                    output.date = (i+1) + "";
                    output.description = totalPayPerPerson[i].name;
                    output.amount =  totalPayPerPerson[i].share;
                    arrayList.add(output);
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