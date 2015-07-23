package com.animals.app.domain;

/**
 * Created by Rostyslav.Viner on 22.07.2015.
 */
public class CitesType {

    private Long id;
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        return "CitesType{" +
                "id=" + id +
                ", type='" + type + "'" +
                '}';
    }
}
