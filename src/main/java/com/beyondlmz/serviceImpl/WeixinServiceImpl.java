package com.beyondlmz.serviceImpl;

import com.beyondlmz.constants.InitData;
import com.beyondlmz.controller.WxController;
import com.beyondlmz.entity.WxUserInfo;
import com.beyondlmz.mapper.WeixinMapper;
import com.beyondlmz.service.WeixinService;
import com.beyondlmz.util.HttpUtil;
import com.beyondlmz.util.StringUtil;
import com.beyondlmz.util.WeixinUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by liumingzhong on 2017/12/18.
 */
@Service
public class WeixinServiceImpl implements WeixinService {
    private Logger logger = LoggerFactory.getLogger(WeixinServiceImpl.class);


    @Autowired
    private WeixinMapper weixinMapper;
    @Autowired
    private InitData initData;
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

    @Override
    public SortedMap<String, String> wxPay(Long tradeId, String openid) throws Exception {
        SortedMap<String, String> finalpackage = new TreeMap<String, String>();
        // 生成支付签名
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid",initData.payAppid);
        packageParams.put("mch_id", initData.payMchid);
        packageParams.put("nonce_str", StringUtil.getRandomString(20,"1"));
        packageParams.put("body", URLEncoder.encode("微信支付测试","UTF-8"));
        packageParams.put("attach", tradeId+","+"110"+","+openid);
        packageParams.put("out_trade_no","201804191214");

        // 这里写的金额为1 分到时修改
        packageParams.put("total_fee", ((int)(0.01*100))+"");
        packageParams.put("spbill_create_ip", "127.0.0.1");
        packageParams.put("notify_url", initData.payNotifyUrl);

        packageParams.put("trade_type", initData.tradeType);
        packageParams.put("openid", openid);
        String sign = WeixinUtil.createPaySign(packageParams);
        logger.info("预支付sign"+sign);

        String xml = "<xml>"
                + "	<appid>"+initData.payAppid+"</appid>"
                + "	<attach>"+packageParams.get("attach")+"</attach>"
                + "	<body>"+packageParams.get("body")+"</body>"
                + "	<mch_id>"+initData.payMchid+"</mch_id>"
                + "	<nonce_str>"+packageParams.get("nonce_str")+"</nonce_str>"
                + "	<notify_url>"+initData.payNotifyUrl+"</notify_url>"
                + "	<openid>"+packageParams.get("openid")+"</openid>"
                + "	<out_trade_no>"+packageParams.get("out_trade_no")+"</out_trade_no>"
                + "	<spbill_create_ip>"+packageParams.get("spbill_create_ip")+"</spbill_create_ip>"
                + "	<total_fee>"+packageParams.get("total_fee")+"</total_fee>"
                + "	<trade_type>"+initData.tradeType+"</trade_type>"
                + "	<sign>"+sign+"</sign>"
                + "</xml>";
        String resultXML = HttpUtil.postJson(WeixinUtil.PAY_URL, new String(xml.getBytes(), "ISO8859-1"),"xml");
        logger.info("微信支付接口返回结果" + resultXML);

        Map<String, String> resultMap = StringUtil.parseXMLtoMap(resultXML);


        String prepay_id = resultMap.get("prepay_id");

        logger.info("预支付ID："+prepay_id);

        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonceStr2 = packageParams.get("nonce_str");
        String packages = "prepay_id=" + prepay_id;
        finalpackage.put("appId", initData.payAppid);
        finalpackage.put("timeStamp", timestamp);
        finalpackage.put("nonceStr", nonceStr2);
        finalpackage.put("package", packages);
        finalpackage.put("signType", "MD5");
        String finalsign = WeixinUtil.createPaySign(finalpackage);
        finalpackage.put("packageValue", packages);
        finalpackage.put("paySign", finalsign);

        return finalpackage;
    }

    /**
     * 保存支付信息
     * @param xml
     * @throws Exception
     */
    @Override
    public void savePayNotify(String xml) throws Exception {

        //解析xml
        Document doc = DocumentHelper.parseText(xml);
        Element rootElt = doc.getRootElement();
        String return_code=rootElt.elementText("return_code");//返回状态码
        String return_msg=rootElt.elementText("return_msg");//返回信息
        String appid = rootElt.elementText("appid");// 微信分配的公众账号ID
        String mch_id = rootElt.elementText("mch_id");// 微信支付分配的商户号
        String device_info=rootElt.elementText("device_info");// 设备号
        String nonce_str=rootElt.elementText("nonce_str");// 随机字符串
        String sign = rootElt.elementText("sign");// 签名
        String result_code = rootElt.elementText("result_code");//SUCCESS/FAIL
        String err_code = rootElt.elementText("err_code");//错误代码
        String err_code_des = rootElt.elementText("err_code_des");//错误代码描述
        String openid = rootElt.elementText("openid");//用户标识
        String is_subscribe= rootElt.elementText("is_subscribe");//是否关注公众账号
        String trade_type= rootElt.elementText("trade_type");//交易类型
        String bank_type= rootElt.elementText("bank_type");//付款银行
        String total_fee= rootElt.elementText("total_fee");//总金额
        String fee_type= rootElt.elementText("fee_type");//货币种类
        String cash_fee= rootElt.elementText("cash_fee");//现金支付金额
        String cash_fee_type= rootElt.elementText("cash_fee_type");//现金支付货币类型
        String coupon_fee= rootElt.elementText("coupon_fee");//代金券或立减优惠金额
        String coupon_count= rootElt.elementText("coupon_count");//代金券或立减优惠使用数量
        String coupon_batch_id= rootElt.elementText("coupon_batch_id_$n");//代金券或立减优惠批次ID
        String coupon_id= rootElt.elementText("coupon_id_$n");//代金券或立减优惠ID
        String coupon_fee_n= rootElt.elementText("coupon_fee_$n");//单个代金券或立减优惠支付金额
        String transaction_id= rootElt.elementText("transaction_id");//微信支付订单号
        String out_trade_no= rootElt.elementText("out_trade_no");//商户订单号
        String attach= rootElt.elementText("attach");//商家数据包
        String time_end= rootElt.elementText("time_end");//支付完成时间


        if("SUCCESS".equals(result_code)){//支付成功
            String tradeId=attach.split(",")[0];
            String tradeTenderId=attach.split(",")[1];
            String payopenid=attach.split(",")[2];
            if(initData.payAppid.equals(appid)&&initData.payMchid.equals(mch_id)&&payopenid.equals(openid)){
                logger.info("执行微信支付成功1.....");
                //获取订单状态
                Long id=Long.parseLong(tradeId);
            }
        }
    }
}
