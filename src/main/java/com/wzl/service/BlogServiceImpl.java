package com.wzl.service;

import com.wzl.NotFoundException;
import com.wzl.dao.BlogRepository;
import com.wzl.po.Blog;
import com.wzl.po.Type;
import com.wzl.util.MarkdownUtils;
import com.wzl.util.MyBeanUtils;
import com.wzl.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @author wang
 * @version 1.0
 */
@Service
public class BlogServiceImpl implements BlogService {
	//把操作数据库的BlogRepository注入过来
	@Autowired
	private BlogRepository blogRepository;

	//根据主键博客id来查询博客
	@Override
	public Blog getBlog(Long id) {
		//findOne通过主键，查询出的对象返回
		return blogRepository.findOne(id);
	}

	//点击标题，获取文章详情页
	@Transactional
	@Override
	public Blog getAndConvert(Long id) {
		Blog blog = blogRepository.findOne(id);
		if (blog == null) {
			throw new NotFoundException("该博客不存在");
		}
		Blog b = new Blog();
		BeanUtils.copyProperties(blog, b);
		String content = b.getContent();
		b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
		blogRepository.updateViews(id);
		return b;
	}

	//根据后台组合文章进行查询，BlogQuery条件是标题+分类+推荐
	@Override
	public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
		return blogRepository.findAll(new Specification<Blog>() {
			@Override
//            Root<Blog> root拿到表名的一些字段
			public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
// 动态查询组合条件，条件放在list里面
				List<Predicate> predicates = new ArrayList<>();
//    文章标题非空判断，然后把前端的值传过来，title，type，推荐生成组合条件
				if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
					predicates.add(cb.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
				}
//                组合分类条件
				if (blog.getTypeId() != null) {
					predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
				}
//                组合推荐查询条件
				if (blog.isRecommened()) {
					predicates.add(cb.equal(root.<Boolean>get("recommened"), blog.isRecommened()));
				}
//                根据cq进行条件查询，转化为数组，根据组合条件自动拼接
				cq.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
		}, pageable);
	}


	//获取博客列表，在前端展示。
	@Override
	public Page<Blog> listBlog(Pageable pageable) {
		return blogRepository.findAll1(pageable);
	}

	//标签
	@Override
	public Page<Blog> listBlog(Long tagId, Pageable pageable) {
		return blogRepository.findAll(new Specification<Blog>() {
			@Override
			public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				Join join = root.join("tags");
				return cb.equal(join.get("id"), tagId);
			}
		}, pageable);
	}

	//搜索功能
	@Override
	public Page<Blog> listBlog(String query, Pageable pageable) {
		return blogRepository.findByQuery(query, pageable);
	}

	//按年份博客
	@Override
	public Map<String, List<Blog>> archiveBlog() {
		List<String> years = blogRepository.findGroupYear();
		Map<String, List<Blog>> map = new HashMap<>();
		for (String year : years) {
			map.put(year, blogRepository.findByYear(year));
		}
		return map;
	}

	//博客的数量
	@Override
	public Long countBlog() {
		return blogRepository.count();
	}

	//博客推荐列表
	@Override
	public List<Blog> listRecommenedBlogTop(Integer size) {
		Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
		Pageable pageable = new PageRequest(0, size, sort);
		return blogRepository.findTop(pageable);
	}

	//保存文章信息
	@Transactional
	@Override
	public Blog saveBlog(Blog blog) {
		if (blog.getId() == null) {
			blog.setCreateTime(new Date());
			blog.setUpdateTime(new Date());
			blog.setViews(0);
		} else {
			blog.setUpdateTime(new Date());
		}
		return blogRepository.save(blog);
	}

	//编辑文章信息
	@Transactional
	@Override
	public Blog updateBlog(Long id, Blog blog) {
		//编辑的时候先进行查询，找到对应对象
		Blog b = blogRepository.findOne(id);
		if (b == null) {
			throw new NotFoundException("该博客不存在");
		}
		//存在的话，通过复制方法，把修改后的blog复制给b
		BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
		b.setUpdateTime(new Date());
		return blogRepository.save(b);
	}

	//删除文章信息，通过delete方法根据文章id进行删除
	@Override
	public void deleteBlog(Long id) {
		blogRepository.delete(id);

	}
}
