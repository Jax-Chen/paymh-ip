package com.cjc.paymh.controller;

import com.cjc.paymh.model.MHRequest;
import com.cjc.paymh.model.SSLClient;
import com.cjc.paymh.service.CollectSerevice;
import com.cjc.paymh.service.DeleteSerevice;
import com.cjc.paymh.thread.MainThread;
import com.cjc.paymh.thread.MainThreadIp;
import com.cjc.paymh.thread.MainThreadIp2;
import com.cjc.paymh.util.DateUtil;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenjc23273 on 2019/02/23
 *
 * @author chenjc23273
 */
@RestController
public class IndexController {

    protected static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value = "/order",method = RequestMethod.POST)
    public String order(HttpServletRequest request, HttpServletResponse response, MHRequest mh){

        String cbgSafeCode = request.getHeader("cbgSafeCode");
        String cookie = request.getHeader("cookie");
        String payCookie = request.getHeader("payCookie");
        String payPassword = request.getHeader("payPassword");
        mh.setPayPassword(payPassword);

        String orderSn = mh.getOrderSn();
//        String payPassword = mh.getPayPassword();
        String sellTime = mh.getSellTime();
        String price = mh.getPrice();
        String beginTime = mh.getBeginTime();
        String endTime = mh.getEndTime();
        boolean isInit = mh.isInit();
        String intervalTime = mh.getIntervalTime();
        String sleepTime = mh.getSleepTime();
        boolean isQuery = mh.isQuery();

        String tmp = orderSn.substring(orderSn.indexOf("-")+1);
        String serverId = tmp.substring(0,tmp.indexOf("-"));

        String testOrderSn = mh.getTestOrderSn();
        String testPrice = mh.getTestPrice();
        String testTmp = testOrderSn.substring(testOrderSn.indexOf("-")+1);
        String testServerId = testTmp.substring(0,testTmp.indexOf("-"));
        Map paramMapInit = new HashMap<String,String>();
        paramMapInit.put("serverid",testServerId);
        paramMapInit.put("ordersn",testOrderSn);
        paramMapInit.put("confirm_price_total",testPrice);
        paramMapInit.put("view_loc","all_list");

        Map paramMap = new HashMap<String,String>();
        paramMap.put("serverid",serverId);
        paramMap.put("ordersn",orderSn);
        paramMap.put("confirm_price_total",price);
        paramMap.put("view_loc","all_list");


        MainThread thread = new MainThread();
        thread.setSellTime(sellTime);
        thread.setParamMap(paramMap);
        thread.setParamMapInit(paramMapInit);
        thread.setCbgSafeCode(cbgSafeCode);
        thread.setCookie(cookie);
        thread.setPrice(price);
        thread.setPayCookie(payCookie);
        thread.setPayPassword(payPassword);
        thread.setBeginTime(beginTime);
        thread.setEndTime(endTime);
        thread.setIntervalTime(intervalTime);
        thread.setInit(isInit);
        thread.setSleepTime(sleepTime);
        thread.setQuery(isQuery);
        thread.start();

        return "调用成功" + mh.toString();
    }



    @RequestMapping(value = "/orderIp",method = RequestMethod.POST)
    public String orderIp(HttpServletRequest request, HttpServletResponse response, MHRequest mh){

        String cbgSafeCode = request.getHeader("cbgSafeCode");
        String cookie = request.getHeader("cookie");
        String payCookie = request.getHeader("payCookie");
        String payPassword = request.getHeader("payPassword");
        String ipUrl = request.getHeader("ipUrl");
        mh.setPayPassword(payPassword);

        String orderSn = mh.getOrderSn();
//        String payPassword = mh.getPayPassword();
        String sellTime = mh.getSellTime();
        String price = mh.getPrice();
        String beginTime = mh.getBeginTime();
        String endTime = mh.getEndTime();
        boolean isInit = mh.isInit();
        String intervalTime = mh.getIntervalTime();
        String sleepTime = mh.getSleepTime();
        boolean isQuery = mh.isQuery();
        int ipCount = mh.getIpCount();

        String tmp = orderSn.substring(orderSn.indexOf("-")+1);
        String serverId = tmp.substring(0,tmp.indexOf("-"));

        String testOrderSn = mh.getTestOrderSn();
        String testPrice = mh.getTestPrice();
        String testTmp = testOrderSn.substring(testOrderSn.indexOf("-")+1);
        String testServerId = testTmp.substring(0,testTmp.indexOf("-"));
        Map paramMapInit = new HashMap<String,String>();
        paramMapInit.put("serverid",testServerId);
        paramMapInit.put("ordersn",testOrderSn);
        paramMapInit.put("confirm_price_total",testPrice);
        paramMapInit.put("view_loc","all_list");

        Map paramMap = new HashMap<String,String>();
        paramMap.put("serverid",serverId);
        paramMap.put("ordersn",orderSn);
        paramMap.put("confirm_price_total",price);
        paramMap.put("view_loc","all_list");


        MainThreadIp thread = new MainThreadIp();
        thread.setSellTime(sellTime);
        thread.setParamMap(paramMap);
        thread.setParamMapInit(paramMapInit);
        thread.setCbgSafeCode(cbgSafeCode);
        thread.setCookie(cookie);
        thread.setPrice(price);
        thread.setPayCookie(payCookie);
        thread.setPayPassword(payPassword);
        thread.setBeginTime(beginTime);
        thread.setEndTime(endTime);
        thread.setIntervalTime(intervalTime);
        thread.setInit(isInit);
        thread.setSleepTime(sleepTime);
        thread.setQuery(isQuery);
        thread.setInit(true);
        thread.setIpCount(ipCount);
        thread.setIpUrl(ipUrl);
        thread.start();

        return "调用成功" + mh.toString();
    }


    @RequestMapping(value = "/orderIp2",method = RequestMethod.POST)
    public String orderIp2(HttpServletRequest request, HttpServletResponse response, MHRequest mh){

        String cbgSafeCode = request.getHeader("cbgSafeCode");
        String cookie = request.getHeader("cookie");
        String payCookie = request.getHeader("payCookie");
        String payPassword = request.getHeader("payPassword");
//        String ipUrl = request.getHeader("ipUrl");
        mh.setPayPassword(payPassword);

        String orderSn = mh.getOrderSn();
//        String payPassword = mh.getPayPassword();
        String sellTime = mh.getSellTime();
        String price = mh.getPrice();
        String beginTime = mh.getBeginTime();
        String endTime = mh.getEndTime();
        boolean isInit = mh.isInit();
        String intervalTime = mh.getIntervalTime();
        String sleepTime = mh.getSleepTime();
        boolean isQuery = mh.isQuery();
//        int ipCount = mh.getIpCount();

        String tmp = orderSn.substring(orderSn.indexOf("-")+1);
        String serverId = tmp.substring(0,tmp.indexOf("-"));

        String testOrderSn = mh.getTestOrderSn();
        String testPrice = mh.getTestPrice();
        String testTmp = testOrderSn.substring(testOrderSn.indexOf("-")+1);
        String testServerId = testTmp.substring(0,testTmp.indexOf("-"));
        Map paramMapInit = new HashMap<String,String>();
        paramMapInit.put("serverid",testServerId);
        paramMapInit.put("ordersn",testOrderSn);
        paramMapInit.put("confirm_price_total",testPrice);
        paramMapInit.put("view_loc","all_list");

        Map paramMap = new HashMap<String,String>();
        paramMap.put("serverid",serverId);
        paramMap.put("ordersn",orderSn);
        paramMap.put("confirm_price_total",price);
        paramMap.put("view_loc","all_list");


        MainThreadIp2 thread = new MainThreadIp2();
        thread.setSellTime(sellTime);
        thread.setParamMap(paramMap);
        thread.setParamMapInit(paramMapInit);
        thread.setCbgSafeCode(cbgSafeCode);
        thread.setCookie(cookie);
        thread.setPrice(price);
        thread.setPayCookie(payCookie);
        thread.setPayPassword(payPassword);
        thread.setBeginTime(beginTime);
        thread.setEndTime(endTime);
        thread.setIntervalTime(intervalTime);
        thread.setInit(isInit);
        thread.setSleepTime(sleepTime);
        thread.setQuery(isQuery);
        thread.setInit(true);
//        thread.setIpCount(ipCount);
//        thread.setIpUrl(ipUrl);
        thread.start();

        return "调用成功" + mh.toString();
    }

    @RequestMapping("/index")
    @ResponseBody
    public String index(HttpServletRequest request, HttpServletResponse response, MHRequest mh){
        logger.info("打印index");
        String h = request.getHeader("payPassword");
        System.out.println(h);
        return mh.toString();
    }

    @RequestMapping("/datetime")
    public String datetime(HttpServletRequest request, HttpServletResponse response, MHRequest mh){
        return DateUtil.currentDateR();
    }

    @RequestMapping("/addCollect")
    public String addCollect(HttpServletRequest request, HttpServletResponse response, MHRequest mh){
        String cbgSafeCode = request.getHeader("cbgSafeCode");
        String cookie = request.getHeader("cookie");
        String time = request.getParameter("time");
        try {
            HttpClient client = new SSLClient();
            CollectSerevice.list(cbgSafeCode,cookie,client,time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "增加收藏成功";
    }


    @RequestMapping("/deleteOrdere")
    public String deleteOrdere(HttpServletRequest request, HttpServletResponse response, MHRequest mh){
        String cbgSafeCode = request.getHeader("cbgSafeCode");
        String cookie = request.getHeader("cookie");
        String time = request.getParameter("time");
        try {
            HttpClient client = new SSLClient();
            DeleteSerevice.delete(cbgSafeCode,cookie,client,time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "增加收藏成功";
    }



}
