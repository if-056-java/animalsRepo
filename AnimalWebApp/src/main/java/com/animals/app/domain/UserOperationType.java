package com.animals.app.domain;

import java.io.Serializable;

/**
 * Created by oleg on 23.07.2015.
 */
public class UserOperationType implements Serializable{

    private Integer id;
    private String operationType;

    public UserOperationType() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserOperationType that = (UserOperationType) o;

        if (!id.equals(that.id)) return false;
        if (!operationType.equals(that.operationType)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + operationType.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UserOperationType{" +
                "id=" + id +
                ", operationType='" + operationType + '\'' +
                '}';
    }
}
