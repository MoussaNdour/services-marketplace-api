package com.example.marketplace.repository;



import com.example.marketplace.entity.Provider;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProviderRepository extends CrudRepository<Provider,Integer> {

    Optional<Provider> findByUserEmail(String email);

    @Query(
            value = "SELECT * FROM provider WHERE id IN (SELECT provider FROM serviceProposal WHERE service = :serviceId)",
            nativeQuery = true
    )
    List<Provider> findByServiceProposalServiceId(@Param("serviceId") int serviceId);
}
