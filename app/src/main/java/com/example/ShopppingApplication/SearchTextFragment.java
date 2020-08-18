package com.example.ShopppingApplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class SearchTextFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search_text, container, false);

        final EditText search = (EditText)rootView.findViewById(R.id.searchtxt);
        Button textBtn = (Button)rootView.findViewById(R.id.text_Btn);
        ListView myList = (ListView)rootView.findViewById(R.id.search_list);

        final ArrayAdapter<Integer> adapterID =new ArrayAdapter<Integer>(getActivity(),android.R.layout.simple_list_item_1);
        final ArrayAdapter<String> adapter =new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1);
        final ArrayAdapter<Integer> adapter_cat_ID =new ArrayAdapter<Integer>(getActivity(),android.R.layout.simple_list_item_1);

        myList.setAdapter(adapter);

        final ShoppingDatabase db = new ShoppingDatabase(getContext());
        textBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(search.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(), "Please Try again", Toast.LENGTH_SHORT).show();
                }
                else {
                    String text = search.getText().toString().toUpperCase();
                    Cursor cur = db.SearchProduct(text);
                    adapter.clear();
                    adapterID.clear();
                    adapter_cat_ID.clear();
                    if (!cur.isAfterLast())
                    {
                        while (!cur.isAfterLast())
                        {
                            adapterID.add(cur.getInt(0));
                            adapter.add(cur.getString(1));
                            adapter_cat_ID.add(cur.getInt(4));
                            cur.moveToNext();
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "SORRY! No Products Matched What You Wrote!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String catName = db.getCategoyName(adapter_cat_ID.getItem(i));
                Integer prodID = adapterID.getItem(i);
                Intent intent = new Intent(getActivity() , ProductInfoActivity.class);
                intent.putExtra("category_name" , catName);
                intent.putExtra("product_id" , String.valueOf(prodID));
                startActivity(intent);
            }
        });
        return rootView;
    }
}
