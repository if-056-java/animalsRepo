package com.example.Model.Entity;

/**
 * Created by oleg on 23.07.2015.
 */
public class UserType {

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
    public String toString() {
        return "UserType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
