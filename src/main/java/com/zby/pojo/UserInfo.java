package com.zby.pojo;

import java.util.Arrays;

public class UserInfo {

    private String url;
    private String ip;
    private String classMethod;
    private Object[] args;
    private String uri;

    public UserInfo(String url, String ip, String classMethod, Object[] args, String uri) {
        this.url = url;
        this.ip = ip;
        this.classMethod = classMethod;
        this.args = args;
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "{" +
                "url='" + url + '\'' +
                ", ip='" + ip + '\'' +
                ", classMethod='" + classMethod + '\'' +
                ", args=" + Arrays.toString(args) +
                ", uri='" + uri + '\'' +
                '}';
    }
}
