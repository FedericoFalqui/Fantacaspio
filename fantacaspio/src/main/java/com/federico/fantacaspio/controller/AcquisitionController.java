package com.federico.fantacaspio.controller;

import com.federico.fantacaspio.dto.TeamResponse;
import com.federico.fantacaspio.entities.Acquisition;
import com.federico.fantacaspio.exception.*;
import com.federico.fantacaspio.service.AcquisitionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/acquisition")
@RequiredArgsConstructor
public class AcquisitionController {
    @Autowired
    private final AcquisitionService acquisitionService;

    @PostMapping("/add-player-to-team")
    public ResponseEntity<?> buyPlayer(@RequestParam(name = "playerId") int playerId,
                                       @RequestParam(name = "teamId") int teamId,
                                       @RequestParam(name = "acquisitionPrice") int acquisitionPrice){

        try {
            Acquisition a = acquisitionService.buyPlayer(playerId, teamId, acquisitionPrice);
            return ResponseEntity.ok("Acquisto avvenuto con successo " + a.toString());

        }catch (NotEnoughCreditsException | PlayerAlreadySoldException | MaxGkException | MaxDefException |
                MaxMidException | MaxStrException | EntityNotFoundException e){
            return ResponseEntity.ofNullable(e.getMessage());

        }

    }

    @DeleteMapping("/sell-player/{playerId}")
    public ResponseEntity<?> sellPlayer(@PathVariable int playerId){

        try {
            acquisitionService.sellPlayer(playerId);
            return ResponseEntity.ok("Giocatore svincolato con successo");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Errore durante lo svincolamento");
        }
    }

    @GetMapping("/get-full-team/{teamId}")
    public ResponseEntity<List<TeamResponse>> getFullTeam(@PathVariable int teamId){
        List<TeamResponse> team = acquisitionService.getFullTeam(teamId);
        if (team != null){
            return ResponseEntity.ok(team);
        } else {
            return ResponseEntity.ofNullable(team);
        }

    }


}
