package com.example.ShopppingApplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ForgetPassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        LinearLayout secondLayout = (LinearLayout) findViewById(R.id.layout2);
        secondLayout.setVisibility(LinearLayout.GONE);   //to hide the second layout

    }

    public void calendar_Btn(View view) {
        Intent i = new Intent(ForgetPassActivity.this, CalendarActivity.class);
        i.putExtra("value", "2");
        startActivityForResult(i, 42);
    }

    //lama calender te2fel lazm anady 3leha
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 42:
                String date = data.getStringExtra("date");
                TextView txt = (TextView) findViewById(R.id.dateText);
                txt.setText(date);
                break;
        }
    }

    public void OkBtn(View view) {
        ShoppingDatabase db = new ShoppingDatabase(this);

        EditText username = (EditText) findViewById(R.id.name_txt);
        TextView dateTxt = (TextView) findViewById(R.id.dateText);

        Cursor cursor = db.forgetPassword(username.getText().toString(), dateTxt.getText().toString());
        if (cursor.isBeforeFirst()) {
            Toast.makeText(this, "Please Try again!", Toast.LENGTH_SHORT).show();
        } else {
            LinearLayout firstLayout = (LinearLayout) findViewById(R.id.layout1);
            LinearLayout secondLayout = (LinearLayout) findViewById(R.id.layout2);
            firstLayout.setVisibility(LinearLayout.GONE);   //to hide the second layout
            secondLayout.setVisibility(LinearLayout.VISIBLE);
        }
    }

    public void SubmitBtn(View view) {
        ShoppingDatabase db = new ShoppingDatabase(this);

        EditText username = (EditText) findViewById(R.id.name_txt);
        EditText newPassword = (EditText) findViewById(R.id.newPass_txt);

        db.UpdatePassword(username.getText().toString() , newPassword.getText().toString());

        Toast.makeText(this, "Done Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ForgetPassActivity.this , LogInActivity.class));
    }
}
