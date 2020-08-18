package com.example.ShopppingApplication;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class CustomAdapter extends ArrayAdapter<ProductClass> {

    public ArrayList<ProductClass> records;
    public ArrayList<String> quantity;
    ShoppingDatabase db;
    public CustomAdapter( Context context ,int d, ArrayList<ProductClass> records) {
        super(context, d ,records );
        this.records = records;
        quantity = new ArrayList<String>();
    }


    @Override
    public View getView(final int position,  View convertView,  ViewGroup parent) {
        // Get the data item for this position
        final ProductClass item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_list, parent, false);
        }

        db = new ShoppingDatabase(getContext());

        final TextView prodQuantity =(TextView)convertView.findViewById(R.id.quantity_txt);

        TextView productName =(TextView)convertView.findViewById(R.id.prod_name);
        TextView productPrice =(TextView)convertView.findViewById(R.id.prod_price);

        Button plusBtn =(Button)convertView.findViewById(R.id.plus_button);
        Button minusBtn =(Button)convertView.findViewById(R.id.minus_button);

        productName.setText(item.name);
        prodQuantity.setText(item.quantity);
        productPrice.setText(item.price);

            plusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Integer quantity = null;
                    Cursor c = db.getProductInfo(Integer.parseInt(item.id));
                    if (!c.isAfterLast()) {
                        quantity = c.getInt(3);
                    }
                    Integer value = Integer.parseInt(prodQuantity.getText().toString());
                    if (quantity==value)
                    {
                        Toast.makeText(getContext(), "this is the maximum quantity", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String newValue = String.valueOf(value + 1);
                        prodQuantity.setText(newValue);
                    }
                    Integer productQuantity = Integer.parseInt(prodQuantity.getText().toString());
                    db.editQuantity(Integer.parseInt(item.id) , productQuantity);
                }
            });
            minusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Integer value = Integer.parseInt(prodQuantity.getText().toString());
                    if (value != 1) {
                            String newValue = String.valueOf(value - 1);
                            prodQuantity.setText(newValue);

                    }
                    Integer q = Integer.parseInt(prodQuantity.getText().toString());
                    db.editQuantity(Integer.parseInt(item.id) , q);
                }
            });

        return convertView;
    }
    public ArrayList<String> getQuantity()
    {

        return quantity;
    }
}