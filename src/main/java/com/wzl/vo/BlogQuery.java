package com.wzl.vo;

/**
 * @author wang
 * @version 1.0
 */
public class BlogQuery {

    private String title;
    private Long typeId;
    private boolean recommened;

    public BlogQuery() {
    }

    public boolean isRecommened() {
        return recommened;
    }

    public void setRecommened(boolean recommened) {
        this.recommened = recommened;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }


}
