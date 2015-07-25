package com.animals.app.domain;

import java.io.Serializable;

/**
 * Created by Rostyslav.Viner on 22.07.2015.
 */
public class AnimalSexType implements Serializable{

    private Long id;
    private String sex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnimalSexType that = (AnimalSexType) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return !(sex != null ? !sex.equals(that.sex) : that.sex != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AnimalSexType{" +
                "id=" + id +
                ", sex='" + sex + "'" +
                '}';
    }
}
