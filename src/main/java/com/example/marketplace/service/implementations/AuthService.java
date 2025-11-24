package com.example.marketplace.service.implementations;

import com.example.marketplace.dto.request.Login;
import com.example.marketplace.dto.request.UserRequest;
import com.example.marketplace.entity.User;
import com.example.marketplace.mapper.request.UserRequestMapper;
import com.example.marketplace.repository.UserRepository;
import com.example.marketplace.service.interfaces.AuthServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements AuthServiceInterface {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRequestMapper userRequestMapper;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public void login(Login user) {

    }

    @Override
    public void registerUser(UserRequest user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(userRequestMapper.toEntity(user));

    }

    @Override
    public boolean checkUserEmail(String email) {
        User user=userRepository.findByEmail(email).orElse(null);

        if(user==null)
            return true;
        else
            return false;
    }
}
