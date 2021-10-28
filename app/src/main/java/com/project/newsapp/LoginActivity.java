package com.project.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.project.newsapp.databinding.ActivityLoginBinding;
import com.project.newsapp.home.HomeActivity;
import com.project.newsapp.model.LoginRequest;
import com.project.newsapp.model.LoginResponse;
import com.project.newsapp.viewmodel.LoginVM;

import java.util.Base64;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private String username;
    private String password;

    private LoginVM loginVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideTitleBar();
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkSession();
        btnLoginOnClick();

    }

    private void btnLoginOnClick() {
        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    private void validate() {

        username = binding.editUsername.getEditText().getText().toString();
        password = binding.editPassword.getEditText().getText().toString();

        if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password)){
            Toast.makeText(this, "Username and Password cannot be blank!", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginRequest loginRequest = new LoginRequest(username, password);

        //Always get NullPointer (?)
        try {
        loginVM.getRetrofitInstance()
                .getUserApi()
                .login(loginRequest)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.body().getStatus() == "true"){
                            startAndStoreSession();
                            startMainActivity();
                            System.out.println("If true");
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }
                });
        }
        catch(NullPointerException e) {
            System.out.println("NullPointerException thrown!");
        }

        //Delete if NullPointer is Done
        startAndStoreSession();
        startMainActivity();
    }

    private void startAndStoreSession(){
        SessionManagerUtil.getInstance()
                .storeUserToken(getApplicationContext(), generateToken(username, password));
        SessionManagerUtil.getInstance()
                .startUserSession(getApplicationContext(), 10);
    }

    private String generateToken(String username, String password){
        String feeds = username+":"+password;
        String token = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            token = Base64.getEncoder().encodeToString(feeds.getBytes());
        } else {
            token = feeds;
        }
        return token;
    }

    private void startMainActivity(){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void checkSession() {
        if (SessionManagerUtil.getInstance().isSessionActive(getApplicationContext(), new Date())){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        } //else setContentView(R.layout.activity_login);
    }

    private void hideTitleBar() {
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
    }
}