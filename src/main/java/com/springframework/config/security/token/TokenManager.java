package com.springframework.config.security.token;

import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by Behrouz-ZD on 9/16/2017.
 */
public interface TokenManager {

    TokenInfo createNewToken(UserDetails userDetails);

    UserDetails getUserDetails(String token);

    String extractTokenFromRequest(HttpServletRequest httpRequest);

}
