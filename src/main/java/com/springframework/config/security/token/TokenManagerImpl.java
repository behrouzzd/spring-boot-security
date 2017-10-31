package com.springframework.config.security.token;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * Created by Behrouz-ZD on 9/16/2017.
 */
@Component
public class TokenManagerImpl implements TokenManager, InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(TokenManagerImpl.class);


    private String tokenHeader = "X-Auth-Token";

    private Cache tokenCache;

    @Autowired
    private CacheManager cacheManager;

    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

    @Override
    public TokenInfo createNewToken(UserDetails userDetails) {
        String token;
        do {
            token = generateToken();
        } while (tokenCache.get(token) != null);

        TokenInfo tokenInfo = new TokenInfo(token, userDetails);
        tokenCache.put(new Element(token, tokenInfo));
        return tokenInfo;
    }

    private String generateToken() {
        byte[] tokenBytes = new byte[32];
        new SecureRandom().nextBytes(tokenBytes);
        return new String(Base64.encode(tokenBytes), StandardCharsets.UTF_8);
    }

    @Override
    public UserDetails getUserDetails(String token) {
        Element element = tokenCache.get(token);
        if(element!=null)
            return ((TokenInfo)element.getObjectValue()).getUserDetails();
        return null;
    }

    @Override
    public String extractTokenFromRequest(HttpServletRequest httpRequest) {
         /* Get token from header */
        String authToken = httpRequest.getHeader(tokenHeader);

		/* If token not found get it from request parameter */
        if (authToken == null) {
            authToken = httpRequest.getParameter("user-token");
        }

        return authToken;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            tokenCache = cacheManager.getCache("tokenCache");
        } catch (Exception exp) {
            logger.error("Error loading tokenCache from cacheManager!", exp);
        }
    }
}
