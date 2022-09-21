package com.example.register_login_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;


public class Form extends AppCompatActivity {

    public EditText fe1, fe2, fe3, fe4;
    public Button sfbtn, listbtn;
    long maxid=0;

    // create an object of database reference to access firebase's realtime database
    DatabaseReference databaseReference ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        fe1 = findViewById(R.id.fedt1);
        fe2 = findViewById(R.id.fedt2);
        fe3 = findViewById(R.id.fedt3);
        fe4 = findViewById(R.id.fedt4);
        sfbtn= findViewById(R.id.sfbut);
        listbtn = findViewById(R.id.vfbut);

        Items item = new Items();
        //make a name of the table which the data will be instered in
        databaseReference = FirebaseDatabase.getInstance().getReference().child("items");

        // set an auto increment to the items of the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    maxid = (snapshot.getChildrenCount());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        sfbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String type= fe1.getText().toString().trim();
                Float quantity= Float.parseFloat(fe2.getText().toString().trim());
                String start_date= fe3.getText().toString().trim();
                String end_date= fe4.getText().toString().trim();

                /*databaseReference.child("items").child(String.valueOf(maxid+1)).child("type").setValue(type);
                databaseReference.child("items").child(String.valueOf(maxid+1)).child("quantity").setValue(quantity);
                databaseReference.child("items").child(String.valueOf(maxid+1)).child("start_date").setValue(start_date);
                databaseReference.child("items").child(String.valueOf(maxid+1)).child("end_date").setValue(end_date);*/
                item.setType(type);
                item.setQuantity(quantity);
                item.setStart_date(start_date);
                item.setEnd_date(end_date);

                databaseReference.child(String.valueOf(maxid+1)).setValue(item);
                Toast.makeText(Form.this, "your data have been submitted", Toast.LENGTH_SHORT).show();

            }
        });


        listbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Form.this,View_form.class );
                startActivity(intent);
            }
        });



    }
}