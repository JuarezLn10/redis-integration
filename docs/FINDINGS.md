# Findings

## 1. Dependencies for usage of Redis

### Spring Boot Starter Data Redis

To use Redis with Spring Boot, you need to include the `spring-boot-starter-data-redis` dependency in your `pom.xml` file. 
This starter provides all the necessary components to interact with Redis, including connection factories, templates, and repositories.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

## 2. Configuration At Application File

To configure Redis in a Spring Boot application, you have to enable Redis repositories and set up the connection factory. This can be done in the main application class or a separate configuration class.

```java
@SpringBootApplication
@EnableJpaAuditing
@EnableMongoAuditing
@EnableRedisRepositories
public class RedisIntegrationApplication {
    static void main(String[] args) {
        SpringApplication.run(RedisIntegrationApplication.class, args);
    }
}
```

## 3. Redis Connection Factory

First, you'll need to define environment variables for the Redis host and port. This allows you to configure the connection to your Redis server.

```properties
spring.data.redis.host=${REDIS_HOST}
spring.data.redis.port=${REDIS_PORT}
spring.data.redis.database=0
spring.data.redis.password=${REDIS_PASSWORD:}
spring.cache.type=redis
```

Then, you can create a configuration class to set up the Redis connection factory and the Redis template. The connection factory is responsible for creating connections to the Redis server, while the Redis template provides a high-level abstraction for performing Redis operations.

```java
@Configuration
public class RedisConfiguration {
    
    @Value("${spring.data.redis.host}")
    private String host;
    
    @Value("${spring.data.redis.port}")
    private Integer port;
    
    @Value("${spring.data.redis.password}")
    private String password;
}
```

Now, I'll explain how to create the Redis connection factory and the Redis template:

- The `redisConnectionFactory` method creates a `LettuceConnectionFactory` bean, which is a connection factory implementation for Redis using the Lettuce client. It configures the connection with the specified host, port, and password. 
- Additionally, it sets command timeout and shutdown timeout options for the Lettuce client.
- Then, create a return a `LettuceConnectionFactory` instance with the specified Redis configuration and client configuration.

```java
@Bean
public LettuceConnectionFactory redisConnectionFactory() {

    // Configure the Redis connection with the specified host and port
    RedisStandaloneConfiguration redisConfiguration =
            new RedisStandaloneConfiguration(host, port);

    // Set the password for the Redis connection if authentication is required
    redisConfiguration.setPassword(password);

    // Configure the Lettuce client with command timeout and shutdown timeout options
    LettuceClientConfiguration clientConfiguration =
            LettuceClientConfiguration.builder()
                    .commandTimeout(Duration.ofSeconds(2))
                    .shutdownTimeout(Duration.ofMillis(100))
                    .build();

    // Create and return a LettuceConnectionFactory with the specified Redis configuration and client configuration
    return new LettuceConnectionFactory(
            redisConfiguration,
            clientConfiguration
    );
}
```

## 4. Usage of Value Objects and Serialization

- When working with Redis, you can use value objects to represent the data you want to store. However, Redis stores data as byte arrays, so you need to configure a serializer to convert your value objects to and from byte arrays.
- To do this, you can use the `ObjectMapper` from the Jackson library to serialize and deserialize your value objects.
- Then, you'll have to create a writer and a reader converter for the value objects, and set them in the Redis configuration.
- This is an example of a writer and reading converters that uses the `ObjectMapper` to serialize a value object to a byte array:

```java
@ReadingConverter
public class MoneyReadConverter implements Converter<byte[], Money> {

    // ObjectMapper instance for converting JSON byte arrays to Money objects
    private final ObjectMapper mapper = new ObjectMapper();
    
    @Override
    public Money convert(byte[] source) {
        try {
            return mapper.readValue(source, Money.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert byte[] to Money", e);
        }
    }
}
```

```java
@WritingConverter
public class MoneyWriteConverter implements Converter<Money, byte[]> {

    // ObjectMapper instance for converting Money objects to JSON byte arrays
    private final ObjectMapper mapper = new ObjectMapper();
    
    @Override
    public byte[] convert(Money source) {
        try {
            return mapper.writeValueAsBytes(source);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert Money to byte[]", e);
        }
    }
}
```

- Now, you'll have to create a specific configuration for custom serialization and register the converters in the Redis configuration. This allows you to use your custom serializers when working with Redis repositories.

```java
@Bean
public ObjectMapper redisObjectMapper() {

    // Create and return an ObjectMapper instance using the JsonMapper builder, which can handle various data types and formats for Redis serialization and deserialization
    return JsonMapper.builder()
            .findAndAddModules()
            .build();
}

    
@Bean
public RedisCustomConversions redisCustomConversions() {

    // Create a list of custom converters for Redis serialization and deserialization, including converters for handling value objects
    List<Converter<?, ?>> converters = new ArrayList<>();
    converters.add(new MoneyWriteConverter());
    converters.add(new MoneyReadConverter());

    // Create and return a RedisCustomConversions instance with the registered custom converters, which will be used by the RedisTemplate for handling serialization and deserialization of objects stored in Redis
    return new RedisCustomConversions(converters);
}
```

## 5. Working with Repositories

To work with Redis repositories, first, you can create a persistence class that represents the data you want to store in Redis.
This class should be annotated with `@Id` to indicate the primary key for the Redis data. You can also use Lombok annotations like `@Getter` and `@Setter` to generate getter and setter methods automatically.

```java
@Getter
public abstract class AbstractPersistenceEntity {
    
    @Id
    @Setter
    private String id;
}
```

Then, you'll have to create a concrete class that extends the abstract persistence entity. This class will represent the specific data you want to store in Redis.
Use the `@RedisHash` annotation to specify the Redis hash name and the time-to-live (TTL) for the cached data. 
You can also use the `@Indexed` annotation to indicate which fields should be indexed for faster querying.'

```java
@Getter
@Setter
@NoArgsConstructor
@RedisHash(value = "products", timeToLive = 60)
public class ProductCacheEntity extends AbstractPersistenceEntity {
    
    @Indexed
    private String name;
    
    private String description;
    
    private Money price;
}

```

Also, you can create an interface that extends `CrudRepository`. This allows you to perform CRUD operations on your Redis data.

```java
@Repository
public interface ProductCacheRepository extends CrudRepository<ProductCacheEntity, String> {
    
}
```

Now, you can set a caching strategy like cache-aside in your service layer. This strategy involves checking the cache for data before querying the database, and updating the cache after modifying the database.

For example, this method retrieves a product by its ID. It first checks the Redis cache for the product, and if it's not found, it retrieves the product from the database and stores it in the cache for future requests.

```java
@Override
    public Optional<Product> findById(Long id) {
        Optional<Product> cached =
                productCacheRepository.findById(id.toString())
                        .map(ProductCacheAssembler::toDomainFromCache);

        if (cached.isPresent()) {
            return cached;
        }

        Optional<Product> product =
                productJpaRepository.findById(id)
                        .map(ProductPersistenceAssembler::toDomainFromPersistence);

        product.ifPresent(p ->
                productCacheRepository.save(
                        ProductCacheAssembler.toCacheFromDomain(p)
                ));

        return product;
    }
```
