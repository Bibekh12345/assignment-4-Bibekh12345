package com.e.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.e.myapplication.INTERFACE_API.Item_Interface;
import com.e.myapplication.Model_API.ImageRespoonse;
import com.e.myapplication.Model_API.Item_Model;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Item extends AppCompatActivity implements View.OnClickListener {
    private ImageButton imgB;
    private Button btnisert;
    private ImageView imgV;
    private static final int imageRequestCode = 0;
    private String imagePath;
    private Retrofit retrofit;
    private Item_Interface item_interface;
    private String ImageName;
    private Button back;
    private EditText itemName, itemPrice, itemDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        init();
    }

    private void init() {
        imgB = findViewById(R.id.imageButton);
        btnisert = findViewById(R.id.insertItem);
        imgV = findViewById(R.id.imageView);
        imgB.setOnClickListener(this);
        btnisert.setOnClickListener(this);
        retrofit = new Api_Connection().getRetrofit();
        item_interface = retrofit.create(Item_Interface.class);
        itemName = findViewById(R.id.etItemName);
        itemPrice = findViewById(R.id.etItemPrice);
        itemDescription = findViewById(R.id.etItemDesc);
        back = findViewById(R.id.btnbad);
        back.setOnClickListener(this);
    }

    private void clear(){
        itemName.setText("");
        itemPrice.setText("0");
        itemDescription.setText("");
        imgV.setImageBitmap(null);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.insertItem){
            if(TextUtils.isEmpty(itemName.getText())){
                itemName.setError("Please Enter Item");
                return;
            }
            if(TextUtils.isEmpty(itemPrice.getText())){
                itemPrice.setError("Please Enter Item");
                return;
            }
            if(TextUtils.isEmpty(itemDescription.getText())){
                itemDescription.setError("Please Enter Item");
                return;
            }
            putUpload();

            if(!ImageName.isEmpty()){
                insertData();
                clear();
            }

        }else if(v.getId() == R.id.imageButton){
            startActivityForResult(new Intent(Intent.ACTION_PICK).setType("image/*"),imageRequestCode);
        }else if(v.getId() == R.id.btnbad){
            startActivity(new Intent(Item.this,Dashboard.class));
        }
    }

    private void insertData() {
        if(ImageName == null && ImageName.isEmpty()){
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            return;
        }
        Item_Model item = new Item_Model(itemName.getText().toString(), itemPrice.getText().toString(), ImageName,itemDescription.getText().toString());
        Call<Void> call = item_interface.insertItem(item);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(Item.this, "data inserted Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void putUpload(){
        if(imagePath.isEmpty()){
            Toast.makeText(this, "please select image", Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("itemImage",file.getName(),requestBody);
        Call<ImageRespoonse> responseCall =  item_interface.uploadImage(body);
        StrictMode();
        try {
            Response<ImageRespoonse> imageResponseResponse = responseCall.execute();
            ImageName = imageResponseResponse.body().getFilename();
        }catch (IOException ex){
            Toast.makeText(this, "Error uploading image", Toast.LENGTH_SHORT).show();
            System.out.println(ex.getMessage());
        }
    }

    private void StrictMode() {
        StrictMode.ThreadPolicy stict =new  StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(stict);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == imageRequestCode){
                if(data == null){
                    Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show();
                    return;
                }
                Uri uri = data.getData();
                imagePath = getImagePath(uri);
                Toast.makeText(this, imagePath, Toast.LENGTH_SHORT).show();
                showimage(imagePath);
            }
        }
    }

    private String getImagePath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader =  new CursorLoader(getApplicationContext(), uri, projection,null,null,null);
        Cursor cursor = loader.loadInBackground();
        int columnindex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(columnindex);
        cursor.close();
        return result;
    }
    private void showimage(String imagePath) {
        File imgFile = new File(imagePath);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imgV.setImageBitmap(myBitmap);
        }

    }
}
