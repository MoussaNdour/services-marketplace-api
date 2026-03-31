package com.example.marketplace.repository;

import com.example.marketplace.entity.Asking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AskingRepository extends JpaRepository<Asking,Integer> {
    List<Asking> findByClientUserEmail(String email);
}
