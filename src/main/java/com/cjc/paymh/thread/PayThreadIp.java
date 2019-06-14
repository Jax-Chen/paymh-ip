package com.cjc.paymh.thread;

import com.cjc.paymh.service.PayService;
import com.cjc.paymh.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.Map;

/**
 * Created by chenjc23273 on 2019/03/12
 *
 * @author chenjc23273
 */
public class PayThreadIp extends Thread {

    protected static final Logger logger = LoggerFactory.getLogger(PayThreadIp.class);
    private Integer index;
    private String cbgSafeCode;
    private String cookie;
    private Map paramMap = null;
    private String payCookie;
    private String payPassword;

    @Override
    public void run() {

        try {

            String orderid_to_epay = OrderThreadIp.createOrderId(index);
            logger.info("-----------------------订单号为：" + orderid_to_epay);
            if(StringUtils.isNotBlank(orderid_to_epay)){
                String epay_pay_url = PayService.getOrderInfoIp(orderid_to_epay,cbgSafeCode,cookie,null,index);
                logger.info("查询订单里的===============获取支付地址完成 " + DateUtil.currentDateR() + " " + epay_pay_url);
                if(StringUtils.isNotBlank(epay_pay_url)){
                    logger.info("访问支付地址，生成订单，返回订单号 " + DateUtil.currentDateR());
                    String payNo = PayService.getPayNoIp(epay_pay_url,payCookie,null,1);
                    logger.info("查询订单里的===============访问支付地址完成 " + payNo + " " + DateUtil.currentDateR());
                    if(StringUtils.isNotBlank(payNo)){
                        String str2 = PayService.goPayFinalIp(payNo,(String)paramMap.get("confirm_price_total"),payPassword,payCookie,null,1);
                    }
                }
            }
//            MainThreadIp.endThreadLocal.set(false);


        } catch (Exception e) {
//            MainThreadIp.endThreadLocal.set(false);
            e.printStackTrace();
        }
    }


    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getCbgSafeCode() {
        return cbgSafeCode;
    }

    public void setCbgSafeCode(String cbgSafeCode) {
        this.cbgSafeCode = cbgSafeCode;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public Map getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map paramMap) {
        this.paramMap = paramMap;
    }

    public String getPayCookie() {
        return payCookie;
    }

    public void setPayCookie(String payCookie) {
        this.payCookie = payCookie;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

}
