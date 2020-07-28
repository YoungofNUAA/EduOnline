package com.young.educenter.controller;

import com.google.gson.Gson;
import com.young.educenter.entity.UcenterMember;
import com.young.educenter.service.UcenterMemberService;
import com.young.educenter.utils.ConstantWxUtils;
import com.young.educenter.utils.HttpClientUtils;
import com.young.servicebase.exceptionhandler.GuliException;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller    //只需请求地址 不需返回数据不需要RestController
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WxApiController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    @GetMapping("callback")
    public String callback(String code,String state){
        try{
            //向认证服务器发送请求换取access_token
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            String acessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code
            );
            String acessTokenInfo = HttpClientUtils.get(acessTokenUrl);
//            System.out.println(acessTokenInfo);
            // gson将json转化为map
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(acessTokenInfo, HashMap.class);
            String access_token = (String)mapAccessToken.get("access_token");
            String openid = (String)mapAccessToken.get("openid");

            //第三步
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
            String userInfo = HttpClientUtils.get(userInfoUrl);
//            System.out.println("userInfo"+userInfo);
            HashMap userInfoMap = gson.fromJson(userInfo,HashMap.class);
            String nickname = (String)userInfoMap.get("nickname");
            String headimgurl = (String)userInfoMap.get("headimgurl");

            UcenterMember member = ucenterMemberService.getOpenIdMember(openid);
            if(member==null){
                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                ucenterMemberService.save(member);
            }
            return "redirect:http://localhost:3000";
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"登录失败");
        }
    }

    @GetMapping("login")
    public String getWxCode(){
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        String redirect_url = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirect_url = URLEncoder.encode(redirect_url,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String Url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirect_url,
                "atguigu"
                );

        return "redirect:" + Url;
    }
}
