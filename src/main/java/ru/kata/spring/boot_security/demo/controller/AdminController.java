package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String showAdminPage(@RequestParam(value = "view", defaultValue = "admin") String view,
                                @RequestParam(required = false) Boolean showForm,
                                Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("currentUser", userService.getUserByEmail(userDetails.getUsername()));
        model.addAttribute("activeView", view);
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("newUser", new User());
        model.addAttribute("showAddForm", Boolean.TRUE.equals(showForm));
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("newUser") User user,
                          @RequestParam(value = "roles", required = false) List<Long> roleIds,
                          Model model) {
        try {
            userService.saveUser(user, roleIds);
            model.addAttribute("successMessage", "Пользователь успешно добавлен!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка при добавлении пользователя: " + e.getMessage());
            model.addAttribute("showForm", true);
        }
        return "redirect:/admin?view=admin";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable("id") Long id,
                             @ModelAttribute("user") User user,
                             @RequestParam(value = "roles", required = false) List<Long> roleIds,
                             Model model) {
        try {
            userService.updateUser(id, user, roleIds);
            model.addAttribute("successMessage", "Редактирование выполнено!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка редактирования пользователя: " + e.getMessage());
        }
        return "redirect:/admin?view=admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        try {
            userService.deleteUserById(id);
            model.addAttribute("successMessage", "Пользователь успешно удален!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка при удалении пользователя: " + e.getMessage());
        }
        return "redirect:/admin?view=admin";
    }
}