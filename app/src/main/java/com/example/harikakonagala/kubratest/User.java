package com.example.harikakonagala.kubratest;

/**
 * Created by Harika Konagala on 6/25/2017.
 */

public class User {
    String id;
    String name;
    String username;
    Address address;

    public User(String id, String name, String username, Address address) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
