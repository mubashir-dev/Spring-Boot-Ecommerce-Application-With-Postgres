package com.ecommerce.service;

import com.ecommerce.dto.auth.AuthForgotPasswordComplete;
import com.ecommerce.dto.auth.AuthForgotPasswordDto;
import com.ecommerce.dto.auth.AuthLoginDto;
import com.ecommerce.dto.auth.AuthResponseDto;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.enums.UserTokenType;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceAlreadyExistException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.User;
import com.ecommerce.model.UserToken;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.repository.UserTokenRepository;
import com.ecommerce.security.JwtUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

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

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private MailService mailService;

    public PageResponse<AuthResponseDto> login(AuthLoginDto authLoginDto) {
        String username = authLoginDto.getUsername();
        String password = authLoginDto.getPassword();

        userRepository.findUserByUsername(username).orElseThrow(() -> {
            throw new ResourceAlreadyExistException("User Record could not be found with Username: " + username);
        });

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtUtil.generateAccessToken(user);
        log.info("JWT Access Token {}", accessToken);

        return new PageResponse<>(modelMapper.map(new AuthResponseDto(user.getId(), accessToken), AuthResponseDto.class), "User Logged In Successfully");
    }

    public String passwordForgotInitiate(AuthForgotPasswordDto authForgotPasswordDto) {
        User user = userRepository.findUserByEmail(authForgotPasswordDto.getEmail()).orElseThrow(() -> new ResourceNotFoundException("The User Record Could Not be Found This Email"));

        //remove all previous forgot password tokens
        userTokenRepository.removeAllUserTokenByTypeAndUserId(user.getId(), UserTokenType.FORGOT_PASSWORD);

        UserToken userToken = UserToken.builder().user(user).token(UUID.randomUUID().toString().replace("-", "")).userTokenType(UserTokenType.FORGOT_PASSWORD).expiresAt(new Date(System.currentTimeMillis() + 300000)).build();
        userTokenRepository.save(userToken);

        //send email
        String resetLink = "http://localhost:3006/api/v1/public/auth/forgot-password/" + userToken.getToken();

        // Build HTML email body
        String emailBody = String.format("<html>" + "<body>" + "<h3>Password Reset Request</h3>" + "<p>Click the link below to reset your password. This link will expire in 5 minutes.</p>" + "<p><a href=\"%s\">Reset Your Password</a></p>" + "<p>If you did not request this, please ignore this email.</p>" + "</body>" + "</html>", resetLink);

        // Send email
        mailService.sendEmail(user.getEmail(), "Reset Your Password", emailBody);

        return UUID.randomUUID().toString().replace("-", "");
    }

    public PageResponse<?> verifyForgotPassword(String token, AuthForgotPasswordComplete authForgotPasswordComplete) {
        UserToken userToken = userTokenRepository.findUserTokenByToken(token).orElseThrow(() -> new ResourceNotFoundException("The Token Record Could Not be Found"));

        if (userToken.getExpiresAt().before(new Date())) {
            userTokenRepository.removeAllUserTokenByTypeAndUserId(userToken.getUser().getId(), UserTokenType.FORGOT_PASSWORD);
            throw new BadRequestException("The Token Has Been Expired");
        }

        //password & confirm password check
        if (!authForgotPasswordComplete.getPassword().equals(authForgotPasswordComplete.getConfirmPassword())) {
            throw new BadRequestException("Password and Confirm Password do not match.");
        }

        //update password
        userToken.getUser().setPassword(passwordEncoder.encode(authForgotPasswordComplete.getPassword()));

        //email
        String emailBody = String.format("<html>" + "<body>" + "<h3>Password Successfully Restored</h3>" + "<p>Password successfully been restored</p>" + "</body>" + "</html>");
        mailService.sendEmail(userToken.getUser().getEmail(), "Password Restored", emailBody);

        //delete tokens
        userTokenRepository.removeAllUserTokenByTypeAndUserId(userToken.getUser().getId(), UserTokenType.FORGOT_PASSWORD);

        return new PageResponse<>("Password Successfully Been Restored");
    }
}
