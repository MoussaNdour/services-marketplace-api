package com.example.marketplace.service.implementations;

import com.example.marketplace.dto.request.ClientRequestDTO;
import com.example.marketplace.dto.request.Login;
import com.example.marketplace.dto.request.ProviderRequestDTO;
import com.example.marketplace.dto.response.AdminResponseDTO;
import com.example.marketplace.dto.response.LoginResponseDTO;
import com.example.marketplace.dto.response.ProviderResponseDTO;
import com.example.marketplace.entity.Client;
import com.example.marketplace.entity.Provider;
import com.example.marketplace.entity.User;
import com.example.marketplace.exception.EmailAlreadyExistException;
import com.example.marketplace.mapper.request.ClientRequestMapper;
import com.example.marketplace.mapper.request.ClientRequestMapperImpl;
import com.example.marketplace.mapper.request.ProviderRequestMapper;
import com.example.marketplace.mapper.request.ProviderRequestMapperImpl;
import com.example.marketplace.mapper.response.*;
import com.example.marketplace.repository.AdminRepository;
import com.example.marketplace.repository.ClientRepository;
import com.example.marketplace.repository.ProviderRepository;
import com.example.marketplace.repository.UserRepository;
import com.example.marketplace.service.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.Optional;



@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    ClientRepository clientRepository;

    @Mock
    ProviderRepository providerRepository;

    @Mock
    AdminRepository adminRepository;

    @Spy
    AdminResponseMapper adminResponseMapper = new AdminResponseMapperImpl();

    @Spy
    ClientRequestMapper clientRequestMapper = new ClientRequestMapperImpl();

    @Spy
    ClientResponseMapper clientResponseMapper = new ClientResponseMapperImpl();

    @Spy
    ProviderRequestMapper providerRequestMapper = new ProviderRequestMapperImpl();

    @Spy
    ProviderResponseMapper providerResponseMapper = new ProviderResponseMapperImpl();

    @Mock
    PasswordEncoder encoder;

    @Mock
    JwtService jwtService;

    @Mock
    AuthenticationManager authenticationManager;

    @InjectMocks
    AuthService underTest;


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
        given(userRepository.findByEmail(client.getEmail())).willReturn(Optional.empty());

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
        provider.setYearsOfExperience(1);


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



        given(encoder.encode(provider.getPassword())).willReturn("$2a$10$l5gBHkQhCrcFiVojU3v7zu.uGGlfjFR1OdEmRUS70onVKw2azs4ru");
        given(userRepository.save(any())).willReturn(user);
        given(providerRepository.save(any())).willReturn(providerReposResponse);
        given(userRepository.findByEmail(provider.getEmail())).willReturn(Optional.empty());

        //when
        ProviderResponseDTO result = underTest.registerProvider(provider);

        //then
        assertAll(
                "Provider Registration Test",
                () -> verify(encoder).encode(provider.getPassword()),
                () -> verify(userRepository).save(userArgumentCaptor.capture()),
                () -> assertThat(userArgumentCaptor.getValue()).isEqualTo(user),
                () -> verify(providerRepository).save(providerArgumentCaptor.capture()),
                () -> assertThat(providerArgumentCaptor.getValue()).isEqualTo(providerReposResponse),
                () -> assertThat(result).isNotNull(),
                () -> assertThat(result.getEmail()).isEqualTo(provider.getEmail()),
                () -> assertThat(result.getProfession()).isEqualTo("Software Engineer"),
                () -> assertThat(result.getLevel()).isEqualTo("JUNIOR")
        );

    }

    @Test
    void RegisterProviderDoThrowExceptionWhenEmailNotFree()
    {

        //give
        ProviderRequestDTO provider = new ProviderRequestDTO();
        provider.setEmail("mndour428@gmail.com");
        provider.setPassword("password123");
        provider.setFirstname("Moussa");
        provider.setLastname("Ndour");
        provider.setProfession("Software Engineer");

        User user = new User();
        user.setEmail(provider.getEmail());
        user.setRole("CLIENT");
        user.setPassword("$2a$10$l5gBHkQhCrcFiVojU3v7zu.uGGlfjFR1OdEmRUS70onVKw2azs4ru");

        given(userRepository.findByEmail(provider.getEmail())).willReturn(Optional.of(user));


        //when & then
        assertThatThrownBy(() -> underTest.registerProvider(provider))
                .isInstanceOf(EmailAlreadyExistException.class)
                .hasMessage("There is already an account with this email.");

        verify(userRepository, never()).save(any());
        verify(providerRepository, never()).save(any());
    }

    @ParameterizedTest
    @CsvSource({
            "1, JUNIOR",
            "2, JUNIOR",
            "3, MEDIUM",
            "4, MEDIUM",
            "5, SENIOR",
            "10, SENIOR"
    })
    void shouldSetCorrectLevelBasedOnExperience(int years, String expectedLevel) {
        // 1. GIVEN
        ProviderRequestDTO request = new ProviderRequestDTO();
        request.setEmail("test@test.com");
        request.setYearsOfExperience(years);
        request.setPassword("123");


        given(userRepository.findByEmail(any())).willReturn(Optional.empty());
        given(encoder.encode(any())).willReturn("$2a$10$l5gBHkQhCrcFiVojU3v7zu.uGGlfjFR1OdEmRUS70onVKw2azs4ru");


        ArgumentCaptor<Provider> providerCaptor = ArgumentCaptor.forClass(Provider.class);

        //WHEN
        underTest.registerProvider(request);

        //THEN
        verify(providerRepository).save(providerCaptor.capture());
        Provider savedProvider = providerCaptor.getValue();

        assertThat(savedProvider.getLevel()).isEqualTo(expectedLevel);
    }

    @Test
    void connect_ShouldReturnLoginResponseDTO_WhenCredentialsAreValid() {

        Login loginRequest = new Login("test@example.com", "password123");
        User mockUser = User.builder()
                .email("test@example.com")
                .role("ADMIN")
                .build();

        AdminResponseDTO mockProfile = new AdminResponseDTO();
        mockProfile.setEmail(loginRequest.getEmail());
        mockProfile.setId(1);

        Authentication auth = new UsernamePasswordAuthenticationToken(mockUser, null);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(auth);


        when(underTest.findProfileByRole("test@example.com", "ADMIN"))
                .thenReturn(mockProfile);

        when(jwtService.generateToken(anyString(), anyString())).thenReturn("access-token");
        when(jwtService.generateRefreshToken(anyString(), anyString())).thenReturn("refresh-token");


        LoginResponseDTO response = underTest.connect(loginRequest);


        assertNotNull(response);
        assertEquals("access-token", response.getToken());
        assertEquals(mockProfile, response.getProfile());

        verify(authenticationManager).authenticate(any());
        verify(jwtService).generateToken(anyString(), anyString());
    }

    @Test
    void connect_ShouldThrowException_WhenProfileNotFound() {
        // Arrange
        Login loginRequest = new Login("test@example.com", "password123");
        User mockUser = User.builder().email("test@example.com").role("USER").build();
        Authentication auth = new UsernamePasswordAuthenticationToken(mockUser, null);

        when(authenticationManager.authenticate(any())).thenReturn(auth);
        // On simule un profil inexistant
        when(underTest.findProfileByRole(anyString(), anyString())).thenReturn(null);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            underTest.connect(loginRequest);
        });
    }


    @Test
    void refreshToken() {

    }


    @Test
    void registerUser() {

    }
}