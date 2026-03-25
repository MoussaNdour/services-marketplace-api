package com.example.marketplace.repository;

import com.example.marketplace.entity.Service;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ServiceRepository extends CrudRepository<Service,Integer> {

    @Query("SELECT s FROM Service s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Service> searchService(@Param("name") String name);

    @Query(value="SELECT s.* FROM service s  JOIN serviceproposal sp ON s.id = sp.service JOIN provider p ON p.id = sp.provider JOIN users u ON u.id = p.userId WHERE u.email = :email",nativeQuery = true)
    List<Service> getAllByProviderEmail(@Param("email") String email);

    @Query(value="select s.* from service s where s.id in (select sp.service from serviceproposal sp where sp.id= :proposalId)", nativeQuery = true)
    Optional<Service> getByProposalId(@Param("proposalId") int proposalId);

    Optional<Service> findByName(String name);
}
