package com.app_example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app_example.entity.User;

/**
 * Репозиторий для работы с пользователями в базе данных.
 * Расширяет JpaRepository и добавляет метод поиска по имени пользователя.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Находит пользователя по имени пользователя (логину).
     * Реализация будет автоматически сгенерирована Spring Data JPA.
     * 
     * @param username имя пользователя для поиска
     * @return найденный пользователь или null, если не найден
     */
    User findByUsername(String username);
    
}