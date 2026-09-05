package com.example.marketplace.repository;

import com.example.marketplace.entity.Asking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AskingRepository extends JpaRepository<Asking,Integer> {
    List<Asking> findByClientUserEmail(String email);

    @Query(value = "SELECT a.* FROM askingservice a JOIN serviceProposal sp ON sp.id = a.proposal JOIN provider p ON p.id = sp.provider JOIN users pu ON pu.id = p.userid WHERE pu.email = :providerEmail", nativeQuery = true)
    List<Asking> getAllByProviderEmail(@Param("providerEmail") String providerEmail);

}
