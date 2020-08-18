package com.example.ShopppingApplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
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

import java.util.ArrayList;

public class SearchVoiceFragment extends Fragment {
    EditText searchTxt;
    ListView myList;
    Cursor cursor;
    ShoppingDatabase db;
    int voiceCode = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_search_voice, container, false);

        searchTxt = (EditText)root.findViewById(R.id.voice_text);
        myList = (ListView)root.findViewById(R.id.voice_list);

        db = new ShoppingDatabase(getContext());

        Button searchBtn = (Button)root.findViewById(R.id.VoiceBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                startActivityForResult(intent , voiceCode);
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == voiceCode && resultCode==getActivity().RESULT_OK)
        {
            ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            searchTxt.setText(text.get(0));

            final ArrayAdapter<Integer> adapterID = new ArrayAdapter<Integer>(getActivity() , android.R.layout.simple_list_item_1);
            ArrayAdapter<String> adapterName = new ArrayAdapter<String>(getActivity() , android.R.layout.simple_list_item_1);
            final ArrayAdapter<Integer> adapter_cat_ID =new ArrayAdapter<Integer>(getActivity(),android.R.layout.simple_list_item_1);

            myList.setAdapter(adapterName);

            String prodName = searchTxt.getText().toString().toUpperCase();
            Cursor cursor = db.SearchProduct(prodName);
            adapterName.clear();
            adapterID.clear();
            adapter_cat_ID.clear();

            if (!cursor.isAfterLast())
            {
                while (!cursor.isAfterLast())
                {
                    adapterID.add(cursor.getInt(0));
                    adapterName.add(cursor.getString(1));
                    adapter_cat_ID.add(cursor.getInt(4));
                    cursor.moveToNext();
                }
            }
            else
            {
                Toast.makeText(getActivity(), "SORRY! No Products Matched What You Said!", Toast.LENGTH_SHORT).show();
            }
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
        }
    }
}
