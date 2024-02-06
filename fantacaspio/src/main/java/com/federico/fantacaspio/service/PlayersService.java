package com.federico.fantacaspio.service;

import com.federico.fantacaspio.dto.PlayerDTO;
import com.federico.fantacaspio.entities.Player;
import com.federico.fantacaspio.repository.PlayersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class PlayersService {
    @Autowired
    private final PlayersRepository repository;

    public List<Player> loadPlayers(String percorso) throws FileNotFoundException {
        List<Player> lista = new ArrayList<>();
        Scanner file = new Scanner(new File(percorso));
        String[] riga;

        //System.out.println("+++++++++++ PATH: " + percorso);
        file.nextLine();

        while (file.hasNextLine()) {
            riga = file.nextLine().split(";");

            Player player = Player.builder()
                    .role(riga[0])
                    .name(riga[1])
                    .squad(riga[2])
                    .suggested_price(Integer.parseInt(riga[3]))
                    .sold(false)
                    .build();

            repository.save(player);
            lista.add(player);

        }
        file.close();
        return lista;
    }

    public List<Player> findBySquad(String squad) {
        return repository.findPlayersBySquad(squad);
    }

    public Optional<Object> findById(int playerId) {
        return Optional.ofNullable(repository.findById(playerId));
    }

    public List<Player> getAllPlayers() {
        return repository.findAll();
    }

    public void deleteAllPlayers() {
        repository.deleteAll();
    }
}
