package com.example.itemgiveaway.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.itemgiveaway.R;
import com.example.itemgiveaway.model.User;
import com.example.itemgiveaway.utils.AuthenticationManager;

public class AccountFragment extends Fragment {
    private EditText name,email,phone;
    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        name = view.findViewById(R.id.name_edit);
        email = view.findViewById(R.id.email_edit);
        phone = view.findViewById(R.id.phone_edit);

        AuthenticationManager.getInstance().getCurrentUser(new AuthenticationManager.OnUserCallbackListener() {
            @Override
            public void onUserDetailReceived(User currentUser) {
                name.setText(currentUser.getName());
                email.setText(currentUser.getEmail());
                phone.setText(currentUser.getNumber());
                Log.d("DATA=================",currentUser.getName());
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}