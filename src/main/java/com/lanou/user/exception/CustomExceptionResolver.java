package com.lanou.user.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dllo on 17/11/7.
 */
public class CustomExceptionResolver implements HandlerExceptionResolver {
    //前端控制器DispatcherServlet在进行HandlerMapping,
    // HandlerAdapter执行Handler的过程中,如果遇到异常,就执行这个方法

    //Handler参数是最终要执行的方法(Handler),真实身份是HandlerMethod
    //Exception e 就是收到的异常信息
    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object o, Exception e) {
        //输出异常信息
        e.printStackTrace();
        CustomException exception = null;
        if (e instanceof CustomException) {
            // shiro的异常
            exception = (CustomException) e;
        } else {
            // 其他异常
            exception = new CustomException("未知错误");
        }
        String msg = exception.getMessage();
        Cookie cookie = new Cookie("msg",msg);
        response.addCookie(cookie);
        try {
            request.getRequestDispatcher("/WEB-INF/login.html").forward(request, response);
        } catch (ServletException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return new ModelAndView();
    }
}
