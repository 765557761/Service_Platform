package com.lanou.user.controller;
import com.lanou.user.bean.SysUser;
import com.lanou.user.exception.CustomException;
import com.lanou.user.service.UserService;
import com.lanou.utils.VerifyCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by dllo on 17/11/8.
 */
@Controller
public class UserController {

    @Resource
    private UserService service;

    @RequestMapping(value = "/toLoginPage")
    public String login1(){
        return "login";
    }
    @RequestMapping(value = "/index")
    public String index(){
        if (SecurityUtils.getSubject().isAuthenticated()){
            SecurityUtils.getSubject().logout();
        }
        return "index";
    }
    @RequestMapping(value = "/welcome")
    public String welcome(){
        return "welcome";
    }
    // 退出/切换用户
    @RequestMapping(value = "/logout")
    public void logout(){
        SecurityUtils.getSubject().logout();
    }
    //个人信息
    @ResponseBody
    @RequestMapping(value = "/myself")
    public SysUser myself(@RequestParam("loginName") String username){
        return service.findSysByName(username);
    }
    //验证码
    @ResponseBody
    @RequestMapping(value = "/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 创建工具类对象p
        VerifyCode verifyCode = new VerifyCode();
        // 验证码工具生成图片对象
        BufferedImage image = verifyCode.getImage();

        // 将验证码保存到session中-verifyCode.getText()
        request.getSession().setAttribute("verifyCode",verifyCode.getText());

        // 获得response对象的输出流-用于图像的写入
        OutputStream os = response.getOutputStream();
        // 将图片对象映射到输出流中
        VerifyCode.output(image,os);
    }


    @RequestMapping(value = "/loginsubmit",method = RequestMethod.POST)
    public String login(HttpServletRequest request) throws Exception {
        String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
        if (exceptionClassName.equals(UnknownAccountException.class.getName())) {
            throw new CustomException("账户名不存在");
        } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            throw new CustomException("密码错误");
        } else {
            throw new Exception();
        }
    }
}
