package com.zby.vo;

import com.zby.pojo.Type;

public class SearchBlog {

    private String title;
    private Long typeId;
    //推荐符号从前端传过来是String类型
    private String recommend;
    private Integer recommend2;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    private Type type;

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

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public Integer getRecommend2() {
        return recommend2;
    }

    public void setRecommend2(Integer recommend2) {
        this.recommend2 = recommend2;
    }
}

