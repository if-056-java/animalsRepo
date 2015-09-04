package com.animals.app.domain;

/**
 * Created by Rostyslav.Viner on 11.08.2015.
 */
public class AnimalsFilter {
    private int page;
    private int limit;
    private Animal animal;
    private int status;

    public AnimalsFilter() {
    }

    public AnimalsFilter(int page, int limit) {
        this.page = page;
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getOffset() {
        return (page-1) * limit;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AnimalsFilter{" +
                "page=" + page +
                ", limit=" + limit +
                ", animal=" + animal +
                ", status=" + status +
                '}';
    }
}
