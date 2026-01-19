package com.example.marketplace.repository;

import com.example.marketplace.entity.Client;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client,Integer> {

    Optional<Client> findByUserEmail(String email);
}
