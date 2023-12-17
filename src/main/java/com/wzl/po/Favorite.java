package com.wzl.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author wang
 * @version 1.0
 */
@Entity
@Table(name = "t_favorite")
public class Favorite {
    @Id
    @GeneratedValue
    private Long id;
    private Long blogid;
    private Long userid;
    private String title;


    public Favorite() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getBlogid() {
        return blogid;
    }

    public void setBlogid(Long blogid) {
        this.blogid = blogid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "id=" + id +
                ", blogid=" + blogid +
                ", userid=" + userid +
                '}';
    }
}
