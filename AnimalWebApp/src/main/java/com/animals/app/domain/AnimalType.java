package com.animals.app.domain;

import java.io.Serializable;

/**
 * Created by Rostyslav.Viner on 22.07.2015.
 */
public class AnimalType implements Serializable{

    private Long id;
    private String type;
    private String typeEn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeEn() {
        return typeEn;
    }

    public void setTypeEn(String typeEn) {
        this.typeEn = typeEn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnimalType that = (AnimalType) o;

        if (!id.equals(that.id)) return false;
        if (!type.equals(that.type)) return false;
        if (!typeEn.equals(that.typeEn)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + typeEn.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AnimalType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", typeEn='" + typeEn + '\'' +
                '}';
    }
}
