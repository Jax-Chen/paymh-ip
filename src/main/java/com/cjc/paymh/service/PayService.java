package com.cjc.paymh.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjc.paymh.constant.URL;
import com.cjc.paymh.model.SSLClient;
import com.cjc.paymh.util.DateUtil;
import com.cjc.paymh.util.HttpsClientUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenjc23273 on 2019/01/29
 *
 * @author chenjc23273
 */
public class PayService {

    protected static final Logger logger = LoggerFactory.getLogger(PayService.class);

    public static Map<Integer,HttpClient> map1 = new HashMap<Integer,HttpClient>();
    public static Map<Integer,HttpClient> map2 = new HashMap<Integer,HttpClient>();
    public static Map<Integer,HttpClient> map3 = new HashMap<Integer,HttpClient>();
    public static Map<Integer,HttpClient> map4 = new HashMap<Integer,HttpClient>();
    public static Map<Integer,HttpClient> map5 = new HashMap<Integer,HttpClient>();
    public static Map<Integer,HttpClient> goPayMap = new HashMap<Integer,HttpClient>();

    public static HttpClient httpClient1= null;
    public static HttpClient httpClient2= null;
    public static HttpClient httpClient3= null;
    public static HttpClient httpClient4= null;
    public static HttpClient httpClient5= null;

    static String orderSn = "201809160901716-103-FLQLRTKGQMTC";
    final static String cbgSafeCode = "ECZ7N54S";
    static String tmp = orderSn.substring(orderSn.indexOf("-")+1);
    static String serverId = tmp.substring(0,tmp.indexOf("-"));
    static String price = "99999999";
    final static String cookie = "usertrack=ezq0pVri5z4pfeFjCLI+Ag==; _ntes_nnid=aeddc0f0d48428394daf0f32351e34c7,1524819775622; _ntes_nuid=aeddc0f0d48428394daf0f32351e34c7; _ga=GA1.2.252405637.1524819776; vjuids=4eea9f1.16348c6d1d6.0.706e760b26a82; fingerprint=r7azf7pnrt04h6k5; __gads=ID=f47e133227159fe9:T=1529990656:S=ALNI_MaeA6oBLicJqUk6XaZj4vX_9qSniA; __f_=1543801309184; vinfo_n_f_l_n3=61dae1c8a939025a.1.1.1529990679307.1529990869635.1546845444202; vjlast=1525934379.1547101224.12; _9755xjdesxxd_=32; YD00000722197596%3AWM_TID=5G9dJeWieLNEUVEARUYslZPit2%2FN5UEW; hb_MA-B9D6-269DF3E58055_source=epay.163.com; hb_MA-B9D6-269DF3E58055_u=%7B%22utm_source%22%3A%20%22epay%22%2C%22utm_medium%22%3A%20%22icon%22%2C%22utm_campaign%22%3A%20%22game_my_icon%22%2C%22utm_content%22%3A%20%22%22%2C%22utm_term%22%3A%20%22%22%2C%22promotional_id%22%3A%20%221468%22%7D; YD00000722197596%3AWM_NI=6QvwbX%2B52okDvd5xTrcuUxVJzbYZQpsNLU%2FkcwFWUb1%2BYrt9TfMVh7cOjUqjKGlLMacU4GWG5GcHJ0bhxgp3nfWwxXuqaw%2FU8V4crjIggI4xNXaj72WxBmkGPoasG6gBYUY%3D; YD00000722197596%3AWM_NIKE=9ca17ae2e6ffcda170e2e6eeb3f44094ebb79bb172a29a8bb2d44e979b8baebb74a7aabbd3d03d919dfdacc12af0fea7c3b92af68fba98e154e9ebfe90ea50b6bcfb99d77aa1ab9b99d542babe97bbd47cb19284b6ae61a1a98ab8d86af3f59793f73398ba83b7eb3da191a6b6f4628999a4add06ba18bfca8d64df799ada9c93ca2b8b888f339aeba9998fb54b7afaf98ae5ffc8afc84f1628bb9a9aab7748e88af8bb63a95a88e8eea4d94b1af94c63ea59e97b6dc37e2a3; gdxidpyhxdE=0JVmRw5h0vk4L0fALfX0t9aZ%2B0Pme%2BVZmhaqfp%5CoSZmcIg8usX%2BdobAkudu4NPK3nti7aUgjve2M%2FMDlz5LLw%2F9xx2fBLZ2kXjMOEMN859Z%2F8SgGp0OApJPf%5CrOeAyyQOkq5L0J%2Fu1ak3PfvEPwxG4ovUMXtHeQXk9hwvLjiyqL76iUk%3A1550499111149; back_url=https%3A%2F%2Fmy.cbg.163.com%2Fcgi%2Fmweb%2Fuser; ANTICSRF=bb598a36a8d287a2960f6e90e2a70378; NTES_YD_PASSPORT=.PuXqtqdFufuAH2gNTLAlkxkrbFawqqLIaFzBk3uYGV9Eox_E6mDytw2rLatwCKS.Hiw7RBYY8mXriNv4IIrA1238nWLTFI5MLV74lUXX.Bd8SandjNT9wWe.idvZ2ssMjNYHBlH5O7byW3wbMKHfXuH9delvHvdfk8CtIYSOvCUTjHlrE0pwaludLgAQ.l5.OVMMbii25g4t; P_INFO=17816116670|1550724694|1|cbg|00&99|zhj&1550713409&cbg#zhj&330100#10#0#0|&0|null|17816116670; is_log_active_stat=1; SID=5b2c4e8a-38fc-4a85-bf2b-40cc9b6e6d5c; NTES_YD_SESS=.hn5wJ_Luk.G_fGuP7X2IZoqPeWyef3MnjdMKaDFk00vkrW0kCD17o3dMSho3GwXqAv3znPEEIDA_vaZThECyil_8mkDlDFXdVL29fnnNZ_sgc.RwILA3oafTPjD5C0QQFD0uHyBn7uiCskaXZ1FxGu6txCXE4wSbLky6Hj8EGyVRRGmbKXELdvjeygCtuNdDUZJlVOKlrmZj2N3q6lJuM6bt; S_INFO=1550727392|1|0&60##|17816116670; sid=g2aLi-ClhLDzaTA7hb47QutrugZYgiO0U28MLz5W";

