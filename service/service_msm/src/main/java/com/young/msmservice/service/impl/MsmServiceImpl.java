package com.young.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.young.msmservice.service.MsmService;
import com.young.servicebase.exceptionhandler.GuliException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {

    //发送短信验证码
    @Override
    public Boolean send(Map<String, Object> param, String phone) {
        if(StringUtils.isEmpty(phone)) return false;

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAI4GFeVgwgc1f5WgvWMcWZ", "KiSuaKwRruJuWFbEo5lOPk1zJ0PTVB");
        IAcsClient client = new DefaultAcsClient(profile);
        //设置参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "尚学在线教育网站");
        request.putQueryParameter("TemplateCode", "SMS_197886224");
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param)); //map-->json
        try{
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
