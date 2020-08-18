package com.example.ShopppingApplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class CategoryAdapter extends ArrayAdapter<CategoryClass> {

    public ArrayList<CategoryClass> records;
    public CategoryAdapter( Context context, int resource , ArrayList<CategoryClass> records) {
        super(context, resource , records);
        this.records = records;
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {

        CategoryClass item = getItem(position);
        // note: add hena eny lazm at2ked eno msh used asln
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_categories, parent, false);
        }

        ImageView icon = (ImageView)convertView.findViewById(R.id.img_icon);
        TextView catName = (TextView)convertView.findViewById(R.id.cat_name);

        icon.setImageResource(item.categoryImage);
        catName.setText(item.name);

        return convertView;
    }
}
