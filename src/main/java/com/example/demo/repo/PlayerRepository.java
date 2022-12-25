package com.example.demo.repo;

import com.example.demo.models.Player;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PlayerRepository extends PagingAndSortingRepository<Player, Long> {
    Player findFirstByOrderByIdDesc();
}
