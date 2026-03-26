package com.example.marketplace.repository;

import com.example.marketplace.entity.Service;
import com.example.marketplace.entity.ServiceProposal;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@DataJpaTest
@Slf4j
/**
 * All tests supposed that there is some datas(like services,providers) inserted in database otherwise we got tests failing
 */
class ServiceRepositoryTest {

    @Autowired
    ServiceRepository underTest;

    @Autowired
    ServiceProposalRepository proposalRepos;

    @Autowired
    ProviderRepository providerRepos;

    @Test
    void canFindServiceBySearching() {
        //given
        String pattern = "dev";

        //when
        List<Service> test = underTest.searchService(pattern);


        //then
        assertAll(
                "Finding service test",
                () -> assertThat(test).isNotEmpty(),
                () -> {
                    test.forEach((service)->{
                        assertThat(service.getName()).containsIgnoringCase("dev");
                    });
                }
        );
    }


    @Test
    void cannotFindService() {

        //given
        String pattern = "Engineer";

        //when
        List<Service> test = underTest.searchService(pattern);

        //then
        assertThat(test).isEmpty();
    }


    @Test
    void canGetServicesByProviderEmail() {
        //given
        String email = "acarless0@plala.or.jp";


        ServiceProposal proposal = new ServiceProposal();

        List<Service> allServices = underTest.findAll();
        assertThat(allServices).isNotEmpty();

        Service firstService = allServices.get(0);
        proposal.setService(firstService);
        proposal.setProvider(providerRepos.findByUserEmail(email).get());
        proposal.setPrice(1000);
        proposal.setDescription("Fast service");
        proposalRepos.save(proposal);

        //when
        List<Service> test = underTest.getAllByProviderEmail(email);


        //then
        assertAll(
                "Testing getting services by provider email",
                () -> assertThat(test).isNotEmpty()
        );
    }

    @Test
    void cannotGetServices(){
        //given
        String email = "acarless0@plala.or.jp";

        //when
        List<Service> test = underTest.getAllByProviderEmail(email);

        //then
        assertThat(test).isEmpty();
    }


    @Test
    void canGetServiceByProposalId() {

        //given
        ServiceProposal proposal = new ServiceProposal();
        List<Service> allservices = underTest.findAll();

        assertThat(allservices).isNotEmpty();

        Service firstService = allservices.get(0);

        proposal.setService(firstService);
        proposal.setProvider(providerRepos.findByUserEmail("acarless0@plala.or.jp").get());
        proposal.setPrice(1000);
        proposal.setDescription("Fast service");
        ServiceProposal saved = proposalRepos.save(proposal);


        //when
        Service test = underTest.getByProposalId(saved.getId()).get();

        //then
        assertThat(test).isEqualTo(firstService);
    }


    @Test
    void cannotGetServiceByProposalId(){
        //given
        int proposalId = 1000;

        //when
        Optional<Service> result = underTest.getByProposalId(proposalId);

        //then
        assertThat(result).isEmpty();
    }
}