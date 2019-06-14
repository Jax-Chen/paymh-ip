package com.cjc.paymh.thread;

import com.cjc.paymh.service.PayService;
import com.cjc.paymh.util.DateUtil;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by chenjc23273 on 2019/02/24
 *
 * @author chenjc23273
 */
public class MainThreadIp2 extends Thread{

    protected static final Logger logger = LoggerFactory.getLogger(MainThreadIp2.class);
    public static int ttt = 1;
    private volatile AtomicInteger index = new AtomicInteger(0);

    public static volatile InheritableThreadLocal<Boolean> endThreadLocal = new InheritableThreadLocal<Boolean>();

    private boolean ifRun = true;
    private String sellTime;
    private Map paramMapInit = new HashMap<String,String>();
    private Map paramMap = new HashMap<String,String>();
    private String cbgSafeCode;
    private String cookie;
    private String price;
    private String payCookie;
    private String payPassword;
    private String beginTime;
    private String endTime;
    private String intervalTime;
    private String sleepTime;
    private boolean isInit = false;
    private boolean isQuery = false;
    private boolean init = true;
    private int ipCount;
    private String ipUrl;

    @Override
    public void run() {
        endThreadLocal.set(false);
        Map<Integer,OrderThreadIp2> orderMap = new HashMap<Integer,OrderThreadIp2>();
        Map<Integer,PayThreadIp> payMap = new HashMap<Integer,PayThreadIp>();
        System.out.println(PayService.ipCount);
        logger.info(DateUtil.currentDateR() + "------线程初始化之前");
        for (int i = 0; i <= 15; i++) {
            OrderThreadIp2 o = new OrderThreadIp2();
            orderMap.put(i,o);
        }
        for (int i = 1; i <= 10; i++) {
            PayThreadIp o = new PayThreadIp();
            payMap.put(i,o);
        }
//        logger.info(DateUtil.currentDateR() + "------线程初始化之后 线程总数：" + 30*ipCount);

        boolean query = true;
        boolean bbb = true;
        try {
            while (ifRun){
                if(!MainThreadIp2.endThreadLocal.get() && bbb){
                    if(DateUtil.guajiTime(sellTime)){

                        logger.info("挂机初始化时间");
                        if (isInit){
                            PayService.initFinal(paramMapInit, cbgSafeCode, cookie, payCookie,payPassword);
                        }
                        try {
                            this.sleep(60000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }else {
                        if (DateUtil.isBuyTime(sellTime,"2000",endTime)) {

                            for (int i = 0; i < 15; i++) {
                                executAdd(orderMap.get(i),null,i,cbgSafeCode,cookie,paramMap,payCookie,payPassword,
                                        sellTime,"2000",endTime,sleepTime,isQuery,payMap);
                                Thread.sleep(250);
                            }
//                            index.incrementAndGet();
                            bbb = false;
                        } else {
                            if (init && DateUtil.isInitTime(sellTime)) {
                                init = false;
//                                list = PayService.initHttpClient(ipCount,ipUrl);
                                PayService.initFinalNoDelete(paramMapInit, cbgSafeCode, cookie, payCookie,payPassword);
                                PayService.queryAllOrder(cbgSafeCode,cookie,0);
                                if(query){
                                    query = false;
                                }
                            }
                        }
                    }
                }else{
                    ifRun = false;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info(e.getMessage());
        }

    }

    public static void executAdd(OrderThreadIp2 thread, TextArea t, Integer index, String cbgSafeCode, String cookie,
                                 Map paramMap, String payCookie, String payPassword,
                                 String sellTime,String beginTime,String endTime,
                                 String sleepTime,boolean isQuery,Map payMap){
        thread.setT(t);
        thread.setIndex(index);
        thread.setCbgSafeCode(cbgSafeCode);
        thread.setCookie(cookie);
        thread.setParamMap(paramMap);
        thread.setPayCookie(payCookie);
        thread.setPayPassword(payPassword);
        thread.setBuyTime(sellTime);
        thread.setBeginTime(beginTime);
        thread.setEndTime(endTime);
        thread.setSleepTime(sleepTime);
        thread.setQuery(isQuery);
//        thread.setHttpClient(client);
        thread.setPayMap(payMap);
        thread.start();

    }

    public String getIpUrl() {
        return ipUrl;
    }

    public void setIpUrl(String ipUrl) {
        this.ipUrl = ipUrl;
    }

    public int getIpCount() {
        return ipCount;
    }

    public void setIpCount(int ipCount) {
        this.ipCount = ipCount;
    }

    public static int getTtt() {
        return ttt;
    }

    public static void setTtt(int ttt) {
        MainThreadIp2.ttt = ttt;
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

    public boolean isInit() {
        return isInit;
    }

    public void setInit(boolean init) {
        isInit = init;
    }

    public String getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(String intervalTime) {
        this.intervalTime = intervalTime;
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

    public AtomicInteger getIndex() {
        return index;
    }

    public void setIndex(AtomicInteger index) {
        this.index = index;
    }

    public boolean isIfRun() {
        return ifRun;
    }

    public void setIfRun(boolean ifRun) {
        this.ifRun = ifRun;
    }

    public String getSellTime() {
        return sellTime;
    }

    public void setSellTime(String sellTime) {
        this.sellTime = sellTime;
    }

    public Map getParamMapInit() {
        return paramMapInit;
    }

    public void setParamMapInit(Map paramMapInit) {
        this.paramMapInit = paramMapInit;
    }

    public Map getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map paramMap) {
        this.paramMap = paramMap;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
