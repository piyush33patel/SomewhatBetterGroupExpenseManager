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

    public ArrayList<String> arrayList;

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
                String str = "";
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    arrayList.add(snapshot.child("Description").getValue().toString());
//                    str += snapshot.child("ID").getValue().toString();
//                    str += "\t\t";
//                    str += snapshot.child("Description").getValue().toString();
//                    str += "\t\t";
//                    str += snapshot.child("Amount").getValue().toString();
//                    str += "\n";
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