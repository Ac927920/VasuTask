package com.example.vasusecondtask;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {
    private TextView name, email, gender, dob;
    private Button update, delete;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        gender = findViewById(R.id.gender);
        dob = findViewById(R.id.dob);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        DB = new DBHelper(this);

        String emailTxt = getIntent().getStringExtra("email");  // Get the user's email from the Intent
        Cursor cursor = DB.getdata(emailTxt);  // Retrieve the user's details from the database

        if (cursor.moveToFirst()) {
            // Set the user's details
            name.setText(cursor.getString(0));
            email.setText(cursor.getString(1));
            gender.setText(cursor.getString(3));
            dob.setText(cursor.getString(4));
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString(); // Get the user's email
                Intent intent = new Intent(Dashboard.this, Update.class);
                intent.putExtra("email", userEmail); // Pass the user's email to the Update activity
                startActivity(intent); // Navigate to Update activity
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString(); // Get the user's email
                Boolean isDeleted = DB.deleteuserdata(userEmail); // Delete user from the database

                if (isDeleted) {
                    // If deletion successful, redirect to RegistrationActivity
                    Intent intent = new Intent(Dashboard.this, Registration.class);
                    startActivity(intent); // Navigate to Registration activity
                    finish(); // Close the Dashboard activity
                } else {
                    // Handle deletion failure
                    Toast.makeText(Dashboard.this, "Deletion failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
