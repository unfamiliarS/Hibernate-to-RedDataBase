package com.app_example.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app_example.entity.Role;
import com.app_example.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;

/**
 * Сервисный слой для работы с ролями пользователей.
 * Обеспечивает бизнес-логику работы с ролями.
 */
@Service // Аннотация указывает, что это Spring-компонент сервисного слоя
public class RoleService {

    // Внедрение зависимости репозитория для работы с БД
    @Autowired
    private RoleRepository roleRepository;
    
    /**
     * Получает роль по её идентификатору.
     * 
     * @param roleId идентификатор роли
     * @return найденная роль
     * @throws EntityNotFoundException если роль с указанным ID не найдена
     */
    public Role getRole(Long roleId) {
        // Используем Optional для безопасной работы с возможным null
        Optional<Role> userRole = roleRepository.findById(roleId);
        
        // Проверяем, найдена ли роль
        if (!userRole.isPresent()) {
            throw new EntityNotFoundException("Role not found with id: " + roleId);
        }
        
        // Возвращаем найденную роль
        return userRole.get();
    }
    
}