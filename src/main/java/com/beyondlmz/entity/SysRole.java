package com.beyondlmz.entity;

/**
 * Created by liumingzhong on 2017/12/19.
 */
public class SysRole {

    private int id;
    private SysUser sysUser;//角色对应的用户实体

    private String name;//角色名称


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
