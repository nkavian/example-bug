package com.example.api.v1.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@CrossOrigin // NOSONAR
@RestController("/")
public class RootController {

    @Hidden
    @GetMapping
    public String redoc() {
        return "<redoc spec-url='/v1/openapi.json'></redoc><script src=\"https://cdn.jsdelivr.net/npm/redoc@next/bundles/redoc.standalone.js\"> </script>";
    }

}
