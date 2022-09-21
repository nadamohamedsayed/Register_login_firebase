package com.example.register_login_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class View_form extends AppCompatActivity {

    public ListView listview;

    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_form);

        listview = findViewById(R.id.list);

        final ArrayList<String> list1 =new ArrayList<>();
        final ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item , list1);
        listview.setAdapter(arrayAdapter);


        ref = FirebaseDatabase.getInstance().getReference().child("items");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list1.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    list1.add(snapshot1.getValue().toString());
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}