package com.animals.app.domain;


import java.sql.Date;

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
    private User user;
    private UserAddress address;
    private Boolean active;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserAddress getAddress() {
        return address;
    }

    public void setAddress(UserAddress address) {
        this.address = address;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", sex=" + sex +
                ", type=" + type +
                ", size=" + size +
                ", cites=" + cites +
                ", sort='" + sort + "'" +
                ", transpNumber='" + transpNumber + "'" +
                ", tokenNumber='" + tokenNumber + "'" +
                ", dateOfRegister='" + dateOfRegister + "'" +
                ", dateOfBirth='" + dateOfBirth + "'" +
                ", dateOfSterilization='" + dateOfSterilization + "'" +
                ", color='" + color + "'" +
                ", user=" + user +
                ", address=" + address +
                ", active='" + active + "'" +
                ", image='" + image + "'" +
                ", service=" + service +
                '}';
    }
}
