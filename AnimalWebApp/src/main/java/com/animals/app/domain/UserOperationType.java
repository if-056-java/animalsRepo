package com.animals.app.domain;

/**
 * Created by oleg on 23.07.2015.
 */
public class UserOperationType {

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
    public String toString() {
        return "UserOperationType{" +
                "id=" + id +
                ", operationType='" + operationType + '\'' +
                '}';
    }
}
