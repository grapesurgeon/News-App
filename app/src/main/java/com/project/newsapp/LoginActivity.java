package com.project.newsapp;

import static com.project.newsapp.Constants.EXTRA_ARTICLE;
import static com.project.newsapp.Constants.EXTRA_EMAIL;
import static com.project.newsapp.Constants.EXTRA_NAME;
import static com.project.newsapp.Constants.USER_PREFERENCE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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
    private SharedPreferences sp;

    private String name;
    private String email;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideTitleBar();
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginVM = new ViewModelProvider(this).get(LoginVM.class);

        sp = getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE);

        checkSession();
        btnLoginOnClick();

        //Logic Remember Me belum ada
        binding.rememberMeCheckBox.setVisibility(View.GONE);

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

        try {
        loginVM.getRetrofitInstance()
                .getUserApi()
                .login(username, password)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()){
                            name = response.body().getData().getFullName();
                            email = response.body().getData().getEmail();
                            sp.edit().putString(EXTRA_NAME, name).apply();
                            sp.edit().putString(EXTRA_EMAIL, email).apply();
                            token = response.body().getToken();
                            startAndStoreSession(token);
                            goToHomeActivity();
                        }
                        else {
                            Log.d("asdf", "onResponse: false");
                            binding.editUsername.getEditText().setText("");
                            binding.editPassword.getEditText().setText("");
                            Toast.makeText(getApplicationContext(), "User and Password not match", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("asdf", "onFailure: false");
                    }
                });
        }
        catch(NullPointerException e) {
            System.out.println("NullPointerException thrown!");
        }
    }

    private void startAndStoreSession(String token){
        SessionManagerUtil.getInstance()
                .storeUserToken(getApplicationContext(), token);
        SessionManagerUtil.getInstance()
                .startUserSession(getApplicationContext(), 30);
    }

    private void checkSession() {
        if (SessionManagerUtil.getInstance().isSessionActive(getApplicationContext(), new Date())){
            name = sp.getString(EXTRA_NAME, "");
            email = sp.getString(EXTRA_EMAIL, "");
            goToHomeActivity();
            finish();
        }
    }

    private void goToHomeActivity(){
        Intent i = new Intent(this, HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra(EXTRA_NAME, name);
        i.putExtra(EXTRA_EMAIL, email);
        startActivity(i);
    }

    private void hideTitleBar() {
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
    }
}