# Findings

## Dependencies for usage of Redis

### Spring Boot Starter Data Redis

To use Redis with Spring Boot, you need to include the `spring-boot-starter-data-redis` dependency in your `pom.xml` file. 
This starter provides all the necessary components to interact with Redis, including connection factories, templates, and repositories.

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```