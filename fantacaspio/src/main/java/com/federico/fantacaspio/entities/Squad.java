package com.federico.fantacaspio.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "squads")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Squad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String president;

    private int num_gk;

    private int nume_def;

    private int num_mid;

    private int num_str;

    private int credits;
}
