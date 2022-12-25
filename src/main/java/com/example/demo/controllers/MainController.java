package com.example.demo.controllers;

import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.repo.PostRepository;
import com.example.demo.services.ActivityService;
import com.example.demo.services.PostService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.Date;

//@RestController
@Controller
public class MainController { //класс, отвещающий за обработку всех переходов по сайту (@Controller)

    @Autowired private PostService postService; //указание репозитория, к которому обращаемся и название пееременной
    @Autowired private ActivityService activityService;

}
