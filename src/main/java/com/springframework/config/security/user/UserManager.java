package com.springframework.config.security.user;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by Behrouz-ZD on 9/20/2017.
 */
public interface UserManager {

    void cacheUserDetails(UserDetails userDetails);

    void cacheUserDetailsIfAbsent(UserDetails userDetails);

    void removeUserDetails(UserDetails userDetails);

    void removeUserDetails(String username);

    UserDetails getUserDetails(String username);

    Collection<UserDetails> getCachedUsers();

}
