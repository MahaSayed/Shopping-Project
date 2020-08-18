package com.example.ShopppingApplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {

    EditText user , pass;
    Button logInBtn;
    Integer custId;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean savelogin;
    CheckBox saveCheckBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        user = (EditText)findViewById(R.id.login_username);
        pass = (EditText)findViewById(R.id.login_password);
        logInBtn = (Button)findViewById(R.id.Login_Btn);

        sharedPreferences = getSharedPreferences("loginRef" , MODE_PRIVATE);  //3shan Remember me
        saveCheckBox = (CheckBox)findViewById(R.id.checkBox);
        editor = sharedPreferences.edit();

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        savelogin = sharedPreferences.getBoolean("savelogin" , true);
        if (savelogin == true)  //law true yb2a rga3ly el values ely kont shaylha tani
        {
             user.setText(sharedPreferences.getString("username" , null));
             pass.setText(sharedPreferences.getString("password" , null));
        }
        else
        {
            user.setText("");
            pass.setText("");
        }
    }
    public void login()
    {
        String username = user.getText().toString();
        String password = pass.getText().toString();

        ShoppingDatabase db = new ShoppingDatabase(this);
        Cursor cursor = db.checkLogIn(username , password);
        if(cursor.isBeforeFirst())
        {
            Toast.makeText(this, "Invalid Username OR Password", Toast.LENGTH_SHORT).show();
            user.setText("");
            pass.setText("");
        }
        else {
            if (saveCheckBox.isChecked())
            {
                 editor.putBoolean("savelogin" , true);
                 editor.putString("username" , username);
                 editor.putString("password" , password);
                 editor.commit();
            }
            else
            {
                editor.putBoolean("savelogin" , false);
                editor.commit();
            }
            custId = cursor.getInt(0);
            Toast.makeText(this, "Done Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LogInActivity.this , Categories.class));

            SharedPreferences prefs = getSharedPreferences("customerID", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("custID" ,custId );
            editor.commit();
        }

    }
    public void RegisterChecked (View view)
    {
        Intent i = new Intent(LogInActivity.this , SignupActivity.class);
        startActivity(i);
    }
    public void ForgetPasswordChecked(View view)
    {
        Intent in = new Intent(LogInActivity.this , ForgetPassActivity.class);
        startActivity(in);
    }

}
