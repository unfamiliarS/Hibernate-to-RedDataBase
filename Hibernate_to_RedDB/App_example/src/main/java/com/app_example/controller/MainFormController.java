package com.app_example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.app_example.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Контроллер для обработки запросов главной страницы.
 * Обеспечивает отображение основной формы приложения.
 */
@Controller // Аннотация указывает, что это Spring MVC Controller
public class MainFormController {

    @Autowired // Внедрение зависимости сервиса пользователей
    private UserService userService;

    /**
     * Обработчик GET-запроса для корневого URL ("/").
     * Добавляет в модель информацию о текущем пользователе.
     * 
     * @param request HTTP-запрос
     * @param model Модель для передачи данных в представление
     * @param session HTTP-сессия
     * @return имя шаблона представления "main_form"
     */
    @GetMapping("/")
    public String mainFormRoot(HttpServletRequest request, Model model, HttpSession session) {
        // Добавляем имя текущего пользователя в модель
        model.addAttribute("username", userService.getUserNameFromContext());
        
        // Добавляем флаг, является ли пользователь администратором
        model.addAttribute("isAdmin", userService.isCurrentUserAdmin());
        
        return "main_form";
    }
}