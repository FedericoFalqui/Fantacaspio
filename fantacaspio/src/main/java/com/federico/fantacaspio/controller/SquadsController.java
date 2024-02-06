package com.federico.fantacaspio.controller;

import com.federico.fantacaspio.entities.Squad;
import com.federico.fantacaspio.service.SquadsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/squads")
@RequiredArgsConstructor
public class SquadsController {
    @Autowired
    private final SquadsService squadsService;

    @PostMapping("/add-team")
    public ResponseEntity<?> addSquad(@RequestParam(name = "name") String squadName,
                                      @RequestParam(name = "president")String president,
                                      @RequestParam(name = "credits")int credits){

        Squad s = squadsService.addTeam(squadName, president, credits);

        if (s != null){
            return ResponseEntity.ok(s);
        } else {
            return ResponseEntity.ofNullable("Non è stato letto niente");
        }

    }

    @GetMapping("/find-all")
    public ResponseEntity<List<Squad>> getAll(){
        List<Squad> squads = squadsService.getAll();
        return new ResponseEntity<>(squads, HttpStatus.OK);

    }

    @DeleteMapping("/delete-team/{id}")
    public ResponseEntity<?> deleteSquad(@PathVariable Long id) {
        try {
            // Chiamata al servizio o al repository per eliminare la squadra
            squadsService.deleteTeam(id);
            System.out.println("eliminata");
            return ResponseEntity.ok("Squadra eliminata con successo");
        } catch (Exception e) {
            // Gestione degli errori
            System.out.println("errore");
            return ResponseEntity.badRequest().body("Errore durante l'eliminazione della squadra");
        }
    }
}
