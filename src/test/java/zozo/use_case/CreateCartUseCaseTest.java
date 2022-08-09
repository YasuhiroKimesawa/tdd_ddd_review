package zozo.use_case;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.UUID;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import zozo.interface_adator.repository.CartRepositoryOnJDBC;
import zozo.testing.utils.FlywayTestUtil;
import zozo.testing.utils.SqlSessionFactoryUtil;

@Testcontainers
class CreateCartUseCaseTest {
  SqlSessionFactory sqlSessionFactory;

  @Container
  MySQLContainer mysqlContainer =
      new MySQLContainer<>(DockerImageName.parse("mysql"))
          .withUsername("sa")
          .withPassword("test")
          .withDatabaseName("test");

  @BeforeEach
  void setUp() throws IOException {
    FlywayTestUtil.migrate(mysqlContainer);
    sqlSessionFactory = SqlSessionFactoryUtil.build(mysqlContainer);
  }

  @Test
  public void カートを作成できる() {
    try (SqlSession session = sqlSessionFactory.openSession()) {
      var cartRepository = new CartRepositoryOnJDBC(session);
      var useCase = new CreateCartUseCase(cartRepository);
      var userAccountId = UUID.randomUUID();
      var upperLimit = 100;
      var cartId = useCase.execute(userAccountId, upperLimit);
      assertNotNull(cartId);
    }
  }
}
