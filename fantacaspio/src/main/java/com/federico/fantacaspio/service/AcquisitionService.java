package com.federico.fantacaspio.service;

import com.federico.fantacaspio.dto.TeamResponse;
import com.federico.fantacaspio.entities.Acquisition;
import com.federico.fantacaspio.entities.Player;
import com.federico.fantacaspio.entities.Squad;
import com.federico.fantacaspio.enums.MaxPlayers;
import com.federico.fantacaspio.exception.*;
import com.federico.fantacaspio.repository.AcquisitionsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AcquisitionService {
    @Autowired
    private final PlayersService playersService;

    @Autowired
    private final SquadsService squadsService;

    @Autowired
    private final AcquisitionsRepository repository;

    @Transactional
    public Acquisition buyPlayer(int playerId, int teamId, int acquisitionPrice) {
        int maxPlayers;
        Player player = (Player) playersService.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Giocatore non trovato con ID: " + playerId));

        Squad squad = (Squad) squadsService.findById(teamId);

        switch (player.getRole()) {
            case "P":
                maxPlayers = MaxPlayers.MAX_PORTIERI.getValoreMassimo();
                performPlayerChecks(player, squad, maxPlayers, acquisitionPrice);
                squad.setNum_gk(squad.getNum_gk() + 1);
                break;
            case "D":
                maxPlayers = MaxPlayers.MAX_DIFENSORI.getValoreMassimo();
                performPlayerChecks(player, squad, maxPlayers, acquisitionPrice);
                squad.setNume_def(squad.getNume_def() + 1);
                break;
            case "C":
                maxPlayers = MaxPlayers.MAX_CENTROCAMPISTI.getValoreMassimo();
                performPlayerChecks(player, squad, maxPlayers, acquisitionPrice);
                squad.setNum_mid(squad.getNum_mid() + 1);
                break;
            case "A":
                maxPlayers = MaxPlayers.MAX_ATTACCANTI.getValoreMassimo();
                performPlayerChecks(player, squad, maxPlayers, acquisitionPrice);
                squad.setNum_str(squad.getNum_str() + 1);
                break;
            default:
                throw new IllegalArgumentException("Ruolo del giocatore non valido");
        }

        squad.setCredits(squad.getCredits() - acquisitionPrice);
        player.setSold(true);

        Acquisition acquisition = Acquisition.builder()
                .player(player)
                .squad(squad)
                .acquisitionPrice(acquisitionPrice)
                .build();

        repository.save(acquisition);

        return acquisition;

    }

    private void performPlayerChecks(Player player, Squad squad, int maxPlayers, int acquisitionPrice) {
        if (player.isSold()) {
            throw new PlayerAlreadySoldException("Giocatore già venduto");
        }

        if (player.getRole().equals("P")){
            if (squad.getNum_gk() >= maxPlayers){
                throw new MaxGkException("Non Puoi avere più di " + maxPlayers + " portieri");
            }
        }

        if (player.getRole().equals("D")){
            if (squad.getNume_def() >= maxPlayers){
                throw new MaxDefException("Non Puoi avere più di " + maxPlayers + " difensori");
            }
        }

        if (player.getRole().equals("C")){
            if (squad.getNum_mid() >= maxPlayers){
                throw new MaxMidException("Non Puoi avere più di " + maxPlayers + " centrocampisti");
            }
        }

        if (player.getRole().equals("A")){
            if (squad.getNum_str() >= maxPlayers){
                throw new MaxStrException("Non Puoi avere più di " + maxPlayers + " attaccanti");
            }
        }

        if ((squad.getCredits() - acquisitionPrice) < 0) {
            throw new NotEnoughCreditsException("Non bastano i crediti per questo acquisto");
        }
    }

    @Transactional
    public void sellPlayer(int playerId) {

        Acquisition a = repository.findPlayer(playerId);

        Player player = (Player) playersService.findById(Math.toIntExact(a.getPlayer().getId()))
                .orElseThrow(() -> new EntityNotFoundException("Giocatore non trovato con ID: "));

        Squad squad =  squadsService.findById(Math.toIntExact(a.getSquad().getId()));

        player.setSold(false);
        switch (player.getRole()) {
            case "P":
                squad.setNum_gk(squad.getNum_gk() - 1);
                break;
            case "D":
                squad.setNum_gk(squad.getNume_def() - 1);
                break;
            case "C":
                squad.setNum_gk(squad.getNum_mid() - 1);
                break;
            case "A":
                squad.setNum_gk(squad.getNum_str() - 1);
                break;
            default:
                throw new IllegalArgumentException("Ruolo del giocatore non valido");
        }
        squad.setCredits(squad.getCredits() + (a.getAcquisitionPrice()/2));
        repository.delete(a);
    }

    public List<TeamResponse> getFullTeam(int teamId) {
        List<Acquisition> list = repository.findPlayers(teamId);
        List<TeamResponse> list2 = new ArrayList<>();

        for (Acquisition a : list) {
            TeamResponse t = new TeamResponse();  // Crea un nuovo oggetto TeamResponse per ogni iterazione
            t.setPlayer(a.getPlayer());
            t.setPurchasePrice(a.getAcquisitionPrice());
            list2.add(t);
        }
        if (!list2.isEmpty()){
            return list2;
        } else {
            return null;
        }
    }
}


        /*if (player.getRole().equals("P")){
            if (squad.getNum_gk() < MaxPlayers.MAX_PORTIERI.getValoreMassimo()) {
                if ((squad.getCredits() - acquisitionPrice) >= 0){
                    squad.setCredits(squad.getCredits() - acquisitionPrice);
                }else {
                    throw new PlayerNotPurchasableException("Non bastano i crediti per questo acquisto");
                }

                if (!player.isSold()){
                    player.setSold(true);
                } else {
                    throw new PlayerNotPurchasableException("Giocatore già venduto");
                }
                a = Acquisition.builder()
                        .player(player)
                        .squad(squad)
                        .acquisitionPrice(acquisitionPrice)
                        .build();
                squad.setNum_gk(squad.getNum_gk()+1);
                repository.save(a);
            }
        } else {
            throw new PlayerNotPurchasableException("Non Puoi avere più di " + MaxPlayers.MAX_PORTIERI.getValoreMassimo() + " portieri");
        }

        if (player.getRole().equals("D") && squad.getNume_def() <= MaxPlayers.MAX_DIFENSORI.getValoreMassimo()){
            a = Acquisition.builder()
                    .player(player)
                    .squad(squad)
                    .acquisitionPrice(acquisitionPrice)
                    .build();
            repository.save(a);
        } else {
            throw new PlayerNotPurchasableException("Non Puoi avere più di " + MaxPlayers.MAX_DIFENSORI.getValoreMassimo() + " difensori");
        }

        if (player.getRole().equals("C") && squad.getNum_mid() <= MaxPlayers.MAX_CENTROCAMPISTI.getValoreMassimo()){
            a = Acquisition.builder()
                    .player(player)
                    .squad(squad)
                    .acquisitionPrice(acquisitionPrice)
                    .build();
            repository.save(a);
        } else {
            throw new PlayerNotPurchasableException("Non Puoi avere più di " + MaxPlayers.MAX_CENTROCAMPISTI.getValoreMassimo() + " centrocampisti");
        }

        if (player.getRole().equals("A") && squad.getNum_str() <= MaxPlayers.MAX_ATTACCANTI.getValoreMassimo()){
            a = Acquisition.builder()
                    .player(player)
                    .squad(squad)
                    .acquisitionPrice(acquisitionPrice)
                    .build();
            repository.save(a);
        } else {
            throw new PlayerNotPurchasableException("Non Puoi avere più di " + MaxPlayers.MAX_ATTACCANTI.getValoreMassimo() + " difensori");
        }*/
