package com.wzl.dao;

import com.wzl.po.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wang
 * @version 1.0
 */
public interface FavoriteRepository extends JpaRepository<Favorite,Long> {
}
