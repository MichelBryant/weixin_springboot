package com.beyondlmz.entity;

import java.util.Date;

/**
 * Created by liumingzhong on 2017/12/19.
 */
public class SysResourceRole {

    private int id;
    private String role_id; //角色ID
    private String resource_id;//资源ID
    private Date update_time;//更新时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}
