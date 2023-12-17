package com.wzl.service;

import com.wzl.NotFoundException;
import com.wzl.dao.TagRepository;
import com.wzl.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wang
 * @version 1.0
 * 实现分类操作的方法
 */
@Service
public class TagServiceImpl implements TagService {
    //    数据库操作
    @Autowired
    private TagRepository tagRepository;
//增删改查写在事务里事务是指一组逻辑操作单元即一组sql语句。
// 当这个单元中的一部分操作失败,整个事务回滚，只有全部正确才完成提交。
    @Transactional
    @Override
//    保存标签
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    @Override
//    根据标签id查找标签
    public Tag getTag(Long id) {
        return tagRepository.findOne(id);
    }

    @Transactional
    @Override
//    分页查询标签的列表
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {
        return tagRepository.findAll(convertToList(ids));
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort=new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable=new PageRequest(0,size,sort);

        return tagRepository.findTop(pageable);
    }

    private List<Long> convertToList(String ids){
        List<Long> list=new ArrayList<>();
        if (!"".equals(ids) && ids!=null){
            String[] idarray=ids.split(",");
            for (int i = 0; i < idarray.length; i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    //    分页查询
    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagRepository.findOne(id);
        if (t == null) {
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(tag, t);

        return tagRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.delete(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }
}
