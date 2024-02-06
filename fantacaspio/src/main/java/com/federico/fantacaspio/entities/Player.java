package com.federico.fantacaspio.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "players")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;

    private String name;

    private String squad;

    private int suggested_price;

    private boolean sold;
}
