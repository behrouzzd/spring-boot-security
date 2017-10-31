package com.springframework.service;

import com.springframework.config.security.SecurityContextManager;
import com.springframework.dao.ItemDAO;
import com.springframework.dao.UserDAO;
import com.springframework.domain.Item;
import com.springframework.domain.User;
import com.springframework.utils.ObjectMapper;
import com.springframework.web.controller.dto.ItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Behrouz-ZD on 9/17/2017.
 */

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDAO itemDAO;

    @Autowired
    private UserDAO userDAO;


    @Override
    public ItemDto findById(Long itemId) {
        return ObjectMapper.mapItem(itemDAO.findOne(itemId));
    }

    @Override
    public List<ItemDto> findItems() {

        User user = userDAO.findByUsername(SecurityContextManager.getCurrentUser());
        if (user != null)
            return ObjectMapper.mapItems(itemDAO.findByUsers(user));
        return null;
    }

    @Override
    public void insertItem(String itemName) {

        User user = userDAO.findByUsername(SecurityContextManager.getCurrentUser());
        if (user != null) {
            Item item = new Item();
            item.setName(itemName);
            itemDAO.save(item);
            user.getItems().add(item);
            userDAO.save(user);
        }

    }
}
