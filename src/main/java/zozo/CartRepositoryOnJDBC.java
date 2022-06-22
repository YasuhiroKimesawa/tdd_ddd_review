package zozo;

import org.apache.ibatis.session.SqlSession;
import zozo.infrastructure.CartMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CartRepositoryOnJDBC implements CartRepository {
    private final SqlSession session;
    private final CartMapper cartMapper;

    public CartRepositoryOnJDBC(SqlSession session) {
       this.session = session;
       cartMapper = session.getMapper(CartMapper.class);
    }

    @Override
    public void store(Cart aggregate) {
        throw new RuntimeException();
    }

    @Override
    public Optional<Cart> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Cart> findAllById(UUID userAccountId) {
        return null;
    }

    @Override
    public void deleteById(UUID id) {

    }
}
