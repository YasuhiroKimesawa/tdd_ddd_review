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
import zozo.interface_adaptor.dao.CartMapper;
import zozo.interface_adaptor.dao.CartRecord;

@MybatisTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartMapperTest {
    @Container
    static final MySQLContainer mysqlContainer =
            new MySQLContainer<>(DockerImageName.parse("mysql"))
                    .withUsername("sa")
                    .withPassword("test")
                    .withDatabaseName("test");

    @Autowired
    CartMapper cartMapper;

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        var jdbcUrl =  mysqlContainer.getJdbcUrl();
        registry.add("spring.datasource.url", () -> jdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
    }

    @Test
    void test() {
        cartMapper.insertCart(new CartRecord("a", "b", 1));
    }

}
