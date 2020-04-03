package org.gourd.hu.core.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 密码加解密工具
 * @author gourd
 */
public class CryptoDemo {

    public static void main(String[] args) throws Exception {
        // jasypt
//        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
//        textEncryptor.setPassword("GOURD-HXN-1314");
//        // 加密
//        String password = textEncryptor.encrypt("gourd123");
//        System.out.println("^0^===password:"+ password);
//        // 解密
//        String originPwd = textEncryptor.decrypt("4M6RKeFuZ7OngpmunjkMm/a+W8eCJrsF");
//        System.out.println("^0^===originPwd:"+ originPwd);

        String obj = "{\"requestRoot\":{\"requestBody\":{\"infoList\":[{\"cntrNo\":\"W0007\",\"cntrUserCode\":\"G1032058500482590S\",\"cntrUserName\":\"太仓福泰物流有限公司\",\"driverId\":\"\",\"driverMobile\":\"17372516546\",\"driverName\":\"陈刚\",\"eirNo\":\"EQP1235849935786872832\",\"eta\":\"2020-03-17 18:10:53\",\"freeHighSpeed\":\"N\",\"opMode\":\"3\",\"planNo\":\"\",\"recordId\":\"ORDER1239851690606465024\",\"reserveNo\":\"\",\"taskNo\":\"EQP12358499357868728321\",\"taskTypeCode\":\"1\",\"trailerNo\":\"暂无\",\"truckNo\":\"苏E13234\",\"unitCode\":\"SZXD\",\"unitName\":\"苏州现代（二期信通）\"}]},\"requestHeader\":{\"dataNumber\":\"1\",\"dataType\":\"truckTaskOrder\",\"encryptType\":\"\",\"messageId\":\"3cb3c0a5ed97a8d8e60d9de589a3dde1d2124d1cf203f48a2351249b4ec4e73ed1dbbbecd1d3b4b8ff579a9cf69bdacc0554fd485b687ec0b69a454e2ca6226730c5c21ccd48422ba1d79985a3f3a6585125b3465ecaae7748e28394b9a64b7d4183acaa363fbba34329b576d9514560e79023b0e3f8fe9fdc0f9c029ce9c3ac\",\"operateMode\":\"W\",\"receiveInfo\":[{\"methodCode\":\"M0002\",\"receiveCode\":\"TCEPORT_OUT_S0013\"}],\"requestMode\":\"Sync\",\"returnMethodCode\":\"\",\"secretKey\":\"478aed1e5a734643f5292a7187d5179f321a5bd41665f5abc6ec23d56134682681413b6f6d7ac023c27a33c06068204c73752315d1bfb9f05dc740b45d902fc662b1d82292305e5feaec7d1d7bf9cbee310f7188527903bbc095d804402b628e2b9cbfb9c2ee0e9056859b0562299b4ec28621b524d7c65a69b2a9b857facc32\",\"sendCode\":\"TCEPORT_IN_S0006\",\"timeStamp\":\"1584438654127\"}}}\n";
        JSONObject jsonObject = JSONObject.parseObject(obj);
        // 码头堆场代码
        JSONObject requestRoot = (JSONObject)jsonObject.get("requestRoot");
        JSONObject requestBody = (JSONObject)requestRoot.get("requestBody");
        JSONArray infoList = (JSONArray)requestBody.get("infoList");
        JSONObject info = (JSONObject)infoList.get(0);
        if(info != null && info.get("unitCode")!=null){
            String unitCode = info.get("unitCode").toString();
            System.out.println(unitCode);
        }
    }
}