package com.vvicey.common.utils;

public class Result {

    private int stateCode;
    private String message;
    private Object object;

    public Result(int stateCode, String message) {
        this.stateCode = stateCode;
        this.message = message;
    }

    public Result(int stateCode, String message, Object object) {
        this.stateCode = stateCode;
        this.message = message;
        this.object = object;
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
}
