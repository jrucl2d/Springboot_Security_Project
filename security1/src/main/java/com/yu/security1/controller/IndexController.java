package com.yu.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // view�� �����ϰڴ�
public class IndexController {

    @GetMapping({ "", "/" })
    public String index() {
        // mustache �⺻ ���� : src/main/resources/
        // ViewResolver ���� : templates(prefix), .mustache(suffix)
        // �� ������ �⺻���� �����Ƿ� ���� ����
        return "index";
    }
}
