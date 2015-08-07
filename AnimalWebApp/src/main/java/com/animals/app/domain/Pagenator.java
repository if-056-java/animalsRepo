package com.animals.app.domain;

/**
 * Created by Rostyslav.Viner on 04.08.2015.
 */
public class Pagenator {
    private int page;
    private int offset;
    private int limit;
    private int rowsCount;

    public Pagenator() {

    }

    public Pagenator(int page, int limit) {
        this.page = page;
        this.limit = limit;
    }

    public int getOffset() {
        if (page <=0) {
            return 0;
        }

        offset = (page--) * limit;

        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public void setRowsCount(int rowsCount) {
        this.rowsCount = rowsCount;
    }
}
