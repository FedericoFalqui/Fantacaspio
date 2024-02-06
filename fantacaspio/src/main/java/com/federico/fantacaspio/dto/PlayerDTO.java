package com.federico.fantacaspio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {
    private String role;

    private String name;

    private String squad;

    private int suggested_price;

    private boolean sold;
}
