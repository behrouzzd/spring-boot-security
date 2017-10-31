package com.springframework.config.security.token;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by Behrouz-ZD on 9/16/2017.
 */
public final class TokenInfo {

    private final long created = System.currentTimeMillis();
    private final String token;
    private final UserDetails userDetails;

    public TokenInfo(String token, UserDetails userDetails) {
        this.token = token;
        this.userDetails = userDetails;
    }

    public String getToken() {
        return token;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }
}
