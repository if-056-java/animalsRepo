package com.animals.app.domain;

import java.io.Serializable;

/**
 * Created by oleg on 22.07.2015.
 */
public class Address implements Serializable {

    private Integer id;
    private String country;
    private String region;
    private String town;
    private String street;
    private int postIndex;

    public Address() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (postIndex != address.postIndex) return false;
        if (country != null ? !country.equals(address.country) : address.country != null) return false;
        if (!id.equals(address.id)) return false;
        if (region != null ? !region.equals(address.region) : address.region != null) return false;
        if (street != null ? !street.equals(address.street) : address.street != null) return false;
        if (town != null ? !town.equals(address.town) : address.town != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (town != null ? town.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + postIndex;
        return result;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", region='" + region + '\'' +
                ", town='" + town + '\'' +
                ", street='" + street + '\'' +
                ", postIndex='" + postIndex + '\'' +
                '}';
    }
}
