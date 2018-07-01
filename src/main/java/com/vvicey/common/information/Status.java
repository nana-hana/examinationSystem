package com.vvicey.common.information;

public enum Status {

    //返回成功失败的状态信息
    SUCCESS("成功", 200), FAIL("失败", 403), FAIL_REPETITION("账号重复", 403), NOT_EXIST("账号不存在", 403);

    private String status;
    private int sign;

    Status(String status, int sign) {
        this.status = status;
        this.sign = sign;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }
}
