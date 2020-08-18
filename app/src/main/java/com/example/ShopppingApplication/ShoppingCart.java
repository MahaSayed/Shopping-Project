package com.example.ShopppingApplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class ShoppingCart extends AppCompatActivity implements AdapterView.OnItemLongClickListener{

    ShoppingDatabase db;
    ListView myList;
    ArrayList<ProductClass> arrayOfProducts;
    CustomAdapter adapter;
    ArrayList<String> iDArray;
    ArrayList<String> quantityArray;
    String id;

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

        Integer productID = Integer.parseInt(iDArray.get(position));
        db.deleteItem(productID);
        iDArray.remove(position);
        InsertIntoAdapter(iDArray);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        myList = (ListView) findViewById(R.id.cart_list);
        iDArray = new ArrayList<String>();
        db = new ShoppingDatabase(this);
        Cursor cursor = db.fetchCart();
        if (!cursor.isAfterLast())
        {
            while (!cursor.isAfterLast())
            {
                iDArray.add(String.valueOf(cursor.getInt(0)));
                cursor.moveToNext();
            }
        }
        else
        {
            Toast.makeText(this, "Shopping Cart Is Empty", Toast.LENGTH_SHORT).show();
        }
        InsertIntoAdapter(iDArray);
        myList.setItemsCanFocus(false);
        myList.setOnItemLongClickListener(this);

    }
    public void InsertIntoAdapter(ArrayList<String> array)
    {
        ProductClass product;
        arrayOfProducts = new ArrayList<ProductClass>();

        db = new ShoppingDatabase(this);
        for (int i = 0; i < iDArray.size(); i++) {
            Cursor cursor = db.getProductInfo(Integer.parseInt(iDArray.get(i)));

            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            String quantity  = String.valueOf(db.getQuantity(Integer.parseInt(iDArray.get(i))));
            product = new ProductClass(id,name, quantity, price);
            arrayOfProducts.add(product);
        }
        adapter = new CustomAdapter(this, 0, arrayOfProducts);
        myList.setAdapter(adapter);
    }
    public void AddProductBtn(View view) {
        Intent intent = new Intent(ShoppingCart.this, Categories.class);
        startActivity(intent);
    }
    public void OrderButton(View view)
    {
        if (myList.getCount()>0) {

            quantityArray = new ArrayList<String>();
            quantityArray = adapter.getQuantity();

            Intent in = new Intent(ShoppingCart.this, OrderActivity.class);
            in.putExtra("productsID", iDArray);
            in.putExtra("productsQuantity", quantityArray);
            startActivity(in);
        }
        else{
            Toast.makeText(this, "Shopping Cart is empty", Toast.LENGTH_SHORT).show();
        }

    }
    public void ShowPriceBtn(View view)
    {
        double totalPrice = 0.0;
        Cursor cursor = db.fetchCart();
        while (!cursor.isAfterLast())
        {
            Integer id = cursor.getInt(0);
            Integer productQuantity = cursor.getInt(1);
            String price = db.getProductPrice(id);
            Double productPrice = Double.parseDouble(price);
            totalPrice += productQuantity*productPrice;
            cursor.moveToNext();
        }
        Toast.makeText(this, String.valueOf(totalPrice), Toast.LENGTH_LONG).show();
    }
    public void Search_Clicked(View view)
    {
        Intent intent = new Intent(ShoppingCart.this , SearchActivity.class);
        startActivity(intent);
    }
    public void Home_Clicked(View view)
    {
        Intent intent = new Intent(ShoppingCart.this , Categories.class);
        startActivity(intent);
    }
    public void Order_Clicked(View view)
    {
        Intent intent = new Intent(ShoppingCart.this , MyOrders.class);
        startActivity(intent);
    }
}
