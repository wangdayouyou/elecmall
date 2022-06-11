package com.sdxx.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestJenkins {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        return "hello World";
    }
}
