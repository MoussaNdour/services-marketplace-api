package com.example.marketplace.repository;

import com.example.marketplace.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User,Integer> {

    Optional<User> findByEmail(String email);

}
