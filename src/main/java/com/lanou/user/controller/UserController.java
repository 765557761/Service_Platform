package com.lanou.user.controller;
import com.lanou.user.exception.CustomException;
import com.lanou.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    @RequestMapping(value = "/logout")
    public void logout(){
        SecurityUtils.getSubject().logout();
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
