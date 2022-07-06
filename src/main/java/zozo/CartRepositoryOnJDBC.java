package zozo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.apache.ibatis.session.SqlSession;
import zozo.infrastructure.CartItemMapper;
import zozo.infrastructure.CartItemRecord;
import zozo.infrastructure.CartMapper;
import zozo.infrastructure.CartRecord;

public class CartRepositoryOnJDBC implements CartRepository {
  private final SqlSession session;
  private final CartMapper cartMapper;

  private final CartItemMapper cartItemMapper;

  public CartRepositoryOnJDBC(SqlSession session) {
    this.session = session;
    cartMapper = session.getMapper(CartMapper.class);
    cartItemMapper = session.getMapper(CartItemMapper.class);
  }

  @Override
  public void store(Cart aggregate) {
    var cartRecord =
        new CartRecord(
            aggregate.id().toString(),
            aggregate.userAccountId().toString(),
            aggregate.upperLimit());
    cartMapper.upsertCart(cartRecord);
    aggregate
        .cartItems()
        .forEach(
            element -> {
              var cartItemRecord =
                  new CartItemRecord(
                      element.getId().toString(),
                      element.getCartId().toString(),
                      element.getItemName(),
                      element.getQuantity(),
                      element.getPrice());
              cartItemMapper.upsertCart(cartItemRecord);
            });
  }

  @Override
  public Optional<Cart> findById(UUID id) {
    var cartRecord = cartMapper.findById(id.toString());
    // var aggregate = new Cart(...);
    // return aggregate;
    return null;
  }

  @Override
  public List<Cart> findAllById(UUID userAccountId) {
    return null;
  }

  @Override
  public void deleteById(UUID id) {}
}
