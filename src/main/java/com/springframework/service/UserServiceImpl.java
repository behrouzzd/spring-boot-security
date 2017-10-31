package com.springframework.service;

import com.springframework.dao.UserDAO;
import com.springframework.domain.User;
import com.springframework.utils.ObjectMapper;
import com.springframework.web.controller.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Behrouz-ZD on 16/09/2017.
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;


    @Override
    public List<UserDto> getUsers() {
        return ObjectMapper.mapUserList(userDAO.findAll());
    }

    @Override
    public UserDto register(UserDto userDto) {

        User user = ObjectMapper.mapUserDto(userDto);
        userDAO.save(user);
        return ObjectMapper.mapUser(user);

    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = userDAO.findByUsername(userDto.getUsername());
        if (user != null) {
            user.setPassword(userDto.getPassword());
            user = userDAO.save(user);
        }
        return ObjectMapper.mapUser(user);
    }

    @Override
    public UserDto updatePassword(String username, String oldPassword, String newPassword) {

        User user = userDAO.findByUsername(username);
        if (user != null && user.getPassword().equals(oldPassword))
            user.setPassword(newPassword);
        return ObjectMapper.mapUser(user);
    }

    @Override
    public void deleteUser(String username) {
        User user = userDAO.findByUsername(username);
        if (user != null)
            userDAO.delete(user);
    }

    @Override
    public UserDto getUser(String username) {
        return ObjectMapper.mapUser(userDAO.findByUsername(username));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDAO.findByUsername(username);
        if (user != null)
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getDefaultAuthorities());
        throw new UsernameNotFoundException("User not found!");
    }

    public Collection<? extends GrantedAuthority> getDefaultAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ADMIN";
            }
        });
        return list;
    }
}
