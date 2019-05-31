package com.e.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e.myapplication.INTERFACE_API.USER_INTERFACE;
import com.e.myapplication.Model_API.USER;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Signup extends AppCompatActivity implements View.OnClickListener{
    private EditText username, firstname, lastname, password;
    private Button btnsignup;
    public static final String URL = "http://192.168.0.106:3000/";
    private Retrofit retrofit ;
    private USER_INTERFACE user_interface;
    private Api_Connection api_connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        api_connection =  new Api_Connection();
        init();
        getConnectionToAPI();
    }

    private void getConnectionToAPI() {
        retrofit = api_connection.getRetrofit();
        user_interface= api_connection.getUser_interface();
    }

    private void init() {
        username = findViewById(R.id.etUsername);
        firstname = findViewById(R.id.etFirstName);
        lastname = findViewById(R.id.etLastname);
        password = findViewById(R.id.etPassword);
        btnsignup = findViewById(R.id.btnsignup);
        btnsignup.setOnClickListener(this);
    }

    private void RegisterUser(USER user){
        Call<Void> call = user_interface.registerUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(Signup.this, "User registered Succesfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Signup.this,Login.class));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Signup.this, "Could not load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnsignup){
            if(TextUtils.isEmpty(username.getText())){
                username.setError("Please Enter Username");
                return;
            }
            if(TextUtils.isEmpty(firstname.getText())){
                firstname.setError("Please Enter Username");
                return;
            }
            if(TextUtils.isEmpty(lastname.getText())){
                lastname.setError("Please Enter Username");
                return;
            }
            if(TextUtils.isEmpty(password.getText())){
                password.setError("Please Enter Username");
                return;
            }
            USER data = new USER(username.getText().toString(), firstname.getText().toString(), lastname.getText().toString(), password.getText().toString());
            RegisterUser(data);
        }
    }
}
