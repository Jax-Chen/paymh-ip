package com.cjc.paymh.thread;

import com.cjc.paymh.service.PayService;
import com.cjc.paymh.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by chenjc23273 on 2019/02/24
 *
 * @author chenjc23273
 */
public class MainThread extends Thread{

    protected static final Logger logger = LoggerFactory.getLogger(MainThread.class);
    public static int ttt = 1;
    private AtomicInteger index = new AtomicInteger(1);

    public static ThreadLocal<Boolean> endThreadLocal = new ThreadLocal<Boolean>();

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

    @Override
    public void run() {
        Map<Integer,OrderThread> orderMap = new HashMap<Integer,OrderThread>();
        System.out.println(PayService.ipCount);
        logger.info(DateUtil.currentDateR() + "------线程初始化之前");
        for (int i = 1; i <= 31; i++) {
            OrderThread o = new OrderThread();
            orderMap.put(i,o);
        }
        logger.info(DateUtil.currentDateR() + "------线程初始化之后");

        boolean init = true;
        boolean query = true;
        try {
            while (ifRun){
                if(index.get()<31 && !OrderThread.end){
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
                        if (DateUtil.isBuyTime(sellTime,beginTime,endTime)) {

                            executAdd(orderMap.get(index.get()),null,index.get(),cbgSafeCode,cookie,paramMap,payCookie,payPassword,
                                    sellTime,beginTime,endTime,sleepTime,isQuery);
                            index.incrementAndGet();

                            try {
                                if (StringUtils.isNotBlank(intervalTime)){
                                    this.sleep(Integer.valueOf(intervalTime));
                                }else{
                                    this.sleep(50);
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (init && DateUtil.isInitTime(sellTime)) {
                                init = false;
                                PayService.initFinal(paramMapInit, cbgSafeCode, cookie, payCookie,payPassword);
                                if(query){
                                    query = false;
                                    PayService.queryAllOrder(cbgSafeCode,cookie,1);
                                }
                            }

//                            if(query){
//                                query = false;
//                                PayService.queryAllOrder(cbgSafeCode,cookie,1);
//                            }

                        }
                    }
                }else{
                    ifRun = false;
//                    OrderThread.end = false;
//                this.interrupt();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info(e.getMessage());
        }
//        finally {
//            ifRun = false;
//            OrderThread.end = false;
//        }


    }

    public static void executAdd(OrderThread thread, TextArea t, Integer index, String cbgSafeCode, String cookie,
                                 Map paramMap, String payCookie, String payPassword,
                                 String sellTime,String beginTime,String endTime,String sleepTime,boolean isQuery){
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
        thread.start();

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
