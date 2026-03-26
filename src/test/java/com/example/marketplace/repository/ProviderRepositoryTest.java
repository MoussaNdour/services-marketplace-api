package com.example.marketplace.repository;

import com.example.marketplace.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;



import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@Slf4j
@DataJpaTest
class ProviderRepositoryTest {

    @Autowired
    ProviderRepository underTest;


    @Autowired
    ServiceProposalRepository proposalRepos;

    @Autowired
    ServiceRepository serviceRepos;


    @Disabled
    @Test
    void canFindProviderByServiceProposalId() {

        //given
        Provider provider = underTest.findById(1).get();

        ServiceProposal proposal = new ServiceProposal();
        proposal.setService(serviceRepos.findById(1).get());
        proposal.setProvider(provider);
        proposal.setPrice(1000);
        proposal.setDescription("Fast service");
        proposalRepos.save(proposal);


        //when
        Provider test = underTest.getByProposalId(1).get();

        //then
        assertThat(test).isEqualTo(provider);
    }

    @Test
    void shouldNotFindProvider(){
        //given
        Provider provider = underTest.findById(1).get();

        ServiceProposal proposal = new ServiceProposal();
        proposal.setService(serviceRepos.findById(1).get());
        proposal.setProvider(underTest.findById(2).get());
        proposal.setPrice(1000);
        proposal.setDescription("Fast service");
        proposalRepos.save(proposal);

        //when
        Provider test = underTest.getByProposalId(1).get();

        //then
        assertThat(provider).isNotEqualTo(test);
    }
}