package com.example.vasusecondtask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


public class Registration extends AppCompatActivity {

    private EditText fullName, email, password, confirmPassword, dobEditText ;
    private RadioButton male, female;
    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        signUp = findViewById(R.id.signUp);
        dobEditText = findViewById(R.id.dob);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate input and attempt registration
                String nameValue = fullName.getText().toString();
                String emailValue = email.getText().toString();
                String passwordValue = password.getText().toString();
                String genderValue = male.isChecked() ? "Male" : "Female";
                String dobValue = dobEditText.getText().toString();

                DBHelper dbHelper = new DBHelper(Registration.this);

                boolean isInserted = dbHelper.insertuserdata(nameValue, emailValue, passwordValue, genderValue, dobValue);
                if (isInserted) {
                    Toast.makeText(Registration.this, "Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Registration.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Registration.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}