package com.example.marketplace.repository;

import com.example.marketplace.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category,Integer> {

}
