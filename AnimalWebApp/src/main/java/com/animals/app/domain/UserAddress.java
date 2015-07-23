package com.example.Model.Entity;

/**
 * Created by oleg on 22.07.2015.
 */
public class UserAddress {

    private Integer userId;
    private String country;
    private String region;
    private String town;
    private String street;
    private int postIndex;

    public UserAddress() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getPostIndex() {
        return postIndex;
    }

    public void setPostIndex(int postIndex) {
        this.postIndex = postIndex;
    }

    @Override
    public String toString() {
        return "UserAddress{" +
                "userId=" + userId +
                ", country='" + country + '\'' +
                ", region='" + region + '\'' +
                ", town='" + town + '\'' +
                ", street='" + street + '\'' +
                ", postIndex='" + postIndex + '\'' +
                '}';
    }
}
