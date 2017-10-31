package com.springframework.web.controller;

import com.springframework.service.ItemService;
import com.springframework.service.UserService;
import com.springframework.web.controller.dto.ItemDto;
import com.springframework.web.controller.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Behrouz-ZD on 9/20/2017.
 */
public class BaseController {

    @Autowired
    protected ItemService itemService;

    @Autowired
    protected UserService userService;

    protected void loadAllElementsForUserPanel(ModelAndView model) {
        List<UserDto> users = userService.getUsers();
        model.addObject("users", users);

        List<ItemDto> items = itemService.findItems();
        model.addObject("items", items);

    }
}
