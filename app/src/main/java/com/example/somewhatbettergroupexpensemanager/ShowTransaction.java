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

public class ShowTransaction extends AppCompatActivity {
    static final String names[] = {"Piyush", "Kishan", "Akhilesh", "Ojas", "Vishal"};

    static class Person{
        static double sumOfRatio = 0;
        static double amount = 0;
        String name;
        double ratio;
        int share;
        public Person(String name, double ratio, int share){
            this.name = name;
            this.ratio = ratio;
            this.share = share;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_transaction);
        Button delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Cannot delete because you are an idiot", Toast.LENGTH_SHORT).show();
            }
        });
        TextView info = findViewById(R.id.info);
        String transferId = getIntent().getStringExtra("ID");

        ArrayList<Person> persons = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("group-expense").child("transaction");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Person.amount = 0;
                Person.sumOfRatio = 0.0;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String id = snapshot.child("ID").getValue().toString();
                    String output = "";
                    if(id.equals(transferId)){
                        output += "ID : " + snapshot.child("ID").getValue().toString();
                        output += "\n\n";
                        output += "DESCRIPTION : " + snapshot.child("Description").getValue().toString();
                        output += "\n\n";
                        output += "DATE : " + snapshot.child("Date").getValue().toString();
                        output += "\n\n";
                        Person.amount = Integer.valueOf(snapshot.child("Amount").getValue().toString());
                        output += "AMOUNT " + Person.amount + "\n\n";
                        for(int i = 0; i < names.length; i++){
                            double ratio = Double.valueOf(snapshot.child(names[i]).getValue().toString());
                            Person.sumOfRatio += ratio;
                            persons.add(new Person(names[i], ratio, 0));
                        }
                        for(int i = 0; i < persons.size(); i++){
                            Person current = persons.get(i);
                            current.share = (int)(Math.ceil((current.ratio/Person.sumOfRatio) * Person.amount));
                            if(current.share==0)
                                output += names[i] + " : " + current.ratio + "  [NIL]"+ "\n\n";
                            else
                                output += names[i] + " : " + current.ratio + "  ["+ current.share +"]"+ "\n\n";
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