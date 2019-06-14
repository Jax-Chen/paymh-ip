package com.cjc.paymh.thread;

import com.cjc.paymh.constant.URL;
import com.cjc.paymh.service.PayService;
import com.cjc.paymh.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.Map;

/**
 * Created by chenjc23273 on 2019/02/15
 *
 * @author chenjc23273
 */
public class OrderThreadIp extends Thread {

    protected static final Logger logger = LoggerFactory.getLogger(OrderThreadIp.class);

    private TextArea t;
    private Integer index;
    private String cbgSafeCode;
    private String cookie;
    private Map paramMap = null;
    private Map<Integer,PayThreadIp> payMap = null;
    private String payCookie;
    private String payPassword;
    private String buyTime;
    private String beginTime;
    private String endTime;
    private String sleepTime;
    private boolean isQuery;
    private String ip;
    private int port;
    private HttpClient httpClient;

//    public static volatile boolean end = false;

    @Override
    public void run() {

        try {
            if (StringUtils.isNotBlank(sleepTime)){
                this.sleep(Integer.valueOf(sleepTime));
            }else{
                this.sleep(500);
            }
            if (DateUtil.isBuyTime(buyTime,beginTime,endTime)){

                if(!isQuery){
//                    logger.info(DateUtil.currentDateR() + "  线程一开始");
                    String orderid_to_epay = "";
                    if(!MainThreadIp.endThreadLocal.get()){
                        orderid_to_epay = PayService.addOrderIp(paramMap,cbgSafeCode,cookie,httpClient);
                    }
                    if(StringUtils.isNotBlank(orderid_to_epay) && !MainThreadIp.endThreadLocal.get()){
                        MainThreadIp.endThreadLocal.set(true);
                        if("-1".equals(orderid_to_epay)){

                            for (int i = 1; i <= 10; i++) {
                                executPay(payMap.get(i),i,cbgSafeCode,cookie,paramMap,payCookie,payPassword);
                            }

                        }else{
                            String epay_pay_url = PayService.getOrderInfo(orderid_to_epay,cbgSafeCode,cookie,t,0);
                            logger.info("增加订单里的===============获取支付地址完成 " + DateUtil.currentDateR() + " " + epay_pay_url);
                            if(StringUtils.isNotBlank(epay_pay_url)){
                                String payNo = PayService.getPayNo(epay_pay_url,payCookie,t,0);
                                logger.info("增加订单里的===============访问支付地址完成 " + payNo + " " + DateUtil.currentDateR());
                                if (StringUtils.isBlank(payNo)){
                                    payNo = PayService.getPayNo(epay_pay_url,payCookie,t,0);
                                }
                                if(StringUtils.isNotBlank(payNo)){
                                    String str2 = PayService.goPayFinal(payNo,(String)paramMap.get("confirm_price_total"),payPassword,payCookie,t,0);
                                }
                            }
//                            MainThreadIp.endThreadLocal.set(false);
                        }
                    }
                }else{
                    String orderid_to_epay = createOrderId(index);
//                    String orderid_to_epay = PayService.queryOrderIp(cbgSafeCode,cookie,httpClient);
                    logger.info("-----------------------订单号为：" + orderid_to_epay);
                    if(StringUtils.isNotBlank(orderid_to_epay)){
                        String epay_pay_url = PayService.getOrderInfoIp(orderid_to_epay,cbgSafeCode,cookie,httpClient);
                        logger.info("查询订单里的===============获取支付地址完成 " + DateUtil.currentDateR() + " " + epay_pay_url);
                        if(StringUtils.isNotBlank(epay_pay_url) && !MainThreadIp.endThreadLocal.get()){
                            MainThreadIp.endThreadLocal.set(true);
//                            String payNo = PayService.getPayNo(epay_pay_url,payCookie,t,Math.floorDiv(index,10));
                            String payNo = PayService.getPayNo(epay_pay_url,payCookie,t,0);
                            logger.info("查询订单里的===============访问支付地址完成 " + payNo + " " + DateUtil.currentDateR());
//                        if (StringUtils.isBlank(payNo)){
//                            payNo = PayService.getPayNo(epay_pay_url,payCookie,t,0);
//                        }
                            if(StringUtils.isNotBlank(payNo)){
//                                String str2 = PayService.goPayFinal(payNo,(String)paramMap.get("confirm_price_total"),payPassword,payCookie,t,Math.floorDiv(index,10));
                                String str2 = PayService.goPayFinal(payNo,(String)paramMap.get("confirm_price_total"),payPassword,payCookie,t,0);
                            }
                        }
                    }
//                    MainThreadIp.endThreadLocal.set(false);
                }
            }
        } catch (Exception e) {
//            MainThreadIp.endThreadLocal.set(false);
            e.printStackTrace();
        }
    }


    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * 生成订单号
     * @param index
     * @return
     */
    public static String createOrderId(Integer index){
        index = index%10;
        if(StringUtils.isNotBlank(URL.ORDERID_TO_EPAY)){
            int i = URL.ORDERID_TO_EPAY.indexOf("_")+1;
            String prev = URL.ORDERID_TO_EPAY.substring(0,i);
            String str = URL.ORDERID_TO_EPAY.substring(i,URL.ORDERID_TO_EPAY.length());
            String fix = (Integer.valueOf(str) + index) + "";
            String result = prev + fix;
            return result;
        }else{
            return "";
        }
    }


    public static void executPay(PayThreadIp thread,Integer index, String cbgSafeCode, String cookie,
                                 Map paramMap, String payCookie, String payPassword){
        thread.setIndex(index);
        thread.setCbgSafeCode(cbgSafeCode);
        thread.setCookie(cookie);
        thread.setParamMap(paramMap);
        thread.setPayCookie(payCookie);
        thread.setPayPassword(payPassword);
        thread.start();

    }

//    public static void main(String[] args) {
//        System.out.println(createOrderId(1));;
//    }


    public Map<Integer, PayThreadIp> getPayMap() {
        return payMap;
    }

    public void setPayMap(Map<Integer, PayThreadIp> payMap) {
        this.payMap = payMap;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isQuery() {
        return isQuery;
    }

    public void setQuery(boolean query) {
        isQuery = query;
    }

    public String getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(String sleepTime) {
        this.sleepTime = sleepTime;
    }
    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public TextArea getT() {
        return t;
    }

    public void setT(TextArea t) {
        this.t = t;
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

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }
}