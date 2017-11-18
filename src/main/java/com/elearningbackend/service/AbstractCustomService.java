/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elearningbackend.service;

import com.elearningbackend.dto.Pager;
import com.elearningbackend.utility.Paginator;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author c1508l3694
 */
public abstract class AbstractCustomService<D, K extends Serializable, E> extends AbstractService<D, K, E>{
    public AbstractCustomService(JpaRepository<E, K> repository, Paginator<E, D> paginator) {
        super(repository, paginator);
    }
    
    public abstract Pager<D> getByCreator(String creatorUsername, int currentPage, int noOfRowInPage);
}
