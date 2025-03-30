package com.app_example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.app_example.entity.User;
import com.app_example.service.UserService;

/**
 * Контроллер для обработки регистрации новых пользователей.
 */
@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    /**
     * Обработка GET запроса на страницу регистрации.
     * @param model Модель для передачи данных в представление
     * @return имя шаблона регистрации
     */
    @GetMapping("/registration")
    public String registration(Model model) {
        // Добавляем в модель пустой объект User для формы
        model.addAttribute("userForm", new User());
        return "registration";
    }

    /**
     * Обработка POST запроса при отправке формы регистрации.
     * @param userForm Данные пользователя из формы
     * @param bindingResult Результаты валидации
     * @param model Модель для передачи данных в представление
     * @return перенаправление на главную страницу или возврат к форме с ошибками
     */
    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") User userForm, 
                         BindingResult bindingResult, 
                         Model model) {

        // Проверка ошибок валидации
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        // Попытка сохранения пользователя
        if (!userService.saveUser(userForm)) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }
        
        return "redirect:/login";
    }
}