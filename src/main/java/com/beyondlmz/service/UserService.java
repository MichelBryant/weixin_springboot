package com.beyondlmz.service;

import com.beyondlmz.entity.SysUser;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by liumingzhong on 2017/11/24.
 */
public interface UserService extends BaseService<SysUser> {
    public List<SysUser> getAllUser();
    public SysUser loginUser(SysUser user);
    public SysUser findUserByAccount(String account);

}
