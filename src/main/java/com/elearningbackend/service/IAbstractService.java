package com.elearningbackend.service;

import com.elearningbackend.dto.Pager;

import java.io.Serializable;

public interface IAbstractService<D, K extends Serializable> {
    Pager<D> loadAll(int currentPage, int noOfRowInPage);
    D getOneByKey(K key);
    D add(D object) throws Exception;
    D edit(D object) throws Exception;
    D delete(K key) throws Exception;
}
