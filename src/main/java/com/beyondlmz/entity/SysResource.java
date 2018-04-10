package com.beyondlmz.entity;

/**
 * Created by liumingzhong on 2017/12/19.
 */
public class SysResource {

    private int id;

    private String resource_string;//url

    private String resource_id;//资源ID

    private String remark;//备注

    private String resource_name;//资源名称

    private String method_name;//资源所对应的方法名

    private String method_path;//资源所对应的包路径


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResource_string() {
        return resource_string;
    }

    public void setResource_string(String resource_string) {
        this.resource_string = resource_string;
    }

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getResource_name() {
        return resource_name;
    }

    public void setResource_name(String resource_name) {
        this.resource_name = resource_name;
    }

    public String getMethod_name() {
        return method_name;
    }

    public void setMethod_name(String method_name) {
        this.method_name = method_name;
    }

    public String getMethod_path() {
        return method_path;
    }

    public void setMethod_path(String method_path) {
        this.method_path = method_path;
    }
}