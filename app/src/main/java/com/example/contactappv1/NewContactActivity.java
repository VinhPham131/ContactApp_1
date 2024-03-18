package com.example.contactappv1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contactappv1.databinding.NewContactActivityBinding;

public class NewContactActivity extends AppCompatActivity {
    private Contact contact;
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

        Intent intent = getIntent();
        int position = intent.getIntExtra("contact", 0);
        contact = MainActivity.getInstance().contactList.get(position);
        binding.etName.setText(contact.getName());
        binding.etPhone.setText(contact.getPhone());
        binding.etEmail.setText(contact.getEmail());

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
                        int id = contact.getId();
                        String name = binding.etName.getText().toString();
                        String phone = binding.etPhone.getText().toString();
                        String email = binding.etEmail.getText().toString();
                        contactDao.updateContact(id, name, phone, email);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MainActivity.getInstance().contactAdapter.notifyDataSetChanged();
                                finish();
                            }
                        });
                    }
                });
            }
        });
//        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AsyncTask.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        contactDao.delete(contact);
//                        MainActivity.getInstance().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                MainActivity.getInstance().contactList.remove(contact);
//                                MainActivity.getInstance().contactAdapter.notifyDataSetChanged();
//                                finish();
//                            }
//                        });
//                    }
//                });
//            }
//        });
    }
}
