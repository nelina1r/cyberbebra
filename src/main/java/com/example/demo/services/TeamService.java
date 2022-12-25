package com.example.demo.services;


import com.example.demo.models.Player;
import com.example.demo.models.Team;
import com.example.demo.repo.PlayerRepository;
import com.example.demo.repo.TeamRepository;
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
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public List<Team> findAll() {
        List<Team> teams = new ArrayList<>();
        teamRepository.findAll().forEach(teams::add);
        return teams;
    }

    public Page<Team> findAll(int page, int size) {
        return teamRepository.findAll(PageRequest.of(page, size, Sort.by("time")));
    }

    public Team save(Team team,List<String> roster) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < roster.size(); i+=2){
            Player player = new Player();
            player.setName(roster.get(i));
            player.setImg(roster.get(i+1));
            playerRepository.save(player);
            players.add(playerRepository.findFirstByOrderByIdDesc());
        }
        team.setRoster(players);
        return teamRepository.save(team);
    }

    public Optional<Team> findById(long id) {
        return  teamRepository.findById(id);
    }

    public boolean existsById(long id) {
        return teamRepository.existsById(id);
    }

    public void delete(Team team) {
        teamRepository.delete(team);
    }
}
