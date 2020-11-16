package com.yu.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping({"", "/"})
    public String index(){
        // mustache 기본 폴더 : src/main/resources/
        // View resolver 설정 : templates (prefix), .mustache (suffix) <- 생략이 가능
        return "index";
    }
}
