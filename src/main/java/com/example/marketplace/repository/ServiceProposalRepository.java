package com.example.marketplace.repository;

import com.example.marketplace.entity.ServiceProposal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ServiceProposalRepository extends CrudRepository<ServiceProposal,Integer> {

    @Query(
            value = "SELECT * FROM serviceProposal WHERE provider = :idprovider",
            nativeQuery = true
    )
    Iterable<ServiceProposal> getServiceProposalsByIdProvider(
            @Param("idprovider") int idprovider
    );
}
