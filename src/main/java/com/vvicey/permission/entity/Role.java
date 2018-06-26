package com.vvicey.permission.entity;

import java.util.List;

/**
 * @Author nana
 * @Date 18-6-26 下午1:40
 * @Description 身份实体类
 */
public class Role {

    private Integer rid;//role id
    private String role;//角色英文名
    private String description;//角色注释
    private List<Permission> permissionList;//权限集合

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}