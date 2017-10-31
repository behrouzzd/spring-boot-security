package com.springframework.utils;

import com.springframework.domain.Item;
import com.springframework.domain.User;
import com.springframework.web.controller.dto.ItemDto;
import com.springframework.web.controller.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Behrouz-ZD on 16/09/2017.
 */
public class ObjectMapper {

    public static UserDto mapUser(User user) {

        if (user == null)
            return null;

        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        return userDto;

    }


    public static User mapUserDto(UserDto userDto) {

        if (userDto == null)
            return null;

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;

    }

    public static List<UserDto> mapUserList(List<User> users) {

        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users)
            userDtos.add(mapUser(user));
        return userDtos;
    }


    public static List<User> mapUserDtoList(List<UserDto> userDtos) {

        List<User> users = new ArrayList<>();
        for (UserDto userDto : userDtos)
            users.add(mapUserDto(userDto));
        return users;
    }


    public static ItemDto mapItem(Item item) {

        if (item == null)
            return null;

        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        return itemDto;

    }

    public static List<ItemDto> mapItems(List<Item> items) {

        if (items == null)
            return null;

        List<ItemDto> list = new ArrayList<>();

        for (Item item : items)
            list.add(mapItem(item));

        return list;

    }
}
