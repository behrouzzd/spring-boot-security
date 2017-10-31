package com.springframework.config.security.user;

import com.springframework.exception.RequiredArgumentException;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Behrouz-ZD on 9/20/2017.
 */

@Component
public class UserCacheManagerImpl implements UserManager, InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(UserCacheManagerImpl.class);

    private Cache userCache;

    @Autowired
    private CacheManager cacheManager;

    @Override
    public void cacheUserDetailsIfAbsent(UserDetails userDetails) {
        if (userDetails == null)
            throw new RequiredArgumentException("Required argument to cache!", "userDetails");

        userCache.putIfAbsent(new Element(userDetails.getUsername(), userDetails));
    }

    @Override
    public void cacheUserDetails(UserDetails userDetails) {

        if (userDetails == null)
            throw new RequiredArgumentException("Required argument to cache!", "userDetails");

        userCache.put(new Element(userDetails.getUsername(), userDetails));

    }

    @Override
    public void removeUserDetails(UserDetails userDetails) {

        if (userDetails == null)
            throw new RequiredArgumentException("Required argument to remove!", "userDetails");

        userCache.remove(userDetails.getUsername());

    }

    @Override
    public void removeUserDetails(String username) {

        if (ObjectUtils.isEmpty(username))
            throw new RequiredArgumentException("Required argument to get user details!", "username");

        userCache.remove(username);

    }

    @Override
    public UserDetails getUserDetails(String username) {

        if (ObjectUtils.isEmpty(username))
            throw new RequiredArgumentException("Required argument to get user details!", "username");

        Element element = userCache.get(username);
        if (element != null)
            return (UserDetails) element.getObjectValue();

        return null;
    }

    @Override
    public Collection<UserDetails> getCachedUsers() {

        List<String> allUsername = userCache.getKeys();
        Map<Object, Element> allUserDetails = userCache.getAll(allUsername);

        List<UserDetails> detailsList = new ArrayList<>();

        for (Map.Entry<Object, Element> entry : allUserDetails.entrySet()) {
            detailsList.add((UserDetails) entry.getValue().getObjectValue());
        }

        return detailsList;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            userCache = cacheManager.getCache("userCache");
        } catch (Exception exp) {
            logger.error("Error loading userCache from cacheManager!", exp);
        }
    }
}
