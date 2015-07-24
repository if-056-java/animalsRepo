package com.animals.app.domain;

import java.io.Serializable;

/**
 * Created by oleg on 23.07.2015.
 */
public class UserType implements Serializable{

    private Integer id;
    private String type;

    public UserType() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserType userType = (UserType) o;

        if (!id.equals(userType.id)) return false;
        if (!type.equals(userType.type)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UserType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
