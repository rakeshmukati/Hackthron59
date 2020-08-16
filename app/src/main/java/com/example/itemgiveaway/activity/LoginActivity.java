package com.example.itemgiveaway.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.itemgiveaway.R;
import com.example.itemgiveaway.controllers.AuthController;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, AuthController.AuthControllerListener {

    private final String TAG = getClass().getSimpleName();
    private AppCompatEditText editEmail, editPassword;
    private AuthController controller;
    private View progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        controller = new AuthController(this);
        initUI();
    }

    private void initUI() {
        progressBar = findViewById(R.id.progressBar);
        editPassword = findViewById(R.id.editPassword);
        editEmail = findViewById(R.id.editEmail);
        findViewById(R.id.btnCreateAccount).setOnClickListener(this);
        findViewById(R.id.btnLogIn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnCreateAccount) {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            finish();
        } else if (id == R.id.btnLogIn) {
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();
            if (email.isEmpty()) {
                editEmail.setError("require!");
                return;
            }
            if (password.isEmpty()) {
                editEmail.setError("require!");
                return;
            }
            controller.login(email, password);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAuthSuccess() {
        Toast.makeText(this, "login success", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void onAuthFailed(String msg) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "login failed! " + msg, Toast.LENGTH_SHORT).show();
    }
}