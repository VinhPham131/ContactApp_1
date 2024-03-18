package com.example.contactappv1;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Dao;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM contact")
    List<Contact> getAll();

    @Query("SELECT * FROM contact WHERE id IN (:id)")
    List<Contact> loadAllByIds(int[] id);

    @Query("SELECT * FROM contact WHERE name LIKE :name LIMIT 1")
    Contact findByName(String name);

    @Insert
    void insertAll(Contact... contacts);

    // Uncomment the following method if you want to update contacts
    // @Update
    // void updateContact(Contact contact);

    @Query("UPDATE contact SET name = :name, phone = :phone, email = :email WHERE id = :id")
    void updateContact(int id, String name, String phone, String email);
    @Delete
    void delete(Contact contact);
}

