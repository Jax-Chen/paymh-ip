package com.cjc.paymh.util;

import com.cjc.paymh.controller.IndexController;
import com.cjc.paymh.model.SSLClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by chenjc23273 on 2019/01/28
 *
 * @author chenjc23273
 */
public class HttpsClientUtil {

    protected static final Logger logger = LoggerFactory.getLogger(HttpsClientUtil.class);

//    private static HttpClient httpClient = null;
//
//    private static synchronized HttpClient getHttpClient() {
//        if(httpClient == null) {
//            try {
//                httpClient = new SSLClient();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return httpClient;
//    }

//    public static void main(String[] args) {
//
//
//        String str = "";
//        boolean b = StringUtils.isNotBlank(str);
//        logger.info(b);
//
//    }


    public static String sendPostSSLRequest(String reqURL, Map<String, String> params,String cbgSafeCode,String cookie,HttpClient httpClient) {
        return sendPostSSLRequest(reqURL,params,cbgSafeCode,cookie,null,null,httpClient);
    }

    /**
     * 发送HTTPS_POST请求
     *  该方法会自动关闭连接,释放资源
     *  该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
     * @param reqURL        请求地址
     * @param params        请求参数
     * @param encodeCharset 编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
     * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
     * @return 远程主机响应正文
     */
    public static String sendPostSSLRequest(String reqURL, Map<String, String> params,String cbgSafeCode,
                                            String cookie, String encodeCharset, String decodeCharset,HttpClient httpClient) {
        logger.info(DateUtil.currentDateR() + reqURL);
        String responseContent = "";
        HttpPost httpPost = null;
        try {
            httpPost = new HttpPost(reqURL);
//            HttpHost proxy = new HttpHost("118.89.138.129", 59460,"https");
//            RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
//            httpPost.setConfig(config);
            httpPost.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
//            httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
//            httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.9");
//            httpPost.addHeader("Cache-Control", "no-cache");
            httpPost.addHeader("cbg-safe-code", cbgSafeCode==null?"yxqNirTr":cbgSafeCode);
            httpPost.addHeader("Connection", "keep-alive");
//            httpPost.addHeader("Content-Length", "70");
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.addHeader("Cookie", cookie!=null?cookie:"usertrack=ezq0pVri5z4pfeFjCLI+Ag==; _ntes_nnid=aeddc0f0d48428394daf0f32351e34c7,1524819775622; _ntes_nuid=aeddc0f0d48428394daf0f32351e34c7; _ga=GA1.2.252405637.1524819776; vjuids=4eea9f1.16348c6d1d6.0.706e760b26a82; fingerprint=r7azf7pnrt04h6k5; __gads=ID=f47e133227159fe9:T=1529990656:S=ALNI_MaeA6oBLicJqUk6XaZj4vX_9qSniA; __f_=1543801309184; vinfo_n_f_l_n3=61dae1c8a939025a.1.1.1529990679307.1529990869635.1546845444202; vjlast=1525934379.1547101224.12; NTES_YD_PASSPORT=G9LAtGogqb7c6akOJG2RSyOj3F_4oPKI1b_R0fHarBEwMmy6MT5QSOvpDubOvFicG9gvXW0rrP5xDg3Z2jjDNYpHPe7ul_jt1uEX2UIxxG0sPcbesq3lwvAmK4QNlrMB7q3r90U9tJXnS7Hvn1i9Vxa9wszUZ9ZsVfPFOjrcJZFIlq9UDMCAvbUasudNoGUtGJE11nggptd2O; P_INFO=17816116670|1548675365|1|epay|00&99|zhj&1548312026&cbg#zhj&330100#10#0#0|&0|null|17816116670; is_log_active_stat=1; SID=e56b18ea-e9c7-4fab-9809-d22a0a05c59a; NTES_YD_SESS=0IraIyBBmMVQ701uSTxhCO0xm_JM27RwQbYg22jLfl9wEox_E6mDytw2rLatwCKS.Hiw7RBYY8mHliegIaY6hsjlGOEmjmTS21bNZMRR9gl05t5wttbwn6qQaDZ95q8WjAMnAsOyR3f1_6b7v_PmN6dIz9WYnyUSXQwQIT3XBcb9_lNxdV52vfdqc5PX9dJdx3dDnGUtyZq3B.tPp9estw8zk; S_INFO=1548724500|1|0&60##|17816116670; sid=Xs96AOXM2bFuwQI4rO7KXGiiFpqm-x3mi2QKBpvA");
//            httpPost.addHeader("Cookie", "EPAY_ENV=YQ_ROOM; usertrack=ezq0pVri5z4pfeFjCLI+Ag==; _ntes_nnid=aeddc0f0d48428394daf0f32351e34c7,1524819775622; _ntes_nuid=aeddc0f0d48428394daf0f32351e34c7; _ga=GA1.2.252405637.1524819776; vjuids=4eea9f1.16348c6d1d6.0.706e760b26a82; __gads=ID=f47e133227159fe9:T=1529990656:S=ALNI_MaeA6oBLicJqUk6XaZj4vX_9qSniA; __f_=1543801309184; vinfo_n_f_l_n3=61dae1c8a939025a.1.1.1529990679307.1529990869635.1546845444202; vjlast=1525934379.1547101224.12; NTES_YD_PASSPORT=G9LAtGogqb7c6akOJG2RSyOj3F_4oPKI1b_R0fHarBEwMmy6MT5QSOvpDubOvFicG9gvXW0rrP5xDg3Z2jjDNYpHPe7ul_jt1uEX2UIxxG0sPcbesq3lwvAmK4QNlrMB7q3r90U9tJXnS7Hvn1i9Vxa9wszUZ9ZsVfPFOjrcJZFIlq9UDMCAvbUasudNoGUtGJE11nggptd2O; _OkLJ_%UJ=ITWH7FIY7J6WNE5E; WM_TID=qnhz3c6ldndFEVBEBQIskS9f2FXaHEWB; EPAYSESSIONID=EPAY-18674ee5-9d09-4581-a0de-e2f879e14222; riskInfoUuid=41bfa137-29cb-451b-acf1-901c9ec01a60; NTES_YD_SESS=p3IRE5QgDDUZm.cyKliDyFGAPsahtiqcraReCvgUJ0PzCIEACNjlM8xnTDo8x2c1g7hxfJs66Kj7HhtLwo6NravHdPCjvj31n_mRSFJJXLH4q8qx88mx5NZUolSXqZKkvhW33p990sKj83gxNfT1_qdVZookGRn5xV3pDgGK8z6SN5VJ.0f9Wj31UT5GN6_.tH00GgA5XWKFO5vL4DyT1YrhkVR3KVC.h; S_INFO=1548675365|0|3&80##|17816116670; P_INFO=17816116670|1548675365|1|epay|00&99|zhj&1548312026&cbg#zhj&330100#10#0#0|&0|null|17816116670; WM_NI=oRwxUg2%2BCqQlgvhEN7%2B97yhEgRClXhyXUckNplfl7UAjR7k6MPdBw4Qn0%2FgtsUReLEEyBMPxLScMN%2BgE5R%2FVLH1ijnPPicVbwdxhhkSv2wlxOF0REtulQfPE3N78%2F254RDc%3D; WM_NIKE=9ca17ae2e6ffcda170e2e6eed6f73caaad968fe2809cb48aa2d15e869a8f85bc6d8e92a9a5b6798192998dd72af0fea7c3b92aad8eb999d86df2f58d92d468a5aaa8a6cd3de9e7f99aeb4fa58e8d8ddb64bb889b84ea46b4b4aa8db65f83b69ed8ea3e8f90aca3f64093b200d0e680b18b9e86cf4f87a78eaed374908dadaecd21a59bfbb3ae5e9098ff8ae9669490a784d56ab5bcb889eb6191aafe98ef748d9a00b3c880b7b49f87d34ff192f9d9ae3af7ec9ea5e237e2a3; EPAY_ENV=YQ_ROOM; _gat=1; JSESSIONID=95D24A1BDFC2F09B96CA98023BD3F7DB; FRMS_FINGERPRINT=1311652e7a11d3f7ccf61201bf934a1ecc1b35971401b326b5062b2f0e691341e4da3b7fbbce23451371dfa004ad8ed498721391000000000000000012416a5889bb0190d0211291502f1bd97b0e7d801281d3d9446802a44259009139fa36c62c3b2354130187374a5ff147c45612511ff1de774005f8da0101b3a5d5fc2cac9d4e12216a5889bb0190d021136100000000000000001331e4da3b7fbbce23452b01537ff3902960649f1381b326b5062b2f0e69142100000000000000001231d4c2e4a3297fe25a12610000000000000000141100000000000000001351a87ff679a2f3e71d132168934a3e9455fa7212113a835d3215755c431271d3d9446802a44259");
//            httpPost.addHeader("Cookie", "usertrack=ezq0pVri5z4pfeFjCLI+Ag==; _ntes_nnid=aeddc0f0d48428394daf0f32351e34c7,1524819775622; _ntes_nuid=aeddc0f0d48428394daf0f32351e34c7; _ga=GA1.2.252405637.1524819776; vjuids=4eea9f1.16348c6d1d6.0.706e760b26a82; fingerprint=r7azf7pnrt04h6k5; __gads=ID=f47e133227159fe9:T=1529990656:S=ALNI_MaeA6oBLicJqUk6XaZj4vX_9qSniA; __f_=1543801309184; vinfo_n_f_l_n3=61dae1c8a939025a.1.1.1529990679307.1529990869635.1546845444202; vjlast=1525934379.1547101224.12; NTES_YD_PASSPORT=G9LAtGogqb7c6akOJG2RSyOj3F_4oPKI1b_R0fHarBEwMmy6MT5QSOvpDubOvFicG9gvXW0rrP5xDg3Z2jjDNYpHPe7ul_jt1uEX2UIxxG0sPcbesq3lwvAmK4QNlrMB7q3r90U9tJXnS7Hvn1i9Vxa9wszUZ9ZsVfPFOjrcJZFIlq9UDMCAvbUasudNoGUtGJE11nggptd2O; P_INFO=17816116670|1548126489|1|cbg|00&99|zhj&1548097749&cbg#zhj&330100#10#0#0|&0|null|17816116670; is_log_active_stat=1; SID=83f6a9d7-7518-4261-a55c-0febfa4d8d02; NTES_YD_SESS=luROm_Ud0oxd_VBmtyXRlKGqMn6BvvXM_F9tNxf8QjjpQkujQqfOaEd9tzWEdU2b_ipdhST55CfiPpxAGW5qe3BP6MQfBf8b9m7Z4oSSgAPwHRlL2C7idExoGTFf1qjVV8fjXseISaX3qwQxbAO8nUX.Ynqb5v2zy7Qe.sF65UemLLUMyNb579pFceHqYXg9f0ADBmrNBkMAFZgd_.BDXt.yY; S_INFO=1548670614|1|0&60##|17816116670; sid=EHkzKDKoUwK2v3Cqefm5RNhDoYSTKibaKWbhOorI;EPAY_ENV=YQ_ROOM; usertrack=ezq0pVri5z4pfeFjCLI+Ag==; _ntes_nnid=aeddc0f0d48428394daf0f32351e34c7,1524819775622; _ntes_nuid=aeddc0f0d48428394daf0f32351e34c7; _ga=GA1.2.252405637.1524819776; vjuids=4eea9f1.16348c6d1d6.0.706e760b26a82; __gads=ID=f47e133227159fe9:T=1529990656:S=ALNI_MaeA6oBLicJqUk6XaZj4vX_9qSniA; __f_=1543801309184; vinfo_n_f_l_n3=61dae1c8a939025a.1.1.1529990679307.1529990869635.1546845444202; vjlast=1525934379.1547101224.12; NTES_YD_PASSPORT=G9LAtGogqb7c6akOJG2RSyOj3F_4oPKI1b_R0fHarBEwMmy6MT5QSOvpDubOvFicG9gvXW0rrP5xDg3Z2jjDNYpHPe7ul_jt1uEX2UIxxG0sPcbesq3lwvAmK4QNlrMB7q3r90U9tJXnS7Hvn1i9Vxa9wszUZ9ZsVfPFOjrcJZFIlq9UDMCAvbUasudNoGUtGJE11nggptd2O; _OkLJ_%UJ=ITWH7FIY7J6WNE5E; WM_TID=qnhz3c6ldndFEVBEBQIskS9f2FXaHEWB; EPAYSESSIONID=EPAY-18674ee5-9d09-4581-a0de-e2f879e14222; riskInfoUuid=41bfa137-29cb-451b-acf1-901c9ec01a60; NTES_YD_SESS=p3IRE5QgDDUZm.cyKliDyFGAPsahtiqcraReCvgUJ0PzCIEACNjlM8xnTDo8x2c1g7hxfJs66Kj7HhtLwo6NravHdPCjvj31n_mRSFJJXLH4q8qx88mx5NZUolSXqZKkvhW33p990sKj83gxNfT1_qdVZookGRn5xV3pDgGK8z6SN5VJ.0f9Wj31UT5GN6_.tH00GgA5XWKFO5vL4DyT1YrhkVR3KVC.h; S_INFO=1548675365|0|3&80##|17816116670; P_INFO=17816116670|1548675365|1|epay|00&99|zhj&1548312026&cbg#zhj&330100#10#0#0|&0|null|17816116670; WM_NI=oRwxUg2%2BCqQlgvhEN7%2B97yhEgRClXhyXUckNplfl7UAjR7k6MPdBw4Qn0%2FgtsUReLEEyBMPxLScMN%2BgE5R%2FVLH1ijnPPicVbwdxhhkSv2wlxOF0REtulQfPE3N78%2F254RDc%3D; WM_NIKE=9ca17ae2e6ffcda170e2e6eed6f73caaad968fe2809cb48aa2d15e869a8f85bc6d8e92a9a5b6798192998dd72af0fea7c3b92aad8eb999d86df2f58d92d468a5aaa8a6cd3de9e7f99aeb4fa58e8d8ddb64bb889b84ea46b4b4aa8db65f83b69ed8ea3e8f90aca3f64093b200d0e680b18b9e86cf4f87a78eaed374908dadaecd21a59bfbb3ae5e9098ff8ae9669490a784d56ab5bcb889eb6191aafe98ef748d9a00b3c880b7b49f87d34ff192f9d9ae3af7ec9ea5e237e2a3; EPAY_ENV=YQ_ROOM; _gat=1; JSESSIONID=95D24A1BDFC2F09B96CA98023BD3F7DB; FRMS_FINGERPRINT=1311652e7a11d3f7ccf61201bf934a1ecc1b35971401b326b5062b2f0e691341e4da3b7fbbce23451371dfa004ad8ed498721391000000000000000012416a5889bb0190d0211291502f1bd97b0e7d801281d3d9446802a44259009139fa36c62c3b2354130187374a5ff147c45612511ff1de774005f8da0101b3a5d5fc2cac9d4e12216a5889bb0190d021136100000000000000001331e4da3b7fbbce23452b01537ff3902960649f1381b326b5062b2f0e69142100000000000000001231d4c2e4a3297fe25a12610000000000000000141100000000000000001351a87ff679a2f3e71d132168934a3e9455fa7212113a835d3215755c431271d3d9446802a44259");
//            httpPost.addHeader("Host", "my.cbg.163.com");
            httpPost.addHeader("Origin", "https://my.cbg.163.com");
//            httpPost.addHeader("Pragma", "no-cache");
            httpPost.addHeader("Referer", "https://my.cbg.163.com/cgi/mweb/order/confirm/47/201901191801716-47-HAPQOEKMSUAAA?view_loc=all_list");
            httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
            httpPost.addHeader("X-Requested-With", "XMLHttpRequest");

            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset == null ? "UTF-8" : encodeCharset));

//            DateUtil.currentDate("调用之前---"  + "---" +  reqURL);
            HttpResponse response = httpClient.execute(httpPost);
//            DateUtil.currentDate("调用之后---" + "---" + reqURL);
//            logger.info(params.get("proposal"));
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("与[" + reqURL + "]通信过程中发生异常,堆栈信息为" + e);
//            logger.debug("与[" + reqURL + "]通信过程中发生异常,堆栈信息为", e);
        }
        return responseContent;
    }


    /**
     * 发送HTTPS_POST请求
     *  该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
     * @param reqURL        请求地址
     * @param params        请求参数
     * @param encodeCharset 编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
     * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
     * @return 远程主机响应正文
     */
    public static String sendPostSSLRequestNoConsume(String reqURL, Map<String, String> params,String cbgSafeCode,
                                            String cookie,HttpClient httpClient) {
        logger.info(DateUtil.currentDateR() + reqURL);
        String responseContent = "";
        HttpPost httpPost = null;
        try {
            httpPost = new HttpPost(reqURL);
//            HttpHost proxy = new HttpHost("118.89.138.129", 59460,"https");
//            RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
//            httpPost.setConfig(config);
            httpPost.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
//            httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
//            httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.9");
//            httpPost.addHeader("Cache-Control", "no-cache");
            httpPost.addHeader("cbg-safe-code", cbgSafeCode==null?"yxqNirTr":cbgSafeCode);
            httpPost.addHeader("Connection", "keep-alive");
//            httpPost.addHeader("Content-Length", "70");
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.addHeader("Cookie", cookie!=null?cookie:"usertrack=ezq0pVri5z4pfeFjCLI+Ag==; _ntes_nnid=aeddc0f0d48428394daf0f32351e34c7,1524819775622; _ntes_nuid=aeddc0f0d48428394daf0f32351e34c7; _ga=GA1.2.252405637.1524819776; vjuids=4eea9f1.16348c6d1d6.0.706e760b26a82; fingerprint=r7azf7pnrt04h6k5; __gads=ID=f47e133227159fe9:T=1529990656:S=ALNI_MaeA6oBLicJqUk6XaZj4vX_9qSniA; __f_=1543801309184; vinfo_n_f_l_n3=61dae1c8a939025a.1.1.1529990679307.1529990869635.1546845444202; vjlast=1525934379.1547101224.12; NTES_YD_PASSPORT=G9LAtGogqb7c6akOJG2RSyOj3F_4oPKI1b_R0fHarBEwMmy6MT5QSOvpDubOvFicG9gvXW0rrP5xDg3Z2jjDNYpHPe7ul_jt1uEX2UIxxG0sPcbesq3lwvAmK4QNlrMB7q3r90U9tJXnS7Hvn1i9Vxa9wszUZ9ZsVfPFOjrcJZFIlq9UDMCAvbUasudNoGUtGJE11nggptd2O; P_INFO=17816116670|1548675365|1|epay|00&99|zhj&1548312026&cbg#zhj&330100#10#0#0|&0|null|17816116670; is_log_active_stat=1; SID=e56b18ea-e9c7-4fab-9809-d22a0a05c59a; NTES_YD_SESS=0IraIyBBmMVQ701uSTxhCO0xm_JM27RwQbYg22jLfl9wEox_E6mDytw2rLatwCKS.Hiw7RBYY8mHliegIaY6hsjlGOEmjmTS21bNZMRR9gl05t5wttbwn6qQaDZ95q8WjAMnAsOyR3f1_6b7v_PmN6dIz9WYnyUSXQwQIT3XBcb9_lNxdV52vfdqc5PX9dJdx3dDnGUtyZq3B.tPp9estw8zk; S_INFO=1548724500|1|0&60##|17816116670; sid=Xs96AOXM2bFuwQI4rO7KXGiiFpqm-x3mi2QKBpvA");
//            httpPost.addHeader("Cookie", "EPAY_ENV=YQ_ROOM; usertrack=ezq0pVri5z4pfeFjCLI+Ag==; _ntes_nnid=aeddc0f0d48428394daf0f32351e34c7,1524819775622; _ntes_nuid=aeddc0f0d48428394daf0f32351e34c7; _ga=GA1.2.252405637.1524819776; vjuids=4eea9f1.16348c6d1d6.0.706e760b26a82; __gads=ID=f47e133227159fe9:T=1529990656:S=ALNI_MaeA6oBLicJqUk6XaZj4vX_9qSniA; __f_=1543801309184; vinfo_n_f_l_n3=61dae1c8a939025a.1.1.1529990679307.1529990869635.1546845444202; vjlast=1525934379.1547101224.12; NTES_YD_PASSPORT=G9LAtGogqb7c6akOJG2RSyOj3F_4oPKI1b_R0fHarBEwMmy6MT5QSOvpDubOvFicG9gvXW0rrP5xDg3Z2jjDNYpHPe7ul_jt1uEX2UIxxG0sPcbesq3lwvAmK4QNlrMB7q3r90U9tJXnS7Hvn1i9Vxa9wszUZ9ZsVfPFOjrcJZFIlq9UDMCAvbUasudNoGUtGJE11nggptd2O; _OkLJ_%UJ=ITWH7FIY7J6WNE5E; WM_TID=qnhz3c6ldndFEVBEBQIskS9f2FXaHEWB; EPAYSESSIONID=EPAY-18674ee5-9d09-4581-a0de-e2f879e14222; riskInfoUuid=41bfa137-29cb-451b-acf1-901c9ec01a60; NTES_YD_SESS=p3IRE5QgDDUZm.cyKliDyFGAPsahtiqcraReCvgUJ0PzCIEACNjlM8xnTDo8x2c1g7hxfJs66Kj7HhtLwo6NravHdPCjvj31n_mRSFJJXLH4q8qx88mx5NZUolSXqZKkvhW33p990sKj83gxNfT1_qdVZookGRn5xV3pDgGK8z6SN5VJ.0f9Wj31UT5GN6_.tH00GgA5XWKFO5vL4DyT1YrhkVR3KVC.h; S_INFO=1548675365|0|3&80##|17816116670; P_INFO=17816116670|1548675365|1|epay|00&99|zhj&1548312026&cbg#zhj&330100#10#0#0|&0|null|17816116670; WM_NI=oRwxUg2%2BCqQlgvhEN7%2B97yhEgRClXhyXUckNplfl7UAjR7k6MPdBw4Qn0%2FgtsUReLEEyBMPxLScMN%2BgE5R%2FVLH1ijnPPicVbwdxhhkSv2wlxOF0REtulQfPE3N78%2F254RDc%3D; WM_NIKE=9ca17ae2e6ffcda170e2e6eed6f73caaad968fe2809cb48aa2d15e869a8f85bc6d8e92a9a5b6798192998dd72af0fea7c3b92aad8eb999d86df2f58d92d468a5aaa8a6cd3de9e7f99aeb4fa58e8d8ddb64bb889b84ea46b4b4aa8db65f83b69ed8ea3e8f90aca3f64093b200d0e680b18b9e86cf4f87a78eaed374908dadaecd21a59bfbb3ae5e9098ff8ae9669490a784d56ab5bcb889eb6191aafe98ef748d9a00b3c880b7b49f87d34ff192f9d9ae3af7ec9ea5e237e2a3; EPAY_ENV=YQ_ROOM; _gat=1; JSESSIONID=95D24A1BDFC2F09B96CA98023BD3F7DB; FRMS_FINGERPRINT=1311652e7a11d3f7ccf61201bf934a1ecc1b35971401b326b5062b2f0e691341e4da3b7fbbce23451371dfa004ad8ed498721391000000000000000012416a5889bb0190d0211291502f1bd97b0e7d801281d3d9446802a44259009139fa36c62c3b2354130187374a5ff147c45612511ff1de774005f8da0101b3a5d5fc2cac9d4e12216a5889bb0190d021136100000000000000001331e4da3b7fbbce23452b01537ff3902960649f1381b326b5062b2f0e69142100000000000000001231d4c2e4a3297fe25a12610000000000000000141100000000000000001351a87ff679a2f3e71d132168934a3e9455fa7212113a835d3215755c431271d3d9446802a44259");
//            httpPost.addHeader("Cookie", "usertrack=ezq0pVri5z4pfeFjCLI+Ag==; _ntes_nnid=aeddc0f0d48428394daf0f32351e34c7,1524819775622; _ntes_nuid=aeddc0f0d48428394daf0f32351e34c7; _ga=GA1.2.252405637.1524819776; vjuids=4eea9f1.16348c6d1d6.0.706e760b26a82; fingerprint=r7azf7pnrt04h6k5; __gads=ID=f47e133227159fe9:T=1529990656:S=ALNI_MaeA6oBLicJqUk6XaZj4vX_9qSniA; __f_=1543801309184; vinfo_n_f_l_n3=61dae1c8a939025a.1.1.1529990679307.1529990869635.1546845444202; vjlast=1525934379.1547101224.12; NTES_YD_PASSPORT=G9LAtGogqb7c6akOJG2RSyOj3F_4oPKI1b_R0fHarBEwMmy6MT5QSOvpDubOvFicG9gvXW0rrP5xDg3Z2jjDNYpHPe7ul_jt1uEX2UIxxG0sPcbesq3lwvAmK4QNlrMB7q3r90U9tJXnS7Hvn1i9Vxa9wszUZ9ZsVfPFOjrcJZFIlq9UDMCAvbUasudNoGUtGJE11nggptd2O; P_INFO=17816116670|1548126489|1|cbg|00&99|zhj&1548097749&cbg#zhj&330100#10#0#0|&0|null|17816116670; is_log_active_stat=1; SID=83f6a9d7-7518-4261-a55c-0febfa4d8d02; NTES_YD_SESS=luROm_Ud0oxd_VBmtyXRlKGqMn6BvvXM_F9tNxf8QjjpQkujQqfOaEd9tzWEdU2b_ipdhST55CfiPpxAGW5qe3BP6MQfBf8b9m7Z4oSSgAPwHRlL2C7idExoGTFf1qjVV8fjXseISaX3qwQxbAO8nUX.Ynqb5v2zy7Qe.sF65UemLLUMyNb579pFceHqYXg9f0ADBmrNBkMAFZgd_.BDXt.yY; S_INFO=1548670614|1|0&60##|17816116670; sid=EHkzKDKoUwK2v3Cqefm5RNhDoYSTKibaKWbhOorI;EPAY_ENV=YQ_ROOM; usertrack=ezq0pVri5z4pfeFjCLI+Ag==; _ntes_nnid=aeddc0f0d48428394daf0f32351e34c7,1524819775622; _ntes_nuid=aeddc0f0d48428394daf0f32351e34c7; _ga=GA1.2.252405637.1524819776; vjuids=4eea9f1.16348c6d1d6.0.706e760b26a82; __gads=ID=f47e133227159fe9:T=1529990656:S=ALNI_MaeA6oBLicJqUk6XaZj4vX_9qSniA; __f_=1543801309184; vinfo_n_f_l_n3=61dae1c8a939025a.1.1.1529990679307.1529990869635.1546845444202; vjlast=1525934379.1547101224.12; NTES_YD_PASSPORT=G9LAtGogqb7c6akOJG2RSyOj3F_4oPKI1b_R0fHarBEwMmy6MT5QSOvpDubOvFicG9gvXW0rrP5xDg3Z2jjDNYpHPe7ul_jt1uEX2UIxxG0sPcbesq3lwvAmK4QNlrMB7q3r90U9tJXnS7Hvn1i9Vxa9wszUZ9ZsVfPFOjrcJZFIlq9UDMCAvbUasudNoGUtGJE11nggptd2O; _OkLJ_%UJ=ITWH7FIY7J6WNE5E; WM_TID=qnhz3c6ldndFEVBEBQIskS9f2FXaHEWB; EPAYSESSIONID=EPAY-18674ee5-9d09-4581-a0de-e2f879e14222; riskInfoUuid=41bfa137-29cb-451b-acf1-901c9ec01a60; NTES_YD_SESS=p3IRE5QgDDUZm.cyKliDyFGAPsahtiqcraReCvgUJ0PzCIEACNjlM8xnTDo8x2c1g7hxfJs66Kj7HhtLwo6NravHdPCjvj31n_mRSFJJXLH4q8qx88mx5NZUolSXqZKkvhW33p990sKj83gxNfT1_qdVZookGRn5xV3pDgGK8z6SN5VJ.0f9Wj31UT5GN6_.tH00GgA5XWKFO5vL4DyT1YrhkVR3KVC.h; S_INFO=1548675365|0|3&80##|17816116670; P_INFO=17816116670|1548675365|1|epay|00&99|zhj&1548312026&cbg#zhj&330100#10#0#0|&0|null|17816116670; WM_NI=oRwxUg2%2BCqQlgvhEN7%2B97yhEgRClXhyXUckNplfl7UAjR7k6MPdBw4Qn0%2FgtsUReLEEyBMPxLScMN%2BgE5R%2FVLH1ijnPPicVbwdxhhkSv2wlxOF0REtulQfPE3N78%2F254RDc%3D; WM_NIKE=9ca17ae2e6ffcda170e2e6eed6f73caaad968fe2809cb48aa2d15e869a8f85bc6d8e92a9a5b6798192998dd72af0fea7c3b92aad8eb999d86df2f58d92d468a5aaa8a6cd3de9e7f99aeb4fa58e8d8ddb64bb889b84ea46b4b4aa8db65f83b69ed8ea3e8f90aca3f64093b200d0e680b18b9e86cf4f87a78eaed374908dadaecd21a59bfbb3ae5e9098ff8ae9669490a784d56ab5bcb889eb6191aafe98ef748d9a00b3c880b7b49f87d34ff192f9d9ae3af7ec9ea5e237e2a3; EPAY_ENV=YQ_ROOM; _gat=1; JSESSIONID=95D24A1BDFC2F09B96CA98023BD3F7DB; FRMS_FINGERPRINT=1311652e7a11d3f7ccf61201bf934a1ecc1b35971401b326b5062b2f0e691341e4da3b7fbbce23451371dfa004ad8ed498721391000000000000000012416a5889bb0190d0211291502f1bd97b0e7d801281d3d9446802a44259009139fa36c62c3b2354130187374a5ff147c45612511ff1de774005f8da0101b3a5d5fc2cac9d4e12216a5889bb0190d021136100000000000000001331e4da3b7fbbce23452b01537ff3902960649f1381b326b5062b2f0e69142100000000000000001231d4c2e4a3297fe25a12610000000000000000141100000000000000001351a87ff679a2f3e71d132168934a3e9455fa7212113a835d3215755c431271d3d9446802a44259");
//            httpPost.addHeader("Host", "my.cbg.163.com");
            httpPost.addHeader("Origin", "https://my.cbg.163.com");
//            httpPost.addHeader("Pragma", "no-cache");
            httpPost.addHeader("Referer", "https://my.cbg.163.com/cgi/mweb/order/confirm/47/201901191801716-47-HAPQOEKMSUAAA?view_loc=all_list");
            httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
            httpPost.addHeader("X-Requested-With", "XMLHttpRequest");

            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(formParams,"UTF-8"));

//            DateUtil.currentDate("调用之前---"  + "---" +  reqURL);
            HttpResponse response = httpClient.execute(httpPost);
//            DateUtil.currentDate("调用之后---" + "---" + reqURL);
//            logger.info(params.get("proposal"));
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, "UTF-8");
//                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("与[" + reqURL + "]通信过程中发生异常,堆栈信息为" + e);
//            logger.debug("与[" + reqURL + "]通信过程中发生异常,堆栈信息为", e);
        }
        return responseContent;
    }

    /**
     * 发送https请求返回response对象
     * @param reqURL
     * @param params
     * @param cbgSafeCode
     * @param cookie
     * @param encodeCharset
     * @param decodeCharset
     * @return
     */
    public static HttpResponse sendPostSSLRequestGetResponse(String reqURL, Map<String, String> params,String cbgSafeCode,
                                                             String cookie, String encodeCharset, String decodeCharset,HttpClient httpClient) {
        DateUtil.currentDate();
        HttpPost httpPost = null;
        HttpResponse response = null;
        try {
            httpPost = new HttpPost(reqURL);
//            HttpHost proxy = new HttpHost("118.89.138.129", 59460,"https");
//            RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
//            httpPost.setConfig(config);
            httpPost.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
//            httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
//            httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.9");
//            httpPost.addHeader("Cache-Control", "no-cache");
            httpPost.addHeader("cbg-safe-code", cbgSafeCode==null?"yxqNirTr":cbgSafeCode);
            httpPost.addHeader("Connection", "keep-alive");
//            httpPost.addHeader("Content-Length", "70");
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.addHeader("Cookie", cookie!=null?cookie:"usertrack=ezq0pVri5z4pfeFjCLI+Ag==; _ntes_nnid=aeddc0f0d48428394daf0f32351e34c7,1524819775622; _ntes_nuid=aeddc0f0d48428394daf0f32351e34c7; _ga=GA1.2.252405637.1524819776; vjuids=4eea9f1.16348c6d1d6.0.706e760b26a82; fingerprint=r7azf7pnrt04h6k5; __gads=ID=f47e133227159fe9:T=1529990656:S=ALNI_MaeA6oBLicJqUk6XaZj4vX_9qSniA; __f_=1543801309184; vinfo_n_f_l_n3=61dae1c8a939025a.1.1.1529990679307.1529990869635.1546845444202; vjlast=1525934379.1547101224.12; NTES_YD_PASSPORT=G9LAtGogqb7c6akOJG2RSyOj3F_4oPKI1b_R0fHarBEwMmy6MT5QSOvpDubOvFicG9gvXW0rrP5xDg3Z2jjDNYpHPe7ul_jt1uEX2UIxxG0sPcbesq3lwvAmK4QNlrMB7q3r90U9tJXnS7Hvn1i9Vxa9wszUZ9ZsVfPFOjrcJZFIlq9UDMCAvbUasudNoGUtGJE11nggptd2O; P_INFO=17816116670|1548675365|1|epay|00&99|zhj&1548312026&cbg#zhj&330100#10#0#0|&0|null|17816116670; is_log_active_stat=1; SID=e56b18ea-e9c7-4fab-9809-d22a0a05c59a; NTES_YD_SESS=0IraIyBBmMVQ701uSTxhCO0xm_JM27RwQbYg22jLfl9wEox_E6mDytw2rLatwCKS.Hiw7RBYY8mHliegIaY6hsjlGOEmjmTS21bNZMRR9gl05t5wttbwn6qQaDZ95q8WjAMnAsOyR3f1_6b7v_PmN6dIz9WYnyUSXQwQIT3XBcb9_lNxdV52vfdqc5PX9dJdx3dDnGUtyZq3B.tPp9estw8zk; S_INFO=1548724500|1|0&60##|17816116670; sid=Xs96AOXM2bFuwQI4rO7KXGiiFpqm-x3mi2QKBpvA");
//            httpPost.addHeader("Cookie", "EPAY_ENV=YQ_ROOM; usertrack=ezq0pVri5z4pfeFjCLI+Ag==; _ntes_nnid=aeddc0f0d48428394daf0f32351e34c7,1524819775622; _ntes_nuid=aeddc0f0d48428394daf0f32351e34c7; _ga=GA1.2.252405637.1524819776; vjuids=4eea9f1.16348c6d1d6.0.706e760b26a82; __gads=ID=f47e133227159fe9:T=1529990656:S=ALNI_MaeA6oBLicJqUk6XaZj4vX_9qSniA; __f_=1543801309184; vinfo_n_f_l_n3=61dae1c8a939025a.1.1.1529990679307.1529990869635.1546845444202; vjlast=1525934379.1547101224.12; NTES_YD_PASSPORT=G9LAtGogqb7c6akOJG2RSyOj3F_4oPKI1b_R0fHarBEwMmy6MT5QSOvpDubOvFicG9gvXW0rrP5xDg3Z2jjDNYpHPe7ul_jt1uEX2UIxxG0sPcbesq3lwvAmK4QNlrMB7q3r90U9tJXnS7Hvn1i9Vxa9wszUZ9ZsVfPFOjrcJZFIlq9UDMCAvbUasudNoGUtGJE11nggptd2O; _OkLJ_%UJ=ITWH7FIY7J6WNE5E; WM_TID=qnhz3c6ldndFEVBEBQIskS9f2FXaHEWB; EPAYSESSIONID=EPAY-18674ee5-9d09-4581-a0de-e2f879e14222; riskInfoUuid=41bfa137-29cb-451b-acf1-901c9ec01a60; NTES_YD_SESS=p3IRE5QgDDUZm.cyKliDyFGAPsahtiqcraReCvgUJ0PzCIEACNjlM8xnTDo8x2c1g7hxfJs66Kj7HhtLwo6NravHdPCjvj31n_mRSFJJXLH4q8qx88mx5NZUolSXqZKkvhW33p990sKj83gxNfT1_qdVZookGRn5xV3pDgGK8z6SN5VJ.0f9Wj31UT5GN6_.tH00GgA5XWKFO5vL4DyT1YrhkVR3KVC.h; S_INFO=1548675365|0|3&80##|17816116670; P_INFO=17816116670|1548675365|1|epay|00&99|zhj&1548312026&cbg#zhj&330100#10#0#0|&0|null|17816116670; WM_NI=oRwxUg2%2BCqQlgvhEN7%2B97yhEgRClXhyXUckNplfl7UAjR7k6MPdBw4Qn0%2FgtsUReLEEyBMPxLScMN%2BgE5R%2FVLH1ijnPPicVbwdxhhkSv2wlxOF0REtulQfPE3N78%2F254RDc%3D; WM_NIKE=9ca17ae2e6ffcda170e2e6eed6f73caaad968fe2809cb48aa2d15e869a8f85bc6d8e92a9a5b6798192998dd72af0fea7c3b92aad8eb999d86df2f58d92d468a5aaa8a6cd3de9e7f99aeb4fa58e8d8ddb64bb889b84ea46b4b4aa8db65f83b69ed8ea3e8f90aca3f64093b200d0e680b18b9e86cf4f87a78eaed374908dadaecd21a59bfbb3ae5e9098ff8ae9669490a784d56ab5bcb889eb6191aafe98ef748d9a00b3c880b7b49f87d34ff192f9d9ae3af7ec9ea5e237e2a3; EPAY_ENV=YQ_ROOM; _gat=1; JSESSIONID=95D24A1BDFC2F09B96CA98023BD3F7DB; FRMS_FINGERPRINT=1311652e7a11d3f7ccf61201bf934a1ecc1b35971401b326b5062b2f0e691341e4da3b7fbbce23451371dfa004ad8ed498721391000000000000000012416a5889bb0190d0211291502f1bd97b0e7d801281d3d9446802a44259009139fa36c62c3b2354130187374a5ff147c45612511ff1de774005f8da0101b3a5d5fc2cac9d4e12216a5889bb0190d021136100000000000000001331e4da3b7fbbce23452b01537ff3902960649f1381b326b5062b2f0e69142100000000000000001231d4c2e4a3297fe25a12610000000000000000141100000000000000001351a87ff679a2f3e71d132168934a3e9455fa7212113a835d3215755c431271d3d9446802a44259");
//            httpPost.addHeader("Cookie", "usertrack=ezq0pVri5z4pfeFjCLI+Ag==; _ntes_nnid=aeddc0f0d48428394daf0f32351e34c7,1524819775622; _ntes_nuid=aeddc0f0d48428394daf0f32351e34c7; _ga=GA1.2.252405637.1524819776; vjuids=4eea9f1.16348c6d1d6.0.706e760b26a82; fingerprint=r7azf7pnrt04h6k5; __gads=ID=f47e133227159fe9:T=1529990656:S=ALNI_MaeA6oBLicJqUk6XaZj4vX_9qSniA; __f_=1543801309184; vinfo_n_f_l_n3=61dae1c8a939025a.1.1.1529990679307.1529990869635.1546845444202; vjlast=1525934379.1547101224.12; NTES_YD_PASSPORT=G9LAtGogqb7c6akOJG2RSyOj3F_4oPKI1b_R0fHarBEwMmy6MT5QSOvpDubOvFicG9gvXW0rrP5xDg3Z2jjDNYpHPe7ul_jt1uEX2UIxxG0sPcbesq3lwvAmK4QNlrMB7q3r90U9tJXnS7Hvn1i9Vxa9wszUZ9ZsVfPFOjrcJZFIlq9UDMCAvbUasudNoGUtGJE11nggptd2O; P_INFO=17816116670|1548126489|1|cbg|00&99|zhj&1548097749&cbg#zhj&330100#10#0#0|&0|null|17816116670; is_log_active_stat=1; SID=83f6a9d7-7518-4261-a55c-0febfa4d8d02; NTES_YD_SESS=luROm_Ud0oxd_VBmtyXRlKGqMn6BvvXM_F9tNxf8QjjpQkujQqfOaEd9tzWEdU2b_ipdhST55CfiPpxAGW5qe3BP6MQfBf8b9m7Z4oSSgAPwHRlL2C7idExoGTFf1qjVV8fjXseISaX3qwQxbAO8nUX.Ynqb5v2zy7Qe.sF65UemLLUMyNb579pFceHqYXg9f0ADBmrNBkMAFZgd_.BDXt.yY; S_INFO=1548670614|1|0&60##|17816116670; sid=EHkzKDKoUwK2v3Cqefm5RNhDoYSTKibaKWbhOorI;EPAY_ENV=YQ_ROOM; usertrack=ezq0pVri5z4pfeFjCLI+Ag==; _ntes_nnid=aeddc0f0d48428394daf0f32351e34c7,1524819775622; _ntes_nuid=aeddc0f0d48428394daf0f32351e34c7; _ga=GA1.2.252405637.1524819776; vjuids=4eea9f1.16348c6d1d6.0.706e760b26a82; __gads=ID=f47e133227159fe9:T=1529990656:S=ALNI_MaeA6oBLicJqUk6XaZj4vX_9qSniA; __f_=1543801309184; vinfo_n_f_l_n3=61dae1c8a939025a.1.1.1529990679307.1529990869635.1546845444202; vjlast=1525934379.1547101224.12; NTES_YD_PASSPORT=G9LAtGogqb7c6akOJG2RSyOj3F_4oPKI1b_R0fHarBEwMmy6MT5QSOvpDubOvFicG9gvXW0rrP5xDg3Z2jjDNYpHPe7ul_jt1uEX2UIxxG0sPcbesq3lwvAmK4QNlrMB7q3r90U9tJXnS7Hvn1i9Vxa9wszUZ9ZsVfPFOjrcJZFIlq9UDMCAvbUasudNoGUtGJE11nggptd2O; _OkLJ_%UJ=ITWH7FIY7J6WNE5E; WM_TID=qnhz3c6ldndFEVBEBQIskS9f2FXaHEWB; EPAYSESSIONID=EPAY-18674ee5-9d09-4581-a0de-e2f879e14222; riskInfoUuid=41bfa137-29cb-451b-acf1-901c9ec01a60; NTES_YD_SESS=p3IRE5QgDDUZm.cyKliDyFGAPsahtiqcraReCvgUJ0PzCIEACNjlM8xnTDo8x2c1g7hxfJs66Kj7HhtLwo6NravHdPCjvj31n_mRSFJJXLH4q8qx88mx5NZUolSXqZKkvhW33p990sKj83gxNfT1_qdVZookGRn5xV3pDgGK8z6SN5VJ.0f9Wj31UT5GN6_.tH00GgA5XWKFO5vL4DyT1YrhkVR3KVC.h; S_INFO=1548675365|0|3&80##|17816116670; P_INFO=17816116670|1548675365|1|epay|00&99|zhj&1548312026&cbg#zhj&330100#10#0#0|&0|null|17816116670; WM_NI=oRwxUg2%2BCqQlgvhEN7%2B97yhEgRClXhyXUckNplfl7UAjR7k6MPdBw4Qn0%2FgtsUReLEEyBMPxLScMN%2BgE5R%2FVLH1ijnPPicVbwdxhhkSv2wlxOF0REtulQfPE3N78%2F254RDc%3D; WM_NIKE=9ca17ae2e6ffcda170e2e6eed6f73caaad968fe2809cb48aa2d15e869a8f85bc6d8e92a9a5b6798192998dd72af0fea7c3b92aad8eb999d86df2f58d92d468a5aaa8a6cd3de9e7f99aeb4fa58e8d8ddb64bb889b84ea46b4b4aa8db65f83b69ed8ea3e8f90aca3f64093b200d0e680b18b9e86cf4f87a78eaed374908dadaecd21a59bfbb3ae5e9098ff8ae9669490a784d56ab5bcb889eb6191aafe98ef748d9a00b3c880b7b49f87d34ff192f9d9ae3af7ec9ea5e237e2a3; EPAY_ENV=YQ_ROOM; _gat=1; JSESSIONID=95D24A1BDFC2F09B96CA98023BD3F7DB; FRMS_FINGERPRINT=1311652e7a11d3f7ccf61201bf934a1ecc1b35971401b326b5062b2f0e691341e4da3b7fbbce23451371dfa004ad8ed498721391000000000000000012416a5889bb0190d0211291502f1bd97b0e7d801281d3d9446802a44259009139fa36c62c3b2354130187374a5ff147c45612511ff1de774005f8da0101b3a5d5fc2cac9d4e12216a5889bb0190d021136100000000000000001331e4da3b7fbbce23452b01537ff3902960649f1381b326b5062b2f0e69142100000000000000001231d4c2e4a3297fe25a12610000000000000000141100000000000000001351a87ff679a2f3e71d132168934a3e9455fa7212113a835d3215755c431271d3d9446802a44259");
//            httpPost.addHeader("Host", "my.cbg.163.com");
            httpPost.addHeader("Origin", "https://my.cbg.163.com");
//            httpPost.addHeader("Pragma", "no-cache");
            httpPost.addHeader("Referer", "https://my.cbg.163.com/cgi/mweb/order/confirm/47/201901191801716-47-HAPQOEKMSUAAA?view_loc=all_list");
            httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
            httpPost.addHeader("X-Requested-With", "XMLHttpRequest");

            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset == null ? "UTF-8" : encodeCharset));

