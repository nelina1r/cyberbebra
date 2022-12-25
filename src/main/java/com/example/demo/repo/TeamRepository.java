package com.example.demo.repo;

import com.example.demo.models.Team;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TeamRepository extends PagingAndSortingRepository<Team, Long> {
    Team findByName(String name);
}
