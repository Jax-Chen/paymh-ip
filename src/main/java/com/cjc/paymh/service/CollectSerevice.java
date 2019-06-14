package com.cjc.paymh.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjc.paymh.util.DateUtil;
import com.cjc.paymh.util.HttpsClientUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenjc23273 on 2019/03/04
 *
 * @author chenjc23273
 */
public class CollectSerevice {

    public static final String LIST_URL = "https://my.cbg.163.com/cgi/api/query";
    public static final String DETAIL_URL = "https://my.cbg.163.com/cgi/api/get_equip_detail";
    public static final String COLLECT_URL = "https://my.cbg.163.com/cgi/api/add_collect";


    public static String list(String cbgSafeCode,String cookie,HttpClient httpClient,String time){

        boolean end = false;
        for (int i = 1; i < 80; i++) {

            Map<String, String> params = new HashMap<String,String>();
            params.put("pass_fair_show","0");
            params.put("price_max","150000");
            params.put("order_by","collect_num DESC");
            params.put("page",i+"");
//        String json = HttpsClientUtil.sendGet(LIST_URL,params);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String json = HttpsClientUtil.sendPostSSLRequest(LIST_URL,params,cbgSafeCode,cookie,httpClient);
//            String msg2 = JSON.parseObject(json).getString("msg");
//            System.out.println(msg2);
            JSONArray array = JSON.parseObject(json).getJSONArray("result");
            for (int j = 0; j < array.size(); j++) {
                Integer collect_num = array.getJSONObject(j).getInteger("collect_num");
                String game_ordersn = array.getJSONObject(j).getString("game_ordersn");
//                if(collect_num>80){
//                    continue;
//                }
                if(collect_num<30){
                    end = true;
                }else{
                    String tmp = game_ordersn.substring(game_ordersn.indexOf("-")+1);
                    String serverid = tmp.substring(0,tmp.indexOf("-"));
                    Map<String, String> params2 = new HashMap<String,String>();
                    params2.put("serverid",serverid);
                    params2.put("ordersn",game_ordersn);
                    params2.put("view_loc","search");
                    System.out.println(collect_num);
                    System.out.println(game_ordersn);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String json2 = HttpsClientUtil.sendPostSSLRequest(DETAIL_URL,params2,cbgSafeCode,cookie,httpClient);
                    System.out.println(json2);
                    if(StringUtils.isNotBlank(JSON.parseObject(json2).getString("equip"))){
                        try {
                            System.out.println("===========" + JSON.parseObject(json2).getString("equip"));
                            JSONObject obj = JSON.parseObject(JSON.parseObject(json2).getString("equip"));
                            Date fair_show_end_time = obj.getDate("fair_show_end_time");
                            boolean b = DateUtil.compare(DateUtil.convertStringToDate("yyyyMMddHHmmss",time),fair_show_end_time);
                            if(b){
                                Map<String, String> params3 = new HashMap<String,String>();
                                params3.put("serverid",serverid);
                                params3.put("ordersn",game_ordersn);
                                params3.put("view_loc","search");
                                try {
                                    Thread.sleep(5000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                String json3 = HttpsClientUtil.sendPostSSLRequest(COLLECT_URL,params3,cbgSafeCode,cookie,httpClient);
                                System.out.println(json3);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if(end){
                break;
            }
        }
        return "";
    }

}
