/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elearningbackend.dto;

import java.util.List;

public class Pager<T> {

    private int currentPage;
    private int noOfRowInPage;
    private long totalRow;
    private long totalPage;
    private List<T> results;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(long totalRow) {
        this.totalRow = totalRow;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public int getNoOfRowInPage() {
        return noOfRowInPage;
    }

    public void setNoOfRowInPage(int noOfRowInPage) {
        this.noOfRowInPage = noOfRowInPage;
    }

    public Pager() {
    }

}
