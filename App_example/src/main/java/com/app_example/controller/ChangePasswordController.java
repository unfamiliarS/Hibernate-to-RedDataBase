package com.app_example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app_example.service.UserService;

/**
 * Контроллер для обработки смены пароля пользователя
 */
@Controller
public class ChangePasswordController {

    @Autowired
    private UserService userService;

    /**
     * Отображение формы смены пароля
     * @param model Модель для передачи данных в представление
     * @return Имя шаблона формы смены пароля
     */
    @GetMapping("/passwd")
    public String showChangePasswordForm(Model model) {
        // Добавляем текущее имя пользователя и статус администратора
        model.addAttribute("username", userService.getUserNameFromContext());
        model.addAttribute("isAdmin", userService.isCurrentUserAdmin());
        return "change_password";
    }

    /**
     * Обработка запроса на смену пароля
     * @param currentPassword Текущий пароль (для проверки)
     * @param newPassword Новый пароль
     * @param confirmPassword Подтверждение нового пароля
     * @param model Модель для передачи данных и ошибок
     * @return Перенаправление или возврат к форме с ошибками
     */
    @PostMapping("/passwd")
    public String changePassword(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {

        // Проверка совпадения нового пароля и подтверждения
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("passwordMismatchError", "Новый пароль и подтверждение не совпадают");
            return showChangePasswordForm(model); // Повторно отображаем форму с ошибкой
        }

        // Попытка смены пароля через сервис
        if (!userService.changePassword(currentPassword, newPassword)) {
            model.addAttribute("currentPasswordError", "Текущий пароль указан неверно");
            return showChangePasswordForm(model);
        }

        // После успешной смены пароля - выход и перенаправление на страницу входа
        return "redirect:/logout";
    }
}