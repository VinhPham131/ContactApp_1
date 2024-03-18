package com.example.contactappv1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contactappv1.databinding.NewContactActivityBinding;

public class AddContact extends AppCompatActivity {
    private ContactDao contactDao;
    private NewContactActivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_contact_activity);

        binding = NewContactActivityBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);


        contactDao = MainActivity.getInstance().contactDao;
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        String name = binding.etName.getText().toString();
                        String phone = binding.etPhone.getText().toString();
                        String email = binding.etEmail.getText().toString();
                        contactDao.insertAll(new Contact(name, phone, email));
                        MainActivity.getInstance().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MainActivity.getInstance().contactList.add(new Contact(name, phone, email));
                                MainActivity.getInstance().contactAdapter.notifyDataSetChanged();
                                finish();
                            }
                        });
                    }
                });

            }
        });
    }
}
