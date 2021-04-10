package com.example.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Payment")
public class Payment {

    private String name;

    //@NotBlank
    //@Schema
    //private Currency currency;

}
