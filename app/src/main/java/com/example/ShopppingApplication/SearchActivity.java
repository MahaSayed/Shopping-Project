package com.example.ShopppingApplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ImageButton search = (ImageButton)findViewById(R.id.search_page);

        final Button text = (Button)findViewById(R.id.searchTxtBtn);
        final Button voice = (Button)findViewById(R.id.searchVoiceBtn);

        text.setBackgroundColor(Color.parseColor("#DCDCDC"));

        setFragment(new SearchTextFragment());

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SearchTextFragment());
                voice.setBackgroundColor(000000);
                text.setBackgroundColor(Color.parseColor("#DCDCDC"));
            }
        });
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SearchVoiceFragment());
                text.setBackgroundColor(000000);
                voice.setBackgroundColor(Color.parseColor("#DCDCDC"));
            }
        });

    }
    public void setFragment(android.support.v4.app.Fragment f)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.frame , f);
        ft.commit();
    }
    public void Home_Clicked(View view)
    {
        Intent intent = new Intent(SearchActivity.this , Categories.class);
        startActivity(intent);

    }
    public void Cart_Clicked(View view)
    {
        Intent intent = new Intent(SearchActivity.this , ShoppingCart.class);
        startActivity(intent);
    }
}
