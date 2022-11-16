package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String open() {
        return "/index";
    }

    //// ДЛЯ РЕГЕСТРАЦИИ //////
    @GetMapping("/registration")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user) {
        userService.setUserForSave(user);
        return "redirect:/admin";
    }

    ////// ДЛЯ АДМИНА ////////

    @GetMapping("/admin")
    public String index(Model model) {
        model.addAttribute("people", userService.getListUsers());
        return "admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.setIdAndUserForEdit(id));
        return "edit";
    }

    @PostMapping("admin/edit/{id}")
    public String update(@PathVariable("id") long id, @ModelAttribute("user") User user) {
        userService.getIdForUser(id, user);
        return "redirect:/admin";
    }

    @PostMapping("admin/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.setIdForDelete(id);
        return "redirect:/admin";
    }

    ///// ДЛЯ ПОЛЬЗОВАТЕЛЯ /////////

    @GetMapping("/user")
    public String show(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userService.getUserByUsername(auth.getName()));
        return "user";
    }


}
