package com.example.api.v1.controller;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.v1.model.Payment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Payments")
@Validated
@CrossOrigin // NOSONAR
@RestController("v1/payments")
@RequestMapping(path = "v1/payments", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {

    @PostMapping
    @Operation(summary = "Create a payment")
    public Payment createPayment(@RequestBody final Payment payment) {
        return payment;
    }

}
