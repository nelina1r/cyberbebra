package com.example.demo.repo;

import com.example.demo.models.Match;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MatchRepository extends PagingAndSortingRepository<Match,Long> {
}
