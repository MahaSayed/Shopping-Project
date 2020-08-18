package com.example.ShopppingApplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        Button calendarBtn = (Button)findViewById(R.id.calendarBtn1);

        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, CalendarActivity.class);
                startActivityForResult(intent , 7);
            }
        });
    }

    // lama el calender t2fel
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 7:
                String date = data.getStringExtra("date");
                TextView txt = (TextView)findViewById(R.id.date_txt);
                txt.setText(date);
                break;
        }
    }

    public void SignUp_Btn (View view)
    {
        EditText name=(EditText)findViewById(R.id.signup_name);
        EditText username=(EditText)findViewById(R.id.signup_username);
        EditText pass=(EditText)findViewById(R.id.signup_password);
        RadioButton MaleRadio=(RadioButton) findViewById(R.id.radioButton_Male);
        RadioButton FemaleRadio=(RadioButton) findViewById(R.id.radioButton_Female);
        TextView txt = (TextView)findViewById(R.id.date_txt);
        EditText job=(EditText)findViewById(R.id.signup_job);
        String gender = "";
        ShoppingDatabase db = new ShoppingDatabase(this);

        Cursor cur = db.getAllCustomers();

        if(MaleRadio.isChecked())
        {
            gender = "Male";
        }
        else if(FemaleRadio.isChecked())
        {
            gender="Female";
        }
        if (name.getText().toString().equals(""))
            Toast.makeText(this, "Please enter your Name", Toast.LENGTH_SHORT).show();
        else if (username.getText().toString().equals(""))
            Toast.makeText(this, "Please enter your Username", Toast.LENGTH_SHORT).show();
        else if (pass.getText().toString().equals(""))
            Toast.makeText(this, "Please enter your Password", Toast.LENGTH_SHORT).show();
        else if (gender == "")
            Toast.makeText(this, "Please enter your Gender", Toast.LENGTH_SHORT).show();
        else if (txt.getText().toString().equals("dd/mm/yyyy"))
            Toast.makeText(this, "Please enter your Birth Date", Toast.LENGTH_SHORT).show();
        else if (job.getText().toString().equals(""))
            Toast.makeText(this, "Please enter your Job", Toast.LENGTH_SHORT).show();


        else if (!cur.isAfterLast())
        {
            String temp = cur.getString(0);
            if(username.getText().toString().equals(temp))
            {
                Toast.makeText(this, "Invalid Username! Try again", Toast.LENGTH_SHORT).show();
                username.setText("");
            }
            else
            {
                db.CreateNewCustomer(name.getText().toString(), username.getText().toString(), pass.getText().toString(), gender, txt.getText().toString(), job.getText().toString());
                Toast.makeText(this, "Done Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignupActivity.this, LogInActivity.class));
            }
        }
        else {
            db.CreateNewCustomer(name.getText().toString(), username.getText().toString(), pass.getText().toString(), gender, txt.getText().toString(), job.getText().toString());
            Toast.makeText(this, "Done Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignupActivity.this, LogInActivity.class));
        }
    }
}
