package com.example.demo.controllers;


import com.example.demo.models.*;
import com.example.demo.repo.ActivityRepository;
import com.example.demo.repo.DisciplineRepository;
import com.example.demo.repo.TeamRepository;
import com.example.demo.services.ActivityService;
import com.example.demo.services.MatchService;
import com.example.demo.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MatchController {
    @Autowired
    private MatchService matchService;
    @Autowired
    private DisciplineRepository disciplineRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ActivityService activityService;


    @GetMapping("/matches")
    public String matches(Model model) {
        List<Match> matches = matchService.findAll();
        List<Match> dota = new ArrayList<>();
        List<Match> cs = new ArrayList<>();
        List<Match> lol = new ArrayList<>();
        for (Match match : matches){
            if (match.getDiscipline().getName().equals("Dota 2")){
                dota.add(match);
            }
            else if (match.getDiscipline().getName().equals("CS:GO")){
                cs.add(match);
            }
            else
                lol.add(match);
        }
        model.addAttribute("dota", dota); //передача значений
        model.addAttribute("cs", cs); //передача значений
        model.addAttribute("lol", lol); //передача значений
        model.addAttribute("title", "Матчи");
        return "match";
    }

    @GetMapping("/matches/add") //GetMapping - пользователь переходит по определённому адресу
    public String matchesAdd(Model model) {
        model.addAttribute("disciplines",disciplineRepository.findAll());
        model.addAttribute("activities",activityService.findAll());
        return "match-add";
    }

    @RequestMapping(value = "/matches/add",method = RequestMethod.POST) //получение данных из формы
    public String matchAdd(@RequestParam String discipline, @RequestParam String time, @RequestParam String tournament, @RequestParam String team1, Model model, @RequestParam String team2) { //@RequestParam - получение значений из формы. title - получение значений из данного поля
        LocalDateTime time1 = LocalDateTime.parse(time);
        Discipline discipline1 = disciplineRepository.findByName(discipline);
        Activity activity = activityRepository.findByTitle(tournament);
        Team team11 = teamRepository.findByName(team1);
        Team team22 = teamRepository.findByName(team2);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Match match = new Match(discipline1, time1, activity, team11, team22); //объект на основе модели Post с названием post. (title, anons, fullText) - передача параметров
        matchService.save(match); //сохранение объекта и добавление в бд -> обращение к репозиторию -> обращение к функции save и передача в него объекта, который необходимо сохранить => добавление в таблицу post навых статей, полученных от пользователя
        return "redirect:/matches"; //переадресация пользователя на указанную страницу после добавления команды
    }

    @GetMapping("/matches/{id}") //{id} - динамическое значение url-адреса
    public String matchDetails(@PathVariable(value = "id") long id, Model model) { //@PathVariable - анотация, принимающая динамический параметр из url-адреса (в определённый параметр (long id) помещается значение, полученное из url-адреса
        Optional<Match> match = matchService.findById(id); //нахождение записи по id и помещение в объект post на основе класса Optional и модели <Post>
        if(match.isPresent()) {
            ArrayList<Match> res = new ArrayList<>();
            match.ifPresent(res::add); //из класса Optional переводим в класс ArrayList
            model.addAttribute("match", res);
            return "match-details";
        } else {
            return "redirect:/matches"; //перенаправление на указанную страницу
        }
    }

    @GetMapping("/matches/{id}/edit") //редактирование статьи
    public String matchesEdit(@PathVariable(value = "id") long id, Model model) { //@PathVariable - анотация, принимающая динамический параметр из url-адреса (в определённый параметр (long id) помещается значение, полученное из url-адреса
        if(!matchService.existsById(id)){ //try - если определённая запись по определённому id не была найдена. иначе false
            return "redirect:/matches"; //перенаправление на указанную страницу
        }
        //Optional<Post> post= postRepository.findById(id);
        Optional<Match> match= matchService.findById(id); //нахождение записи по id и помещение в объект post на основе класса Optional и модели <Post>
        ArrayList<Match> res = new ArrayList<>();
        match.ifPresent(res::add); //из класса Optional переводим в класс ArrayList
        model.addAttribute("match", res);
        return "match-edit";
    }

    @PostMapping("/matches/{id}/edit") //получение данных из формы
    public String matchEdit(@PathVariable(value = "id") long id, @RequestParam String team1, Model model, @RequestParam String team2) { //@RequestParam - получение значений из формы. title - получение значений из данного поля
        Match match = matchService.findById(id).orElseThrow(
                () -> new RuntimeException()
        ); //orElseTrow() - исключительная ситуация в случае не нахождения записи
        Team team11 = teamRepository.findByName(team1);
        Team team22 = teamRepository.findByName(team2);
        match.setTeam1(team11);
        match.setTeam2(team22);
        matchService.save(match); //сохранение обновлённого объекта
        return "redirect:/matches/{id}"; //переадресация пользователя на указанную страницу
    }

    @PostMapping("/matches/{id}/remove") //получение данных из формы
    public String matchDelete(@PathVariable(value = "id") long id, Model model) { //@RequestParam - получение значений из формы. title - получение значений из данного поля
        Match match = matchService.findById(id).orElseThrow(
                () -> new RuntimeException()
        ); //orElseTrow() - исключительная ситуация в случае не нахождения записи
        matchService.delete(match); //удаление определенной записи
        return "redirect:/matches"; //переадресация пользователя на указанную страницу после удаления статьи
    }
}
