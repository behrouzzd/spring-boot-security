package com.springframework.web.controller;

import com.springframework.web.controller.dto.ItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController extends BaseController {

    @RequestMapping("/item")
    public ModelAndView findItem(@RequestParam("itemId") Long itemId) {
        ItemDto itemDto = itemService.findById(itemId);
        ModelAndView model = new ModelAndView();
        if (itemDto != null)
            model.addObject("item", itemDto);

        model.setViewName("user");
        return model;
    }


    @RequestMapping("/items")
    public ModelAndView findItems() {
        List<ItemDto> items = itemService.findItems();
        ModelAndView model = new ModelAndView();
        if (items != null)
            model.addObject("items", items);
        model.setViewName("userPanel");
        return model;
    }

    @RequestMapping("/addItem")
    public ModelAndView addItem(@RequestParam("itemName") String itemName) {

        ModelAndView model = new ModelAndView();
        model.setViewName("userPanel");

        itemService.insertItem(itemName);

        loadAllElementsForUserPanel(model);

        return model;
    }

    @RequestMapping("/403")
    public ModelAndView accessDenied() {
        ModelAndView model = new ModelAndView();
        model.setViewName("403");
        return model;
    }


}
