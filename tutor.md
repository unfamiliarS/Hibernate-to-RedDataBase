# Spring + Hibernate + RedDataBase

## Spring
Spring — это популярный фреймворк для разработки на Java, который используют для создания корпоративных приложений. Он предоставляет разработчикам инструменты для создания сложных систем, например многопользовательских корпоративных веб-приложений со множеством функций для бизнеса. Spring позволяет быстро создавать приложения, которые умеют работать с базами данных и облаками, состоят из разных модулей, обмениваются данными с пользователями через интернет по защищённым каналам.

## Hibernate
Hibernate — это фреймворк для объектно-реляционного отображения баз данных (ORM), разработанный для языка программирования Java. Он упрощает взаимодействие разработчика с разными видами баз данных, позволяя ему работать с привычными объектами Java вместо написания SQL-запросов.

## Настройка RedDataBase с Hibernate в Spring
Чтобы заставить работать RedDataBase с Hibernate в экосистеме Spring, нужно выполнить следующие шаги:

1. **Создать проект Spring**. Это можно сделать через IDE или через [start.spring.io](https://start.spring.io/).

2. **Добавить зависимости** в файл конфигурации сборки проекта.

### Для Gradle
В файл `build.gradle` добавить в раздел `dependencies`:
```groovy
dependencies {
    // hibernate-community-dialects для доступа к диалекту firebirdsql
    implementation 'org.hibernate.orm:hibernate-community-dialects'
    // JDBC драйвер
    runtimeOnly 'org.firebirdsql.jdbc:jaybird:5.0.6.java11'
    // ...
}
```

### Для Maven
В файл `pom.xml` добавить в раздел `dependencies`:
``` xml
<dependencies>
      <!-- Hibernate Community Dialects (для Firebird) -->
      <dependency>
          <groupId>org.hibernate.orm</groupId>
          <artifactId>hibernate-community-dialects</artifactId>
          <version>6.4.1.Final</version> <!-- проверить актуальную версию -->
      </dependency>
      
      <!-- JDBC драйвер Firebird -->
      <dependency>
          <groupId>org.firebirdsql.jdbc</groupId>
          <artifactId>jaybird</artifactId>
          <version>5.0.6.java11</version>
          <scope>runtime</scope>
      </dependency>
          ...
</dependencies>
```

## JDBC-драйвер

JDBC-драйвер — это программный компонент, который:
- Обеспечивает взаимодействие Java-приложений с базами данных  
- Устанавливает подключения к БД  
- Реализует протоколы передачи запросов и результатов между клиентом и БД

## Диалект Hibernate

Диалект Hibernate — это настройка, которая:
- Генерирует SQL, оптимизированный для конкретной СУБД  
- Позволяет использовать специфичные функции базы данных  
- Преобразует HQL-запросы в родной SQL-формат БД (так как HQL не зависит от БД)

## Настройка подключения

3. **Добавить в `application.properties`**:

```properties
# Настройки диалекта и драйвера
# В нашем случае будем использовать FirebirdDialect и FBDriver
spring.jpa.properties.hibernate.dialect=org.hibernate.community.dialect.FirebirdDialect
spring.datasource.driver-class-name=org.firebirdsql.jdbc.FBDriver

# Параметры подключения к БД
spring.datasource.url=jdbc:firebird://localhost:3050//путь/к/базе/HIBERNATE_TUTOR.FDB
spring.datasource.username=sysdba
spring.datasource.password=*****
```

Отлично! Настройка выполнена и подключение к RedDataBase теперь доступно.

## Для наглядности напишем небольшое web-приложение

### Технологический стек
- **JDK 8+**
- **Gradle** - система сборки
- **Spring**:
  - Spring Boot
  - Spring MVC
  - Spring Security
- **Hibernate** - ORM
- **Thymeleaf** - шаблонизатор
- **RedDataBase** - БД
- **RedExpert** - СУБД

### Функционал приложения
#### Общедоступные страницы:
1. Страница регистрации
2. Страница входа (логина)

#### Для авторизованных пользователей:
1. Личный кабинет
2. Смена пароля

#### Для администраторов:
1. Административная панель

## Структура проекта

```
src/
├── main/
│ ├── java/
│ │ └── com/
│ │ └── example/
│ │ ├── config/
│ │ ├── controller/
│ │ ├── entity/
│ │ ├── repository/
│ │ ├── service/
│ │ └── ExampleApplication.java
│ ├── resources/
│ │ └── static/
│ │ └── templates/
│ └── application.properties
└── test/
```
### Пояснения:
1. **config/** - содержит классы конфигурации Spring (SecurityConfig, WebConfig и др.)
2. **controller/** - MVC контроллеры для обработки запросов
3. **entity/** - JPA-сущности (User, Role и др.)
4. **repository/** - Spring Data JPA репозитории
5. **service/** - бизнес-логика приложения
6. **templates/** - Thymeleaf шаблоны

## Конфигурация проекта

### build.gradle
```groovy
plugins {
	id 'java'
	id 'org.springframework.boot' version '3.3.4'
	id 'io.spring.dependency-management' version '1.1.6'
}

group = 'com.app_example'
version = '0.0.1-SNAPSHOT'

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
  implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
  implementation 'org.springframework.boot:spring-boot-starter-web'
  implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
  implementation 'org.springframework.boot:spring-boot-starter-security'
  
  implementation 'org.hibernate.orm:hibernate-community-dialects'
  runtimeOnly 'org.firebirdsql.jdbc:jaybird:5.0.6.java11'
  
  testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```
### application.properties

```properties
spring.application.name=app_example

spring.datasource.url=jdbc:firebird://localhost:3050//home/redos/db/HIBERNATE_TUTOR.FDB
spring.datasource.driver-class-name=org.firebirdsql.jdbc.FBDriver

spring.datasource.username=sysdba
spring.datasource.password=******** # Заменить на свой
spring.jpa.show-sql=true
spring.jpa.generate-ddl=false
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
spring.jpa.properties.hibernate.dialect=org.hibernate.community.dialect.FirebirdDialect
spring.main.allow-circular-references=true
```

## Структура сущностей на примере User

```java
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
@Table(name = "user") // Соответствует таблице "user" в БД
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(
        name = "user_seq",
        sequenceName = "user_sequence",
        allocationSize = 1
    )
    private Long id; // Уникальный идентификатор
    private String username; // Логин
    private String password; // Зашифрованный пароль
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles; // Роли пользователя

    // Реализация методов UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    // Остальные методы UserDetails...
    
    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    // ... остальные геттеры и сеттеры
}
```
Более подробные объяснения c комментариями см. в примере проекта. <ссылка на папку>

## Инициализация приложения

Пришло время создать пустую БД, это нужно сделать перед первым запуском приложения и только один раз.

После настройки и создания проекта и пустой бд, пробуем запустить приложение.
В итоге вы должны увидеть что-то похожее:

```bash
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::                (v3.3.4)

2025-03-29T16:40:40.269+03:00  INFO 15936 --- [app_example] [           main] c.app_example.ExampleApplication  : Starting ExampleApplication using Java 21.0.4 with PID 15936
2025-03-29T16:40:40.271+03:00  INFO 15936 --- [app_example] [           main] c.app_example.ExampleApplication  : No active profile set, falling back to 1 default profile: "default"
2025-03-29T16:40:40.926+03:00  INFO 15936 --- [app_example] [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2025-03-29T16:40:41.002+03:00  INFO 15936 --- [app_example] [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 67 ms. Found 2 JPA repository interfaces.
2025-03-29T16:40:41.554+03:00  INFO 15936 --- [app_example] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8080 (http)
2025-03-29T16:40:41.569+03:00  INFO 15936 --- [app_example] [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2025-03-29T16:40:41.569+03:00  INFO 15936 --- [app_example] [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.30]
2025-03-29T16:40:41.638+03:00  INFO 15936 --- [app_example] [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2025-03-29T16:40:41.640+03:00  INFO 15936 --- [app_example] [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1321 ms
2025-03-29T16:40:41.797+03:00  INFO 15936 --- [app_example] [           main] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
2025-03-29T16:40:41.850+03:00  INFO 15936 --- [app_example] [           main] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 6.5.3.Final
2025-03-29T16:40:41.882+03:00  INFO 15936 --- [app_example] [           main] o.h.c.internal.RegionFactoryInitiator    : HHH000026: Second-level cache disabled
2025-03-29T16:40:42.174+03:00  INFO 15936 --- [app_example] [           main] o.s.o.j.p.SpringPersistenceUnitInfo      : No LoadTimeWeaver setup: ignoring JPA class transformer
2025-03-29T16:40:42.206+03:00  INFO 15936 --- [app_example] [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2025-03-29T16:40:42.388+03:00  INFO 15936 --- [app_example] [           main] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection org.firebirdsql.jdbc.FBConnection@1999149e
2025-03-29T16:40:42.390+03:00  INFO 15936 --- [app_example] [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2025-03-29T16:40:43.185+03:00  INFO 15936 --- [app_example] [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
Hibernate: create table role (id bigint not null, name varchar(255), primary key (id))
Hibernate: create table "user" (id bigint not null, password varchar(255), username varchar(255), primary key (id))
Hibernate: create table "user_roles" ("user_id" bigint not null, roles_id bigint not null, primary key ("user_id", roles_id))
Hibernate: create sequence role_sequence
Hibernate: create sequence user_sequence
Hibernate: alter table "user_roles" add constraint FKsoyrbfa9510yyn3n9as9pfcsx foreign key (roles_id) references role
Hibernate: alter table "user_roles" add constraint FK40cm955hgg5oxf1oax8mqw0c4 foreign key ("user_id") references "user"
2025-03-29T16:40:43.349+03:00  INFO 15936 --- [app_example] [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2025-03-29T16:40:43.710+03:00  WARN 15936 --- [app_example] [           main] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
2025-03-29T16:40:43.728+03:00  WARN 15936 --- [app_example] [           main] r$InitializeUserDetailsManagerConfigurer : Global AuthenticationManager configured with an AuthenticationProvider bean. UserDetailsService beans will not be used for username/password login. Consider removing the AuthenticationProvider bean. Alternatively, consider using the UserDetailsService in a manually instantiated DaoAuthenticationProvider.
2025-03-29T16:40:44.159+03:00  INFO 15936 --- [app_example] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path '/'
2025-03-29T16:40:44.169+03:00  INFO 15936 --- [app_example] [           main] c.app_example.ExampleApplication  : Started ExampleApplication in 4.291 seconds (process running for 4.689)
```
Видим, что у нас создались таблицы, последовательности и прочие элементы по нашим сущностям.

Через **RedExpert** можем посмотреть, как именно изменилась БД – в ней создались 3 пустые таблицы. Далее нужно добавить роли пользователей в таблицу `role`. В RedExpert это делается через: `‘выполнить sql-скрипт из файла’` -> `галочка рядом с ‘Использовать подключение’` -> `вводим следующий код:`
```sql
INSERT INTO ROLE(id, name) VALUES (1, 'ROLE_USER');
INSERT INTO ROLE(id, name) VALUES (2, 'ROLE_ADMIN');
```
-> `'начать'`

Снова запускаем приложение и, переходя по адресу http://localhost:8080/registration, регистрируемся, а затем авторизуемся. (По умолчанию создаётся пользователь-администратор для наглядности, но по желанию можно добавить логику изменения ролей, например правами того же админа).

На этом приложение готово. Ознакомиться с кодом можно здесь <добавить ссылку на проект>.
