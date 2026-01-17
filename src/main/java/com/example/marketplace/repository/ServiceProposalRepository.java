package com.example.marketplace.repository;

import com.example.marketplace.entity.Provider;
import com.example.marketplace.entity.ServiceProposal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ServiceProposalRepository extends CrudRepository<ServiceProposal,Integer> {

    @Query(
            value = "SELECT * FROM serviceProposal WHERE provider = :idprovider",
            nativeQuery = true
    )
    Iterable<ServiceProposal> getServiceProposalsByIdProvider(
            @Param("idprovider") int idprovider
    );

    @Query(
            value = "SELECT * from provider where id IN (SELECT provider FROM serviceProposal WHERE service = :serviceid)",
            nativeQuery = true
    )
    Iterable<Provider> getProvidersByServiceId(@Param("serviceid") int serviceid);


    @Query(
            value = "SELECT * from serviceProposal WHERE provider =:idprovider and service =:idservice",
            nativeQuery = true
    )
    Optional<ServiceProposal> getServiceProposalByServiceIdAndProviderId(@Param("idservice") int idservice, @Param("idprovider") int idprovider);
}
