package com.animals.app.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Rostyslav.Viner on 22.07.2015.
 */

public class Animal implements Serializable{
    public enum SexType {
        NONE,
        MALE,
        FEMALE
    }

    public enum CitesType {
        NONE,
        CITES1,
        CITES2,
        CITES3
    }

    public enum SizeType {
        NONE,
        SMALL,
        MIDDLE,
        LARGE
    }

    private Long id;
    private SexType sex;
    private AnimalType type;
    private SizeType size;
    private CitesType cites;
    private AnimalBreed breed;
    private String transpNumber;
    private String tokenNumber;
    private Date dateOfRegister;
    private Date dateOfBirth;
    private Date dateOfSterilization;
    private Date dateOfFacebook;
    private Date dateOfTwitter;
    private String color;
    private String description;
    private User user;
    private String address;
    private Boolean active;
    private String image;
    private AnimalService service;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SexType getSex() {
        return sex;
    }

    public void setSex(SexType sex) {
        this.sex = sex;
    }

    public AnimalType getType() {
        return type;
    }

    public void setType(AnimalType type) {
        this.type = type;
    }

    public SizeType getSize() {
        return size;
    }

    public void setSize(SizeType size) {
        this.size = size;
    }

    public CitesType getCites() {
        return cites;
    }

    public void setCites(CitesType cites) {
        this.cites = cites;
    }

    public AnimalBreed getBreed() {
        return breed;
    }

    public void setBreed(AnimalBreed breed) {
        this.breed = breed;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
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

    public Date getDateOfFacebook() {
        return dateOfFacebook;
    }

    public void setDateOfFacebook(Date dateOfFacebook) {
        this.dateOfFacebook = dateOfFacebook;
    }

    public Date getDateOfTwitter() {
        return dateOfTwitter;
    }

    public void setDateOfTwitter(Date dateOfTwitter) {
        this.dateOfTwitter = dateOfTwitter;
    }

    public boolean checkNewBreed(AnimalBreed animalBreed){
        return ((animalBreed != null) && (animalBreed.getId() == null) && (animalBreed.getBreedUa() != null));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Animal animal = (Animal) o;

        if (active != null ? !active.equals(animal.active) : animal.active != null) return false;
        if (address != null ? !address.equals(animal.address) : animal.address != null) return false;
        if (breed != null ? !breed.equals(animal.breed) : animal.breed != null) return false;
        if (cites != animal.cites) return false;
        if (color != null ? !color.equals(animal.color) : animal.color != null) return false;
        if (!dateOfBirth.equals(animal.dateOfBirth)) return false;
        if (!dateOfRegister.equals(animal.dateOfRegister)) return false;
        if (dateOfSterilization != null ? !dateOfSterilization.equals(animal.dateOfSterilization) : animal.dateOfSterilization != null)
            return false;
        if (dateOfFacebook != null ? !dateOfFacebook.equals(animal.dateOfFacebook) : animal.dateOfFacebook != null)
            return false;
        if (dateOfTwitter != null ? !dateOfTwitter.equals(animal.dateOfTwitter) : animal.dateOfTwitter != null)
            return false;
        if (description != null ? !description.equals(animal.description) : animal.description != null) return false;
        if (!id.equals(animal.id)) return false;
        if (image != null ? !image.equals(animal.image) : animal.image != null) return false;
        if (!service.equals(animal.service)) return false;
        if (sex != animal.sex) return false;
        if (size != animal.size) return false;
        if (tokenNumber != null ? !tokenNumber.equals(animal.tokenNumber) : animal.tokenNumber != null) return false;
        if (transpNumber != null ? !transpNumber.equals(animal.transpNumber) : animal.transpNumber != null)
            return false;
        if (!type.equals(animal.type)) return false;
        if (user != null ? !user.equals(animal.user) : animal.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + sex.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + size.hashCode();
        result = 31 * result + cites.hashCode();
        result = 31 * result + (breed != null ? breed.hashCode() : 0);
        result = 31 * result + (transpNumber != null ? transpNumber.hashCode() : 0);
        result = 31 * result + (tokenNumber != null ? tokenNumber.hashCode() : 0);
        result = 31 * result + dateOfRegister.hashCode();
        result = 31 * result + dateOfBirth.hashCode();
        result = 31 * result + (dateOfSterilization != null ? dateOfSterilization.hashCode() : 0);
        result = 31 * result + (dateOfFacebook != null ? dateOfFacebook.hashCode() : 0);
        result = 31 * result + (dateOfTwitter != null ? dateOfTwitter.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + service.hashCode();
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
                ", breed=" + breed +
                ", transpNumber='" + transpNumber + '\'' +
                ", tokenNumber='" + tokenNumber + '\'' +
                ", dateOfRegister=" + dateOfRegister +
                ", dateOfBirth=" + dateOfBirth +
                ", dateOfSterilization=" + dateOfSterilization +
                ", dateOfFacebook=" + dateOfFacebook +
                ", dateOfTwitter=" + dateOfTwitter +
                ", color='" + color + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                ", address='" + address + '\'' +
                ", active=" + active +
                ", image='" + image + '\'' +
                ", service=" + service +
                '}';
    }
}
