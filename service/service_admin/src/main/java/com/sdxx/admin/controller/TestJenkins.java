package com.sdxx.admin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestJenkins {
    @RequestMapping("/hello")
    public String test01(){
        return "hello";
    }
}
