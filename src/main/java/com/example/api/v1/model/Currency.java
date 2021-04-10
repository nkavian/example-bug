package com.example.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Currency", enumAsRef = false)
public enum Currency {

    USD, EUR;

}
