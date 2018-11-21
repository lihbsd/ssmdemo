package com.ckjs.ssmdemo.base;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;

/**
 * @Description:工具类，写一些常用的方法类
 * @Auther: ckjs
 * @Date: 2018/11/9 09:31
 */
public class Util {

    /**
     * @Description:shiro提供的随机字符串生成方法
     * @param:
     * @return: 
     * @auther@date: ckjs 2018/11/9 9:32
     */
    private String getRandomNumber(int numBytes){
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes(numBytes).toHex(); //一个Byte占两个字节，此处生成的3字节，字符串长度为6
        return hex;
    }
}