package com.federico.fantacaspio.repository;

import com.federico.fantacaspio.entities.Squad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SquadsRepository extends JpaRepository<Squad, Long> {
    Squad findById(int id);
}
