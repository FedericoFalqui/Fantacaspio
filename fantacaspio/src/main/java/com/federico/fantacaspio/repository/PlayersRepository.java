package com.federico.fantacaspio.repository;

import com.federico.fantacaspio.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayersRepository extends JpaRepository<Player, Long> {
    List<Player> findPlayersByRole(String role);

    List<Player> findPlayersBySquad(String squad);

    List<Player> findPlayersByName(String name);

    Player findById(int id);
}
