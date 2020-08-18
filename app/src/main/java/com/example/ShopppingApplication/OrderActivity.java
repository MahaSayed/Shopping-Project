package com.example.ShopppingApplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    ArrayList<String> iDArray;
    ArrayList<String> quantityArray;
    EditText city;
    EditText street;
    EditText building;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        iDArray = new ArrayList<String>();
        iDArray = getIntent().getStringArrayListExtra("productsID");
        quantityArray = new ArrayList<String>();
        quantityArray = getIntent().getStringArrayListExtra("productsQuantity");

    }
    public void Confirm_Btn_Clicked(View view)
    {
        city = (EditText)findViewById(R.id.city_txt);
        street = (EditText)findViewById(R.id.street_txt);
        building = (EditText)findViewById(R.id.building_txt);
        String cityString = city.getText().toString();
        String streetString = street.getText().toString();
        String buildingString = building.getText().toString();
        if (cityString.equals(""))
        {
            Toast.makeText(this, "Please Enter Your City", Toast.LENGTH_SHORT).show();
        }
        else if (streetString.equals(""))
        {
            Toast.makeText(this, "Please Enter Your Street", Toast.LENGTH_SHORT).show();
        }
        else if (buildingString.equals(""))
        {
            Toast.makeText(this, "Please Enter Your Building", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent i = new Intent(OrderActivity.this , OrderDetailsActivity.class);
            i.putExtra("productsID" , iDArray);
            i.putExtra("productsQuantity" , quantityArray);
            i.putExtra("address" , cityString + "," + streetString + "," + buildingString);
            startActivity(i);
        }
    }
    public void Map_Btn_Clicked(View view)
    {
        Intent i = new Intent(OrderActivity.this , MapsActivity.class);
        i.putExtra("productsID" , iDArray);
        i.putExtra("productsQuantity" , quantityArray);
        startActivity(i);
    }

}
