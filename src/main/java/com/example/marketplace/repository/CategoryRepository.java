package com.example.marketplace.repository;

import com.example.marketplace.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category,Integer> {


    Optional<Category> findByName(@Param("name") String name);
}
