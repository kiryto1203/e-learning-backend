package com.elearningbackend.service;

import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.CategoryDto;
import com.elearningbackend.dto.Pager;
import com.elearningbackend.entity.Category;
import com.elearningbackend.repository.ICategoryRepository;
import com.elearningbackend.repository.ISubcategoryRepository;
import com.elearningbackend.utility.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class CategoryService extends AbstractService<CategoryDto, String, Category> {

    @Autowired
    private ISubcategoryRepository iSubcategoryRepository;

    @Autowired
    public CategoryService(JpaRepository<Category, String> repository) {
        super(repository, new Paginator<>(CategoryDto.class));
    }

    @Override
    public Pager<CategoryDto> loadAll(int currentPage, int noOfRowInPage, String sortBy, String direction) {
        Page<Category> pager = getCategoryRepository().findAll(Paginator.getValidPageRequest(currentPage, noOfRowInPage, null));
        List<String> subcategoriesByCategory = pager.getContent().stream().map(a -> iSubcategoryRepository.findNameByCategory(a))
            .findFirst().orElse(null);
        Pager<CategoryDto> paginate = paginator.paginate(currentPage, pager, noOfRowInPage, mapper);
        paginate.setResults(paginate.getResults().stream().map(a -> {
            a.setSubcategoriesName(subcategoriesByCategory);
            a.setSubcategoriesCount(subcategoriesByCategory.size());
            return a;
        }).collect(Collectors.toList()));
        return paginate;
    }

    @Override
    public CategoryDto getOneByKey(String key) throws ElearningException {
        Category category = getCategoryRepository().findOne(key.toUpperCase());
        List<String> subcategories = iSubcategoryRepository.findNameByCategory(category);
        if (category == null){
            throw new ElearningException(Errors.CATEGORY_NOT_FOUND.getId(), Errors.CATEGORY_NOT_FOUND.getMessage());
        }
        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
        categoryDto.setSubcategoriesName(subcategories);
        categoryDto.setSubcategoriesCount(subcategories.size());
        return categoryDto;
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
