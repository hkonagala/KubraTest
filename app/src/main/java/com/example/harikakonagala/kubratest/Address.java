package com.example.harikakonagala.kubratest;

import android.location.Location;

/**
 * Created by Harika Konagala on 6/25/2017.
 */

public class Address {
    String street;
    String suite;
    String city;
    String zipcode;
    String latitude;
    String longitude;

    public Address(String street, String suite, String city, String zipcode, String latitude, String longitude) {
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipcode = zipcode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return street + ", " + suite + ", " + city + ", " + zipcode + ". Lat: " + latitude + ", Lng:" + longitude;
    }
}
