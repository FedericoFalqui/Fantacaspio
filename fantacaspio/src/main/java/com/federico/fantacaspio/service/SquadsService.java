package com.federico.fantacaspio.service;

import com.federico.fantacaspio.entities.Squad;
import com.federico.fantacaspio.repository.SquadsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SquadsService {
    @Autowired
    private final SquadsRepository repository;
    public Squad addTeam(String name, String president, int credits){
        var s = Squad.builder()
                .name(name)
                .president(president)
                .num_str(0)
                .num_gk(0)
                .num_mid(0)
                .nume_def(0)
                .credits(credits)
                .build();

        repository.save(s);

        return s;

    }

    public Squad findById(int teamId) {
        return repository.findById(teamId);
    }

    public List<Squad> getAll() {
        return repository.findAll();
    }

    public void deleteTeam(Long id) {
        repository.deleteById(id);
    }
}
