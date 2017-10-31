package com.springframework;

import net.sf.ehcache.CacheManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootApplication
@EnableJpaRepositories
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ServletInitializer.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ServletInitializer.class, args);
    }

    @Bean
    public CacheManager cacheManager() {
        String url = "classpath:ehcache.xml";
        InputStream inp = null;
        try {
            inp = new FileInputStream(ResourceUtils.getFile(url));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error init cacheManager!",e);
        }
        return CacheManager.create(inp);
    }

}
