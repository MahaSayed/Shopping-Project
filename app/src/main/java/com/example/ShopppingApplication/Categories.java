package com.example.ShopppingApplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import java.util.ArrayList;

public class Categories extends AppCompatActivity {

    ArrayList<Integer> icons;
    ArrayList<CategoryClass> arrayOfCategories;
    CategoryAdapter adapter;
    ImageButton home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        icons = new ArrayList<Integer>();
        icons.add(R.drawable.mobile_icon);
        icons.add(R.drawable.laptop_icon);

        home = (ImageButton)findViewById(R.id.home_page);
        ListView catList = (ListView)findViewById(R.id.cat_list);

        arrayOfCategories = new ArrayList<CategoryClass>();
        ShoppingDatabase db = new ShoppingDatabase(this);
        Cursor cur = db.getCategories();
        Integer n = 0;
        while (!cur.isAfterLast())
        {
            Integer imgage = icons.get(n);
            String name = cur.getString(1);
            CategoryClass cat = new CategoryClass(imgage , name);
            arrayOfCategories.add(cat);
            cur.moveToNext();
            n++;
        }
        adapter = new CategoryAdapter(this, R.layout.activity_categories, arrayOfCategories);
        catList.setAdapter(adapter);

        catList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Categories.this , Products.class);
                intent.putExtra("cat_id" , String.valueOf(i+1)); // category ID
                startActivity(intent);
            }
        });
    }
    public void Search_Clicked(View view)
    {
        Intent intent = new Intent(Categories.this , SearchActivity.class);
        startActivity(intent);

    }
    public void Cart_Clicked(View view)
    {
        Intent intent = new Intent(Categories.this , ShoppingCart.class);
        startActivity(intent);
    }
    public void Order_Clicked(View view)
    {
        Intent intent = new Intent(Categories.this , MyOrders.class);
        startActivity(intent);
    }
}
