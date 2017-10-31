package com.springframework.config.security.auth;

import com.springframework.config.security.user.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * Created by Behrouz-ZD on 9/16/2017.
 */

@Component
public class CustomDaoAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userService;

    @Autowired
    private UserManager userManager;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();

        // Load user from cache
        UserDetails userDetails = userManager.getUserDetails(username);

        // Load user from DB If not exist in cache
        if (userDetails == null) {

            String password = (String) authentication.getCredentials();
            userDetails = userService.loadUserByUsername(username);

            if (userDetails == null || !userDetails.getUsername().equalsIgnoreCase(username)) {
                throw new BadCredentialsException("Username not found.");
            }

            if (!password.equals(userDetails.getPassword())) {
                throw new BadCredentialsException("Wrong password.");
            }

            // Cache user
            userManager.cacheUserDetails(userDetails);

        }

        // Load user into SecurityContextHolder
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return auth;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}