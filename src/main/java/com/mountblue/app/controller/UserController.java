package com.mountblue.app.controller;

import com.mountblue.app.model.User;
import com.mountblue.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Optional;


@Controller
@RequestMapping("/user")
public class UserController
{
    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public String home(@RequestParam("name") String name) {

        return "redirect:/"+name;
    }

    @GetMapping("/add")
    public String addUser(@ModelAttribute("user") User user) {

        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping("/check")
    public String check(@RequestParam("name") String name, @RequestParam("password") String pass, Model model, HttpSession session) {

        Optional<User> optional = userService.findByName(name);
        if (optional.isPresent()) {
            User user = optional.get();
            if (user.getPassword().equals(pass)) {
                session.setAttribute("sessionUserId", user.getId());
                model.addAttribute("user", user);
                return "redirect:/dashboard/" + user.getId();
            }
        }
        return "redirect:/login";
    }
}
