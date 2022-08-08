package zozo.use_case;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import zozo.interface_adator.repository.CartRepositoryOnJDBC;

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
    var configure = Flyway.configure();
    configure.dataSource(
        mysqlContainer.getJdbcUrl(), mysqlContainer.getUsername(), mysqlContainer.getPassword());
    configure.locations("rdb-migration");
    var flyway = configure.load();
    flyway.migrate();

    String resource = "mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    var properties = new Properties();
    properties.put("driver", mysqlContainer.getDriverClassName());
    properties.put("url", mysqlContainer.getJdbcUrl());
    properties.put("userName", mysqlContainer.getUsername());
    properties.put("password", mysqlContainer.getPassword());
    sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, properties);
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
