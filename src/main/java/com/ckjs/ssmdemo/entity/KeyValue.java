package com.ckjs.ssmdemo.entity;

/**
 * @Description:key value形式的基础类，用于所有需要key value形式传值的情况
 * @Auther: ckjs
 * @Date: 2018/11/16 14:28
 */
public class KeyValue {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}