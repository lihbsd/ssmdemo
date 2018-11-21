package com.ckjs.ssmdemo.exception;

/**
 * @Description:
 * @Auther: ckjs
 * @Date: 2018/11/8 18:46
 */
public class MyException extends Exception {

    //异常信息
    private String message;
    //异常编码
    private String code;
    //异常类型
    private String type;

    public MyException(String message) {
        super(message);
        this.message = message;
    }
    public MyException(String type,String message) {
        super(message);
        this.type = type;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}