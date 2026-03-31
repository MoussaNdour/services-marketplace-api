package com.example.marketplace.repository;


import com.example.marketplace.entity.ServiceProposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ServiceProposalRepository extends JpaRepository<ServiceProposal,Integer> {


    List<ServiceProposal> findByProviderId(int idprovider);

    List<ServiceProposal> findByServiceId(int serviceId);

    @Query(value = "SELECT sp.* FROM serviceproposal sp JOIN askingService ask ON sp.id = ask.proposal WHERE ask.id = :askingId" , nativeQuery = true)
    Optional<ServiceProposal> findProposalByAskingId(@Param("askingId") int askingId);
}
