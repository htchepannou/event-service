package com.tchepannou.event.service.domain;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import org.apache.commons.codec.digest.DigestUtils;

public class Address {
    private long id;
    private String street;
    private String zipCode;
    private String city;
    private String state;
    private String country;
    private String hash;
    private int quality = -1;

    public boolean isEmpty (){
        return Strings.isNullOrEmpty(this.getCity())
                && Strings.isNullOrEmpty(this.getCountry())
                && Strings.isNullOrEmpty(this.getState())
                && Strings.isNullOrEmpty(this.getStreet())
                && Strings.isNullOrEmpty(this.getZipCode())
        ;
    }

    public String computeHash (){
        return DigestUtils.md5Hex(
                Joiner.on("").skipNulls().join(state, city, country, zipCode, street).toLowerCase()
        );
    }

    public int computeQuality () {
        return (!Strings.isNullOrEmpty(state) ? 1 : 0)
                + (!Strings.isNullOrEmpty(city) ? 2 : 0)
                + (!Strings.isNullOrEmpty(country) ?  4 : 0)
                + (!Strings.isNullOrEmpty(zipCode) ? 8 : 0)
                + (!Strings.isNullOrEmpty(street) ? 16 : 0)
        ;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHash() {
        if (hash == null){
            hash = computeHash();
        }
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getQuality() {
        if (quality < 0){
            quality = computeQuality();
        }
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }
}
