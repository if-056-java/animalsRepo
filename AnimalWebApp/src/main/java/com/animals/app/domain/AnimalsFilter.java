package com.animals.app.domain;

/**
 * Created by Rostyslav.Viner on 11.08.2015.
 */
public class AnimalsFilter {
    private int page;
    private long offset;
    private int limit;
    private AnimalType type;
    private AnimalBreed breed;
    private Animal.SexType sex;
    private Animal.SizeType size;
    private Animal.CitesType cites;

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

    public AnimalType getType() {
        return type;
    }

    public void setType(AnimalType type) {
        this.type = type;
    }

    public AnimalBreed getBreed() {
        return breed;
    }

    public void setBreed(AnimalBreed breed) {
        this.breed = breed;
    }

    public Animal.SexType getSex() {
        return sex;
    }

    public void setSex(Animal.SexType sex) {
        this.sex = sex;
    }

    public Animal.SizeType getSize() {
        return size;
    }

    public void setSize(Animal.SizeType size) {
        this.size = size;
    }

    public Animal.CitesType getCites() {
        return cites;
    }

    public void setCites(Animal.CitesType cites) {
        this.cites = cites;
    }
}
