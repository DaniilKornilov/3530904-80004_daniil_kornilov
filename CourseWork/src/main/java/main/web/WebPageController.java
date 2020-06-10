package main.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebPageController {

    @Autowired
    RestController restController;

    @RequestMapping("/login")
    public String login() {
        return "LogIn";
    }

    @RequestMapping("/")
    public String mainPage() {
        return "MainPage";
    }

}