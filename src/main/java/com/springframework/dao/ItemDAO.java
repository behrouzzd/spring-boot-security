package com.springframework.dao;

import com.springframework.domain.Item;
import com.springframework.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Behrouz-ZD on 9/17/2017.
 */
@Repository
public interface ItemDAO extends JpaRepository<Item, Long> {

    List<Item> findByUsers(User user);
}
