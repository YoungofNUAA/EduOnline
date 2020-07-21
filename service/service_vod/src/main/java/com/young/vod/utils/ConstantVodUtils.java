package com.young.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantVodUtils implements InitializingBean {

    @Value("${aliyun.vod.file.keyid}")
    private String keyId;
    @Value("${aliyun.vod.file.keysecret}")
    private String keySecret;

    public static String ACCESS_KEY_SECRET;
    public static String ACCESS_ID;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
    }
}
