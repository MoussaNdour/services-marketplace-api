package com.example.marketplace.repository;



import com.example.marketplace.entity.Provider;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProviderRepository extends CrudRepository<Provider,Integer> {

    Optional<Provider> findByUserEmail(String email);

}
