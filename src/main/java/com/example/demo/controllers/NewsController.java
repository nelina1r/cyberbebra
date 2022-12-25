package com.example.demo.controllers;

import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.repo.PostRepository;
import com.example.demo.services.PostService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class NewsController {

    @Autowired //анотация для создания переменной, ссылающейся на репозиторий
    private PostService postService; //указание репозитория, к которому обращаемся и название пееременной
    //private PostRepository postRepository;

    @GetMapping("/news")
    public String activity(Model model) {
        model.addAttribute("posts", postService.findAll(0,10).getContent());
        //model.addAttribute("posts", postService.findAll()); //передача значений
        model.addAttribute("title", "Новости");
        return "news";
    }

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        if(!userService.registration(user)) {
            model.addAttribute("errorMessage", "Пользователь с таким логином уже существует");
            return "registration";
        }
        return "redirect:/login";
    }

//    public NewsController(PostRepository postRepository) {
//        this.postRepository = postRepository;
//    }

//    @RequestMapping(value = "/news", method = RequestMethod.GET)
//    public String listBooks(
//            Model model,
//            @RequestParam("page") Optional<Integer> page,
//            @RequestParam("size") Optional<Integer> size) {
//        int currentPage = page.orElse(1);
//        int pageSize = size.orElse(5);
//
//        Page<Post> postPage = postService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
//
//        model.addAttribute("postPage", postPage);
//
//        int totalPages = postPage.getTotalPages();
//        if (totalPages > 0) {
//            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
//                    .boxed()
//                    .collect(Collectors.toList());
//            model.addAttribute("pageNumbers", pageNumbers);
//        }
//
//        return "news";
//    }

//    @GetMapping("/news")
//    public String news(/*HttpServletRequest request, */Model model) {
////        int page = 0; //номер страницы по умолчанию равен 0
////        int size = 10; //размер страницы по умолчанию 10
////        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
////            page = Integer.parseInt(request.getParameter("page")) - 1;
////        }
////
////        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
////            size = Integer.parseInt(request.getParameter("size"));
////        }
//
////        model.addAttribute("posts", postRepository.findAll(PageRequest.of(page, size)).getContent());
////        Pageable firstPageWithTwoElements = PageRequest.of(0, 2);
////        Pageable pageable = PageRequest.of(0, 10);
////        Page<Post> page = postRepository.findAll(pageable);
//        model.addAttribute("posts", postService.findAll(0,10).getContent());
////        model.addAttribute("posts", postService.findAll()); //передача значений
////        model.addAttribute("posts", postRepository.findAll());
//        model.addAttribute("title", "Новости");
//        return "news";
//    }

    @GetMapping("/news/add") //GetMapping - пользователь переходит по определённому адресу
    public String newsAdd(Model model) {
        return "news-add";
    }

    @PostMapping("/news/add") //получение данных из формы
    public String newsPostAdd(@AuthenticationPrincipal User user, @RequestParam String img, @RequestParam String title, @RequestParam String anons, @RequestParam String fullText, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date/*@RequestParam(value="date") @DateTimeFormat(pattern="dd-MM-yyyy") Date date /@RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") Date date*/, Model model) { //@RequestParam - получение значений из формы. title - получение значений из данного поля
        Post post = new Post(img, title, anons, fullText, date, user.getUsername()); //объект на основе модели Post с названием post. (title, anons, fullText) - передача параметров
        postService.save(post); //сохранение объекта и добавление в бд -> обращение к репозиторию -> обращение к функции save и передача в него объекта, который необходимо сохранить => добавление в таблицу post навых статей, полученных от пользователя
        //postRepository.save(post);
        return "redirect:/news"; //переадресация пользователя на указанную страницу после добавления статьи
    }

   @GetMapping("/news/{id}") //{id} - динамическое значение url-адреса
   public String newsDetails(@PathVariable(value = "id") long id, Model model) { //@PathVariable - анотация, принимающая динамический параметр из url-адреса (в определённый параметр (long id) помещается значение, полученное из url-адреса
        Optional<Post> post= postService.findById(id); //нахождение записи по id и помещение в объект post на основе класса Optional и модели <Post>
        if(post.isPresent()) {
            ArrayList<Post> res = new ArrayList<>();
            post.ifPresent(res::add); //из класса Optional переводим в класс ArrayList
            model.addAttribute("post", res);
            return "news-details";
        } else {
            return "redirect:/news"; //перенаправление на указанную страницу
        }
    }

    @GetMapping("/news/{id}/edit") //редактирование статьи
    public String newsEdit(@PathVariable(value = "id") long id, Model model) { //@PathVariable - анотация, принимающая динамический параметр из url-адреса (в определённый параметр (long id) помещается значение, полученное из url-адреса
        if(!postService.existsById(id)){ //try - если определённая запись по определённому id не была найдена. иначе false
            return "redirect:/news/{id}"; //перенаправление на указанную страницу
        }
        //Optional<Post> post= postRepository.findById(id);
        Optional<Post> post= postService.findById(id); //нахождение записи по id и помещение в объект post на основе класса Optional и модели <Post>
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add); //из класса Optional переводим в класс ArrayList
        model.addAttribute("post", res);
        return "news-edit";
    }

    @PostMapping("/news/{id}/edit") //получение данных из формы
    public String newsPostUpdate(@PathVariable(value = "id") long id, @RequestParam String img, @RequestParam String title, @RequestParam String anons, @RequestParam String fullText, Model model) { //@RequestParam - получение значений из формы. title - получение значений из данного поля
        Post post = postService.findById(id).orElseThrow(
                () -> new RuntimeException()
        ); //orElseTrow() - исключительная ситуация в случае не нахождения записи
        post.setImg(img);
        post.setTitle(title); //установка введеного заголовка
        post.setAnons(anons);
        post.setFullText(fullText);
        postService.save(post); //сохранение обновлённого объекта
        return "redirect:/news/{id}"; //переадресация пользователя на указанную страницу после добавления статьи
    }

    @PostMapping("/news/{id}/remove") //получение данных из формы
    public String newsPostDelete(@PathVariable(value = "id") long id, Model model) { //@RequestParam - получение значений из формы. title - получение значений из данного поля
        Post post = postService.findById(id).orElseThrow(
                () -> new RuntimeException()
        ); //orElseTrow() - исключительная ситуация в случае не нахождения записи
        postService.delete(post); //удаление определенной записи
        return "redirect:/news"; //переадресация пользователя на указанную страницу после удаления статьи
    }

}