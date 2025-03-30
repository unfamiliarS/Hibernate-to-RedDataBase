package com.app_example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app_example.entity.User;
import com.app_example.service.UserService;

/**
 * Контроллер для административных действий
 */
@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    /**
     * Отображение списка всех пользователей
     * @param model Модель для передачи данных в представление
     * @return Шаблон административной панели
     */
    @GetMapping("/admin")
    public String userList(Model model) {
        // Добавляем информацию о текущем пользователе
        model.addAttribute("username", userService.getUserNameFromContext());
        model.addAttribute("isAdmin", userService.isCurrentUserAdmin());
        // Получаем и добавляем список всех пользователей
        model.addAttribute("allUsers", userService.allUsers());

        return "admin";
    }

    /**
     * Обработка действий администратора (удаление пользователей)
     * @param userId ID пользователя для действия
     * @param action Тип действия (например, "delete")
     * @param model Модель для передачи данных
     * @return Перенаправление на страницу админа или выход
     */
    @PostMapping("/admin")
    public String deleteUser(@RequestParam(value = "userId", defaultValue = "" ) Long userId,
                             @RequestParam(value = "action", defaultValue = "" ) String action,
                             Model model) {
                                
        User currentUser = userService.getUserByUsername(userService.getUserNameFromContext());

        if (action.equals("delete")){
            userService.deleteUser(userId);
            if (currentUser.getId() == userId) {
                return "redirect:/logout";
            }
        }
        return "redirect:/admin";
    }

}
