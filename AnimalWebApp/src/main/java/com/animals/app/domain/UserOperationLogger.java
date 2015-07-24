package com.animals.app.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by oleg on 23.07.2015.
 */
public class UserOperationLogger implements Serializable{

    private Integer id;
    private Timestamp date;
    private User user;
    private UserOperationType userOperationType;
    private Animal animal;

    public UserOperationLogger() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserOperationType getUserOperationType() {
        return userOperationType;
    }

    public void setUserOperationType(UserOperationType userOperationType) {
        this.userOperationType = userOperationType;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserOperationLogger that = (UserOperationLogger) o;

        if (!date.equals(that.date)) return false;
        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UserOperationLogger{" +
                "date=" + date +
                ", id=" + id +
                '}';
    }
}
