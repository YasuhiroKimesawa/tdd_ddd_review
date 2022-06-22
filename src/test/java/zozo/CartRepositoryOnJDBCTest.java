package zozo;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import zozo.infrastructure.CartMapper;
import zozo.infrastructure.CartRecord;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

class CartRepositoryOnJDBCTest {


    @Test
    void Cartの状態をリポジトリを使って保存できる() throws IOException {

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        try (SqlSession session = sqlSessionFactory.openSession()) {
            var cartMapper = session.getMapper(CartMapper.class);
            var id = UUID.randomUUID();
            var userAccountId = UUID.randomUUID();
            var cart = new CartRecord(id.toString(), userAccountId.toString(), 1);
            var result = cartMapper.insertCart(cart);
            System.out.println(result);
            session.commit();
        }

//        var repository = new CartRepositoryOnJDBC();
//        var id = UUID.randomUUID();
//        var userAccountId = UUID.randomUUID();
//        var expected = 100;
//        var cart = new Cart(id, userAccountId, expected);
//        repository.store(cart);
    }
}