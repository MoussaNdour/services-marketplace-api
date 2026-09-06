package com.example.marketplace.repository;

import com.example.marketplace.entity.Asking;
import com.example.marketplace.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    boolean existsByAskingservice(Asking asking);
}