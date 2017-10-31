package com.springframework.service;

import com.springframework.web.controller.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface UserService extends UserDetailsService {

    List<UserDto> getUsers();

    @Transactional
    UserDto register(UserDto userDto);

    @Transactional
    UserDto updateUser(UserDto userDto);

    @Transactional
    void deleteUser(String username);

    @Transactional
    UserDto updatePassword(String username, String oldPassword, String newPassword);

    UserDto getUser(String username);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
