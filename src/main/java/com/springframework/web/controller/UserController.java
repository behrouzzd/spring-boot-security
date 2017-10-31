package com.springframework.web.controller;

import com.springframework.config.security.SecurityContextManager;
import com.springframework.service.ItemService;
import com.springframework.service.UserService;
import com.springframework.web.controller.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    ItemService itemService;


    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("custom-login");
        return mav;
    }

    @RequestMapping(value = "/completeLogin")
    public ModelAndView completeLogin() {

        ModelAndView model = new ModelAndView();
        model.setViewName("user");

        return model;

    }

    @RequestMapping(value = "/failedLogin")
    public ModelAndView failedLogin() {

        ModelAndView model = new ModelAndView();
        model.addObject("error", "Invalid username or password!");
        model.setViewName("custom-login");

        return model;

    }

    @RequestMapping(value = "/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {

        SecurityContextManager.doLogout(request, response);
        ModelAndView model = new ModelAndView();
        model.setViewName("custom-login");

        return model;

    }

    @RequestMapping("/userPanel")
    public ModelAndView listUsers() {

        ModelAndView model = new ModelAndView();
        model.setViewName("userPanel");
        loadAllElementsForUserPanel(model);
        return model;
    }

    @RequestMapping("/addUser")
    public ModelAndView addUser(@Valid UserDto userDto, BindingResult result) {

        ModelAndView model = new ModelAndView();
        model.setViewName("userPanel");


        if (result.hasErrors()) {
            model.addObject("error", "Input Bad format.!");
        } else {
            if(userService.getUser(userDto.getUsername())== null)
                userService.register(userDto);
            else
                model.addObject("userExist", "User exist.!");
        }

        loadAllElementsForUserPanel(model);
        return model;

    }





}
