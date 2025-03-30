package com.app_example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app_example.entity.Role;

/**
 * Репозиторий для работы с ролями в базе данных.
 * Наследует стандартные CRUD-операции от JpaRepository.
 */
@Repository // Аннотация указывает, что это Spring-компонент для доступа к данным
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Наследует все основные методы работы с БД:
    // save(), findById(), findAll(), delete(), count() и другие
}