package com.animals.app.domain;

/**
 * Created by Rostyslav.Viner on 25.08.2015.
 */
public class AnimalStatus {
    private Long id;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnimalStatus that = (AnimalStatus) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return !(status != null ? !status.equals(that.status) : that.status != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AnimalStatuse{" +
                "id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}
