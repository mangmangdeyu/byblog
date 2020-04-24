package com.zby.vo;

public class BlogAndTag {

    private Long tagsId;

    private Long blogsId;

    public BlogAndTag(Long tagsId, Long blogsId) {
        this.tagsId = tagsId;
        this.blogsId = blogsId;
    }

    public BlogAndTag() {
    }
}