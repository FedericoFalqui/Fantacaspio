package com.federico.fantacaspio.dto;

import com.federico.fantacaspio.entities.Player;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamResponse {
    Player player;
    int purchasePrice;
}
