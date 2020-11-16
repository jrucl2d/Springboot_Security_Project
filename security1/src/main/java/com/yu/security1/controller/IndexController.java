package com.yu.security1.controller;

import com.yu.security1.model.User;
import com.yu.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encodePwd;

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
    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }
    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }
    @PostMapping("/join")
    public String join(User user){
        user.setRole("ROLE_USER");

        String rawPw = user.getPassword();
        String encPw = encodePwd.encode(rawPw);
        user.setPassword(encPw);
        userRepository.save(user);
        return "redirect:/loginForm";
    }
}
