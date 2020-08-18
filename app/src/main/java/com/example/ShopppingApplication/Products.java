package com.example.ShopppingApplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Products extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Intent incomingIntent = getIntent();
        String ID = incomingIntent.getStringExtra("cat_id");
        Integer cat_ID = Integer.parseInt(ID);

        ListView productList = (ListView)findViewById(R.id.productsList);
        final ArrayAdapter<Integer> adapterID = new ArrayAdapter<Integer>(this,android.R.layout.simple_list_item_1);
        ArrayAdapter<String> adapterName = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        productList.setAdapter(adapterName);

        ShoppingDatabase db = new ShoppingDatabase(this);
        final String catName = db.getCategoyName(cat_ID);
        Cursor cur = db.getProducts(cat_ID);
        while (!cur.isAfterLast())
        {
            adapterID.add(cur.getInt(0));
            adapterName.add("\n"+cur.getString(1)+"\nPrice : "+cur.getString(2)+"\n__________________________________________");
            cur.moveToNext();
        }

        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Integer prodID = adapterID.getItem(i);
                Intent intent = new Intent(Products.this, ProductInfoActivity.class);
                intent.putExtra("category_name" , catName);
                intent.putExtra("product_id" , String.valueOf(prodID));
                startActivity(intent);
            }
        });
    }
    public void Home_Clicked(View view)
    {
        Intent intent = new Intent(Products.this , Categories.class);
        startActivity(intent);
    }
    public void Search_Clicked(View view)
    {
        Intent intent = new Intent(Products.this , SearchActivity.class);
        startActivity(intent);
    }
    public void Cart_Clicked(View view)
    {
        Intent intent = new Intent(Products.this , ShoppingCart.class);
        startActivity(intent);
    }
    public void Order_Clicked(View view)
    {
        Intent intent = new Intent(Products.this , MyOrders.class);
        startActivity(intent);
    }
}