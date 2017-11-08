package com.lanou.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dllo on 17/11/8.
 */
@Controller
public class UserController {

    @RequestMapping(value = "/login")
    public String index(){
        return "login";
    }
}
