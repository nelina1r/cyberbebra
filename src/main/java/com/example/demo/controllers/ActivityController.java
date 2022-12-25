package com.example.demo.controllers;

import com.example.demo.models.Activity;
import com.example.demo.models.Discipline;
import com.example.demo.models.Team;
import com.example.demo.repo.DisciplineRepository;
import com.example.demo.repo.TeamRepository;
import com.example.demo.services.ActivityService;
import com.example.demo.services.TeamService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class ActivityController {


    @Autowired //анотация для создания переменной, ссылающейся на репозиторий
    private ActivityService activityService; //указание репозитория, к которому обращаемся и название пееременной

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamService teamService;
    @Autowired
    private DisciplineRepository disciplineRepository;

    @Configuration
    public class MvcConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/css/**")
                    .addResourceLocations("classpath:/css/");
        }
    }

    @GetMapping("/tournaments")
    public String activity(Model model) {
        model.addAttribute("activities", activityService.findAll()); //передача значений
        model.addAttribute("title", "Турниры");
        return "activity";
    }

    @GetMapping("/tournaments/add") //GetMapping - пользователь переходит по определённому адресу
    public String activityAdd(Model model) {
        model.addAttribute("teams",teamService.findAll());
        model.addAttribute("disciplines",disciplineRepository.findAll());
        return "activity-add";
    }

    @RequestMapping(value = "/tournaments/add",method = RequestMethod.POST) //получение данных из формы
    public String activityActivityAdd(@RequestParam String img, @RequestParam String title, @RequestParam String anons, @RequestParam("start") String start, @RequestParam String fullText, Model model, @RequestParam String prize, @RequestParam List<String> participants, @RequestParam String discipline,@RequestParam String end) { //@RequestParam - получение значений из формы. title - получение значений из данного поля
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Team> teams = new ArrayList<>();
        for (String value: participants){
            teams.add(teamRepository.findByName(value));
        }
        LocalDate startTime = LocalDate.parse(start);
        LocalDate endTime = LocalDate.parse(end);
        Discipline discipline1 = disciplineRepository.findByName(discipline);
        Activity activity = new Activity(img, title, anons, startTime,endTime, fullText,prize,teams,discipline1); //объект на основе модели Post с названием post. (title, anons, fullText) - передача параметров
        activityService.save(activity); //сохранение объекта и добавление в бд -> обращение к репозиторию -> обращение к функции save и передача в него объекта, который необходимо сохранить => добавление в таблицу post навых статей, полученных от пользователя
        //postRepository.save(post);
        return "redirect:/tournaments"; //переадресация пользователя на указанную страницу после добавления турнира
    }

    @GetMapping("/tournaments/{id}") //{id} - динамическое значение url-адреса
    public String activityDetails(@PathVariable(value = "id") long id, Model model) { //@PathVariable - анотация, принимающая динамический параметр из url-адреса (в определённый параметр (long id) помещается значение, полученное из url-адреса
        Optional<Activity> activity= activityService.findById(id); //нахождение записи по id и помещение в объект post на основе класса Optional и модели <Post>
        if(activity.isPresent()) {
            ArrayList<Activity> res = new ArrayList<>();
            activity.ifPresent(res::add); //из класса Optional переводим в класс ArrayList
            model.addAttribute("activity", res);
            return "activity-details";
        } else {
            return "redirect:/tournaments"; //перенаправление на указанную страницу
        }
    }

    @GetMapping("/tournaments/{id}/edit") //редактирование статьи
    public String activityEdit(@PathVariable(value = "id") long id, Model model) { //@PathVariable - анотация, принимающая динамический параметр из url-адреса (в определённый параметр (long id) помещается значение, полученное из url-адреса
        if(!activityService.existsById(id)){ //try - если определённая запись по определённому id не была найдена. иначе false
            return "redirect:/tournaments"; //перенаправление на указанную страницу
        }
        //Optional<Post> post= postRepository.findById(id);
        Optional<Activity> post= activityService.findById(id); //нахождение записи по id и помещение в объект post на основе класса Optional и модели <Post>
        ArrayList<Activity> res = new ArrayList<>();
        post.ifPresent(res::add); //из класса Optional переводим в класс ArrayList
        model.addAttribute("activity", res);
        return "activity-edit";
    }

    @PostMapping("/tournaments/{id}/edit") //получение данных из формы
    public String activityActivityUpdate(@PathVariable(value = "id") long id, @RequestParam String img, @RequestParam String title, @RequestParam String anons, @RequestParam String fullText, Model model,@RequestParam String prize) { //@RequestParam - получение значений из формы. title - получение значений из данного поля
        Activity activity = activityService.findById(id).orElseThrow(
                () -> new RuntimeException()
        ); //orElseTrow() - исключительная ситуация в случае не нахождения записи
        activity.setImg(img);
        activity.setTitle(title); //установка введеного заголовка
        activity.setAnons(anons);
        activity.setFullText(fullText);
        activity.setPrize(prize);
        activityService.save(activity); //сохранение обновлённого объекта
        return "redirect:/tournaments/{id}"; //переадресация пользователя на указанную страницу после добавления статьи
    }

    @PostMapping("/tournament/{id}/remove") //получение данных из формы
    public String newsActivityDelete(@PathVariable(value = "id") long id, Model model) { //@RequestParam - получение значений из формы. title - получение значений из данного поля
        Activity activity = activityService.findById(id).orElseThrow(
                () -> new RuntimeException()
        ); //orElseTrow() - исключительная ситуация в случае не нахождения записи
        activityService.delete(activity); //удаление определенной записи
        return "redirect:/tournaments"; //переадресация пользователя на указанную страницу после удаления статьи
    }
}
