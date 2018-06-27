package com.vvicey.common.utils;

import com.vvicey.login.entity.Loginer;

/**
 * @Author nana
 * @Date 18-6-24 下午7:15
 * @Description 返回CRUD状态信息
 */
public class Result {

    private int stateCode;//HTTP状态码
    private String message;//返回的提示信息
    private Object returnObject;//返回数据对象

    /**
     * 构造器
     *
     * @param stateCode HTTP状态码
     * @param message   返回的提示信息
     */
    public Result(int stateCode, String message) {
        this.stateCode = stateCode;
        this.message = message;
    }

    /**
     * 带实体返回构造器
     *
     * @param stateCode HTTP状态码
     * @param message   返回的提示信息
     * @param returnObject   返回数据对象
     */
    public Result(int stateCode, String message, Object returnObject) {
        this.stateCode = stateCode;
        this.message = message;
        this.returnObject = returnObject;
    }

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getLoginer() {
        return returnObject;
    }

    public void setLoginer(Loginer returnObject) {
        this.returnObject = returnObject;
    }
}
