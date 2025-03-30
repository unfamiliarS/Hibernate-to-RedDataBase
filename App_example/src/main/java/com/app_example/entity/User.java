package com.app_example.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Set;

/**
 * Класс-сущность пользователя системы.
 * Реализует интерфейс UserDetails для интеграции с Spring Security.
 */
@Entity
@Table(name = "user") // Указывает имя таблицы в БД
public class User implements UserDetails {
    
    @Id // Поле является первичным ключом
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq") // Стратегия генерации ID
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1) // Настройки sequence
    private Long id; // Уникальный идентификатор пользователя
    
    private String username; // Логин пользователя
    
    private String password; // Пароль (должен храниться в зашифрованном виде)
    
    // Связь многие-ко-многим с таблицей ролей
    // FetchType.EAGER - роли загружаются сразу вместе с пользователем
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles; // Набор ролей пользователя

    // Конструктор по умолчанию (требуется для JPA)
    public User() {
    }

    // Геттер для ID
    public Long getId() {
        return id;
    }

    // Сеттер для ID
    public void setId(Long id) {
        this.id = id;
    }

    // Реализация метода из UserDetails - возвращает имя пользователя
    @Override
    public String getUsername() {
        return username;
    }

    // Сеттер для имени пользователя
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Реализация метода из UserDetails.
     * Возвращает коллекцию прав/ролей пользователя.
     * В данном случае возвращает набор ролей пользователя.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    // Реализация метода из UserDetails - возвращает пароль
    @Override
    public String getPassword() {
        return password;
    }

    // Сеттер для пароля
    public void setPassword(String password) {
        this.password = password;
    }

    // Геттер для набора ролей
    public Set<Role> getRoles() {
        return roles;
    }

    // Сеттер для набора ролей
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    // Остальные методы UserDetails (не реализованы в этом примере)
    @Override
    public boolean isAccountNonExpired() {
        return true; // Аккаунт не просрочен
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Аккаунт не заблокирован
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Учетные данные не просрочены
    }

    @Override
    public boolean isEnabled() {
        return true; // Аккаунт включен/активен
    }
}