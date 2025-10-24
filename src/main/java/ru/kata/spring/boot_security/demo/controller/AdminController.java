package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
                                Model model,
                                @AuthenticationPrincipal UserDetails userDetails) {
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
                          RedirectAttributes redirectAttributes) {
        try {
            if (roleIds == null || roleIds.isEmpty()) {
                roleIds = List.of(2L);
            }
            userService.saveUser(user, roleIds);
            redirectAttributes.addFlashAttribute("successMessage", "Пользователь успешно добавлен!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при добавлении пользователя: " + e.getMessage());
            redirectAttributes.addAttribute("showForm", true);
        }
        return "redirect:/admin?view=admin";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable("id") Long id,
                             @ModelAttribute("user") User user,
                             @RequestParam(value = "roles", required = false) List<Long> roleIds,
                             RedirectAttributes redirectAttributes) {
        try {
            userService.updateUser(id, user, roleIds); // новая сигнатура
            redirectAttributes.addFlashAttribute("successMessage", "Редактирование выполнено!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка редактирования пользователя: " + e.getMessage());
        }
        return "redirect:/admin?view=admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUserById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Пользователь успешно удален!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при удалении пользователя: " + e.getMessage());
        }
        return "redirect:/admin?view=admin";
    }
}