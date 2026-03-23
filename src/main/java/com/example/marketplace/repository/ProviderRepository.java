package com.example.marketplace.repository;



import com.example.marketplace.entity.Provider;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProviderRepository extends CrudRepository<Provider,Integer> {

    Optional<Provider> findByUserEmail(String email);

    @Query("SELECT p FROM Provider p JOIN ServiceProposal sp ON sp.provider.id = p.id WHERE sp.service.id = :serviceId")
    List<Provider> findProvidersByServiceId(@Param("serviceId") int serviceId);

}
