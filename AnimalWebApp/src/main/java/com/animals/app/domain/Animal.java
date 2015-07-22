package com.animals.app.domain;

import java.lang.Boolean;import java.lang.Integer;import java.lang.Long;import java.lang.String;import java.sql.Date;

/**
 * Created by Rostyslav.Viner on 22.07.2015.
 */
public class Animal {

    private Long id;
    private AnimalSexType sex;
    private AnimalType type;
    private AnimalSize size;
    private CitesType cites;
    private String sort;
    private String transpNumber;
    private String tokenNumber;
    private Date dateOfRegister;
    private Date dateOfBirth;
    private Date dateOfSterilization;
    private String color;
    private Integer ownerID;
    private String address;
    private Boolean status;
    private String image;
    private AnimalService service;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnimalSexType getSex() {
        return sex;
    }

    public void setSex(AnimalSexType sex) {
        this.sex = sex;
    }

    public AnimalType getType() {
        return type;
    }

    public void setType(AnimalType type) {
        this.type = type;
    }

    public AnimalSize getSize() {
        return size;
    }

    public void setSize(AnimalSize size) {
        this.size = size;
    }

    public CitesType getCites() {
        return cites;
    }

    public void setCites(CitesType cites) {
        this.cites = cites;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getTranspNumber() {
        return transpNumber;
    }

    public void setTranspNumber(String transpNumber) {
        this.transpNumber = transpNumber;
    }

    public String getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(String tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    public Date getDateOfRegister() {
        return dateOfRegister;
    }

    public void setDateOfRegister(Date dateOfRegister) {
        this.dateOfRegister = dateOfRegister;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfSterilization() {
        return dateOfSterilization;
    }

    public void setDateOfSterilization(Date dateOfSterilization) {
        this.dateOfSterilization = dateOfSterilization;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(Integer ownerID) {
        this.ownerID = ownerID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public AnimalService getService() {
        return service;
    }

    public void setService(AnimalService service) {
        this.service = service;
    }
}
