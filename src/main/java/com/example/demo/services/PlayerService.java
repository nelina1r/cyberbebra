package com.example.demo.services;

import com.example.demo.models.Match;
import com.example.demo.models.Player;
import com.example.demo.repo.MatchRepository;
import com.example.demo.repo.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public List<Player> findAll() {
        List<Player> players = new ArrayList<>();
        playerRepository.findAll().forEach(players::add);
        return players;
    }

    public Page<Player> findAll(int page, int size) {
        return playerRepository.findAll(PageRequest.of(page, size, Sort.by("time")));
    }

    public Player save(Player player) {
        return playerRepository.save(player);
    }

    public Optional<Player> findById(long id) {
        return  playerRepository.findById(id);
    }

    public boolean existsById(long id) {
        return playerRepository.existsById(id);
    }

    public void delete(Player player) {
        playerRepository.delete(player);
    }

}
