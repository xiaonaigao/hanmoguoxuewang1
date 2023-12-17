package com.wzl.service;

import com.wzl.po.Blog;
import com.wzl.po.Favorite;
import com.wzl.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author wang
 * @version 1.0
 */
public interface BlogService {
    //根据文章id查询文章
    Blog getBlog(Long id);
    //根据文章id获取文章详情
    Blog getAndConvert(Long id);
    //根据后台组合文章进行查询，BlogQuery条件是标题+分类+推荐
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
    //首页展示博客列表
    Page<Blog> listBlog(Pageable pageable);
    //主页标签查询
    Page<Blog> listBlog(Long tagId,Pageable pageable);
    //通过搜索按钮查找出文章相应的界面.
    Page<Blog> listBlog(String query,Pageable pageable);
   //归档查询
   Map<String,List<Blog>> archiveBlog();
    Long countBlog();
    //推荐博客
    List<Blog> listRecommenedBlogTop(Integer size);
    //新增文章
    Blog saveBlog(Blog blog);
    //修改文章，根据id查出来，然后再复制到新的对象里面
    Blog updateBlog(Long id,Blog blog);
    //删除文章
    void deleteBlog(Long id);


}
