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
public class DeleteSerevice {

    public static final String LIST_URL = "https://my.cbg.163.com/cgi/api/query";
    public static final String DETAIL_URL = "https://my.cbg.163.com/cgi/api/get_equip_detail";
    public static final String COLLECT_URL = "https://my.cbg.163.com/cgi/api/add_collect";
    public static final String ORDER_CANCEL_URL = "https://my.cbg.163.com/cgi/api/my_orders?page=1&status=3,4,5,7";
    public static final String DELETE_ORDER_URL = "https://my.cbg.163.com/cgi/api/delete_order";

    public static String delete(String cbgSafeCode,String cookie,HttpClient httpClient,String time){

        while(true) {

            Map<String, String> params = new HashMap<String,String>();
            params.put("pass_fair_show","0");
            params.put("price_max","150000");
            params.put("order_by","collect_num DESC");
            params.put("page","1");
            params.put("status","3,4,5,7");
//        String json = HttpsClientUtil.sendGet(LIST_URL,params);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String json = HttpsClientUtil.sendPostSSLRequest(ORDER_CANCEL_URL,params,cbgSafeCode,cookie,httpClient);
            JSONArray array = JSON.parseObject(json).getJSONArray("result");
            for (int j = 0; j < array.size(); j++) {
                String orderid_to_epay = array.getJSONObject(j).getString("orderid_to_epay");
                Map<String, String> params2 = new HashMap<String,String>();
                params2.put("orderid_to_epay",orderid_to_epay);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String json1 = HttpsClientUtil.sendPostSSLRequest(DELETE_ORDER_URL,params2,cbgSafeCode,cookie,httpClient);
                System.out.println(json1);
                String msg = JSON.parseObject(json1).getString("msg");
                System.out.println(msg);
            }
            if(array.size()<1){
                break;
            }
        }
        return "";
    }

}
