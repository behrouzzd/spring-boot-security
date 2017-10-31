package com.springframework.config.security;

import com.springframework.config.security.filter.CORSFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Created by Behrouz-ZD on 9/16/2017.
 */

@Configuration
public class WebSecurityAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("customDaoAuthenticationProvider")
    private AuthenticationProvider authenticationProvider;

    @Autowired
    @Qualifier("customAuthenticationSuccessHandler")
    AuthenticationSuccessHandler authenticationSuccessHandler;


    @Autowired
    @Qualifier("customAuthenticationFailureHandler")
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    @Qualifier("tokenBasedAuthenticationFilter")
    GenericFilterBean tokenBasedAuthenticationFilter;

    @Autowired
    @Qualifier("customAccessDeniedHandler")
    private AccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login", "/failedLogin").permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)

                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .and().csrf().disable();

        // register custom filters
        http.addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class);
        //http.addFilterAfter(tokenBasedAuthenticationFilter, SecurityContextPersistenceFilter.class);
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    @Bean(name = "customAuthenticationManager")
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
