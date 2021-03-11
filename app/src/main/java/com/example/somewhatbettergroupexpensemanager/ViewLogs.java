package com.example.somewhatbettergroupexpensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

    public ArrayList<Transaction> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_logs);
        ListView listView = findViewById(R.id.list_view);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("group-expense").child("transactions");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String id = snapshot.child("ID").getValue().toString();
                    String date = snapshot.child("Date").getValue().toString();
                    String description = snapshot.child("Description").getValue().toString();
                    String amount = snapshot.child("Amount").getValue().toString();
                    String deleted = snapshot.child("Deleted").getValue().toString();
                    Transaction temp = new Transaction();
                    temp.id = id;
                    temp.date = date;
                    temp.description = description;
                    temp.amount = amount;
                    if(deleted.equals("0"))
                        arrayList.add(temp);
                }
                CustomSimpleAdapter arrayAdapter = new CustomSimpleAdapter(getApplicationContext(), arrayList);
                listView.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ShowTransaction.class);
                intent.putExtra("ID", arrayList.get(position).id+"");
                startActivity(intent);
            }
        });

    }
}