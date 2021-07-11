package com.example.spectrumtask2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Registration extends AppCompatActivity {
    private EditText txt_fullName,txt_email,txt_password,txt_confirmPassword;

    ProgressBar progressBar;
    DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_registration);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        txt_fullName=(EditText)findViewById(R.id.usernameR);
        txt_email=(EditText)findViewById(R.id.emailR);
        txt_password=(EditText)findViewById(R.id.passwordR);
        txt_confirmPassword=(EditText)findViewById(R.id.conpasswordR);
        Button btn_register = (Button) findViewById(R.id.registerR);
        TextView btn_already = findViewById(R.id.loginR);
        progressBar=(ProgressBar)findViewById(R.id.progressBarR);

        databaseReference= FirebaseDatabase.getInstance().getReference("User");
        firebaseAuth=FirebaseAuth.getInstance();



        btn_register.setOnClickListener(v -> {


            String fullName = txt_fullName.getText().toString();
            String email = txt_email.getText().toString().trim();
            String password = txt_password.getText().toString().trim();
            String confirmPassword = txt_confirmPassword.getText().toString().trim();


            if (TextUtils.isEmpty(email)) {
                Toast.makeText(Registration.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(Registration.this, "Please Enter password", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(fullName)) {
                Toast.makeText(Registration.this, "Please Enter Fullname", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(Registration.this, "Please Enter Confirm Password", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.length() < 6) {
                Toast.makeText(Registration.this, "password too short", Toast.LENGTH_SHORT).show();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            if (password.equals((confirmPassword))) {

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Registration.this, task -> {

                            if (task.isSuccessful()) {
                                User information = new User();
                                //(fullName, email, gender, phoneNumber);
                                information.setEmail(email);
                                information.setFullName (fullName);

                                FirebaseDatabase.getInstance().getReference("User")
                                        .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                        .setValue(information).addOnCompleteListener(task1 -> {

                                            startActivity((new Intent (getApplicationContext(), MainActivity.class)));
                                            Toast.makeText(Registration.this, "Register Successful", Toast.LENGTH_SHORT).show();

                                        });


                            } else {

                                Toast.makeText(Registration.this, "Authentication Failed", Toast.LENGTH_SHORT).show();

                            }
                        });

            }

        });

        btn_already.setOnClickListener (v -> startActivity(new Intent (getApplicationContext(),MainActivity.class)));

    }
}
