package com.animals.app.domain;

import java.io.Serializable;

/**
 * Created by oleg on 22.07.2015.
 */
public class UserRole implements Serializable{

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole userRole = (UserRole) o;

        if (!id.equals(userRole.id)) return false;
        if (!role.equals(userRole.role)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "role='" + role + '\'' +
                ", id=" + id +
                '}';
    }
}
