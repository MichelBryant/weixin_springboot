package com.beyondlmz.serviceImpl;

import com.beyondlmz.entity.WxUserInfo;
import com.beyondlmz.mapper.WeixinMapper;
import com.beyondlmz.service.WeixinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by liumingzhong on 2017/12/18.
 */
@Service
public class WeixinServiceImpl implements WeixinService {
    @Autowired
    private WeixinMapper weixinMapper;
    @Override
    public WxUserInfo getWxInfoByOpenId(String openId) {
        return weixinMapper.getWxInfoByOpenId(openId);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean saveWxUserInfo(WxUserInfo wxUserInfo) {
        return weixinMapper.saveWxUserInfo(wxUserInfo);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean updateWxUserInfo(WxUserInfo wxUserInfo) {
        return weixinMapper.updateWxUserInfo(wxUserInfo);
    }
}
