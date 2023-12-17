package com.wzl.service;

import com.wzl.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author wang
 * @version 1.0
 */
public interface TagService {
    //    添加标签
    Tag saveTag(Tag tag);

    //    查询标签
    Tag getTag(Long id);


    //    标签分页查询
    Page<Tag> listTag(Pageable pageable);
    //获取所有的标签,在文章编辑页面
    List<Tag> listTag();
    List<Tag> listTag(String ids);
    List<Tag> listTagTop(Integer size);

    //    修改标签
    Tag updateTag(Long id, Tag tag);

    //    删除标签
    void deleteTag(Long id);

    //通过标签名称查找标签
    Tag getTagByName(String name);

}
