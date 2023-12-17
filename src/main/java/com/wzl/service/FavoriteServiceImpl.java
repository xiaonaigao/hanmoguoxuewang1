package com.wzl.service;

import com.wzl.dao.FavoriteRepository;
import com.wzl.po.Favorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author wang
 * @version 1.0
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;
    //保存喜爱的文章
    @Override
    public Favorite saveFavorite(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }
    //查找喜爱的文章
    @Transactional
    @Override
    public Page<Favorite> listFavorite(Pageable pageable) {
        return favoriteRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        favoriteRepository.delete(id);

    }


}
