package zozo.use_case;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.BeforeEach;
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
import zozo.testing.utils.FlywayTestUtil;
import zozo.testing.utils.SqlSessionFactoryUtil;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.fail;

@MybatisTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreateCartUseCaseTest {

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
    CartMapper cartMapper;
    @Autowired
    private CreateCartUseCase createCartUseCase;

    @Test
    public void カートを作成できる() {
        // Given
        var userAccountId = UUID.randomUUID();
        var upperLimit = 100;

        // When
        var cartId = createCartUseCase.execute(userAccountId, upperLimit);

        // Then
        var cartRecord = cartMapper.findById(cartId.toString()).get();
        assert cartRecord.getUserAccountId().equals(userAccountId.toString());
        assert cartRecord.getUpperLimit() == upperLimit;
    }
}
