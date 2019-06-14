package com.cjc.paymh.model;

/**
 * Created by chenjc23273 on 2019/02/23
 *
 * @author chenjc23273
 */
public class MHRequest {

    private String orderSn;

    private String price;

    private String payPassword;

    private String sellTime;

    private String testOrderSn;

    private String testPrice;

    private String beginTime;

    private String endTime;

    private String sleepTime;

    private String intervalTime;

    private boolean isInit;

    private boolean isQuery;

    private int ipCount;

    private String ipUrl;

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

    public boolean isQuery() {
        return isQuery;
    }

    public void setQuery(boolean query) {
        isQuery = query;
    }

    public boolean isInit() {
        return isInit;
    }

    public void setInit(boolean init) {
        isInit = init;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getSellTime() {
        return sellTime;
    }

    public void setSellTime(String sellTime) {
        this.sellTime = sellTime;
    }

    public String getTestOrderSn() {
        return testOrderSn;
    }

    public void setTestOrderSn(String testOrderSn) {
        this.testOrderSn = testOrderSn;
    }

    public String getTestPrice() {
        return testPrice;
    }

    public void setTestPrice(String testPrice) {
        this.testPrice = testPrice;
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

    public String getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(String sleepTime) {
        this.sleepTime = sleepTime;
    }

    public String getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(String intervalTime) {
        this.intervalTime = intervalTime;
    }

    @Override
    public String toString() {
        return "MHRequest{" +
                "orderSn='" + orderSn + '\'' +
                ", price='" + price + '\'' +
                ", payPassword='" + payPassword + '\'' +
                ", sellTime='" + sellTime + '\'' +
                ", testOrderSn='" + testOrderSn + '\'' +
                ", testPrice='" + testPrice + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", sleepTime='" + sleepTime + '\'' +
                ", intervalTime='" + intervalTime + '\'' +
                '}';
    }
}
