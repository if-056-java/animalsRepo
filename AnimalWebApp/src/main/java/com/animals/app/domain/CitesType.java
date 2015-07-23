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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CitesType citesType = (CitesType) o;

        if (id != null ? !id.equals(citesType.id) : citesType.id != null) return false;
        return !(type != null ? !type.equals(citesType.type) : citesType.type != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CitesType{" +
                "id=" + id +
                ", type='" + type + "'" +
                '}';
    }
}
