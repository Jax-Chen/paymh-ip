package com.cjc.paymh.constant;

/**
 * Created by chenjc23273 on 2019/02/23
 *
 * @author chenjc23273
 */
public class URL {

    public static final String ADD_ORDER_URL = "https://my.cbg.163.com/cgi/api/add_order";
    public static final String GET_ORDER_INFO_URL = "https://my.cbg.163.com/cgi/api/get_order_pay_info";
    public static final String PAY_URL = "https://epay.163.com/cashier/ajaxPay";
    public static final String ORDER_URL = "https://my.cbg.163.com/cgi/api/my_orders?page=1&status=1";
    public static final String CANCEL_ORDER_URL = "https://my.cbg.163.com/cgi/api/cancel_order";
    public static final String DELETE_ORDER_URL = "https://my.cbg.163.com/cgi/api/delete_order";

    public static final String WAP_PAY_URL = "https://epay.163.com/cashier/m/security/verifyPayItems";
    public static final String IP_URL = "http://piping.mogumiao.com/proxy/api/get_ip_bs?appKey=a49052dc9fa44e3c82acb840721f7544&count=1&expiryDate=0&format=1&newLine=2";

    public static final String IS_HIDE_URL = "http://118.25.104.171/hello";
    public static final String HTTP_BIN_URL = "http://httpbin.org/get";

    public volatile static String ORDERID_TO_EPAY = "103_7891";

}
