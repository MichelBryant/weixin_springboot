package com.beyondlmz.mapper;

import com.beyondlmz.entity.SysUser;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liumingzhong on 2017/11/24.
 */
@MapperScan
@Repository
public interface UserMapper {
    public List<SysUser> findAllUser();

    public SysUser loginUser(SysUser user);

    public boolean add(SysUser user);

    public SysUser findUserByAccount(String account);
}
