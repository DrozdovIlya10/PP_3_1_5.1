package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/panel")
    public String adminPanel(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("people", userService.getListUsers());
        model.addAttribute("user", userService.getUserByUsername(auth.getName()));
        model.addAttribute("new_user", new User());
        model.addAttribute("roles", roleService.getListRoles());
        return "adminPanel";
    }

    @GetMapping(value = "/user/{id}")
    public String user(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.getIdForUser(id));
        return "user";
    }

    @PutMapping(value = "/edit/{id}")
    public String update(@PathVariable("id") long id, @ModelAttribute("user") User user, @RequestParam("listRoles") ArrayList<Long> roles) {
        User user1 = userService.getIdForUser(id);
        user1.setUsername(user.getUsername());
        user1.setLastname(user.getLastname());
        user1.setAge(user.getAge());
        user1.setEmail(user.getEmail());
        user1.setRoles(roleService.findByIdRoles(roles));

        userService.setUserForEdit(user1);
        return "redirect:/admin/panel";
    }

    @DeleteMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.setIdForDelete(id);
        return "redirect:/admin/panel";
    }

    @PostMapping(value = "/panel")
    public String create(@ModelAttribute("user") User user) {
        userService.setUserForSave(user);
        return "redirect:/admin/panel";
    }

}
