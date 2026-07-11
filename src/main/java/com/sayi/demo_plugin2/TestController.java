package com.sayi.demo_plugin2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class TestController {

    @Value("${spring.application.name}")
    private String name;

    @GetMapping("/")
    public String test() {
        return name;
    }
}