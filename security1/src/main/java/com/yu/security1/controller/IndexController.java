package com.yu.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // view를 리턴하겠다
public class IndexController {

    @GetMapping({ "", "/" })
    public String index() {
        // mustache 기본 폴더 : src/main/resources/
        // ViewResolver 설정 : templates(prefix), .mustache(suffix)
        // 위 설정이 기본으로 잡히므로 생략 가능
        return "index";
    }
}
