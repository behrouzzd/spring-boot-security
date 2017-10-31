package com.springframework.service;

import com.springframework.web.controller.dto.ItemDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Behrouz-ZD on 9/17/2017.
 */
public interface ItemService {


    @PreAuthorize("hasPermission(#itemId,'viewItem')")
    ItemDto findById(Long id);

    @PreAuthorize("hasPermission('allItem','viewItems')")
    List<ItemDto> findItems();

    @Transactional
    void insertItem(String itemName);
}
