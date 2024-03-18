package com.example.contactappv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.contactappv1.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    ArrayList<Contact> contactList;
    ContactAdapter contactAdapter;
    private AppDatabase appDatabase;
    ContactDao contactDao;
    private Contact contact;
    private static MainActivity instance;
    public MainActivity() {
        instance = this;
    }
    public static MainActivity getInstance(){
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        binding.rvContact.setLayoutManager(new LinearLayoutManager(this));
        contactList = new ArrayList<Contact>();
        contactAdapter = new ContactAdapter(contactList, MainActivity.this);
        binding.rvContact.setAdapter(contactAdapter);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase = AppDatabase.getInstance(getApplicationContext());
                contactDao = appDatabase.contactDao();
//                contactDao.insertAll(new Contact("Pham Quang Vinh", "1301", "vinhpham@gmail.com"));
//                contactDao.insertAll(new Contact("Le Thi Ngoc Van", "2705", "ngocvanle@gmail.com"));
//                contactDao.insertAll(new Contact("Chau Hai My", "1005", "haimy@gmail.com"));
//                contactDao.insertAll(new Contact("Dao Xuan Nguyen", "0805", "billdao@gmail.com"));
//                contactDao.insertAll(new Contact("Tran Duong Quynh Trang", "0710", "bimngu@gmail.com"));
//                contactDao.insertAll(new Contact("Tran Huynh Tram Anh", "1507", "vinhpham@gmail.com"));
                final ArrayList<Contact> contacts = new ArrayList<>(contactDao.getAll());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        contactList.clear();
                        contactList.addAll(contacts);
                        contactAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdd = new Intent(MainActivity.this, AddContact.class);
                startActivity(intentAdd);
            }
        });
        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = binding.etSearch.getText().toString().toLowerCase();
                ArrayList<Contact> searchList = new ArrayList<>();
                for(Contact c : contactList) {
                    if(c.getName().toLowerCase().contains(searchText) ||
                        c.getPhone().toLowerCase().contains(searchText) ||
                            c.getEmail().toLowerCase().contains(searchText)) {
                        searchList.add(c);
                    }
                }
                contactAdapter = new ContactAdapter(searchList, MainActivity.this);
                binding.rvContact.setAdapter(contactAdapter);
                contactAdapter.notifyDataSetChanged();
            }
        });
        binding.btnCloseSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.etSearch.setText("");
                contactAdapter = new ContactAdapter(contactList, MainActivity.this);
                binding.rvContact.setAdapter(contactAdapter);
                contactAdapter.notifyDataSetChanged();
            }
        });
    }
}