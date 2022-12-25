package com.example.demo.services;

import com.example.demo.models.Activity;
import com.example.demo.models.Match;
import com.example.demo.repo.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public Page<Activity> findAll(int page, int size) {
        return activityRepository.findAll(PageRequest.of(page, size, Sort.by("date")));
    }

    public Activity save(Activity activity) {
        return activityRepository.save(activity);
    }

    public Optional<Activity> findById(long id) {
        return  activityRepository.findById(id);
    }

    public boolean existsById(long id) {
        return activityRepository.existsById(id);
    }

    public void delete(Activity activity) {
        activityRepository.delete(activity);
    }

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

