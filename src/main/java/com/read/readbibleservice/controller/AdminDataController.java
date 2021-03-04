package com.read.readbibleservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin/data")
public class AdminDataController {

    @GetMapping
    public String get() {
        return "Success read!";
    }
}
