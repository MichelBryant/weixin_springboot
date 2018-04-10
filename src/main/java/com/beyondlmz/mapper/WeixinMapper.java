package com.beyondlmz.mapper;

import com.beyondlmz.entity.WxUserInfo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

/**
 * Created by liumingzhong on 2017/12/18.
 */
@MapperScan
@Repository
public interface WeixinMapper {

    public WxUserInfo getWxInfoByOpenId(String openId);

    public boolean saveWxUserInfo(WxUserInfo wxUserInfo);

    public boolean updateWxUserInfo(WxUserInfo wxUserInfo);
}
