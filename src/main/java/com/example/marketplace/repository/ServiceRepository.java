package com.example.marketplace.repository;

import com.example.marketplace.entity.Service;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ServiceRepository extends CrudRepository<Service,Integer> {


    @Query("SELECT s FROM Service s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Service> searchService(@Param("name") String name);

}
