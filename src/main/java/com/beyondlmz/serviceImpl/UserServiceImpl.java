package com.beyondlmz.serviceImpl;

import com.beyondlmz.entity.SysUser;
import com.beyondlmz.mapper.UserMapper;
import com.beyondlmz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liumingzhong on 2017/11/24.
 */
@Service
public class UserServiceImpl implements UserService  {

    @Autowired
    private UserMapper userMapper;

    @Cacheable(value = "users")
    @Override
    public List<SysUser> getAllUser() {
        return userMapper.findAllUser();
    }

    @Override
    public SysUser loginUser(SysUser user) {
        return userMapper.loginUser(user);
    }

    @Override
    public boolean add(SysUser user) {
    return userMapper.add(user);
    }

    @Override
    public SysUser findUserByAccount(String account) {

        return userMapper.findUserByAccount(account);
    }
}
