package com.springframework.config.security;

import com.springframework.domain.Item;
import com.springframework.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;

/**
 * Created by Behrouz-ZD on 17/09/2017.
 */
public class SecurityContextManager {

    public static String getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }

        throw new RuntimeException("Current user did not initialize!");
    }

    public static boolean isCurrentUserAllowed(Item item) {
        if (item == null || item.getUsers() == null)
            return false;

        String username = getCurrentUser();

        Iterator<User> userIterator = item.getUsers().iterator();
        while (userIterator.hasNext()) {
            if (userIterator.next().getUsername().equals(username))
                return true;
        }
        return false;
    }

    public static void doLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }

}
