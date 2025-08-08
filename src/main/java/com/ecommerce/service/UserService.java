package com.ecommerce.service;

import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.dto.user.CreateUserDto;
import com.ecommerce.dto.user.ResponseUserDto;
import com.ecommerce.dto.user.UpdateUserDto;
import com.ecommerce.exception.ResourceAlreadyExistException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public PageResponse<ResponseUserDto> save(CreateUserDto createUserDto) {
        String email = createUserDto.getEmail();
        String username = createUserDto.getUsername();

        userRepository.findUserByEmail(email).ifPresent(user -> {
            throw new ResourceAlreadyExistException("User Already Exist With Email: " + email);
        });

        userRepository.findUserByUsername(username).ifPresent(user -> {
            throw new ResourceAlreadyExistException("User Already Exist With Username: " + username);
        });

        User user = userRepository.save(User.builder().email(createUserDto.getEmail()).name(createUserDto.getName()).password(passwordEncoder.encode(createUserDto.getPassword())).username(username).build());
        return new PageResponse<>(modelMapper.map(user, ResponseUserDto.class), "User Record Has Been Created Successfully");
    }

    public PageResponse<ResponseUserDto> find(Pageable pageable, String search) {
        Page<User> users = userRepository.findAll(pageable,search);
        List<ResponseUserDto> content = users.getContent().stream().map(user -> modelMapper.map(user, ResponseUserDto.class)).toList();
        return new PageResponse<>("Users Data Fetched Successfully", content, users.getNumber(), users.getSize(), users.getTotalElements(), users.getTotalPages(), users.isLast());
    }

    public PageResponse<ResponseUserDto> findOne(UUID uuid) {
        User user = userRepository.findByUUID(uuid).orElseThrow(() -> new ResourceNotFoundException("User Not Found With UUID " + uuid));
        return new PageResponse<>(modelMapper.map(user, ResponseUserDto.class), "User Data Fetched Successfully");
    }

    public PageResponse<ResponseUserDto> findByUsername(String username) {
        return new PageResponse<>(modelMapper.map(userRepository.findUserByUsername(username), ResponseUserDto.class), "Data Fetched Successfully");
    }

    public PageResponse<ResponseUserDto> update(UpdateUserDto updateUserDto, UUID uuid) {
        User updateUser = userRepository.findByUUID(uuid).orElseThrow(() -> new ResourceNotFoundException("User Not Found With UUID " + uuid));

        if (updateUserDto.getEmail() != null && updateUser.getEmail() != updateUser.getEmail()) {
            String email = updateUserDto.getEmail();
            userRepository.findUserByEmail(email).ifPresent(user -> {
                throw new ResourceAlreadyExistException("User Already Exist With Email: " + email);
            });
        }

        if (updateUserDto.getUsername() != null && updateUser.getUsername() != updateUser.getUsername()) {
            String username = updateUserDto.getUsername();
            userRepository.findUserByUsername(username).ifPresent(user -> {
                throw new ResourceAlreadyExistException("User Already Exist With Username: " + username);
            });
        }

        User.UserBuilder userBuilder = updateUser.toBuilder();
        userBuilder.username(updateUserDto.getUsername() != null ? updateUserDto.getUsername() : updateUser.getUsername());
        userBuilder.email(updateUserDto.getEmail() != null ? updateUserDto.getEmail() : updateUser.getEmail());
        userBuilder.name(updateUserDto.getName() != null ? updateUserDto.getName() : updateUser.getName());
        userBuilder.password(updateUserDto.getPassword() != null ? passwordEncoder.encode(updateUserDto.getPassword()) : updateUser.getPassword());

        User user = userRepository.save(userBuilder.build());
        return new PageResponse<>(modelMapper.map(user, ResponseUserDto.class), "User Record Has Been Updated Successfully");
    }

    public PageResponse<ResponseUserDto> delete(UUID uuid) {
        User deleteUser = userRepository.findByUUID(uuid).orElseThrow(() -> new ResourceNotFoundException("User Not Found With UUID " + uuid));
        userRepository.delete(deleteUser);
        return new PageResponse<>("User Record Has Been Deleted Successfully");
    }
}
