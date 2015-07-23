package com.animals.app.domain;

/**
 * Created by Rostyslav.Viner on 22.07.2015.
 */
public class AnimalSize {

    private Long id;
    private String size;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnimalSize that = (AnimalSize) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return !(size != null ? !size.equals(that.size) : that.size != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (size != null ? size.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AnimalSize{" +
                "id=" + id +
                ", size='" + size + "'" +
                '}';
    }
}
