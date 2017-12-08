package com.elearningbackend.service;

import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.Pager;
import com.elearningbackend.dto.SubcategoryDto;
import com.elearningbackend.entity.Subcategory;
import com.elearningbackend.repository.ISubcategoryRepository;
import com.elearningbackend.utility.Paginator;
import com.elearningbackend.utility.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class SubcategoryService extends AbstractService<SubcategoryDto, String, Subcategory>{

    @Autowired
    public SubcategoryService(JpaRepository<Subcategory, String> repository) {
        super(repository, new Paginator<>(SubcategoryDto.class));
    }

    @Override
    public Pager<SubcategoryDto> loadAll(int currentPage, int noOfRowInPage, String sortBy, String direction) {
        Page<Subcategory> subcategories = getSubcategoryRepository().findAll(Paginator.getValidPageRequest(currentPage, noOfRowInPage,
            ServiceUtils.proceedSort(sortBy, direction)));
        return paginator.paginate(currentPage, subcategories, noOfRowInPage, mapper);
    }

    @Override
    public SubcategoryDto getOneByKey(String key) throws ElearningException {
        Subcategory subcategory = getSubcategoryRepository().findOne(key.toUpperCase());
        if (subcategory == null) {
            throw new ElearningException(Errors.SUBCATEGORY_NOT_FOUND.getId(), Errors.SUBCATEGORY_NOT_FOUND.getMessage());
        }
        return mapper.map(subcategory, SubcategoryDto.class);
    }

    @Override
    public SubcategoryDto add(SubcategoryDto object) throws ElearningException {
        return null;
    }

    @Override
    public SubcategoryDto edit(SubcategoryDto object) throws ElearningException {
        return null;
    }

    @Override
    public SubcategoryDto delete(String key) throws ElearningException {
        return null;
    }

    ISubcategoryRepository getSubcategoryRepository(){
        return (ISubcategoryRepository) getRepository();
    }

}
