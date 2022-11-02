package zozo.domain.repository;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import zozo.domain.model.Cart;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MybatisTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartRepositoryOnJDBCTest {
    @Container
    static final MySQLContainer mysqlContainer =
            new MySQLContainer<>(DockerImageName.parse("mysql"))
                    .withUsername("sa")
                    .withPassword("test")
                    .withDatabaseName("test");

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
    }

    @Autowired
    CartRepository cartRepository;

    @Test
    void test() {
        var cartId = UUID.randomUUID();
        var userAccountId = UUID.randomUUID();
        Cart cart = new Cart(cartId, userAccountId, 100);
        cartRepository.store(cart);
        var actual = cartRepository.findById(cartId).get();
        assertEquals(actual, cart);
    }
}
