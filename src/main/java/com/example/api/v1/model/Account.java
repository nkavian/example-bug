package com.example.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;

@Data
@Schema(name = "Account")
public class Account {

    private String name;

    @Schema(accessMode = AccessMode.READ_ONLY)
    private Currency currency;

    @Schema(accessMode = AccessMode.READ_ONLY)
    private String other;

}
