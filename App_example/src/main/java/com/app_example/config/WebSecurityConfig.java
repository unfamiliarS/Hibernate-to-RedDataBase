package com.app_example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.app_example.service.UserService;

// Помечаем класс как конфигурационный для Spring
@Configuration
// Включаем безопасность веб-приложения
@EnableWebSecurity
public class WebSecurityConfig {
    
    // Внедряем сервис для работы с пользователями
    @Autowired
    UserService userService;

    // Создаем бин для шифрования паролей
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        // Используем BCrypt с силой хеширования по умолчанию (10)
        return new BCryptPasswordEncoder();
    }  

    // Основной бин для настройки безопасности
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Отключаем CSRF защиту (для упрощения, в production нужно включить)
            .csrf(csrf -> csrf.disable())
            
            // Настраиваем авторизацию запросов
            .authorizeHttpRequests(auth -> auth
                // Доступ к /admin только для пользователей с ролью ADMIN
                .requestMatchers("/admin").hasRole("ADMIN")
                
                // Доступ к /registration только для неаутентифицированных пользователей
                .requestMatchers("/registration").not().fullyAuthenticated()
                
                // Все остальные запросы требуют аутентификации
                .anyRequest().authenticated()
            )
            
            // Настраиваем форму входа
            .formLogin(formLogin -> formLogin
                // Указываем кастомную страницу логина
                .loginPage("/login")
                
                // URL для перенаправления после успешного входа
                .defaultSuccessUrl("/")
                
                // Разрешаем доступ к странице логина всем
                .permitAll()
            )
            
            // Настраиваем выход из системы
            .logout(logout -> logout
                // URL для выхода
                .logoutUrl("/logout")
                
                // URL для перенаправления после выхода
                .logoutSuccessUrl("/login")
            );
        
        return http.build();
    }

    // Настраиваем аутентификацию
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // Указываем наш сервис для загрузки данных пользователей
        // и кодировщик паролей
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }
}