package com.vvicey.permission.entity;

/**
 * @Author nana
 * @Date 18-6-26 下午1:40
 * @Description 权限实体类
 */
public class Permission {

    private Integer pid;//权限 id
    private String permission;//权限英文名
    private String description;//权限注释

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}