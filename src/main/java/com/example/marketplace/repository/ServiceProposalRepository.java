package com.example.marketplace.repository;

import com.example.marketplace.entity.Provider;
import com.example.marketplace.entity.ServiceProposal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ServiceProposalRepository extends CrudRepository<ServiceProposal,Integer> {


    Iterable<ServiceProposal> findByProviderId(int idprovider);

    List<ServiceProposal> findByServiceId(int serviceId);

}
