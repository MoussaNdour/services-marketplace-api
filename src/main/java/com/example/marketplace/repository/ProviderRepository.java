package com.example.marketplace.repository;



import com.example.marketplace.entity.Provider;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProviderRepository extends CrudRepository<Provider,Integer> {

    Optional<Provider> findByUserEmail(String email);

    @Query(value="select p.* from provider p where p.id in (select sp.provider from serviceproposal sp where sp.id= :proposalId)", nativeQuery = true)
    Optional<Provider> getByProposalId(@Param("proposalId") int proposalId);

}
