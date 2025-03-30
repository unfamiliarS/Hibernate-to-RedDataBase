package com.app_example.entity;

import org.springframework.security.core.GrantedAuthority;
import jakarta.persistence.*;
import java.util.Set;

/**
 * Класс-сущность роли пользователя.
 * Реализует интерфейс GrantedAuthority для интеграции с Spring Security.
 * Каждая роль представляет собой право доступа в системе.
 */
@Entity
@Table(name = "role") // Указывает имя таблицы в базе данных
public class Role implements GrantedAuthority {
    
    @Id // Поле является первичным ключом
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE, // Используем последовательность для генерации ID
        generator = "role_seq"
    )
    @SequenceGenerator(
        name = "role_seq", // Имя генератора
        sequenceName = "role_sequence", // Имя последовательности в БД
        allocationSize = 1 // Инкремент последовательности
    )  
    private Long id; // Уникальный идентификатор роли
    
    private String name; // Название роли (например, "ROLE_ADMIN", "ROLE_USER")
    
    /**
     * Связь многие-ко-многим с пользователями.
     * @Transient - указывает, что это поле не должно сохраняться в БД,
     * так как связь уже определена в классе User через mappedBy.
     * Это "обратная" сторона двунаправленной связи.
     */
    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users; // Пользователи, имеющие данную роль

    // Конструктор по умолчанию (требуется для JPA)
    public Role() {
    }

    // Конструктор с ID (может использоваться для поиска существующей роли)
    public Role(Long id) {
        this.id = id;
    }

    // Полный конструктор
    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Геттер для ID
    public Long getId() {
        return id;
    }

    // Сеттер для ID
    public void setId(Long id) {
        this.id = id;
    }

    // Геттер для названия роли
    public String getName() {
        return name;
    }

    // Сеттер для названия роли
    public void setName(String name) {
        this.name = name;
    }

    // Геттер для списка пользователей с этой ролью
    public Set<User> getUsers() {
        return users;
    }

    // Сеттер для списка пользователей
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    /**
     * Реализация метода из интерфейса GrantedAuthority.
     * Возвращает название роли как authority (право доступа).
     * Используется Spring Security для проверки прав доступа.
     */
    @Override
    public String getAuthority() {
        return getName();
    }
}