package com.e.myapplication;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Description extends AppCompatActivity {
    private TextView itemName, itemPrice, ItemDesc;
    private ImageView imgV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        itemName = findViewById(R.id.tvItemName);
        itemPrice = findViewById(R.id.tvprice);
        ItemDesc  = findViewById(R.id.tvDesc);
        imgV = findViewById(R.id.imageView3);

        if(getIntent() != null){
            Toast.makeText(this, "Got some data", Toast.LENGTH_SHORT).show();
            String name = getIntent().getStringExtra("name");
            String price = getIntent().getStringExtra("price");
            String desc = getIntent().getStringExtra("desc");
            String url = getIntent().getStringExtra("url");
            Toast.makeText(this, url, Toast.LENGTH_SHORT).show();

            try {
                URL url1 = new URL(url);
                imgV.setImageBitmap(BitmapFactory.decodeStream((InputStream) url1.getContent()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            itemName.setText(name);
            itemPrice.setText(price);
            ItemDesc.setText(desc);
        }
    }
}

