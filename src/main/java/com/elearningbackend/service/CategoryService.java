package com.elearningbackend.service;

import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.CategoryDto;
import com.elearningbackend.dto.Pager;
import com.elearningbackend.entity.Category;
import com.elearningbackend.repository.ICategoryRepository;
import com.elearningbackend.utility.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class CategoryService extends AbstractService<CategoryDto, String, Category> {

    @Autowired
    public CategoryService(JpaRepository<Category, String> repository) {
        super(repository, new Paginator<>(CategoryDto.class));
    }

    @Override
    public Pager<CategoryDto> loadAll(int currentPage, int noOfRowInPage) {
        Page<Category> pager = getCategoryRepository().findAll(Paginator.getValidPageRequest(currentPage, noOfRowInPage, null));
        return paginator.paginate(currentPage, pager, noOfRowInPage, mapper);
    }

    @Override
    public CategoryDto getOneByKey(String key) throws ElearningException {
        Category category= getCategoryRepository().findOne(key.toUpperCase());
        if (category == null){
            throw new ElearningException(Errors.CATEGORY_NOT_FOUND.getId(), Errors.CATEGORY_NOT_FOUND.getMessage());
        }
        return mapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto add(CategoryDto object) throws ElearningException {
        return null;
    }

    @Override
    public CategoryDto edit(CategoryDto object) throws ElearningException {
        return null;
    }

    @Override
    public CategoryDto delete(String key) throws ElearningException {
        return null;
    }

    ICategoryRepository getCategoryRepository(){
        return (ICategoryRepository) getRepository();
    }
}
