package com.bp.customerapi.presentation.controllers;

import com.bp.customerapi.presentation.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String get(){
        return Constants.API_WELCOME_MESSAGE;
    }
}
