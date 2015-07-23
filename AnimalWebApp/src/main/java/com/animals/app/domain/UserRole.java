package com.animals.app.domain;

/**
 * Created by oleg on 22.07.2015.
 */
public class UserRole {

    private Integer id;
    private String role;

    public UserRole() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "role='" + role + '\'' +
                ", id=" + id +
                '}';
    }
}