    static String payCookie = "EPAY_ENV=YQ_ROOM; usertrack=ezq0pVri5z4pfeFjCLI+Ag==; _ntes_nnid=aeddc0f0d48428394daf0f32351e34c7,1524819775622; _ntes_nuid=aeddc0f0d48428394daf0f32351e34c7; _ga=GA1.2.252405637.1524819776; vjuids=4eea9f1.16348c6d1d6.0.706e760b26a82; __gads=ID=f47e133227159fe9:T=1529990656:S=ALNI_MaeA6oBLicJqUk6XaZj4vX_9qSniA; __f_=1543801309184; vinfo_n_f_l_n3=61dae1c8a939025a.1.1.1529990679307.1529990869635.1546845444202; vjlast=1525934379.1547101224.12; _OkLJ_%UJ=ITWH7FIY7J6WNE5E; WM_TID=qnhz3c6ldndFEVBEBQIskS9f2FXaHEWB; riskInfoUuid=41bfa137-29cb-451b-acf1-901c9ec01a60; _9755xjdesxxd_=32; YD00000595128763%3AWM_TID=ntaqqTRpf8hBAVRBAVd9kbx18Al%2BVVUO; gdxidpyhxdE=mjagjMgZ5iSTm6jsE20%2FLs1iM0dIG7kp9DuhXxevfhnfms28Lcxjh6H0qSl7PNhx%5Cuqzq%2Bi3k9d70hc459ehCobOBsO9J56A%2F2Bs3IRvy2NyZZtvos5O5YpekydBj433MwlgaHZUH5ftc6s%5CLb9eCKU7vhtI6PEerGHQjx5ktsq843D1%3A1550234199336; YD00000595128763%3AWM_NI=kHwMaUjkadj%2BYKSg4UMlh%2FbeTzOYY%2B8WnQjOfltoeQ2KPMhGgAYt3Tisb6y1p5MBJvzIeTLcQVYMOfn1HGyi1s5UbDjNCrjdg6lNKUOJLmKPbsKz3hHtYCAUtx%2Fl0CUdMTU%3D; YD00000595128763%3AWM_NIKE=9ca17ae2e6ffcda170e2e6eed5e93a91eaa3bbf270ad968bb6c44e869e9bbbbb6194e78db2f739fca6999ab62af0fea7c3b92aa998b9aee45faab5af85e9219b959d88b75f8fb4a8d0e16f98a681badc21fc89a2b2e945a6b4bc90e85bf295a0aeeb4da59f9795b260abadaed7fc3bb4eb83a3c97eba97abb5cf69938a97aece6eaeef88a8fc63f3adfbb6e150aa928d93e970a5eaa4afca5f8d96848eca5ca2ad859bb2648cac97a4c45fb8a8e18db34997bc83b5d837e2a3; hb_MA-B9D6-269DF3E58055_source=epay.163.com; hb_MA-B9D6-269DF3E58055_u=%7B%22utm_source%22%3A%20%22epay%22%2C%22utm_medium%22%3A%20%22icon%22%2C%22utm_campaign%22%3A%20%22game_my_icon%22%2C%22utm_content%22%3A%20%22%22%2C%22utm_term%22%3A%20%22%22%2C%22promotional_id%22%3A%20%221468%22%7D; WM_NI=k1AdfCZH%2BZlgX0BJI1m%2F8%2FDAp5uKPQYRTqZcgmH8dE%2FqcHoFLd3BFYwqhfMFiM4IdVFSxPN9cPIPfNsQxV8k%2Fr8RmKQhnvMEMOkxc1QCHT2F%2BJsTQN5ntKwFwTVjYIIiZ1Q%3D; WM_NIKE=9ca17ae2e6ffcda170e2e6eea2d56b90ee98d0ee6a8ebc8fb2c84b928a9fbbb83ba988abb2b63cace9a687e62af0fea7c3b92aa9ef8f85e5499a98e5bbef6d9cf08eabcc3baff1f8d0c74bb4ebbbacd35b9b8e9c92d969a5b388d9e74fedafada9ee4e90bcfda3c553b5afa887d04eab93fcaae75e868db8b1cd33a19087b1e546b18fb8b7bb61edaef8b3f04193b8a297d04f8f91a599e23b939d88b5b14ee996bdb5d446f7b2bcacf64eb58c9a8bdc438bb796d2d437e2a3; EPAYSESSIONID=EPAY-c844c397-d319-46b7-8800-8252043b6055; riskInfoBsLanguage=zh-CN; riskInfoTimeZone=-480; riskInfoScrResolution=1366%2C768; riskInfoHardwareConcurrency=4; riskInfoCanvasFp=b0c8f230; riskInfoPlugins=internal-pdf-viewer%2Cmhjfbmdgcfjbbpaeojofohoefgiehjai%2Cinternal-nacl-plugin%2Cwidevinecdmadapter.dll; riskInfoFonts=1%2C2%2C5%2C7%2C9%2C10%2C11%2C12%2C13%2C14%2C15%2C16%2C18%2C19%2C20%2C21%2C23%2C24%2C26%2C27%2C28%2C29%2C30%2C32%2C33%2C34%2C35%2C36%2C38%2C39%2C41%2C42%2C43%2C44%2C48%2C49%2C50%2C51%2C52%2C53%2C54%2C55%2C56%2C57%2C59%2C60%2C61%2C62%2C63; JSESSIONID=9E5ECE724C23F5DB45E5430E44E0795F; ANTICSRF=bb598a36a8d287a2960f6e90e2a70378; NTES_YD_PASSPORT=.PuXqtqdFufuAH2gNTLAlkxkrbFawqqLIaFzBk3uYGV9Eox_E6mDytw2rLatwCKS.Hiw7RBYY8mXriNv4IIrA1238nWLTFI5MLV74lUXX.Bd8SandjNT9wWe.idvZ2ssMjNYHBlH5O7byW3wbMKHfXuH9delvHvdfk8CtIYSOvCUTjHlrE0pwaludLgAQ.l5.OVMMbii25g4t; P_INFO=17816116670|1550724694|1|cbg|00&99|zhj&1550713409&cbg#zhj&330100#10#0#0|&0|null|17816116670; NTES_YD_SESS=.hn5wJ_Luk.G_fGuP7X2IZoqPeWyef3MnjdMKaDFk00vkrW0kCD17o3dMSho3GwXqAv3znPEEIDA_vaZThECyil_8mkDlDFXdVL29fnnNZ_sgc.RwILA3oafTPjD5C0QQFD0uHyBn7uiCskaXZ1FxGu6txCXE4wSbLky6Hj8EGyVRRGmbKXELdvjeygCtuNdDUZJlVOKlrmZj2N3q6lJuM6bt; S_INFO=1550727392|1|0&60##|17816116670; FRMS_FINGERPRINT=1281d3d9446802a442591371dfa004ad8ed4987212511ff1de774005f8da12216a5889bb0190d0211231d4c2e4a3297fe25a009139fa36c62c3b2354141100000000000000001291502f1bd97b0e7d80130187374a5ff147c45613910000000000000000126100000000000000001311652e7a11d3f7ccf612416a5889bb0190d021142100000000000000001341e4da3b7fbbce23451401b326b5062b2f0e691381b326b5062b2f0e69136100000000000000000101b3a5d5fc2cac9d4e1271d3d9446802a442591351a87ff679a2f3e71d132168934a3e9455fa721201bf934a1ecc1b359712113a835d3215755c432b01537ff3902960649f1331e4da3b7fbbce2345; _gat=1";
    static String payPassword = "E4BAAbclkgViYGkqCPb%2BvO%2BfVVDb%2FbCtCKvQ3XFv0R0BATse6iwX3J4QjBNZ08qT";
    final static Map paramMap = new HashMap<String,String>();
    static Map paramMap1 = new HashMap<String,String>();
    static {

        try {
            logger.info(DateUtil.currentDateR() + "------Http初始化之前");
            for (int i = 0; i <= 30; i++) {
                HttpClient httpClient1 = new SSLClient();
                HttpClient httpClient2 = new SSLClient();
                map1.put(i,httpClient1);
                map2.put(i,httpClient2);
            }
            for (int j = 0; j < 50; j++) {
                HttpClient httpClient3 = new SSLClient();
                HttpClient httpClient4 = new SSLClient();
                HttpClient httpClient5 = new SSLClient();
                HttpClient httpClient6 = new SSLClient();
                map3.put(j,httpClient3);
                map4.put(j,httpClient4);
                map5.put(j,httpClient5);
                goPayMap.put(j,httpClient6);
            }
            logger.info(DateUtil.currentDateR() + "------Http初始化之后");
            httpClient1 = new SSLClient();
            httpClient2 = new SSLClient();
            httpClient3 = new SSLClient();
            httpClient4 = new SSLClient();
            httpClient5 = new SSLClient();
        } catch (Exception e) {
            e.printStackTrace();
        }

        paramMap.put("serverid",serverId);
        paramMap.put("ordersn",orderSn);
        paramMap.put("confirm_price_total",price);
        paramMap.put("view_loc","all_list");

        paramMap1.put("serverid",serverId);
        paramMap1.put("ordersn","111-11-11");
        paramMap1.put("confirm_price_total",price);
        paramMap1.put("view_loc","all_list");
    }

    public static final String ADD_ORDER_URL = "https://my.cbg.163.com/cgi/api/add_order";
    public static final String GET_ORDER_INFO_URL = "https://my.cbg.163.com/cgi/api/get_order_pay_info";
    public static final String PAY_URL = "https://epay.163.com/cashier/ajaxPay";
    public static final String ORDER_URL = "https://my.cbg.163.com/cgi/api/my_orders?page=1&status=1";
    public static final String ORDER_ALL_URL = "https://my.cbg.163.com/cgi/api/my_orders?page=1";
    public static final String CANCEL_ORDER_URL = "https://my.cbg.163.com/cgi/api/cancel_order";
    public static final String DELETE_ORDER_URL = "https://my.cbg.163.com/cgi/api/delete_order";

