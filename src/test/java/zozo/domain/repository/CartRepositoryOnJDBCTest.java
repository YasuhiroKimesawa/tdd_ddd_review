package zozo.domain.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import zozo.application.Application;
import zozo.domain.model.AddCartException;
import zozo.domain.model.Cart;
import zozo.interface_adaptor.dao.CartMapper;
import zozo.interface_adaptor.repository.CartRepositoryOnJDBC;
import zozo.testing.utils.FlywayTestUtil;
import zozo.testing.utils.SqlSessionFactoryUtil;

@RunWith(SpringRunner.class)
// @MybatisTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { CartRepositoryOnJDBC.class }))
@MybatisTest
@Import(CartRepositoryOnJDBC.class)
// @Testcontainers
// @ContextConfiguration(initializers = {CartRepositoryOnJDBCTest.Initializer.class})
class CartRepositoryOnJDBCTest {

  // SqlSessionFactory sqlSessionFactory;
//  @Container
//    @ClassRule
    // @Container
  static final MySQLContainer mysqlContainer =
          new MySQLContainer<>(DockerImageName.parse("mysql"))
                  .withUsername("sa")
                  .withPassword("test")
                  .withDatabaseName("test");

//    static {
//        mysqlContainer.start();
//    }
//    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
//
//        @Override
//        public void initialize(ConfigurableApplicationContext applicationContext) {
//            TestPropertyValues.of(
//                    "spring.datasource.url=" + mysqlContainer.getJdbcUrl(),
//                    "spring.datasource.username=" + mysqlContainer.getUsername(),
//                    "spring.datasource.password=" + mysqlContainer.getPassword()
//            ).applyTo(applicationContext.getEnvironment());
//        }
//    }

  @Autowired
  CartRepository cartRepository;


  @DynamicPropertySource
  static void registerProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.url", mysqlContainer::getJdbcUrl);
    registry.add("spring.datasource.username", mysqlContainer::getUsername);
    registry.add("spring.datasource.password", mysqlContainer::getPassword);
  }

  @BeforeEach
  void setUp() throws IOException {
    FlywayTestUtil.migrate(mysqlContainer);
    // sqlSessionFactory = SqlSessionFactoryUtil.build(mysqlContainer);
  }

  @Test
    public void dummy() {

  }

//  @Test
//  void Cartの状態をリポジトリを使って保存できる() throws AddCartException {
// //   try (SqlSession session = sqlSessionFactory.openSession()) {
// //     var repository = new CartRepositoryOnJDBC(session);
//      var id = UUID.randomUUID();
//      var userAccountId = UUID.randomUUID();
//      var expected = 100;
//
//      var cart = new Cart(id, userAccountId, expected);
//      cart = cart.addItem("商品1", 1, 50);
//    cartRepository.store(cart);
//
////      session.rollback();
////    }
//  }
//
//  @Test
//  void リポジトリで保存されたCartを読み込むことができる() {
////    try (SqlSession session = sqlSessionFactory.openSession()) {
////      var repository = new CartRepositoryOnJDBC(session);
//      var id = UUID.randomUUID();
//      var userAccountId = UUID.randomUUID();
//      var expected = 100;
//      var cart = new Cart(id, userAccountId, expected);
//      cartRepository.store(cart);
//
//      var cartSavedOpt = cartRepository.findById(id);
//      assertEquals(cartSavedOpt, Optional.of(cart));
////      session.commit();
////    }
//  }
//
//  @Test
//  void あるユーザアカウントに紐付くすべてのCartを読み込むことができる() {
// //   try (SqlSession session = sqlSessionFactory.openSession()) {
// //     var repository = new CartRepositoryOnJDBC(session);
//      var userAccountId = UUID.randomUUID();
//
//      var id1 = UUID.randomUUID();
//      var expected1 = 100;
//      var cart1 = new Cart(id1, userAccountId, expected1);
//      cartRepository.store(cart1);
//
//      var id2 = UUID.randomUUID();
//      var expected2 = 100;
//      var cart2 = new Cart(id2, userAccountId, expected2);
//      cartRepository.store(cart2);
//
//      var cartSavedOpt = cartRepository.findAllById(userAccountId);
//      assertTrue(cartSavedOpt.stream().anyMatch(cart -> cart.id().equals(cart1.id())));
//      assertTrue(cartSavedOpt.stream().anyMatch(cart -> cart.id().equals(cart2.id())));
//    //      session.rollback();
//   // }
//  }
//
//  @Test
//  void 指定したIDのカートを削除できる() {
////    try (SqlSession session = sqlSessionFactory.openSession()) {
////      var repository = new CartRepositoryOnJDBC(session);
//      var userAccountId = UUID.randomUUID();
//
//      var id1 = UUID.randomUUID();
//      var expected1 = 100;
//      var cart1 = new Cart(id1, userAccountId, expected1);
//    cartRepository.store(cart1);
//
//      var id2 = UUID.randomUUID();
//      var expected2 = 100;
//      var cart2 = new Cart(id2, userAccountId, expected2);
//    cartRepository.store(cart2);
//
//    cartRepository.deleteById(cart1.id());
//
//      var cartSavedOpt = cartRepository.findAllById(userAccountId);
//      assertEquals(cartSavedOpt, List.of(cart2));
////      session.rollback();
////    }
//  }
}
