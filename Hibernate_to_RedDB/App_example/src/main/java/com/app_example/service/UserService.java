package com.app_example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app_example.entity.Role;
import com.app_example.entity.User;
import com.app_example.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Сервис для работы с пользователями.
 * Реализует UserDetailsService для интеграции с Spring Security.
 */
@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Загрузка пользователя по имени для Spring Security.
     * @param username имя пользователя
     * @return UserDetails объект пользователя
     * @throws UsernameNotFoundException если пользователь не найден
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден!");
        }
        return user;
    }

    /**
     * Получение пользователя по имени.
     * @param username имя пользователя
     * @return объект пользователя или null
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Получение списка всех пользователей.
     * @return список пользователей
     */
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    /**
     * Сохранение нового пользователя.
     * @param user объект пользователя
     * @return true если сохранение успешно, false если пользователь уже существует
     */
    public boolean saveUser(User user) {
        // Проверка существования пользователя
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return false;
        }
        
        // Назначение ролей (по умолчанию ROLE_ADMIN)
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleService.getRole(2L)); // 2L - это ID ROLE_ADMIN

        // Шифрование пароля и сохранение
        user.setRoles(userRoles);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    /**
     * Удаление пользователя.
     * @param userId ID пользователя
     * @return true если удаление успешно, false если пользователь не найден
     */
    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    /**
     * Получение имени текущего аутентифицированного пользователя.
     * @return имя пользователя
     */
    public String getUserNameFromContext() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * Получение роли текущего пользователя.
     * @return строка с названиями ролей через запятую
     */
    public String getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(", "));
        }
        return "ROLE_ANONYMOUS";
    }

    /**
     * Проверка, является ли текущий пользователь администратором.
     * @return true если пользователь имеет роль ADMIN
     */
    public boolean isCurrentUserAdmin() {
        return getCurrentUserRole().contains("ROLE_ADMIN");
    }

    /**
     * Смена пароля текущего пользователя.
     * @param currentPassword текущий пароль
     * @param newPassword новый пароль
     * @return true если смена успешна, false если пароль неверный или пользователь не найден
     */
    public boolean changePassword(String currentPassword, String newPassword) {
        User user = userRepository.findByUsername(getUserNameFromContext());
        
        if (user == null || !bCryptPasswordEncoder.matches(currentPassword, user.getPassword())) {
            return false;
        }

        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }
}