    public static final String WAP_PAY_URL = "https://epay.163.com/cashier/m/security/verifyPayItems";
    public static final String IP_URL = "http://webapi.http.zhimacangku.com/getip?num=1&type=2&pro=330000&city=330100&yys=0&port=11&pack=41902&ts=0&ys=0&cs=0&lb=1&sb=0&pb=4&mr=1&regions=";

//    public static String IP_URL_NO_IP_COUNT = "http://webapi.http.zhimacangku.com/getip?num=" + IP_COUNT + "&type=2&pro=330000&city=330100&yys=0&port=11&pack=41902&ts=0&ys=0&cs=0&lb=1&sb=0&pb=4&mr=1&regions=";
    public static String IP_URL_PREV = "http://webapi.http.zhimacangku.com/getip?num=";
    public static String IP_URL_AFTER = "&type=2&pro=330000&city=330100&yys=0&port=11&pack=41902&ts=0&ys=0&cs=0&lb=1&sb=0&pb=4&mr=1&regions=";

    public static final String IS_HIDE_URL = "http://118.25.104.171/hello";
    public static final String HTTP_BIN_URL = "http://httpbin.org/get";
    static volatile Integer i = 1;

    public static String[] ips = {"10.26.116.150","115.236.91.15"};

    static Thread thread1 = new Thread(new Runnable() {
        public void run() {
            Integer index = i.intValue();

//            logger.info("线程一开始");
            DateUtil.currentDate();
            String orderid_to_epay = PayService.addOrder(paramMap,cbgSafeCode,cookie,null,index);

            try {
                thread1.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            PayService.cancelOrder(index,orderid_to_epay);
//            PayService.deleteOrder(index,orderid_to_epay);


        }
    });

    static Thread thread2 = new Thread(new Runnable() {
        public void run() {

            Integer index = i.intValue();
            try {
                thread2.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            logger.info("线程二开始");
            DateUtil.currentDate();
            String result = PayService.queryOrder(cbgSafeCode,cookie,index);
            if(StringUtils.isNotBlank(result)){
                logger.info("解析status之前 " + DateUtil.currentDateR());
                String status = JSON.parseObject(result).getString("status");
                logger.info("解析status_desc之前 " + DateUtil.currentDateR());
                String status_desc = JSON.parseObject(result).getString("status_desc");
                logger.info("订单状态：" + status);
                logger.info(status_desc);
//                String orderid_from_epay = JSON.parseObject(result).getString("orderid_from_epay");
//                logger.info("支付单号：" + orderid_from_epay);
                String orderid_to_epay = JSON.parseObject(result).getString("orderid_to_epay");
                String epay_pay_url = PayService.getOrderInfo(orderid_to_epay,cbgSafeCode,cookie,null,index);
                logger.info("获取支付地址完成");
                String payNo = PayService.getPayNo(epay_pay_url,payCookie,null,index);
                logger.info("访问支付地址完成 " + payNo);
                String str = PayService.goPay(payNo,price,payPassword,payCookie,null,index);
//                if(StringUtils.isNotBlank(orderid_from_epay)){
//                    int begin = orderid_from_epay.indexOf("JY");
//                    String prev = orderid_from_epay.substring(0,begin+2);
//                    String res = orderid_from_epay.substring(begin+2, orderid_from_epay.length());
//
//                    for (int j = 1; j <= 100; j++) {
//                        GoPayThread thread4 = new GoPayThread();
//                        thread4.setPayNo(prev + (Integer.parseInt(res) + j));
//                        thread4.setPrice(price);
//                        thread4.setPayPassword(payPassword);
//                        thread4.setPayCookie(payCookie);
//                        thread4.setT(null);
//                        thread4.setIndex(j);
//                        thread4.start();
//                    }
//                }
            }


        }
    });

    static Thread thread3 = new Thread(new Runnable() {
        public void run() {

            Integer index = i.intValue()+1;
            try {
                thread2.sleep(120);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            logger.info("线程三开始");
            DateUtil.currentDate();
            String result = PayService.queryOrder(cbgSafeCode,cookie,index);
            if(StringUtils.isNotBlank(result)){
                logger.info("解析status之前 " + DateUtil.currentDateR());
                String status = JSON.parseObject(result).getString("status");
                logger.info("解析status_desc之前 " + DateUtil.currentDateR());
                String status_desc = JSON.parseObject(result).getString("status_desc");
                logger.info("订单状态：" + status);
                logger.info(status_desc);
//                String orderid_from_epay = JSON.parseObject(result).getString("orderid_from_epay");
//                logger.info("支付单号：" + orderid_from_epay);
                String orderid_to_epay = JSON.parseObject(result).getString("orderid_to_epay");
                String epay_pay_url = PayService.getOrderInfo(orderid_to_epay,cbgSafeCode,cookie,null,index);
                logger.info("获取支付地址完成");
                String payNo = PayService.getPayNo(epay_pay_url,payCookie,null,index);
                logger.info("访问支付地址完成 " + payNo);
                String str = PayService.goPay(payNo,price,payPassword,payCookie,null,index);
            }


        }
    });

    private static volatile int b = 1;

    static Thread testThread = new Thread(new Runnable() {
        public void run() {
            Map map = new HashMap<String,String>();
            HttpClient httpClient11 = null;
            try {
                httpClient11 = map1.get(b);
                HttpHost proxy = new HttpHost("118.89.138.129", 59460);
                httpClient11.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
            } catch (Exception e) {
                e.printStackTrace();
            }
//        for (int j = 0; j < 100; j++) {
//            String result = PayService.queryOrder(cbgSafeCode,cookie,1);
            String jsonContent = HttpsClientUtil.sendPostSSLRequest(ORDER_URL,map,cbgSafeCode,cookie,httpClient11);
            logger.info(jsonContent);

        }
    });



    /**
     * 添加订单
     * @param paramMap
     * @param cbgSafeCode
     * @param cookie
     * @return
     */
    public static String addOrder(Map paramMap, String cbgSafeCode, String cookie, TextArea t,Integer index){

//        logger.info("addOrder" + DateUtil.currentDateR());
        String jsonContent = HttpsClientUtil.sendPostSSLRequest(ADD_ORDER_URL,paramMap,cbgSafeCode,cookie, map1.get(index));
//        logger.info(index);
//        String jsonContent = "{\"status\": 1, \"order\": {\"price_total\": 6100, \"orderid_to_epay\": \"159_3909\"}}";
        logger.info(DateUtil.currentDateR() + "  " + jsonContent);
//        if(null!=t){
//            t.append(jsonContent);
//            t.append("\n");
//        }
//        logger.info("11");
        if(StringUtils.isNotBlank(jsonContent)){
            Integer status = JSON.parseObject(jsonContent).getInteger("status");

            if(1==status){
                Integer price_total = JSON.parseObject(jsonContent).getJSONObject("order").getInteger("price_total");
                String orderid_to_epay = JSON.parseObject(jsonContent).getJSONObject("order").getString("orderid_to_epay");
                String log = DateUtil.currentDateR() + " 下单成功,支付金额为：" + new BigDecimal(price_total).divide(new BigDecimal(100)) + "元，订单号为：" + orderid_to_epay;
                if(null!=t){
//                    t.append(log);
//                    t.append("\n");
                }
                logger.info(log);
                return orderid_to_epay;
            }else{
                String msg = JSON.parseObject(jsonContent).getString("msg");
                String log = "下单失败，失败原因：" + msg;
                if(null!=t){
//                    t.append(log);
//                    t.append("\n");
                }
                logger.info(log);
                return "";
            }
        }else{
            return "";
        }


    }

    /**
     * 添加订单
     * @param paramMap
     * @param cbgSafeCode
     * @param cookie
     * @return
     */
    public static String addOrderIp(Map paramMap, String cbgSafeCode, String cookie,HttpClient client){

//        logger.info("addOrder" + DateUtil.currentDateR());
        String jsonContent = HttpsClientUtil.sendPostSSLRequest(ADD_ORDER_URL,paramMap,cbgSafeCode,cookie, client);
        logger.info(DateUtil.currentDateR() + "  " + jsonContent);
        if(StringUtils.isNotBlank(jsonContent)){
            Integer status = JSON.parseObject(jsonContent).getInteger("status");
            if(1==status){
                Integer price_total = JSON.parseObject(jsonContent).getJSONObject("order").getInteger("price_total");
                String orderid_to_epay = JSON.parseObject(jsonContent).getJSONObject("order").getString("orderid_to_epay");
                String log = DateUtil.currentDateR() + " 下单成功,支付金额为：" + new BigDecimal(price_total).divide(new BigDecimal(100)) + "元，订单号为：" + orderid_to_epay;
                logger.info(log);
                return orderid_to_epay;
            }else{
                String msg = JSON.parseObject(jsonContent).getString("msg");
                String log = "下单失败，失败原因：" + msg;
                logger.info(log);
                if(StringUtils.contains(msg,"您有一个角色商品尚未付款，不能再购买了")){
                    return "-1";
                }
                return "";
            }
        }else{
            return "";
        }
    }

    public static String addOrderIp2(Map paramMap, String cbgSafeCode, String cookie,Integer index){

//        logger.info("addOrder" + DateUtil.currentDateR());
        String jsonContent = HttpsClientUtil.sendPostSSLRequest(ADD_ORDER_URL,paramMap,cbgSafeCode,cookie, map1.get(index));
        logger.info(DateUtil.currentDateR() + "  " + jsonContent);
        if(StringUtils.isNotBlank(jsonContent)){
            Integer status = JSON.parseObject(jsonContent).getInteger("status");
            if(1==status){
                Integer price_total = JSON.parseObject(jsonContent).getJSONObject("order").getInteger("price_total");
                String orderid_to_epay = JSON.parseObject(jsonContent).getJSONObject("order").getString("orderid_to_epay");
                String log = DateUtil.currentDateR() + " 下单成功,支付金额为：" + new BigDecimal(price_total).divide(new BigDecimal(100)) + "元，订单号为：" + orderid_to_epay;
                logger.info(log);
                return orderid_to_epay;
            }else{
                String msg = JSON.parseObject(jsonContent).getString("msg");
                String log = "下单失败，失败原因：" + msg;
                logger.info(log);
                if(StringUtils.contains(msg,"您有一个角色商品尚未付款，不能再购买了")){
                    return "-1";
                }
                return "";
            }
        }else{
            return "";
        }


    }

    /**
     * 查询订单，获取epay支付单号
     * @param cbgSafeCode
     * @param cookie
     * @return
     */
    public static String queryOrder(String cbgSafeCode,String cookie,Integer index){
        logger.info("queryOrder" + DateUtil.currentDateR());
        Map map = new HashMap<String,String>();
//        map.put("orderid_to_epay",orderid_to_epay);
        String jsonContent = HttpsClientUtil.sendPostSSLRequest(ORDER_URL,map,cbgSafeCode,cookie,map2.get(index));
        logger.info("所有解析之前 " + DateUtil.currentDateR() + jsonContent);
        JSONArray jo =  JSON.parseObject(jsonContent).getJSONArray("result");
        logger.info("解析数组之后 " + DateUtil.currentDateR() + " ");
        if(null != jo){
            if(jo.size()>=1){
                String result = jo.get(0).toString();
                logger.info("result之后 " + DateUtil.currentDateR() + " ");
                return result;
            }else{
                return "";
            }
        } else{
            String msg =  JSON.parseObject(jsonContent).getString("msg");
            logger.info("查询订单出错 " + DateUtil.currentDateR() + " " + msg);
            return "";

        }
//        String orderid_from_epay = JSON.parseObject(result).getString("orderid_from_epay");
//        logger.info(orderid_from_epay);
//        return result;
    }


    /**
     * 查询订单，获取epay支付单号
     * @param cbgSafeCode
     * @param cookie
     * @return
     */
    public static String queryOrderIp(String cbgSafeCode,String cookie,HttpClient client){
        logger.info("queryOrder" + DateUtil.currentDateR());
        Map map = new HashMap<String,String>();
//        map.put("orderid_to_epay",orderid_to_epay);
        String jsonContent = HttpsClientUtil.sendPostSSLRequest(ORDER_URL,map,cbgSafeCode,cookie,client);
        logger.info("所有解析之前 " + DateUtil.currentDateR() + jsonContent);
        JSONArray jo =  JSON.parseObject(jsonContent).getJSONArray("result");
        logger.info("解析数组之后 " + DateUtil.currentDateR() + " ");
        if(null != jo){
            if(jo.size()>=1){
                String result = jo.get(0).toString();
                String orderid_to_epay = "";
                if (StringUtils.isNotBlank(result)){
                    orderid_to_epay = JSON.parseObject(result).getString("orderid_to_epay");
                }
                logger.info("result之后 " + DateUtil.currentDateR() + " ");
                return orderid_to_epay;
            }else{
                return "";
            }
        } else{
            String msg =  JSON.parseObject(jsonContent).getString("msg");
            logger.info("查询订单出错 " + DateUtil.currentDateR() + " " + msg);
            return "";

        }
//        String orderid_from_epay = JSON.parseObject(result).getString("orderid_from_epay");
//        logger.info(orderid_from_epay);
//        return result;
    }


    /**
     * 查询订单，获取epay支付单号
     * @param cbgSafeCode
     * @param cookie
     * @return
     */
    public static String queryAllOrder(String cbgSafeCode,String cookie,Integer index){
        Map map = new HashMap<String,String>();
        String jsonContent = HttpsClientUtil.sendPostSSLRequest(ORDER_ALL_URL,map,cbgSafeCode,cookie,map2.get(1));
//        logger.info("所有解析之前 " + DateUtil.currentDateR() + jsonContent);
        JSONArray jo =  JSON.parseObject(jsonContent).getJSONArray("result");
        logger.info("解析数组之后 " + DateUtil.currentDateR() + " ");
        if(null != jo){
            if(jo.size()>=1){
                String result = jo.get(0).toString();
                logger.info("result之后 " + DateUtil.currentDateR() + " ");
                URL.ORDERID_TO_EPAY = JSON.parseObject(result).getString("orderid_to_epay");
                logger.info("============现在的订单号为:" + URL.ORDERID_TO_EPAY);
                return result;
            }else{
                return "";
            }
        } else{
            String msg =  JSON.parseObject(jsonContent).getString("msg");
            logger.info("查询订单出错 " + DateUtil.currentDateR() + " " + msg);
            return "";

        }
    }

    public static void cancelOrder(Integer index,String orderid_to_epay,String cbgSafeCode,String cookie){

        Map map = new HashMap<String,String>();
        map.put("orderid_to_epay",orderid_to_epay);
        String jsonContent = HttpsClientUtil.sendPostSSLRequest(CANCEL_ORDER_URL,map,cbgSafeCode,cookie,map2.get(5));

    }

    public static void deleteOrder(Integer index,String orderid_to_epay,String cbgSafeCode,String cookie){

        Map map = new HashMap<String,String>();
        map.put("orderid_to_epay",orderid_to_epay);
        String jsonContent = HttpsClientUtil.sendPostSSLRequest(DELETE_ORDER_URL,map,cbgSafeCode,cookie,map3.get(5));

    }


    /**
     * 查询订单，并获取支付地址
     * @param orderid_to_epay
     * @param cbgSafeCode
     * @param cookie
     * @return
     */
    /**
     * 查询订单，并获取支付地址
     * @param orderid_to_epay
     * @param cbgSafeCode
     * @param cookie
     * @return
     */
    public static String getOrderInfo(String orderid_to_epay,String cbgSafeCode, String cookie,TextArea t,Integer index){

        Map map = new HashMap<String,String>();
        map.put("orderid_to_epay",orderid_to_epay);
        String jsonContent = HttpsClientUtil.sendPostSSLRequest(GET_ORDER_INFO_URL,map,cbgSafeCode,cookie,map3.get(index));
        logger.info(orderid_to_epay + " getorderinfo 返回值 " + jsonContent);
        if(StringUtils.isNotBlank(jsonContent)){
            if(!jsonContent.contains("{")){
                logger.info("=========================没有查询到该订单");
                return "";
            }

            String msg1 = JSON.parseObject(jsonContent).getString("msg");
            logger.info(orderid_to_epay + "---------jieximsg之后 " + msg1);
            Integer status = JSON.parseObject(jsonContent).getInteger("status");
            if(1==status){
                logger.info(orderid_to_epay + "---------解析epay_pay_url之前");
                String epay_pay_url = JSON.parseObject(jsonContent).getJSONObject("pay_info").getString("epay_pay_url");
                return epay_pay_url;
            }else{
                String msg = JSON.parseObject(jsonContent).getString("msg");
                String log = orderid_to_epay + "查询失败，失败原因：" + msg;
                logger.info(log);
                return "";

            }
        }else{
            return "";

        }

    }


    /**
     * 查询订单，并获取支付地址
     * @param orderid_to_epay
     * @param cbgSafeCode
     * @param cookie
     * @return
     */
    /**
     * 查询订单，并获取支付地址
     * @param orderid_to_epay
     * @param cbgSafeCode
     * @param cookie
     * @return
     */
    public static String getOrderInfoIp(String orderid_to_epay,String cbgSafeCode, String cookie,TextArea t,Integer index){

        Map map = new HashMap<String,String>();
        map.put("orderid_to_epay",orderid_to_epay);
        String jsonContent = HttpsClientUtil.sendPostSSLRequestNoConsume(GET_ORDER_INFO_URL,map,cbgSafeCode,cookie,map3.get(index));
        logger.info(orderid_to_epay + " getorderinfo 返回值 " + jsonContent);
        if(StringUtils.isNotBlank(jsonContent)){
            if(!jsonContent.contains("{")){
                logger.info("=========================没有查询到该订单");
                return "";
            }

            String msg1 = JSON.parseObject(jsonContent).getString("msg");
            logger.info(orderid_to_epay + "---------jieximsg之后 " + msg1);
            Integer status = JSON.parseObject(jsonContent).getInteger("status");
            if(1==status){
                logger.info(orderid_to_epay + "---------解析epay_pay_url之前");
                String epay_pay_url = JSON.parseObject(jsonContent).getJSONObject("pay_info").getString("epay_pay_url");
                return epay_pay_url;
            }else{
                String msg = JSON.parseObject(jsonContent).getString("msg");
                String log = orderid_to_epay + "查询失败，失败原因：" + msg;
                logger.info(log);
                return "";

            }
        }else{
            return "";

        }

    }

    /**
     * 查询订单，并获取支付地址
     * @param orderid_to_epay
     * @param cbgSafeCode
     * @param cookie
     * @return
     */
    /**
     * 查询订单，并获取支付地址
     * @param orderid_to_epay
     * @param cbgSafeCode
     * @param cookie
     * @return
     */
    public static String getOrderInfoIp(String orderid_to_epay,String cbgSafeCode, String cookie,HttpClient client){

        Map map = new HashMap<String,String>();
        map.put("orderid_to_epay",orderid_to_epay);
        String jsonContent = HttpsClientUtil.sendPostSSLRequest(GET_ORDER_INFO_URL,map,cbgSafeCode,cookie,client);
        logger.info(DateUtil.currentDateR() + " getorderinfo 返回值 " + jsonContent);
        if(StringUtils.isNotBlank(jsonContent)){
            if(!jsonContent.contains("{")){
                logger.info(DateUtil.currentDateR() + "=========================没有查询到该订单");
                return "";
            }

            String msg1 = JSON.parseObject(jsonContent).getString("msg");
            logger.info(DateUtil.currentDateR() + "---------jieximsg之后 " + msg1);
            Integer status = JSON.parseObject(jsonContent).getInteger("status");
            if(1==status){
                logger.info(DateUtil.currentDateR() + "---------解析epay_pay_url之前");
                String epay_pay_url = JSON.parseObject(jsonContent).getJSONObject("pay_info").getString("epay_pay_url");
                return epay_pay_url;
            }else{
                String msg = JSON.parseObject(jsonContent).getString("msg");
                String log = "查询失败，失败原因：" + msg;
                logger.info(log);
                return "";
            }
        }else{
            return "";
        }
    }

    /**
     * 通过代理地址找到真实支付地址，获取支付单号
     * @param proxUrl
     * @return
     */
    public static String getPayNo(String proxUrl,String cookie,TextArea t,Integer index){
        HttpResponse response = HttpsClientUtil.sendPostSSLRequestGetResponse(proxUrl,new HashMap<String, String>(),null,cookie,null,null,map4.get(index));
        logger.info(DateUtil.currentDateR() + "---------返回getPayNo方法");
        Header[] headers = response.getHeaders("Location");
        if(null == headers || headers.length==0){
            return "";
        }else{
            String url = response.getHeaders("Location")[0].getValue();
            String log = DateUtil.currentDateR() + " 真实支付地址为：" + url;
            logger.info(log);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                try {
                    EntityUtils.toString(entity, "UTF-8");
                    EntityUtils.consume(entity);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String payNo = url.substring(url.indexOf("=")+1,url.length());
            logger.info(DateUtil.currentDateR() + "---------getPayNo方法return之前");
            return payNo;
        }

    }

    /**
     * 通过代理地址找到真实支付地址，获取支付单号
     * @param proxUrl
     * @return
     */
    public static String getPayNoIp(String proxUrl,String cookie,TextArea t,Integer index){
        HttpResponse response = HttpsClientUtil.sendPostSSLRequestGetResponse(proxUrl,new HashMap<String, String>(),null,cookie,null,null,map4.get(index));
        logger.info(DateUtil.currentDateR() + "---------返回getPayNo方法");
        Header[] headers = response.getHeaders("Location");
        if(null == headers || headers.length==0){
            return "";
        }else{
            String url = response.getHeaders("Location")[0].getValue();
            String log = DateUtil.currentDateR() + " 真实支付地址为：" + url;
            logger.info(log);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                try {
                    EntityUtils.toString(entity, "UTF-8");
                    EntityUtils.consume(entity);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String payNo = url.substring(url.indexOf("=")+1,url.length());
            logger.info(DateUtil.currentDateR() + "---------getPayNo方法return之前");
            return payNo;
        }

    }

    public static String goPay(String payNo,String price, String payPassword,String payCookie,TextArea t,Integer index){

        String payAmount = new BigDecimal(price).divide(new BigDecimal(100),2,RoundingMode.HALF_UP).toString();

        Map map = new HashMap<String,String>();
        map.put("securityValid","{\"shortPayPassword\":\"" + payPassword + "\"}");
        map.put("orderId",payNo);
        map.put("envData","{\"term\":\"wap\"}");
        String jsonContent = HttpsClientUtil.sendWapPostSSLRequest(WAP_PAY_URL,payNo,map,payCookie,null,null,map5.get(index));
//        String jsonContent = HttpsClientUtil.sendPostSSLRequest(PAY_URL,map,null,payCookie,null,null,map5.get(index));
        logger.info(DateUtil.currentDateR() +  " 普通支付 " + payNo + " =====================" + jsonContent);

        if(StringUtils.isNotBlank(jsonContent)){
            String errorCode = JSON.parseObject(jsonContent).getString("errorCode");
            String result = JSON.parseObject(jsonContent).getString("result");
            String errorMsg = JSON.parseObject(jsonContent).getString("errorMsg");

            logger.info(errorCode);
//            if(null!=t){
//                t.append(errorCode);t.append("\n");
//            }
            logger.info(result);
//            if(null!=t){
//                t.append(result);t.append("\n");
//            }
            logger.info(errorMsg);
//            if(null!=t){
//                t.append(errorMsg);t.append("\n");
//            }
        }

        return "";
    }

    public static String goPayT(String payNo,String price, String payPassword,String payCookie,TextArea t,Integer index){

        String payAmount = new BigDecimal(price).divide(new BigDecimal(100),2,RoundingMode.HALF_UP).toString();

        Map map = new HashMap<String,String>();
        map.put("proposal","{\"coupon\":null,\"balance\":{\"payAmount\":\"" + payAmount + "\"},\"orderId\":\"" + payNo + "\"}");
        map.put("securityValid","{\"pwEnVer\":\"M_A_1\",\"payPassword\":\"" + payPassword + "\"}");
        map.put("envData","{\"term\":\"web\"}");
        map.put("track","[[\"cashier\",\"paymode-hongbao\"],[\"cashier\",\"paymode-account\"],[\"cashier\",\"paybutton-combinpay\"],[\"cashier\",\"paybutton-combinpay\"]]");
        String jsonContent = HttpsClientUtil.sendPostSSLRequest(PAY_URL,map,null,payCookie,null,null,goPayMap.get(index));
        logger.info(DateUtil.currentDateR() + " 线程支付 " + payNo + " =============" + jsonContent);

        if(StringUtils.isNotBlank(jsonContent)){
            String errorCode = JSON.parseObject(jsonContent).getString("errorCode");
            String result = JSON.parseObject(jsonContent).getString("result");
            String errorMsg = JSON.parseObject(jsonContent).getString("errorMsg");

            logger.info(errorCode);
            if(null!=t){
                t.append(errorCode);t.append("\n");
            }
            logger.info(result);
            if(null!=t){
                t.append(result);t.append("\n");
            }
            logger.info(errorMsg);
            if(null!=t){
                t.append(errorMsg);t.append("\n");
            }
        }

        return "";
    }

    public static String goPayFinal(String payNo,String price, String payPassword,String payCookie,TextArea t,Integer index){

        String payAmount = new BigDecimal(price).divide(new BigDecimal(100),2,RoundingMode.HALF_UP).toString();

        Map map = new HashMap<String,String>();
        map.put("proposal","{\"coupon\":null,\"balance\":{\"payAmount\":\"" + payAmount + "\"},\"orderId\":\"" + payNo + "\"}");
        map.put("securityValid","{\"pwEnVer\":\"M_A_1\",\"payPassword\":\"" + payPassword + "\"}");
        map.put("envData","{\"term\":\"web\"}");
        map.put("track","[[\"cashier\",\"paymode-hongbao\"],[\"cashier\",\"paymode-account\"],[\"cashier\",\"paybutton-combinpay\"]]");
        String jsonContent = HttpsClientUtil.sendPayPostSSLRequest(PAY_URL,payNo,map,payCookie,null,null,map5.get(index));
//        String jsonContent = HttpsClientUtil.sendPostSSLRequest(PAY_URL,map,null,payCookie,null,null,map5.get(index));
        logger.info(DateUtil.currentDateR() +  " 普通支付 " + payNo + " =====================" + jsonContent);

        if(StringUtils.isNotBlank(jsonContent)){
            String errorCode = JSON.parseObject(jsonContent).getString("errorCode");
            String result = JSON.parseObject(jsonContent).getString("result");
            String errorMsg = JSON.parseObject(jsonContent).getString("errorMsg");

            logger.info(errorCode);
//            if(null!=t){
//                t.append(errorCode);t.append("\n");
//            }
            logger.info(result);
//            if(null!=t){
//                t.append(result);t.append("\n");
//            }
            logger.info(errorMsg);
//            if(null!=t){
//                t.append(errorMsg);t.append("\n");
//            }
        }

        return "";
    }

    public static String goPayFinalIp(String payNo,String price, String payPassword,String payCookie,TextArea t,Integer index){

        String payAmount = new BigDecimal(price).divide(new BigDecimal(100),2,RoundingMode.HALF_UP).toString();

        Map map = new HashMap<String,String>();
        map.put("proposal","{\"coupon\":null,\"balance\":{\"payAmount\":\"" + payAmount + "\"},\"orderId\":\"" + payNo + "\"}");
        map.put("securityValid","{\"pwEnVer\":\"M_A_1\",\"payPassword\":\"" + payPassword + "\"}");
        map.put("envData","{\"term\":\"web\"}");
        map.put("track","[[\"cashier\",\"paymode-hongbao\"],[\"cashier\",\"paymode-account\"],[\"cashier\",\"paybutton-combinpay\"]]");
        String jsonContent = HttpsClientUtil.sendPayPostSSLRequestNoConsume(PAY_URL,payNo,map,payCookie,null,null,map5.get(index));
        logger.info(DateUtil.currentDateR() +  " 普通支付 " + payNo + " =====================" + jsonContent);

        if(StringUtils.isNotBlank(jsonContent)){
            String errorCode = JSON.parseObject(jsonContent).getString("errorCode");
            String result = JSON.parseObject(jsonContent).getString("result");
            String errorMsg = JSON.parseObject(jsonContent).getString("errorMsg");

            logger.info(errorCode);
            logger.info(result);
            logger.info(errorMsg);
        }

        return "";
    }

    public static void  init(Map paramMap,String cbgSafeCode,String cookie,String payCookie){
        StringUtils.isNotBlank("za");
        Integer status1 = JSON.parseObject("{\"msg\": \"提前注册JSON\", \"status\": 4}").getInteger("status");
        JSONArray jo =  JSON.parseObject("{\"status\": 1, \"paging\": {\"is_last_page\": true}, \"unpaid_order_num\": 1, \"result\": [{\"buyer_name\": \"18699664447\", \"subtitle\": \"\\u68a6\\u5e7b\\u897f\\u6e38\\u624b\\u6e38-\\u65f6\\u7a7a\\u4e4b\\u9699\", \"is_user_cancel\": false, \"buy_poundages\": [], \"expire_remain_seconds\": 1799, \"expire_time\": \"2019-02-14 11:07:14\", \"create_time\": \"2019-02-14 10:37:14\", \"equipid\": 19110, \"pay_time\": null, \"equip\": {\"appointed_data\": null, \"create_time_desc\": \"2019-01-16 08:00:18\", \"storage_type\": 4, \"create_time\": \"2019-01-16 08:00:18\", \"equipid\": 19110, \"allow_multi_order\": true, \"collect_num\": 5, \"pass_fair_show\": 1, \"appointed_roleid\": null, \"fair_show_end_time\": \"2019-01-17 08:00:18\", \"platform_type\": 1, \"level_desc\": \"69\\u7ea7\", \"min_buy_level\": null, \"kindid\": 1, \"equip_level\": 69, \"icon\": \"/game_res/res/photo/0004.png\", \"area_name\": \"\\u53cc\\u5e73\\u53f0\", \"desc_sumup_short\": \"\\u603b\\u8bc4\\u5206:10645\", \"status\": 3, \"cross_buy_serverid_list\": [], \"equip_type\": \"1\", \"price\": 6100, \"other_info\": {\"school\": 4, \"format_equip_name\": \"\\u666e\\u9640\\u5c71\", \"level_desc\": \"69\\u7ea7\", \"desc_sumup\": \"\\u603b\\u8bc4\\u5206:10645\", \"basic_attrs\": [\"\\u603b\\u8bc4\\u5206:10645\"], \"icon\": \"/game_res/res/photo/0004.png\", \"desc_sumup_short\": \"\\u603b\\u8bc4\\u5206:10645\"}, \"status_desc\": \"\\u88ab\\u4e0b\\u5355\", \"expire_remain_seconds\": 111260, \"expire_time\": \"2019-02-15 17:31:35\", \"serverid\": 47, \"first_onsale_price\": 8000, \"server_name\": \"\\u65f6\\u7a7a\\u4e4b\\u9699\", \"allow_cross_buy\": false, \"game_ordersn\": \"201901081901716-47-IOTBGGXLFF9ZL\", \"web_last_price\": 6100, \"equip_count\": 1, \"equip_name\": \"\\u044d\\u5929\\u771f\", \"eid\": \"201901160801713-47-ABUZ2NDYOGGEB\", \"format_equip_name\": \"\\u666e\\u9640\\u5c71\", \"selling_time\": \"2019-02-01 17:31:35\"}, \"orderid_to_epay\": \"47_39226\", \"orderid\": 39226, \"remain_seconds\": 1799, \"buyer_role\": null, \"status\": 1, \"return_money_time\": null, \"invalid_time\": null, \"price\": 6100, \"status_desc\": \"\\u5f85\\u4ed8\\u6b3e\", \"wallet_pay_type\": 0, \"price_desc\": \"\\uffe561.00\", \"total_poundage\": 0, \"serverid\": 47, \"total_poundage_desc\": \"0.00\", \"price_total\": 6100, \"buyer_urs\": \"yd.3fc2c824585b43f59@163.com\", \"orderid_from_epay\": \"2019021410JY30878933\"}]}").getJSONArray("result");
//        String epay_pay_url = JSON.parseObject(jsonContent).getJSONObject("pay_info").getString("epay_pay_url");
        String result = jo.get(0).toString();
        String status = JSON.parseObject(result).getString("status");
        String status_desc = JSON.parseObject(result).getString("status_desc");

        String jsonContent1 = HttpsClientUtil.sendPostSSLRequest(ADD_ORDER_URL,paramMap,cbgSafeCode,cookie,httpClient1);
        logger.info(jsonContent1);

        Map map = new HashMap<String,String>();
        map.put("orderid_to_epay","111");
        String jsonContent2 = HttpsClientUtil.sendPostSSLRequest(ORDER_URL,map,cbgSafeCode,cookie,httpClient2);
        logger.info(jsonContent2);

        HttpResponse response = HttpsClientUtil.sendPostSSLRequestGetResponse("https://api.epay.163.com/mwallet/order/pay.htm?msg=eyJvcmRlcklkIjogIjIwMTkwMTI5MTdKWTk4MjY3MTg2IiwgImNhbGxlclBsYXRmb3JtSWQiOiAiMjAxNzExMjAxNlBUNTM4NzU3NDYiLCAicGxhdGZvcm1JZCI6ICIyMDE4MDQyNTEzUFQ1NTA1MDc2NiIsICJ2ZXJzaW9uIjogIjEuMC4wIiwgInBheVN0cmF0ZWd5IjogIntcIndhbGxldFR5cGVcIjogXCJDQkdfV0FMTEVUXCIsIFwiZXBheUFtb3VudFwiOiAyODgwMDAuMCwgXCJtZXJjaGFudFdhbGxldEFtb3VudFwiOiAwLjAsIFwidXNlSW5zcGVjdEJhbGFuY2VcIjogZmFsc2UsIFwib3V0ZXJDaGFyZ2VGZWVOb3RlXCI6IHtcImFwRmVlXCI6IDAuMCwgXCJ3eEZlZVwiOiAwLjB9LCBcIm91dGVyQ2hhcmdlRmVlU3RyYXRlZ3lcIjoge1wiYXBGZWVcIjogMzE2OC4wLCBcInd4RmVlXCI6IDIwMTYuMH19IiwgImFjY291bnRJZCI6ICJ5ZC41ZWM0ODU3YTQzNjE0NGVjYUAxNjMuY29tIn0%3D&msgType=JSON&sign=RWQLn6sM04BS8yxvswHseY%2FjQf867EHmmMitxiyDBcmRKR78l9HIzCxBetvVD957Us22bwmIrNWe6zs%2FNn8dz4vYKI3tEn72phehQF4AC%2Bx6nJ13J8D1E49g68CegWKN5e32gJYYqrVbDAPHz1bqwDIKloTRb36pUTqkMjIWtvGkW55KFDQtDsSVyYWVrhuz7ulo2vftxtyyyjLtltLNjYvmJuGXj2cTgRkH5ZJPiKD95%2BxSpZNXp2%2BMGmQ8vwTnr0ij5VG2MLUGgP630CJhKHLQplzPM1gMYXHhLTaYLfZ3odBXZLMp7jArgCBvU6z24SRkSg3QoSck1W77MtMdhg%3D%3D",new HashMap<String, String>(),null,cookie,null,null,httpClient5);
        logger.info(response.toString());
//        response.getHeaders("Location");
        String url1 = "https://epay.163.com/cashier/standardCashier?orderId=2019021614JY66222093";
        String payNo = url1.substring(url1.indexOf("=")+1,url1.length());

        String payAmount = new BigDecimal("6100").divide(new BigDecimal(100),2,RoundingMode.HALF_UP).toString();

        Map map1 = new HashMap<String,String>();
        map1.put("proposal","{\"coupon\":null,\"balance\":{\"payAmount\":\"" + payAmount + "\"},\"orderId\":\"" + 111 + "\"}");
        map1.put("securityValid","{\"pwEnVer\":\"M_A_1\",\"shortPayPassword\":\"" + 111 + "\"}");
        map1.put("envData","{\"term\":\"web\"}");
        map1.put("track","[[\"cashier\",\"paymode-hongbao\"],[\"cashier\",\"paymode-account\"],[\"cashier\",\"paybutton-combinpay\"]]");
        String jsonContent4 = HttpsClientUtil.sendPostSSLRequest(PAY_URL,map1,null,payCookie,null,null,httpClient4);
        logger.info(jsonContent4);


    }

    public static void  initFinal(Map paramMap,String cbgSafeCode,String cookie,String payCookie,String payPassword){
        String orderid_to_epay1 = PayService.addOrder(paramMap,cbgSafeCode,cookie,null,0);
        String result = PayService.queryOrder(cbgSafeCode,cookie,0);
        if(StringUtils.isNotBlank(result)){
            logger.info("解析status之前 " + DateUtil.currentDateR());
            String status = JSON.parseObject(result).getString("status");
            logger.info("解析status_desc之前 " + DateUtil.currentDateR());
            String status_desc = JSON.parseObject(result).getString("status_desc");
            logger.info("订单状态：" + status);
            logger.info(status_desc);
            String orderid_to_epay = JSON.parseObject(result).getString("orderid_to_epay");


            String epay_pay_url = PayService.getOrderInfo(orderid_to_epay,cbgSafeCode,cookie,null,0);
            logger.info("增加订单里的===============获取支付地址完成 " + DateUtil.currentDateR() + " " + epay_pay_url);
            if(StringUtils.isNotBlank(epay_pay_url)){
                String payNo = PayService.getPayNo(epay_pay_url,payCookie,null,0);
                logger.info("增加订单里的===============访问支付地址完成 " + payNo + " " + DateUtil.currentDateR());
                if (StringUtils.isBlank(payNo)){
                    payNo = PayService.getPayNo(epay_pay_url,payCookie,null,0);
                }
                if(StringUtils.isNotBlank(payNo)){
                    String str2 = PayService.goPayFinal(payNo,(String)paramMap.get("confirm_price_total"),payPassword,payCookie,null,0);
                }
            }


            PayService.cancelOrder(0,orderid_to_epay,cbgSafeCode,cookie);
            PayService.deleteOrder(0,orderid_to_epay,cbgSafeCode,cookie);
            logger.info("初始化结束----------------------------------------");
        }

    }

    public static void  initFinalNoDelete(Map paramMap,String cbgSafeCode,String cookie,String payCookie,String payPassword){
        String orderid_to_epay = PayService.addOrder(paramMap,cbgSafeCode,cookie,null,0);
//        String result = PayService.queryOrder(cbgSafeCode,cookie,0);
        if(StringUtils.isNotBlank(orderid_to_epay)){
//            logger.info("解析status之前 " + DateUtil.currentDateR());
//            String status = JSON.parseObject(result).getString("status");
//            logger.info("解析status_desc之前 " + DateUtil.currentDateR());
//            String status_desc = JSON.parseObject(result).getString("status_desc");
//            logger.info("订单状态：" + status);
//            logger.info(status_desc);
//            String orderid_to_epay = JSON.parseObject(result).getString("orderid_to_epay");
//            for (int i = 0; i <= 7; i++) {
                int i = 0 ;
                String epay_pay_url = PayService.getOrderInfo(orderid_to_epay, cbgSafeCode, cookie, null, i);
                logger.info("增加订单里的===============获取支付地址完成 " + DateUtil.currentDateR() + " " + epay_pay_url);
                if (StringUtils.isNotBlank(epay_pay_url)) {
                    String payNo = PayService.getPayNo(epay_pay_url, payCookie, null, i);
                    logger.info("增加订单里的===============访问支付地址完成 " + payNo + " " + DateUtil.currentDateR());
                    if (StringUtils.isBlank(payNo)) {
                        payNo = PayService.getPayNo(epay_pay_url, payCookie, null, i);
                    }
                    if (StringUtils.isNotBlank(payNo)) {
                        String str2 = PayService.goPayFinal(payNo, (String) paramMap.get("confirm_price_total"), payPassword, payCookie, null, i);
                    }
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//            }
            PayService.cancelOrder(0,orderid_to_epay,cbgSafeCode,cookie);
            logger.info("初始化结束----------------------------------------");
        }
    }


    public static void  initFinalTest(Map paramMap,String cbgSafeCode,String cookie,String payCookie,String payPassword,Integer index){
        String orderid_to_epay1 = PayService.addOrder(paramMap,cbgSafeCode,cookie,null,index);
        String result = PayService.queryOrder(cbgSafeCode,cookie,index);
        if(StringUtils.isNotBlank(result)){
            logger.info("解析status之前 " + DateUtil.currentDateR());
            String status = JSON.parseObject(result).getString("status");
            logger.info("解析status_desc之前 " + DateUtil.currentDateR());
            String status_desc = JSON.parseObject(result).getString("status_desc");
            logger.info("订单状态：" + status);
            logger.info(status_desc);
            String orderid_to_epay = JSON.parseObject(result).getString("orderid_to_epay");


            String epay_pay_url = PayService.getOrderInfo(orderid_to_epay,cbgSafeCode,cookie,null,index);
            logger.info("增加订单里的===============获取支付地址完成 " + DateUtil.currentDateR() + " " + epay_pay_url);
            if(StringUtils.isNotBlank(epay_pay_url)){
                String payNo = PayService.getPayNo(epay_pay_url,payCookie,null,index);
                logger.info("增加订单里的===============访问支付地址完成 " + payNo + " " + DateUtil.currentDateR());
                if (StringUtils.isBlank(payNo)){
                    payNo = PayService.getPayNo(epay_pay_url,payCookie,null,index);
                }
                if(StringUtils.isNotBlank(payNo)){
                    String str2 = PayService.goPayFinal(payNo,(String)paramMap.get("confirm_price_total"),payPassword,payCookie,null,index);
                }
            }


            PayService.cancelOrder(index,orderid_to_epay,cbgSafeCode,cookie);
            PayService.deleteOrder(index,orderid_to_epay,cbgSafeCode,cookie);
            logger.info("初始化结束----------------------------------------");
        }

    }

    /**
     * 初始化HttpClinet
     *
     */
    public static void initHttp(int ipApiTimes,String apiUrl){
        try{

            for (int j = 0; j < ipApiTimes; j++) {
                String jsonContent = HttpsClientUtil.sendPost(apiUrl,null);
                JSONArray array = JSON.parseObject(jsonContent).getJSONArray("msg");
                for (int k = 0; k < array.size(); k++) {
                    JSONObject model = array.getJSONObject(k);
                    String port = model.getString("port");
                    String ip = model.getString("ip");
                    logger.info(ip + ":" +  port);
//                    if(isHide(ip,Integer.valueOf(port))){
                        for (int l = 0; l < 30; l++) {
                            ipCount ++;
                            ipMap.put(ipCount,ip);
                            portMap.put(ipCount,Integer.valueOf(port));
                        }
//                    }
                }
                Thread.sleep(1000);
            }

            logger.info("---------总共可用ip个数：" + ipCount);
//            Thread.sleep(1000);
            logger.info(DateUtil.currentDateR() + "------请求ip，初始化代理之前");
            int init = 1;
            for (int i = 1; i <= ipCount; i++) {
//                HttpClient httpClient1 = new SSLClient();
//                HttpClient httpClient2 = new SSLClient();
//                map1.put(i,httpClient1);
//                map2.put(i,httpClient2);
                if(i%6==0){
                    HttpClient hc = map1.get(i);
                    HttpHost proxy = new HttpHost(ipMap.get(i), portMap.get(i));
                    hc.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
                    map1.put(i,hc);
                }else if(i%6==1){
                    HttpClient hc = map2.get(i);
                    HttpHost proxy = new HttpHost(ipMap.get(i), portMap.get(i));
                    hc.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
                    map2.put(i,hc);
                }else if(i%6==2){
                    HttpClient hc = map3.get(i);
                    HttpHost proxy = new HttpHost(ipMap.get(i), portMap.get(i));
                    hc.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
                    map3.put(i,hc);
                }else if(i%6==3){
                    HttpClient hc = map4.get(i);
                    HttpHost proxy = new HttpHost(ipMap.get(i), portMap.get(i));
                    hc.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
                    map4.put(i,hc);
                }else if(i%6==4){
                    HttpClient hc = map5.get(i);
                    HttpHost proxy = new HttpHost(ipMap.get(i), portMap.get(i));
                    hc.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
                    map5.put(i,hc);
                }else if(i%6==5){
                    HttpClient hc = goPayMap.get(i);
                    HttpHost proxy = new HttpHost(ipMap.get(i), portMap.get(i));
                    hc.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
                    goPayMap.put(i,hc);
                }else{

                }
            }
            ipCount = ipCount/6;
//            for (int j = 0; j < 200; j++) {
//                HttpClient httpClient3 = new SSLClient();
//                HttpClient httpClient4 = new SSLClient();
//                HttpClient httpClient5 = new SSLClient();
//                HttpClient httpClient6 = new SSLClient();
//
//                map3.put(i,httpClient3);
//                map4.put(i,httpClient4);
//                map5.put(i,httpClient5);
//                goPayMap.put(i,httpClient6);
//            }
            logger.info(DateUtil.currentDateR() + "------请求ip，初始化代理之后");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initHttpTest(int ipApiTimes,String apiUrl){
        try{

            for (int j = 0; j < ipApiTimes; j++) {
                String jsonContent = HttpsClientUtil.sendPost(apiUrl,null);
                JSONArray array = JSON.parseObject(jsonContent).getJSONArray("msg");
                for (int k = 0; k < array.size(); k++) {
                    JSONObject model = array.getJSONObject(k);
                    String port = model.getString("port");
                    String ip = model.getString("ip");
                    logger.info(ip + ":" +  port);
//                    if(isHide(ip,Integer.valueOf(port))){
//                    for (int l = 0; l < 30; l++) {
                        ipCount ++;
                        ipMap.put(ipCount,ip);
                        portMap.put(ipCount,Integer.valueOf(port));
//                    }
//                    }
                }
                Thread.sleep(1000);
            }

            logger.info("---------总共可用ip个数：" + ipCount);
//            Thread.sleep(1000);
            logger.info(DateUtil.currentDateR() + "------请求ip，初始化代理之前");
            for (int i = 1; i <= ipCount; i++) {
                HttpClient hc = map1.get(i);
                HttpClient hc1 = map2.get(i);
                HttpClient hc2 = map3.get(i);
                HttpClient hc3 = map4.get(i);
                HttpClient hc4 = map5.get(i);
                HttpClient hc5 = goPayMap.get(i);
                HttpHost proxy = new HttpHost(ipMap.get(i), portMap.get(i));
                hc.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
                hc1.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
                hc2.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
                hc3.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
                hc4.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
                hc5.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
            }
            logger.info(DateUtil.currentDateR() + "------请求ip，初始化代理之后");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int ipCount = 0;
    private static Map<Integer,String> ipMap  = new HashMap<Integer, String>();
    private static Map<Integer,Integer> portMap = new HashMap<Integer, Integer>();


    public static boolean isHide(String ip,int port){
//        String json = HttpsClientUtil.sendProxyGet(HTTP_BIN_URL,null,ip,port);
        String trueIp = HttpsClientUtil.sendProxyGet(IS_HIDE_URL,null,ip,port);
        logger.info("海鸥那返回的ip：" + trueIp);
        String[] haiouIps = trueIp.split(",");
        for (String ipStr : haiouIps){
            boolean haiouB = contaisIp(ipStr);
            if(haiouB){
                return false;
            }
        }
//        logger.info(json);
//        if(StringUtils.isNotBlank(json)){
//            String ips = JSON.parseObject(json).getString("origin");
//            String[] ipArray = ips.split(",");
//            for (String ipStr : ipArray){
//                if(contaisIp(ipStr)){
//                    return false;
//                }
//            }
//        }else{
//            return false;
//        }
        return true;
    }

    /**
     * 判断ip是否跟本地ip一样
     * @param ip
     * @return
     */
    public static boolean contaisIp(String ip){
        for (String ipStr : ips){
            if(ipStr.equals(ip)){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
//        int i = 1;
//        HttpClient hc = map1.get(i);
//        HttpClient hc1 = map2.get(i);
//        HttpClient hc2 = map3.get(i);
//        HttpClient hc3 = map4.get(i);
//        HttpClient hc4 = map5.get(i);
//        HttpClient hc5 = goPayMap.get(i);
//        HttpHost proxy = new HttpHost("60.187.170.251", 668);
//        hc.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
//        hc1.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
//        hc2.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
//        hc3.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
//        hc4.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
//        hc5.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);

//        initHttpTest(10,IP_URL);
////        for (int j = 1; j <= 10; j++) {
//            initFinalTest(paramMap,cbgSafeCode,cookie,payCookie,payPassword,i);
////        }

    }

    public static List<HttpClient> initHttpClient(int ipCount,String ipUrl){
        List<HttpClient> list = new ArrayList<HttpClient>();
        try {
            logger.info("=====================初始化HttpClient之前");
            String json = HttpsClientUtil.sendGet(ipUrl,null);
            JSONArray array = JSON.parseObject(json).getJSONArray("data");
            for (int j = 0; j < array.size(); j++) {
                JSONObject model = array.getJSONObject(j);
                String ip = model.getString("ip");
                int port = model.getInteger("port");
                for (int k = 0; k < 30; k++) {
                    HttpClient httpClient = null;
                    httpClient = new SSLClient();
                    HttpHost proxy = new HttpHost(ip, port);
                    httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
                    list.add(httpClient);
                }
            }
            logger.info("=====================初始化HttpClient之后 ip数量：" + array.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
