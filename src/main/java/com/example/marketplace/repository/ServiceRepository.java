package com.example.marketplace.repository;

import com.example.marketplace.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ServiceRepository extends JpaRepository<Service,Integer> {

    @Query("SELECT s FROM Service s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Service> searchService(@Param("name") String name);

    @Query("SELECT sp.service FROM ServiceProposal sp WHERE sp.provider.user.email = :email")
    List<Service> getAllByProviderEmail(@Param("email") String email);

    @Query(value="select s.* from service s where s.id in (select sp.service from serviceproposal sp where sp.id= :proposalId)", nativeQuery = true)
    Optional<Service> getByProposalId(@Param("proposalId") int proposalId);

    Optional<Service> findByName(String name);

    List<Service> findByCategoryId(int categoryId);
}
