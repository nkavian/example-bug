package com.example.api.v1.controller;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.v1.model.Account;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Accounts")
@Validated
@CrossOrigin // NOSONAR
@RestController("v1/accounts")
@RequestMapping(path = "v1/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    @PostMapping
    @Operation(summary = "Create an account")
    public Account createAccount(@RequestBody final Account account) {
        return account;
    }

}
