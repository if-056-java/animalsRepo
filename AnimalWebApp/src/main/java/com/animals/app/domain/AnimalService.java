package com.animals.app.domain;

/**
 * Created by Rostyslav.Viner on 22.07.2015.
 */
public class AnimalService {

    private Long id;
    private String service;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnimalService that = (AnimalService) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return !(service != null ? !service.equals(that.service) : that.service != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (service != null ? service.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AnimalService{" +
                "id=" + id +
                ", service='" + service + "'" +
                '}';
    }
}
