package com.elearningbackend.repository;

import com.elearningbackend.entity.Category;
import com.elearningbackend.entity.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISubcategoryRepository extends JpaRepository<Subcategory, String>{
    @Query("select a.displayName from Subcategory a where a.category = ?1")
    List<String> findNameByCategory(Category category);
}
