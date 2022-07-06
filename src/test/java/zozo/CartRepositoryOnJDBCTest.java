package zozo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CartRepositoryOnJDBCTest {

  SqlSessionFactory sqlSessionFactory;

  @BeforeEach
  void setUp() throws IOException {
    String resource = "mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
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
      session.commit();
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
      var cartOnRepository = repository.findById(id);
      assertEquals(cartOnRepository, Optional.of(cart));
      session.commit();
    }
  }
}
