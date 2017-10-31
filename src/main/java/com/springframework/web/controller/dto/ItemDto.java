package com.springframework.web.controller.dto;

/**
 * Created by Behrouz-ZD on 9/17/2017.
 */
public class ItemDto {

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
