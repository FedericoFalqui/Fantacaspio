package com.federico.fantacaspio.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "acquisitions")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Acquisition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "squad_id")
    private Squad squad;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    private int acquisitionPrice;
}
