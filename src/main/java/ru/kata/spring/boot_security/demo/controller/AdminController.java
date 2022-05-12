package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserServiceImpl userService;
    private RoleServiceImpl roleService;

    @Autowired
    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    public AdminController() {}

    @GetMapping
    public String printUsers(Model model) {
        model.addAttribute("userSet", userService.getAllUsers());
        return "all_users";
    }

    // добавить пользователя

    @GetMapping(value = "/new")
    public String newUserForm(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "add";
    }

    @PostMapping(value = "users/add")
    public String createNewUser(@ModelAttribute("user") User user
            , @RequestParam(value = "roles") String[] roles) {
        user.setRoles(roleService.getSetOfRoles(roles));
        userService.addUser(user);
        return "redirect:/admin";
    }

    // изменение пользователя

    @GetMapping("/{id}/edit")
    public String editUserForm(Model model, @PathVariable("id") long id) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user
            , @RequestParam(value = "roles") String[] roles) {
        user.setRoles(roleService.getSetOfRoles(roles));
        userService.editUser(user);
        return "redirect:/admin";
    }

    // удаление пользователя

    @DeleteMapping("/{id}/delete")
    public String deleteUserById(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }


}
