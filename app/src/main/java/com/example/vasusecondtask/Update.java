package com.example.vasusecondtask;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Update extends AppCompatActivity {
    private EditText fullName, email, password, confirmPassword, dob_up;
    private RadioButton male, female;
    private Button update;
    DBHelper DB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        fullName = findViewById(R.id.fullName_up);
        email = findViewById(R.id.email_up);
        password = findViewById(R.id.password_up);
        confirmPassword = findViewById(R.id.confirmPassword_up);
        male = findViewById(R.id.male_up);
        female = findViewById(R.id.female_up);
        update = findViewById(R.id.update_up);
        dob_up = findViewById(R.id.dob_up);

        DB = new DBHelper(this);

        String emailTxt = getIntent().getStringExtra("email");
        Cursor cursor = DB.getdata(emailTxt);

        if (cursor.moveToFirst()) {
            fullName.setText(cursor.getString(0));
            email.setText(cursor.getString(1));
            if (cursor.getString(3).equals("Male")) {
                male.setChecked(true);
            } else {
                female.setChecked(true);
            }
            dob_up.setText(cursor.getString(4));
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTxt = fullName.getText().toString();
                String emailTxt = email.getText().toString();
                String passwordTxt = password.getText().toString();
                String confirmPasswordTxt = confirmPassword.getText().toString();
                String dobTxt = dob_up.getText().toString();
                String genderTxt = male.isChecked() ? "Male" : "Female";

                if(nameTxt.equals("") || emailTxt.equals("") || passwordTxt.equals("") || confirmPasswordTxt.equals(""))
                    Toast.makeText(Update.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    if(passwordTxt.equals(confirmPasswordTxt)){
                        Boolean isUpdated = DB.updateuserdata(nameTxt, emailTxt, passwordTxt, genderTxt, dobTxt);
                        if(isUpdated){
                            Toast.makeText(Update.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Update.this, Dashboard.class);
                            intent.putExtra("email",emailTxt);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Update.this, "Update failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Update.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
