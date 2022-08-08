package zozo.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
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
import zozo.domain.model.AddCartException;
import zozo.domain.model.Cart;
import zozo.interface_adator.repository.CartRepositoryOnJDBC;

@Testcontainers
class CartRepositoryOnJDBCTest {

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
  void Cartの状態をリポジトリを使って保存できる() throws AddCartException {
    try (SqlSession session = sqlSessionFactory.openSession()) {
      var repository = new CartRepositoryOnJDBC(session);
      var id = UUID.randomUUID();
      var userAccountId = UUID.randomUUID();
      var expected = 100;
      var cart = new Cart(id, userAccountId, expected);
      cart = cart.addItem("商品1", 1, 50);
      repository.store(cart);

      session.rollback();
    }
  }

  @Test
  void リポジトリで保存されたCartを読み込むことができる() {
    try (SqlSession session = sqlSessionFactory.openSession()) {
      var repository = new CartRepositoryOnJDBC(session);
      var id = UUID.randomUUID();
      var userAccountId = UUID.randomUUID();
      var expected = 100;
      var cart = new Cart(id, userAccountId, expected);
      repository.store(cart);
      // userRepository.store(user);
      // groupRepository.store(group);

      var cartSavedOpt = repository.findById(id);
      assertEquals(cartSavedOpt, Optional.of(cart));
      session.commit();
    }
  }

  @Test
  void あるユーザアカウントに紐付くすべてのCartを読み込むことができる() {
    try (SqlSession session = sqlSessionFactory.openSession()) {
      var repository = new CartRepositoryOnJDBC(session);
      var userAccountId = UUID.randomUUID();

      var id1 = UUID.randomUUID();
      var expected1 = 100;
      var cart1 = new Cart(id1, userAccountId, expected1);
      repository.store(cart1);

      var id2 = UUID.randomUUID();
      var expected2 = 100;
      var cart2 = new Cart(id2, userAccountId, expected2);
      repository.store(cart2);

      var cartSavedOpt = repository.findAllById(userAccountId);
      assertEquals(cartSavedOpt, List.of(cart1, cart2));
      session.commit();
    }
  }

  @Test
  void 指定したIDのカートを削除できる() {
    try (SqlSession session = sqlSessionFactory.openSession()) {
      var repository = new CartRepositoryOnJDBC(session);
      var userAccountId = UUID.randomUUID();

      var id1 = UUID.randomUUID();
      var expected1 = 100;
      var cart1 = new Cart(id1, userAccountId, expected1);
      repository.store(cart1);

      var id2 = UUID.randomUUID();
      var expected2 = 100;
      var cart2 = new Cart(id2, userAccountId, expected2);
      repository.store(cart2);

      repository.deleteById(cart1.id());

      var cartSavedOpt = repository.findAllById(userAccountId);
      assertEquals(cartSavedOpt, List.of(cart2));
      session.commit();
    }
  }
}
