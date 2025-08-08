package com.ecommerce.service;

import com.ecommerce.dto.auth.AuthLoginDto;
import com.ecommerce.dto.auth.AuthRegisterDto;
import com.ecommerce.dto.auth.AuthResponseDto;
import com.ecommerce.exception.ResourceAlreadyExistException;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.security.JwtUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponseDto register(AuthRegisterDto authRegisterDto) {
        String email = authRegisterDto.getEmail();
        String username = authRegisterDto.getUsername();

        userRepository.findUserByEmail(email).ifPresent(user -> {
            throw new ResourceAlreadyExistException("User Already Exist With Email: " + email);
        });

        userRepository.findUserByUsername(username).ifPresent(user -> {
            throw new ResourceAlreadyExistException("User Already Exist With Username: " + username);
        });

        User user = userRepository.save(User.builder().email(authRegisterDto.getEmail()).name(authRegisterDto.getName()).password(passwordEncoder.encode(authRegisterDto.getPassword())).username(username).build());
        return modelMapper.map(user, AuthResponseDto.class);
    }

    public AuthResponseDto login(AuthLoginDto authLoginDto) {
        String username = authLoginDto.getUsername();
        String password = authLoginDto.getPassword();

        userRepository.findUserByUsername(username).orElseThrow(() -> {
            throw new ResourceAlreadyExistException("User Record could not be found with Username: " + username);
        });

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtUtil.generateAccessToken(user);
        log.info("JWT Access Token {}", accessToken);

        return modelMapper.map(new AuthResponseDto(user.getId(), accessToken), AuthResponseDto.class);
    }
}
