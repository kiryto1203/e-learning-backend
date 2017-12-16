package com.elearningbackend.service;

import com.elearningbackend.utility.Paginator;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dohalong on 16/12/2017.
 */
public abstract class AbstractCommonService<D,K> implements IAbstractCommonService<D,K> {

    @Getter
    private HashMap<String,IAbstractService> serviceHashMap;

    protected final ModelMapper mapper = new ModelMapper();

    @Getter
    protected Class<D> dtoClass;

    public AbstractCommonService(HashMap<String,IAbstractService> serviceHashMap) {
        this.serviceHashMap = serviceHashMap;
    }

    public AbstractCommonService(){

    }
}
