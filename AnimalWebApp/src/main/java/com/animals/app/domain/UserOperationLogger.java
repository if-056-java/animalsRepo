package com.animals.app.domain;

import java.sql.Timestamp;

/**
 * Created by oleg on 23.07.2015.
 */
public class UserOperationLogger {

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
    public String toString() {
        return "UserOperationLogger{" +
                "date=" + date +
                ", id=" + id +
                '}';
    }
}
