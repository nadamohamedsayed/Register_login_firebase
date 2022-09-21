package com.example.register_login_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    public TextView txt;
    public EditText ledt1, ledt2;
    public Button lbtn;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        txt = findViewById(R.id.ltxt2);
        ledt1 = findViewById(R.id.ledt1);
        ledt2 = findViewById(R.id.ledt2);
        lbtn = findViewById(R.id.lbut);

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

        lbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String lemail = ledt1.getText().toString().trim();
                String pass = ledt2.getText().toString().trim();

                if(lemail.isEmpty())
                {
                    ledt1.setError("your email is required");
                    ledt2.requestFocus();
                    return;
                }

                if(pass.isEmpty())
                {
                    ledt1.setError("your password is required");
                    ledt2.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(lemail, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful())
                                {
                                    // redirect to user profile
                                    Intent intent = new Intent(MainActivity.this, Home_page.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(MainActivity.this, "failed to login", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}