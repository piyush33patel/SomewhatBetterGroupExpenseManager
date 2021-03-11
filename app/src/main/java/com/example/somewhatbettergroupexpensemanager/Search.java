package com.example.somewhatbettergroupexpensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.TreeSet;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        EditText searchBar = findViewById(R.id.search_bar);
        Button search = findViewById(R.id.search_button);
        ListView display = findViewById(R.id.display);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputs[] = searchBar.getText().toString().split(" ");
                TreeSet<String> ids = new TreeSet<>();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("group-expense").child("individual");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.child(inputs[0]).getChildren()){
                            String id = snapshot.getValue().toString();
                            ids.add(id);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                reference = FirebaseDatabase.getInstance().getReference().child("group-expense").child("transaction");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<ViewLogs.Details> arrayList = new ArrayList<>();
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            String id = snapshot.child("ID").getValue().toString();
                            String date = snapshot.child("Date").getValue().toString();
                            String description = snapshot.child("Description").getValue().toString();
                            if(ids.contains(id)){
                                Toast.makeText(getApplicationContext(), ids.size()+" = size", Toast.LENGTH_SHORT).show();
                                // id, date, descrption, share
                                arrayList.add(new ViewLogs.Details(id, date, description, "0"));
                            }
                        }
                        CustomSimpleAdapter arrayAdapter = new CustomSimpleAdapter(getApplicationContext(), arrayList);
                        display.setAdapter(arrayAdapter);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}