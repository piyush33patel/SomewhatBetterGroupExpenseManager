package com.example.somewhatbettergroupexpensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowTransaction extends AppCompatActivity {
    static final String names[] = {"Piyush", "Kishan", "Akhilesh", "Ojas", "Vishal"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_transaction);
        Button delete = findViewById(R.id.delete);
        TextView info = findViewById(R.id.info);
        String transferId = getIntent().getStringExtra("ID");

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("group-expense").child("transaction");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            String id = snapshot.child("ID").getValue().toString();
                            if(id.equals(transferId)){
                                reference.child(snapshot.getKey()).child("Deleted").setValue("1");
                                Toast.makeText(getApplicationContext(), "Khatam Tata Bye Bye GoodBye", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("group-expense").child("transaction");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String id = snapshot.child("ID").getValue().toString();
                    String deleted = snapshot.child("Deleted").getValue().toString();
                    String output = "";
                    if(id.equals(transferId) && deleted.equals("0")){
                        output += "ID : " + snapshot.child("ID").getValue().toString();
                        output += "\n\n";
                        output += "DESCRIPTION : " + snapshot.child("Description").getValue().toString();
                        output += "\n\n";
                        output += "DATE : " + snapshot.child("Date").getValue().toString();
                        output += "\n\n";
                        output += "AMOUNT : " + snapshot.child("Amount").getValue().toString();
                        output += "\n\n";
                        HashMap<String, HashMap<String, String>> persons = (HashMap)snapshot.child("Person").getValue();
                        for(Map.Entry it : persons.entrySet()){
                            String name = (String)it.getKey();
                            HashMap<String, Object> value = (HashMap)it.getValue();
                            if((int)Double.parseDouble(value.get("share") + "")==0)
                                output += name + " : " + " [NIL]";
                            else
                                output += name + " : " + value.get("share") + " [" + String.format("%.2f",Double.parseDouble(value.get("ratio")+"")) + "]";
                            output += "\n\n";
                        }
                        info.setText(output);
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}