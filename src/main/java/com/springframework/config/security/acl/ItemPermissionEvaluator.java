package com.springframework.config.security.acl;

import com.springframework.config.security.SecurityContextManager;
import com.springframework.dao.ItemDAO;
import com.springframework.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Behrouz-ZD on 9/17/2017.
 */

@Component
public class ItemPermissionEvaluator implements PermissionEvaluator {

    private static final String VIEW_ITEM = "viewItem";
    private static final String VIEW_ITEMS = "viewItems";

    @Autowired
    private ItemDAO itemDAO;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

        if (permission.toString().equals(VIEW_ITEM)) {
            Long itemId = (Long) targetDomainObject;
            Item item = itemDAO.findOne(itemId);
            if (!SecurityContextManager.isCurrentUserAllowed(item))
                return false;
        }
        return true;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
