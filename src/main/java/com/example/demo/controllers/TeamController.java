package com.example.demo.controllers;


import com.example.demo.models.Activity;
import com.example.demo.models.Discipline;
import com.example.demo.models.Player;
import com.example.demo.models.Team;
import com.example.demo.repo.DisciplineRepository;
import com.example.demo.repo.TeamRepository;
import com.example.demo.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class TeamController {

    @Autowired
    private TeamService teamService;
    @Autowired
    private DisciplineRepository disciplineRepository;
    @Autowired
    private TeamRepository teamRepository;

    @GetMapping("/teams")
    public String teams(Model model) {
        List<Team> teams = teamService.findAll();
        List<Team> dota = new ArrayList<>();
        List<Team> cs = new ArrayList<>();
        List<Team> lol = new ArrayList<>();
        for (Team team : teams){
            if (team.getDiscipline().getName().equals("Dota 2")){
                dota.add(team);
            }
            else if (team.getDiscipline().getName().equals("CS:GO")){
                cs.add(team);
            }
            else
                lol.add(team);
        }
        model.addAttribute("dota", dota); //передача значений
        model.addAttribute("cs", cs); //передача значений
        model.addAttribute("lol", lol); //передача значений
        model.addAttribute("title", "Команды");
        return "team";
    }

    @GetMapping("/teams/add") //GetMapping - пользователь переходит по определённому адресу
    public String teamsAdd(Model model) {
        model.addAttribute("disciplines",disciplineRepository.findAll());
        return "team-add";
    }

    @RequestMapping(value = "/teams/add",method = RequestMethod.POST) //получение данных из формы
    public String teamTeamAdd(@RequestParam String img, @RequestParam String name, @RequestParam String description, @RequestParam("date")String date, @RequestParam String site, Model model, @RequestParam List<String> roster, @RequestParam String discipline) { //@RequestParam - получение значений из формы. title - получение значений из данного поля
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LocalDate localDate = LocalDate.parse(date);
        Discipline discipline1 = disciplineRepository.findByName(discipline);
        Team team = new Team(name, img, description, site, localDate,discipline1); //объект на основе модели Post с названием post. (title, anons, fullText) - передача параметров
        teamService.save(team,roster); //сохранение объекта и добавление в бд -> обращение к репозиторию -> обращение к функции save и передача в него объекта, который необходимо сохранить => добавление в таблицу post навых статей, полученных от пользователя
        return "redirect:/teams"; //переадресация пользователя на указанную страницу после добавления команды
    }

    @GetMapping("/teams/{id}") //{id} - динамическое значение url-адреса
    public String teamsDetails(@PathVariable(value = "id") long id, Model model) { //@PathVariable - анотация, принимающая динамический параметр из url-адреса (в определённый параметр (long id) помещается значение, полученное из url-адреса
        Optional<Team> team = teamService.findById(id); //нахождение записи по id и помещение в объект post на основе класса Optional и модели <Post>
        if(team.isPresent()) {
            ArrayList<Team> res = new ArrayList<>();
            team.ifPresent(res::add); //из класса Optional переводим в класс ArrayList
            model.addAttribute("team", res);
            return "team-details";
        } else {
            return "redirect:/teams"; //перенаправление на указанную страницу
        }
    }

    @GetMapping("/teams/{id}/edit") //редактирование статьи
    public String teamsEdit(@PathVariable(value = "id") long id, Model model) { //@PathVariable - анотация, принимающая динамический параметр из url-адреса (в определённый параметр (long id) помещается значение, полученное из url-адреса
        if(!teamService.existsById(id)){ //try - если определённая запись по определённому id не была найдена. иначе false
            return "redirect:/teams"; //перенаправление на указанную страницу
        }
        //Optional<Post> post= postRepository.findById(id);
        Optional<Team> team= teamService.findById(id); //нахождение записи по id и помещение в объект post на основе класса Optional и модели <Post>
        ArrayList<Team> res = new ArrayList<>();
        team.ifPresent(res::add); //из класса Optional переводим в класс ArrayList
        model.addAttribute("team", res);
        return "team-edit";
    }

    @PostMapping("/teams/{id}/edit") //получение данных из формы
    public String teamTeamsEdit(@PathVariable(value = "id") long id, @RequestParam String img, @RequestParam String name, @RequestParam String description, @RequestParam String site, Model model) { //@RequestParam - получение значений из формы. title - получение значений из данного поля
        Team team = teamService.findById(id).orElseThrow(
                () -> new RuntimeException()
        ); //orElseTrow() - исключительная ситуация в случае не нахождения записи
        team.setImg(img);
        team.setName(name); //установка введеного заголовка
        team.setDescription(description);
        team.setSite(site);
        teamRepository.save(team); //сохранение обновлённого объекта
        return "redirect:/teams/{id}"; //переадресация пользователя на указанную страницу
    }

    @PostMapping("/teams/{id}/remove") //получение данных из формы
    public String teamDelete(@PathVariable(value = "id") long id, Model model) { //@RequestParam - получение значений из формы. title - получение значений из данного поля
        Team team = teamService.findById(id).orElseThrow(
                () -> new RuntimeException()
        ); //orElseTrow() - исключительная ситуация в случае не нахождения записи
        teamService.delete(team); //удаление определенной записи
        return "redirect:/teams"; //переадресация пользователя на указанную страницу после удаления статьи
    }
}
