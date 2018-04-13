package com.beyondlmz.service;

import com.beyondlmz.entity.WxUserInfo;

import java.util.SortedMap;

/**
 * Created by liumingzhong on 2017/12/18.
 */
public interface WeixinService {
    public WxUserInfo getWxInfoByOpenId(String openId);

    public boolean saveWxUserInfo(WxUserInfo wxUserInfo);

    public boolean updateWxUserInfo(WxUserInfo wxUserInfo);

    /***
     * 生成微信支付
     * @return
     * @throws Exception
     */
    SortedMap<String, String> wxPay(Long tradeId, String openid) throws Exception;


    /***
     * 保存支付信息
     * @throws Exception
     */
    void savePayNotify(String xml) throws Exception;
}
