package com.federico.fantacaspio.controller;

import com.federico.fantacaspio.entities.Player;
import com.federico.fantacaspio.service.PlayersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/players")
@RequiredArgsConstructor
public class PlayersController {
    @Autowired
    private final PlayersService playersService;

    @PostMapping("/load-players")
    public ResponseEntity<?> loadPlayers(@RequestParam(name = "percorso") String percorso) throws FileNotFoundException {
        //System.out.println("+++++++++++ PATH: " + percorso);

        List<Player> players = playersService.loadPlayers(percorso);

        if (!players.isEmpty()){
            return ResponseEntity.ok(Map.of("message", "Giocatori caricati con successo"));
        } else {
            return ResponseEntity.ok(Map.of("message", "Non è stato letto niente"));
        }
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<Player>> getAllPlayers(){
        List<Player> listone = playersService.getAllPlayers();

        return new ResponseEntity<>(listone, HttpStatus.OK);
    }

    @GetMapping("/find-by-squad")
    public ResponseEntity<?> findPlayersBySquad(@RequestParam(name = "squad") String squad){
        List<Player> players = playersService.findBySquad(squad);
        if (!players.isEmpty()){
            return ResponseEntity.ok(players);
        } else {
            return ResponseEntity.ofNullable("Non ci sono giocatori che appartengono alla squadra: " + squad);
        }
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<?> deleteAllPlayers() {
        playersService.deleteAllPlayers();
        return ResponseEntity.ok(Map.of("message", "Tutti i giocatori sono stati cancellati"));
    }
}
