package com.example.marketplace.repository;

import com.example.marketplace.entity.Service;
import org.springframework.data.repository.CrudRepository;

public interface ServiceRepository extends CrudRepository<Service,Integer> {
}
