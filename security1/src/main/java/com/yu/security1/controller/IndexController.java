package com.yu.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @GetMapping({"", "/"})
    public String index(){
        // mustache 기본 폴더 : src/main/resources/
        // View resolver 설정 : templates (prefix), .mustache (suffix) <- 생략이 가능
        return "index";
    }

    @GetMapping("/user")
    public String user(){
        return "user";
    }
    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }
    @GetMapping("/manager")
    public @ResponseBody String manager(){
        return "manager";
    }

    // Spring Security가 이 주소를 낚아 챔 - Security Config 파일에서 설정해서 비활성화 됨
    @GetMapping("/login")
    public @ResponseBody String login(){
        return "login";
    }
    @GetMapping("/join")
    public @ResponseBody String join(){
        return "join";
    }
    @GetMapping("/joinProc")
    public @ResponseBody String joinProc(){
        return "회원가입 완료됨!";
    }
}
