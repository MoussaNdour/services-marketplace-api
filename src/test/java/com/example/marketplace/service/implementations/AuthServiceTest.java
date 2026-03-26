package com.example.marketplace.service.implementations;

import com.example.marketplace.dto.request.ClientRequestDTO;
import com.example.marketplace.dto.request.ProviderRequestDTO;
import com.example.marketplace.entity.Client;
import com.example.marketplace.entity.Provider;
import com.example.marketplace.entity.User;
import com.example.marketplace.exception.EmailAlreadyExistException;
import com.example.marketplace.mapper.request.ClientRequestMapper;
import com.example.marketplace.mapper.request.ProviderRequestMapper;
import com.example.marketplace.mapper.response.AdminResponseMapper;
import com.example.marketplace.mapper.response.ClientResponseMapper;
import com.example.marketplace.mapper.response.ProviderResponseMapper;
import com.example.marketplace.repository.AdminRepository;
import com.example.marketplace.repository.ClientRepository;
import com.example.marketplace.repository.ProviderRepository;
import com.example.marketplace.repository.UserRepository;
import com.example.marketplace.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    AuthService underTest;

    @Mock
    UserRepository userRepository;

    @Mock
    ClientRepository clientRepository;

    @Mock
    ProviderRepository providerRepository;

    @Mock
    AdminRepository adminRepository;

    @Mock
    AdminResponseMapper adminResponseMapper;

    @Mock
    ClientRequestMapper clientRequestMapper;

    @Mock
    ClientResponseMapper clientResponseMapper;

    @Mock
    ProviderRequestMapper providerRequestMapper;

    @Mock
    ProviderResponseMapper providerResponseMapper;

    @Mock
    PasswordEncoder encoder;

    @Mock
    JwtService jwtService;

    @Mock
    AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        underTest = new AuthService(
            userRepository,
            clientRepository,
            providerRepository,
            adminRepository,
            adminResponseMapper,
            clientRequestMapper,
            clientResponseMapper,
            providerRequestMapper,
            providerResponseMapper,
            encoder,
            authenticationManager,
            jwtService
        );
    }


    @Test
    void canSayIfEmailNotFree() {

        //given
        String email = "mndour428@gmail.com";

        User user = new User();
        user.setEmail(email);
        user.setRole("ADMIN");
        user.setId(1);
        user.setCreatedAt(new Date());
        user.setPassword("$2a$10$l5gBHkQhCrcFiVojU3v7zu.uGGlfjFR1OdEmRUS70onVKw2azs4ru");

        Optional<User> response= Optional.of(user);

        given(userRepository.findByEmail(email)).willReturn(response);

        //when
        boolean test = underTest.isEmailFree(email);

        //then
        assertThat(test).isFalse();

    }


    @Test
    void canSayIfEmailFree()
    {
        //given
        String email = "moussandour2023@gmail.com";
        given(userRepository.findByEmail(email)).willReturn(Optional.empty());

        //when
        boolean test = underTest.isEmailFree(email);

        //then
        assertThat(test).isTrue();

    }


    @Test
    void canRegisterClient() {
        //given
        ClientRequestDTO client = new ClientRequestDTO();
        client.setEmail("mndour428@gmail.com");
        client.setPassword("password123");
        client.setFirstname("Moussa");
        client.setLastname("Ndour");
        client.setProfession("Software Engineer");


        User user = new User();
        user.setEmail(client.getEmail());
        user.setRole("CLIENT");
        user.setPassword("$2a$10$l5gBHkQhCrcFiVojU3v7zu.uGGlfjFR1OdEmRUS70onVKw2azs4ru");

        Client clientReposResponse = new Client();
        clientReposResponse.setFirstname(client.getFirstname());
        clientReposResponse.setLastname(client.getLastname());
        clientReposResponse.setUser(user);
        clientReposResponse.setProfession(client.getProfession());


        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor.forClass(Client.class);

        Client mapperReturn = new Client();
        mapperReturn.setFirstname(client.getFirstname());
        mapperReturn.setLastname(client.getLastname());
        mapperReturn.setProfession(client.getProfession());


        given(clientRequestMapper.toEntity(client)).willReturn(mapperReturn);
        given(encoder.encode(client.getPassword())).willReturn("$2a$10$l5gBHkQhCrcFiVojU3v7zu.uGGlfjFR1OdEmRUS70onVKw2azs4ru");
        given(userRepository.save(any())).willReturn(user);
        given(clientRepository.save(any())).willReturn(clientReposResponse);



        //when
        underTest.registerClient(client);

        //then
        assertAll(
                "Client Registration Test",
                () -> verify(encoder).encode(client.getPassword()),
                () -> verify(userRepository).save(userArgumentCaptor.capture()),
                () -> assertThat(userArgumentCaptor.getValue()).isEqualTo(user),
                () -> verify(clientRepository).save(clientArgumentCaptor.capture()),
                () -> assertThat(clientArgumentCaptor.getValue()).isEqualTo(clientReposResponse)
        );

    }

    @Test
    void RegisterClientDoThrowExceptionWhenEmailNotFree()
    {

        //give

        ClientRequestDTO client = new ClientRequestDTO();
        client.setEmail("mndour428@gmail.com");
        client.setPassword("password123");
        client.setFirstname("Moussa");
        client.setLastname("Ndour");
        client.setProfession("Software Engineer");

        User user = new User();
        user.setEmail(client.getEmail());
        user.setRole("CLIENT");
        user.setPassword("$2a$10$l5gBHkQhCrcFiVojU3v7zu.uGGlfjFR1OdEmRUS70onVKw2azs4ru");

        given(userRepository.findByEmail(client.getEmail())).willReturn(Optional.of(user));


        //when & then
        assertThatThrownBy(() -> underTest.registerClient(client))
                .isInstanceOf(EmailAlreadyExistException.class)
                .hasMessage("There is already an account with this email.");

        verify(userRepository, never()).save(any());
        verify(clientRepository, never()).save(any());
    }


    @Test
    void canRegisterProvider() {

        //given
        ProviderRequestDTO provider = new ProviderRequestDTO();
        provider.setEmail("mndour428@gmail.com");
        provider.setPassword("password123");
        provider.setFirstname("Moussa");
        provider.setLastname("Ndour");
        provider.setProfession("Software Engineer");


        User user = new User();
        user.setEmail(provider.getEmail());
        user.setRole("PROVIDER");
        user.setPassword("$2a$10$l5gBHkQhCrcFiVojU3v7zu.uGGlfjFR1OdEmRUS70onVKw2azs4ru");

        Provider providerReposResponse = new Provider();
        providerReposResponse.setFirstname(provider.getFirstname());
        providerReposResponse.setLastname(provider.getLastname());
        providerReposResponse.setUser(user);
        providerReposResponse.setLevel("JUNIOR");
        providerReposResponse.setYearsOfExperience(1);
        providerReposResponse.setProfession(provider.getProfession());


        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<Provider> providerArgumentCaptor = ArgumentCaptor.forClass(Provider.class);

        Provider mapperReturn = new Provider();
        mapperReturn.setFirstname(provider.getFirstname());
        mapperReturn.setLastname(provider.getLastname());
        mapperReturn.setProfession(provider.getProfession());


        given(providerRequestMapper.toEntity(provider)).willReturn(mapperReturn);
        given(encoder.encode(provider.getPassword())).willReturn("$2a$10$l5gBHkQhCrcFiVojU3v7zu.uGGlfjFR1OdEmRUS70onVKw2azs4ru");
        given(userRepository.save(any())).willReturn(user);
        given(providerRepository.save(any())).willReturn(providerReposResponse);



        //when
        underTest.registerProvider(provider);

        //then
        assertAll(
                "Provider Registration Test",
                () -> verify(encoder).encode(provider.getPassword()),
                () -> verify(userRepository).save(userArgumentCaptor.capture()),
                () -> assertThat(userArgumentCaptor.getValue()).isEqualTo(user),
                () -> verify(providerRepository).save(providerArgumentCaptor.capture()),
                () -> assertThat(providerArgumentCaptor.getValue()).isEqualTo(providerReposResponse)
        );

    }

    @Test
    void connect() {

    }


    @Test
    void refreshToken() {
    }


    @Test
    void registerUser() {
    }
}