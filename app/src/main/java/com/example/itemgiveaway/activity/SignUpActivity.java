package com.example.itemgiveaway.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.itemgiveaway.R;
import com.example.itemgiveaway.controllers.AuthController;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, AuthController.AuthControllerListener {
    private final String TAG = getClass().getSimpleName();
    private AppCompatEditText editEmail, editPassword, editName, editPasswordConfirm, editPhone;
    private AuthController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        controller = new AuthController(this);
        initUI();
    }

    private void initUI() {
        findViewById(R.id.btnLogIn).setOnClickListener(this);
        findViewById(R.id.btnCreateAccount).setOnClickListener(this);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);
        editPassword = findViewById(R.id.editPassword);
        editPasswordConfirm = findViewById(R.id.editConfirmPassword);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnLogIn) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else if (id == R.id.btnCreateAccount) {
            String name = editName.getText().toString();
            String email = editEmail.getText().toString().trim();
            String phone = editPhone.getText().toString();
            String password = editPassword.getText().toString().trim();
            String passwordConfirm = editPasswordConfirm.getText().toString();
            if (name.isEmpty()) {
                editName.setError("require!");
                return;
            }
            if (email.isEmpty()) {
                editEmail.setError("require!");
                return;
            }
            if (phone.isEmpty()) {
                editPhone.setError("require!");
                return;
            }
            if (password.isEmpty()) {
                editPassword.setError("require!");
                return;
            }
            if (passwordConfirm.isEmpty()) {
                editPasswordConfirm.setError("require!");
                return;
            }

            if (!password.equals(passwordConfirm)) {
                editPasswordConfirm.setError("password not match");
                return;
            }

            controller.createAccount(name, email, phone, password);
        }
    }

    @Override
    public void onAuthSuccess() {
        //todo start suitable activity
        Toast.makeText(this, "account created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}