package com.example.marketplace.repository;

import com.example.marketplace.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,Integer> {

    Optional<Client> findByUserEmail(String email);

    @Query(value = "SELECT c.* FROM CLIENT c JOIN askingService ask ON c.id = ask.client where ask.id= :askingId", nativeQuery = true)
    Optional<Client> findByAskingId(@Param("askingId") int askingId);
}
