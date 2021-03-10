package com.example.somewhatbettergroupexpensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewLogs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_logs);
        TextView text = findViewById(R.id.text);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("group-expense").child("transaction");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String str = "";
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    str += snapshot.getValue().toString();
                    str += "\n";
                }
                text.setText(str);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}