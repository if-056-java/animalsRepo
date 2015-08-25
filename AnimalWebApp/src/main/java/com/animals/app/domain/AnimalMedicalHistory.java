package com.animals.app.domain;

import java.sql.Date;

/**
 * Created by Rostyslav.Viner on 25.08.2015.
 */
public class AnimalMedicalHistory {
    private Long id;
    private AnimalStatus status;
    private Long animalId;
    private User user;
    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnimalStatus getStatus() {
        return status;
    }

    public void setStatus(AnimalStatus status) {
        this.status = status;
    }

    public Long getAnimalId() {
        return animalId;
    }

    public void setAnimalId(Long animalId) {
        this.animalId = animalId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnimalMedicalHistory that = (AnimalMedicalHistory) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (animalId != null ? !animalId.equals(that.animalId) : that.animalId != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return !(date != null ? !date.equals(that.date) : that.date != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (animalId != null ? animalId.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AnimalMedicalHistory{" +
                "id=" + id +
                ", status=" + status +
                ", animalId=" + animalId +
                ", user=" + user +
                ", date=" + date +
                '}';
    }
}
