package com.animals.app.domain;


import org.apache.log4j.Logger;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Rostyslav.Viner on 22.07.2015.
 */
public class Animal implements Serializable{
    private static final Logger log = Logger.getLogger(Animal.class);
    private Long id;
    private AnimalSexType sex;
    private AnimalType type;
    private AnimalSize size;
    private AnimalCitesType cites;
    private String sort;
    private String transpNumber;
    private String tokenNumber;
    private Date dateOfRegister;
    private Date dateOfBirth;
    private Date dateOfSterilization;
    private String color;
    private User user;
    private Address address;
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

    public AnimalCitesType getAnimalCites() {
        return cites;
    }

    public void setAnimalCites(AnimalCitesType cites) {
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Animal animal = (Animal) o;

        if (id != null ? !id.equals(animal.id) : animal.id != null) return false;
        if (sex != null ? !sex.equals(animal.sex) : animal.sex != null) return false;
        if (type != null ? !type.equals(animal.type) : animal.type != null) return false;
        if (size != null ? !size.equals(animal.size) : animal.size != null) return false;
        if (cites != null ? !cites.equals(animal.cites) : animal.cites != null) return false;
        if (sort != null ? !sort.equals(animal.sort) : animal.sort != null) return false;
        if (transpNumber != null ? !transpNumber.equals(animal.transpNumber) : animal.transpNumber != null)
            return false;
        if (tokenNumber != null ? !tokenNumber.equals(animal.tokenNumber) : animal.tokenNumber != null) return false;
        if (dateOfRegister != null ? !dateOfRegister.equals(animal.dateOfRegister) : animal.dateOfRegister != null)
            return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(animal.dateOfBirth) : animal.dateOfBirth != null) return false;
        if (dateOfSterilization != null ? !dateOfSterilization.equals(animal.dateOfSterilization) : animal.dateOfSterilization != null)
            return false;
        if (color != null ? !color.equals(animal.color) : animal.color != null) return false;
        if (user != null ? !user.equals(animal.user) : animal.user != null) return false;
        if (address != null ? !address.equals(animal.address) : animal.address != null) return false;
        if (active != null ? !active.equals(animal.active) : animal.active != null) return false;
        if (image != null ? !image.equals(animal.image) : animal.image != null) return false;
        return !(service != null ? !service.equals(animal.service) : animal.service != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (cites != null ? cites.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (transpNumber != null ? transpNumber.hashCode() : 0);
        result = 31 * result + (tokenNumber != null ? tokenNumber.hashCode() : 0);
        result = 31 * result + (dateOfRegister != null ? dateOfRegister.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (dateOfSterilization != null ? dateOfSterilization.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (service != null ? service.hashCode() : 0);
        return result;
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
