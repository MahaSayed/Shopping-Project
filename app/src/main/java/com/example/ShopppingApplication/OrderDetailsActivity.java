package com.example.ShopppingApplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class OrderDetailsActivity extends AppCompatActivity {

    ArrayList<String> iDArray;
    ArrayList<Integer> QArray;
    ShoppingDatabase db;
    Integer custID;
    String address , date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

            iDArray = new ArrayList<String>();
            iDArray = getIntent().getStringArrayListExtra("productsID");
            address = getIntent().getStringExtra("address");
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            date = df.format(c.getTime());

        SharedPreferences prefs = getSharedPreferences("customerID", MODE_PRIVATE);
        custID = prefs.getInt("custID", 0);  //ely 3amal logIn

        TextView totalPrice = (TextView)findViewById(R.id.total_price);
        TextView address_text = (TextView)findViewById(R.id.address_text);
        ListView myList = (ListView)findViewById(R.id.order_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1);
        myList.setAdapter(adapter);
        QArray = new ArrayList<Integer>();
        db = new ShoppingDatabase(this);

        double total = 0.0;
        Cursor cursor = db.fetchCart();
        while (!cursor.isAfterLast())
        {
            Integer id = cursor.getInt(0);
            Integer quantity = cursor.getInt(1);
            QArray.add(quantity);
            Cursor cur = db.getProductInfo(id);
            adapter.add(""+cur.getString(1) + "\nPrice : " + cur.getString(2)+"\nQuantity : "+ quantity + "\n____________________________________________");
            String price = db.getProductPrice(id);
            Double prodPrice = Double.parseDouble(price);
            total += quantity * prodPrice;
            cursor.moveToNext();
        }
        totalPrice.setText(String.valueOf(total)+" EGP");
        address_text.setText("Address :   "+address);
    }
    public void CheckOut_Clicked(View view) {
        // add values to Orders table
        db.CreateNewOrder(custID, date, address);
        Integer OrderID = db.getLastOrderID(); //3shan table el order_details

        for (int j = 0; j < iDArray.size(); j++) {
            db.OrderDetails(OrderID , Integer.parseInt(iDArray.get(j)),QArray.get(j));
            Integer available = db.getProductQuantity(Integer.parseInt(iDArray.get(j)));
            Integer newQty = available - QArray.get(j);
            db.editProduct(Integer.parseInt(iDArray.get(j)),newQty);
        }
        db.deleteCart();
        Toast.makeText(this, "The Order will arrive in 5 to 7 working days", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(OrderDetailsActivity.this, Categories.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.edit_order) {

            Intent i = new Intent(OrderDetailsActivity.this, ShoppingCart.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
