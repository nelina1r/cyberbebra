package com.example.demo.services;

import com.example.demo.models.Activity;
import com.example.demo.models.Match;
import com.example.demo.repo.ActivityRepository;
import com.example.demo.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    @Autowired private ActivityRepository activityRepository;

    public List<Activity> findAll() {
        ArrayList<Activity> activities = new ArrayList<>();
        activityRepository.findAll().forEach(activities::add);
        return activities;
    }

    @Transactional
    public Page<Activity> findAll(int page, int size) {
        return activityRepository.findAll(PageRequest.of(page, size, Sort.by("date")));
    }

    @Transactional
    public Activity save(Activity activity) {
        return activityRepository.save(activity);
    }

    @Transactional
    public Optional<Activity> findById(long id) {
        return  activityRepository.findById(id);
    }

    @Transactional
    public boolean existsById(long id) {
        return activityRepository.existsById(id);
    }

    @Transactional
    public void delete(Activity activity) {
        activityRepository.delete(activity);
    }

    @Transactional
    public List<Activity> findAllByDiscipline(String disciplineName){
        List<Activity> tournaments = new ArrayList<>();
        activityRepository.findAll().forEach(activity -> {
            if (activity.getDiscipline().getName().equals(disciplineName)) {
                tournaments.add(activity);
            }
        });
        return tournaments;
    }

}

