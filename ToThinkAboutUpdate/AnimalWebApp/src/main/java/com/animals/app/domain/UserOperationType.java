package com.animals.app.domain;

import java.io.Serializable;

/**
 * Created by oleg on 23.07.2015.
 */
public class UserOperationType implements Serializable{

    private Integer id;
    private String type;

    public UserOperationType() {
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

        UserOperationType that = (UserOperationType) o;

        if (!id.equals(that.id)) return false;
        if (!type.equals(that.type)) return false;

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
        return "UserOperationType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
