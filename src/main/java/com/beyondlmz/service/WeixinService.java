package com.beyondlmz.service;

import com.beyondlmz.entity.WxUserInfo;

/**
 * Created by liumingzhong on 2017/12/18.
 */
public interface WeixinService {
    public WxUserInfo getWxInfoByOpenId(String openId);

    public boolean saveWxUserInfo(WxUserInfo wxUserInfo);

    public boolean updateWxUserInfo(WxUserInfo wxUserInfo);
}
