package com.federico.fantacaspio.repository;

import com.federico.fantacaspio.entities.Acquisition;
import com.federico.fantacaspio.entities.Squad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcquisitionsRepository extends JpaRepository<Acquisition, Long> {

    List<Acquisition> findBySquad(Squad squad);
    @Query(nativeQuery = true, value = "select * from fantacaspio.acquisitions where squad_id = :teamId")
    List<Acquisition> findPlayers(int teamId);
    @Query(nativeQuery = true, value = "select * from fantacaspio.acquisitions where player_id = :playerId")
    Acquisition findPlayer(int playerId);
}
