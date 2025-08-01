package com.ecommerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
public class HomeController {

    @GetMapping("")
    public String home(){
        return "Ecommerce Application 🛍️ "+LocalTime.now() +" "+LocalDate.now();
    }

    @GetMapping("/health")
    public String health(){
        return "Ecommerce Application Health : ✅";
    }
}
