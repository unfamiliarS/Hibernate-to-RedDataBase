package com.app_example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Конфигурация MVC (Model-View-Controller) для приложения.
 * Настраивает простые контроллеры представлений без явной логики.
 */
@Configuration // Помечает класс как класс конфигурации Spring
public class MvcConfig implements WebMvcConfigurer {

    /**
     * Регистрирует простые контроллеры представлений.
     * @param registry Реестр для регистрации контроллеров представлений
     */
    @Override
    public void addViewControllers(@NonNull ViewControllerRegistry registry) {
        // Регистрируем путь "/login" и связываем его с шаблоном "login"
        // Это позволяет избежать создания отдельного контроллера для простых страниц
        registry.addViewController("/login").setViewName("login");
    }
}