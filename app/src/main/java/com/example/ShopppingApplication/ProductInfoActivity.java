package com.example.ShopppingApplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProductInfoActivity extends AppCompatActivity {

    ShoppingDatabase db;
    public static ArrayList<String> prodID = new ArrayList<String>(); // 3shan law das mara 3la add to cart maydossh tani
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);


        TextView name = (TextView)findViewById(R.id.productName);
        TextView msg = (TextView)findViewById(R.id.message_txt);
        TextView catname = (TextView)findViewById(R.id.type_name);
        TextView price = (TextView)findViewById(R.id.productPrice);
        TextView quantity = (TextView)findViewById(R.id.quantity);

        Intent incomingIntent = getIntent();
        String cat_name = incomingIntent.getStringExtra("category_name");
        String ID = incomingIntent.getStringExtra("product_id");
        final Integer productID = Integer.parseInt(ID);

        catname.setText(cat_name);
        db = new ShoppingDatabase(this);
            Cursor cursor = db.getProductInfo(productID);
            if(!cursor.isAfterLast())
            {
            name.setText("____"+cursor.getString(1)+"____");
            price.setText(cursor.getString(2));
            quantity.setText(cursor.getString(3));
            }


        final Button addCart = (Button)findViewById(R.id.addToCart);
        if (quantity.getText().toString().equals("0"))
        {
            addCart.setEnabled(false);
            msg.setText("Sorry, this item became out of stock");
        }
        Cursor cur = db.fetchCart();
        if (!cur.isAfterLast())
        {
            while (!cur.isAfterLast())
            {
                Integer id = cur.getInt(0);
                if (id==productID)
                {
                    addCart.setEnabled(false);
                    Toast.makeText(this, "Already Added", Toast.LENGTH_SHORT).show();
                    break;
                }
                cur.moveToNext();
            }
        }
        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addCart.setEnabled(false);
                db.addtoCart(productID , 1);
            }
        });
    }
    public void Home_Clicked(View view)
    {
        Intent intent = new Intent(ProductInfoActivity.this , Categories.class);
        startActivity(intent);
    }
    public void Search_Clicked(View view)
    {
        Intent intent = new Intent(ProductInfoActivity.this , SearchActivity.class);
        startActivity(intent);
    }
    public void Cart_Clicked(View view)
    {
        Intent intent = new Intent(ProductInfoActivity.this , ShoppingCart.class);
        startActivity(intent);
    }
    public void Order_Clicked(View view)
    {
        Intent intent = new Intent(ProductInfoActivity.this , MyOrders.class);
        startActivity(intent);
    }
}
