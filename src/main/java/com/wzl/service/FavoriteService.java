package com.wzl.service;

import com.wzl.po.Favorite;
import com.wzl.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author wang
 * @version 1.0
 */
public interface FavoriteService {
    Favorite saveFavorite(Favorite favorite);
    //查询
    Page<Favorite> listFavorite(Pageable pageable);
    //删除收藏
    void deleteBlog(Long id);

}
