package zozo.interface_adaptor.repository;

import org.springframework.stereotype.Repository;
import zozo.domain.model.AddCartException;
import zozo.domain.model.Cart;
import zozo.domain.model.CartItem;
import zozo.domain.repository.CartRepository;
import zozo.interface_adaptor.dao.CartItemMapper;
import zozo.interface_adaptor.dao.CartItemRecord;
import zozo.interface_adaptor.dao.CartMapper;
import zozo.interface_adaptor.dao.CartRecord;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class CartRepositoryOnJDBC implements CartRepository {
//  private final SqlSession session;
  private final CartMapper cartMapper;

  private final CartItemMapper cartItemMapper;

  public CartRepositoryOnJDBC(CartMapper cartMapper, CartItemMapper cartItemMapper) {
    this.cartMapper = cartMapper;
    this.cartItemMapper = cartItemMapper;
  }

//  public CartRepositoryOnJDBC(SqlSession session) {
//    this.session = session;
//    cartMapper = session.getMapper(CartMapper.class);
//    cartItemMapper = session.getMapper(CartItemMapper.class);
//  }

  @Override
  public void store(Cart aggregate) {
    cartMapper.upsert(convertToCartRecord(aggregate));
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
              cartItemMapper.upsert(cartItemRecord);
            });
  }

  private CartRecord convertToCartRecord(Cart aggregate) {
    return new CartRecord(
        aggregate.id().toString(), aggregate.userAccountId().toString(), aggregate.upperLimit());
  }

  @Override
  public Optional<Cart> findById(UUID id) {
    return cartMapper.findById(id.toString()).map(this::convertToCart);
  }

  private Cart convertToCart(CartRecord cartRecord) {
    var cartId = UUID.fromString(cartRecord.getId());
    var userAccountId = UUID.fromString(cartRecord.getUserAccountId());
    var cartItemRecords = cartItemMapper.findByCartId(cartRecord.getId());
    List<CartItem> cartItems = convertToCartItems(cartId, cartItemRecords);
    return new Cart(cartId, userAccountId, cartRecord.getUpperLimit(), cartItems);
  }

  private List<CartItem> convertToCartItems(UUID cartId, List<CartItemRecord> cartItemRecords) {
    return cartItemRecords.stream()
        .map(
            cartItemRecord -> {
              var cartItemId = UUID.fromString(cartItemRecord.getId());
              CartItem cartItem = null;
              try {
                cartItem =
                    new CartItem(
                        cartItemId,
                        cartId,
                        cartItemRecord.getItemName(),
                        cartItemRecord.getQuantity(),
                        cartItemRecord.getPrice());
              } catch (AddCartException e) {
                throw new RuntimeException(e);
              }
              return cartItem;
            })
        .collect(Collectors.toList());
  }

  @Override
  public List<Cart> findAllById(UUID userAccountId) {
    var cartRecords = cartMapper.findByUserAccountId(userAccountId.toString());
    return cartRecords.stream().map(this::convertToCart).collect(Collectors.toList());
  }

  @Override
  public void deleteById(UUID id) {
    cartItemMapper.deleteByCartId(id.toString());
    cartMapper.deleteById(id.toString());
  }
}