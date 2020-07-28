package com.young.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.young.commonutils.JwtUtils;
import com.young.commonutils.MD5;
import com.young.educenter.entity.UcenterMember;
import com.young.educenter.entity.vo.RegisterVo;
import com.young.educenter.mapper.UcenterMemberMapper;
import com.young.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.young.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-26
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    //登录方法
    @Override
    public String login(UcenterMember member) {
        //判断手机号  判断密码  判断是否禁用
        String mobile = member.getMobile();
        String password = member.getPassword();

        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"登录失败");
        }
        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        if(mobileMember == null){
            throw  new GuliException(20001,"登录失败");
        }
        //判断密码  存储的为加密后的密码 MD5不能解密
        if(!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new GuliException(20001,"登录失败");
        }

        //判断用户是否禁用
        if(mobileMember.getIsDisabled()){
           throw new GuliException(20001,"登陆失败");
        }

        //登陆成功
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(),mobileMember.getNickname());

        return jwtToken;
    }

    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        if(StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"注册失败");
        }
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)){
            throw new GuliException(20001,"注册失败");
        }

        //手机号重复不允许添加
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(queryWrapper);
        if(count>0){
            throw new GuliException(20001,"注册失败");
        }

        //数据添加数据库
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setMobile(mobile);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setNickname(nickname);
        ucenterMember.setIsDisabled(false);
        ucenterMember.setAvatar("https://guli-file-190513.oss-cn-beijing.aliyuncs.com/avatar/default.jpg");
        baseMapper.insert(ucenterMember);
    }

    //根据OpenId进行查询

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }
}
