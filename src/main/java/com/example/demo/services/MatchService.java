package com.example.demo.services;

import com.example.demo.models.Activity;
import com.example.demo.models.Match;
import com.example.demo.repo.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    @Transactional
    public List<Match> findAll() {
        List<Match> matches = new ArrayList<>();
        matchRepository.findAll().forEach(matches::add);
        return matches;
    }

    @Transactional
    public Page<Match> findAll(int page, int size) {
        return matchRepository.findAll(PageRequest.of(page, size, Sort.by("time")));
    }

    @Transactional
    public Match save(Match match) {
        return matchRepository.save(match);
    }

    @Transactional
    public Optional<Match> findById(long id) {
        return  matchRepository.findById(id);
    }

    @Transactional
    public boolean existsById(long id) {
        return matchRepository.existsById(id);
    }

    @Transactional
    public void delete(Match match) {
        matchRepository.delete(match);
    }

    @Transactional
    public List<Match> findAllByDiscipline(String disciplineName){
        List<Match> matches = new ArrayList<>();
        matchRepository.findAll().forEach(match -> {
            if (match.getDiscipline().getName().equals(disciplineName)) {
                matches.add(match);
            }
        });
        return matches;
    }
}
