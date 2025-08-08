package com.ecommerce.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@Tag(name="Home Controller")
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
