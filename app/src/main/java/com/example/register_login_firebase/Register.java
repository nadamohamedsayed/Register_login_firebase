package com.example.register_login_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public EditText edt1, edt2, edt3, edt4;
    public Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initialization for the variable
        mAuth = FirebaseAuth.getInstance();

        edt1= findViewById(R.id.edt1);
        edt2= findViewById(R.id.edt2);
        edt3= findViewById(R.id.edt3);
        edt4= findViewById(R.id.edt4);
        btn = findViewById(R.id.but);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = edt1.getText().toString().trim();
                String age =  edt2.getText().toString().trim();
                String email = edt3.getText().toString().trim();
                String password = edt4.getText().toString().trim();

                if(name.isEmpty())
                {
                    edt1.setError("your full name is required");
                    edt1.requestFocus();
                    return;
                }

                if(age.isEmpty())
                {
                    edt2.setError("your age is required");
                    edt2.requestFocus();
                    return;
                }

                if(email.isEmpty())
                {
                    edt3.setError("your email is required");
                    edt3.requestFocus();
                    return;
                }

                //to make the mail matches the email requirments
                /*if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    edt3.setError("please provide a valid email");
                    edt3.requestFocus();
                    return;
                }*/

                if(password.isEmpty())
                {
                    edt4.setError("your password is required");
                    edt4.requestFocus();
                    return;
                }

                /*if(password.length()<6)
                {
                    edt4.setError("minimum password length should be at least 6 char");
                    edt4.requestFocus();
                    return;
                }*/

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful())
                                {
                                    user user = new user(name, age, email);

                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful())
                                                    {
                                                        Toast.makeText(Register.this, "user has been registered successfully", Toast.LENGTH_SHORT).show();
                                                        // redirect to login page
                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(Register.this, "failed to register! please try again", Toast.LENGTH_SHORT).show();

                                                    }
                                                }
                                            });
                                }
                                else
                                {
                                    Toast.makeText(Register.this, "user already exist", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });
    }
}