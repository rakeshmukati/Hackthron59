package com.example.itemgiveaway.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.itemgiveaway.R;
import com.example.itemgiveaway.activity.LoginActivity;
import com.example.itemgiveaway.interfaces.OnSuccessListener;
import com.example.itemgiveaway.model.Address;
import com.example.itemgiveaway.model.User;
import com.example.itemgiveaway.utils.AuthenticationManager;

public class AccountFragment extends Fragment {
    private EditText nameEdit, emailEdit, phoneEdit, pinCodeEdit, streetAddressEdit;
    private User user;

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
        super.onViewCreated(view, savedInstanceState);
        nameEdit = view.findViewById(R.id.nameEdit);
        emailEdit = view.findViewById(R.id.emailEdit);
        phoneEdit = view.findViewById(R.id.phoneEdit);
        pinCodeEdit = view.findViewById(R.id.pinCodeEdit);
        streetAddressEdit = view.findViewById(R.id.streetAddressEdit);
        final View progressBar = view.findViewById(R.id.progressBar);

        AuthenticationManager.getInstance().getCurrentUser(new AuthenticationManager.OnUserCallbackListener() {
            @Override
            public void onUserDetailReceived(User currentUser) {
                nameEdit.setText(currentUser.getName());
                emailEdit.setText(currentUser.getEmail());
                phoneEdit.setText(currentUser.getPhone());
                Address address = currentUser.getAddress();
                if (address != null) {
                    pinCodeEdit.setText(new StringBuilder().append(address.getPinCode()));
                    streetAddressEdit.setText(address.getStreetAddress());
                }
                user = currentUser;
            }
        });


        view.findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthenticationManager.getInstance().logout();
                startActivity(new Intent(requireContext(), LoginActivity.class));
                requireActivity().finish();
            }
        });

        view.findViewById(R.id.btnUpdateProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEdit.getText().toString();
                String email = emailEdit.getText().toString();
                String phone = phoneEdit.getText().toString();
                String pinCode = pinCodeEdit.getText().toString();
                String streetAddress = streetAddressEdit.getText().toString();

                if (name.length() == 0) {
                    nameEdit.setError("require!");
                    return;
                }
                if (email.length() == 0) {
                    emailEdit.setError("require!");
                    return;
                }
                if (phone.length() == 0) {
                    phoneEdit.setError("require!");
                    return;
                }
                if (pinCode.length() == 0) {
                    pinCodeEdit.setError("require!");
                    return;
                }
                if (streetAddress.length() == 0) {
                    streetAddressEdit.setError("require!");
                    return;
                }

                user.setName(name);
                user.setEmail(email);
                user.setPhone(phone);
                Address address = new Address();
                address.setPinCode(Long.parseLong(pinCode));
                address.setStreetAddress(streetAddress);
                user.setAddress(address);
                progressBar.setVisibility(View.VISIBLE);
                AuthenticationManager.getInstance().updateUser(user, new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(requireContext(), "profile  updated", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}