package com.beyondlmz.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



/**
 * @author liumingzhong
 */
@Component("initData")
public class InitData {

    /***
     * 微信身份验证授权回调地址
     */
    @Value("${redirect_uri}")
    public String redirectUri;

    @Value("${corp_redirect_uri}")
    public String corpRedirectUri;

    /***
     * 项目地址
     */
    @Value("${project_address}")
    public String projectAddress;

    /***
     * 项目域名
     */
    @Value("${project_domian}")
    public String projectDomian;


    /***
     * 项目名称
     */
    @Value("${context_root}")
    public String contextRoot;

    /***
     *素材图片上传地址
     */
    @Value("${material_img_upload_path}")
    public String materialImgUploadPath;

    /***
     * 二维码下载地址
     */
    @Value("${qrcode_down_path}")
    public String qrcodeDownPath;

    /***
     * 临时文件夹目录
     */
    @Value("${file_tmp_path}")
    public String fileTmpPath;

    /***
     * 条形码保存路径
     */
    @Value("${barcode_path}")
    public String barcodePath;

    /***
     * 条形码访问地址
     */
    @Value("${barcode_root}")
    public String barcodeRoot;

    /***
     * 图片服务器地址
     */
    @Value("${images_root}")
    public String imagesRoot;

    /***
     * 是否执行task任务
     */
    @Value("${is_task}")
    public String isTask;


    /***
     * 支付的appid
     */
    @Value("${pay_appid}")
    public String payAppid;

    /***
     * 支付商户号
     */
    @Value("${pay_mchid}")
    public String payMchid;


    /***
     * 支付结果通知地址
     */
    @Value("${pay_notify_url}")
    public String payNotifyUrl;

    /***
     * 支付调用的接口类型
     */
    @Value("${trade_type}")
    public String tradeType;

    /***
     * 微信支付的公众号
     */
    @Value("${pay_no}")
    public String payNo;

}
