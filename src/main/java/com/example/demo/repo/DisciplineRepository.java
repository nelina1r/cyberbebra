package com.example.demo.repo;

import com.example.demo.models.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
    Discipline findByName(String name);
}
