package com.example.marketplace.repository;

import com.example.marketplace.entity.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AdminRepository extends CrudRepository<Admin,Integer> {

    Optional<Admin> findByUserEmail(String email);
}