//            DateUtil.currentDate("调用之前-------------" + DateUtil.currentDateR() + "  " + reqURL  );
            response = httpClient.execute(httpPost);
//            DateUtil.currentDate("调用之后-------------"+ DateUtil.currentDateR() + "  " + reqURL );

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("与[" + reqURL + "]通信过程中发生异常,堆栈信息为" + e);
//            logger.debug("与[" + reqURL + "]通信过程中发生异常,堆栈信息为", e);
        }
        return response;
    }

    /**
     * 发送HTTPS_POST请求
     *  该方法会自动关闭连接,释放资源
     *  该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
     * @param reqURL        请求地址
     * @param params        请求参数
     * @param encodeCharset 编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
     * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
     * @return 远程主机响应正文
     */
    public static String sendWapPostSSLRequest(String reqURL, String payNo, Map<String, String> params,
                                            String cookie, String encodeCharset, String decodeCharset,HttpClient httpClient) {
        String v = Long.toString(System.currentTimeMillis());
        reqURL = reqURL + "?v=" + v;
        logger.info(DateUtil.currentDateR() + reqURL);
        String responseContent = "";
        HttpPost httpPost = null;
        try {
            httpPost = new HttpPost(reqURL);
            httpPost.addHeader(":authority", "epay.163.com");
            httpPost.addHeader(":method", "POST");
            httpPost.addHeader(":path", "/cashier/m/security/verifyPayItems?v=" + v);
            httpPost.addHeader(":scheme", "https");
            httpPost.addHeader("accept", "*/*");
            httpPost.addHeader("accept-encoding", "gzip, deflate, br");
            httpPost.addHeader("accept-language", "zh-CN,zh;q=0.9,en;q=0.8");
//            httpPost.addHeader("content-length", "149");
            httpPost.addHeader("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.addHeader("cookie", cookie);
            httpPost.addHeader("origin", "https://epay.163.com");
            httpPost.addHeader("referer", "https://epay.163.com/cashier/m/standardCashier?orderId=" + payNo);
            httpPost.addHeader("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
            httpPost.addHeader("x-requested-with", "XMLHttpRequest");
            httpPost.addHeader("Connection", "keep-alive");

            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                logger.info(entry.getValue());
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset == null ? "UTF-8" : encodeCharset));

//            DateUtil.currentDate("调用之前---"  + "---" +  reqURL);
            HttpResponse response = httpClient.execute(httpPost);
//            DateUtil.currentDate("调用之后---"  + "---" + reqURL);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("与[" + reqURL + "]通信过程中发生异常,堆栈信息为" + e);
//            logger.debug("与[" + reqURL + "]通信过程中发生异常,堆栈信息为", e);
        }
        return responseContent;
    }

    /**
     * 发送HTTPS_POST请求
     *  该方法会自动关闭连接,释放资源
     *  该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
     * @param reqURL        请求地址
     * @param params        请求参数
     * @param encodeCharset 编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
     * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
     * @return 远程主机响应正文
     */
    public static String sendPayPostSSLRequest(String reqURL, String payNo, Map<String, String> params,
                                               String cookie, String encodeCharset, String decodeCharset,HttpClient httpClient) {
        String responseContent = "";
        HttpPost httpPost = null;
        try {
            httpPost = new HttpPost(reqURL);
            httpPost.addHeader(":authority", "epay.163.com");
            httpPost.addHeader(":method", "POST");
            httpPost.addHeader(":path", "/cashier/ajaxPay");
            httpPost.addHeader(":scheme", "https");
            httpPost.addHeader("accept", "*/*");
            httpPost.addHeader("accept-encoding", "gzip, deflate, br");
            httpPost.addHeader("accept-language", "zh-CN,zh;q=0.9,en;q=0.8");
//            httpPost.addHeader("content-length", "149");
            httpPost.addHeader("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.addHeader("cookie", cookie);
            httpPost.addHeader("origin", "https://epay.163.com");
            httpPost.addHeader("referer", "https://epay.163.com/cashier/standardCashier?orderId=" + payNo);
            httpPost.addHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
            httpPost.addHeader("x-requested-with", "XMLHttpRequest");
            httpPost.addHeader("Connection", "keep-alive");

            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                logger.info(entry.getValue());
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset == null ? "UTF-8" : encodeCharset));

            DateUtil.currentDate("调用之前---"  + "---" +  reqURL);
            HttpResponse response = httpClient.execute(httpPost);
            DateUtil.currentDate("调用之后---"  + "---" + reqURL);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("与[" + reqURL + "]通信过程中发生异常,堆栈信息为" + e);
//            logger.debug("与[" + reqURL + "]通信过程中发生异常,堆栈信息为", e);
        }
        return responseContent;
    }

    /**
     * 发送HTTPS_POST请求
     *  该方法会自动关闭连接,释放资源
     *  该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
     * @param reqURL        请求地址
     * @param params        请求参数
     * @param encodeCharset 编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
     * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
     * @return 远程主机响应正文
     */
    public static String sendPayPostSSLRequestNoConsume(String reqURL, String payNo, Map<String, String> params,
                                               String cookie, String encodeCharset, String decodeCharset,HttpClient httpClient) {
        String responseContent = "";
        HttpPost httpPost = null;
        try {
            httpPost = new HttpPost(reqURL);
            httpPost.addHeader(":authority", "epay.163.com");
            httpPost.addHeader(":method", "POST");
            httpPost.addHeader(":path", "/cashier/ajaxPay");
            httpPost.addHeader(":scheme", "https");
            httpPost.addHeader("accept", "*/*");
            httpPost.addHeader("accept-encoding", "gzip, deflate, br");
            httpPost.addHeader("accept-language", "zh-CN,zh;q=0.9,en;q=0.8");
//            httpPost.addHeader("content-length", "149");
            httpPost.addHeader("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.addHeader("cookie", cookie);
            httpPost.addHeader("origin", "https://epay.163.com");
            httpPost.addHeader("referer", "https://epay.163.com/cashier/standardCashier?orderId=" + payNo);
            httpPost.addHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
            httpPost.addHeader("x-requested-with", "XMLHttpRequest");
            httpPost.addHeader("Connection", "keep-alive");

            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                logger.info(entry.getValue());
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset == null ? "UTF-8" : encodeCharset));

            DateUtil.currentDate("调用之前---"  + "---" +  reqURL);
            HttpResponse response = httpClient.execute(httpPost);
            DateUtil.currentDate("调用之后---"  + "---" + reqURL);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
//                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("与[" + reqURL + "]通信过程中发生异常,堆栈信息为" + e);
        }
        return responseContent;
    }


    /**
     * 发送HTTPS_POST请求
     *  该方法会自动关闭连接,释放资源
     *  该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
     * @param reqURL        请求地址
     * @param params        请求参数
     * @return 远程主机响应正文
     */
    public static String sendPost(String reqURL, Map<String, String> params) {
        logger.info(DateUtil.currentDateR() + reqURL);
        String responseContent = "";
        HttpPost httpPost = null;
        HttpClient httpClient = null;
        try {
            httpClient = new SSLClient();
            httpPost = new HttpPost(reqURL);
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            if(null!=params){
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8" ));

            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, "UTF-8");
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("与[" + reqURL + "]通信过程中发生异常,堆栈信息为" + e);
        }
        return responseContent;
    }

    /**
     * 发送HTTPS_POST请求
     *  该方法会自动关闭连接,释放资源
     *  该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
     * @param reqURL        请求地址
     * @param params        请求参数
     * @return 远程主机响应正文
     */
    public static String sendGet(String reqURL, Map<String, String> params) {
        logger.info(DateUtil.currentDateR() + reqURL);
        String responseContent = "";
//        HttpPost httpPost = null;
        HttpGet httpGet = null;
        HttpClient httpClient = null;
        try {
            httpClient = HttpClientBuilder.create().build();
            httpGet = new HttpGet(reqURL);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                    .setSocketTimeout(3000).build();
            httpGet.setConfig(requestConfig);
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            if(null!=params){
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
//            httpGet.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8" ));

            HttpResponse response = httpClient.execute(httpGet);
//            response.getLocale().
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, "UTF-8");
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("与[" + reqURL + "]通信过程中发生异常,堆栈信息为" + e);
        }
        return responseContent;
    }

    /**
     * 发送HTTPS_POST请求
     *  该方法会自动关闭连接,释放资源
     *  该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
     * @param reqURL        请求地址
     * @param params        请求参数
     * @return 远程主机响应正文
     */
    public static String sendProxyGet(String reqURL, Map<String, String> params,String ip,int port) {
        logger.info(DateUtil.currentDateR() + reqURL);
        String responseContent = "";
//        HttpPost httpPost = null;
        HttpGet httpGet = null;
        CloseableHttpClient httpClient = null;
        try {
            httpClient = new SSLClient();
            HttpHost proxy = new HttpHost(ip, port);
            httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
            httpGet = new HttpGet(reqURL);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(1000).setConnectionRequestTimeout(1000)
                    .setSocketTimeout(1000).build();
            httpGet.setConfig(requestConfig);
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            if(null!=params){
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
//            httpGet.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8" ));

            HttpResponse response = httpClient.execute(httpGet);
//            response.getLocale().
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, "UTF-8");
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("与[" + reqURL + "]通信过程中发生异常,堆栈信息为" + e);
        }
        return responseContent;
    }
}
