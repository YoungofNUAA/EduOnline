package com.young.educenter.controller;


import com.young.commonutils.R;
import com.young.educenter.entity.UcenterMember;
import com.young.educenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-26
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;

    //登录
    @GetMapping("login")
    public R loginUser(@RequestBody UcenterMember member){
        String token = memberService.login(member);

        return R.ok().data("token",token);
    }
    //注册

}

