package zozo;

import java.util.Objects;
import java.util.UUID;

public class CartItem {
  private UUID id;

  private UUID cartId;
  private String itemName;
  private int quantity;
  private int price;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CartItem cartItem = (CartItem) o;
    return quantity == cartItem.quantity
        && price == cartItem.price
        && itemName.equals(cartItem.itemName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemName, quantity, price);
  }

  static final int CART_QUANTITY_MAX = 99;
  static final int CART_QUANTITY_MIN = 1;

  public CartItem(UUID id, UUID cartId, String itemName, int quantity, int price)
      throws AddCartException {
    if (quantity < CART_QUANTITY_MIN) {
      throw new AddCartException();
    }
    if (quantity > CART_QUANTITY_MAX) {
      throw new AddCartException();
    }
    this.id = id;
    this.cartId = cartId;
    this.itemName = itemName;
    this.quantity = quantity;
    this.price = price;
  }

  public UUID getId() {
    return id;
  }

  public UUID getCartId() {
    return cartId;
  }

  public int getPrice() {
    return price;
  }

  public int getQuantity() {
    return quantity;
  }

  public String getItemName() {
    return itemName;
  }

  public CartItem withQuantity(int value) throws AddCartException {
    return new CartItem(id, cartId, itemName, value, price);
  }

  public int totalPrice() {
    return price * quantity;
  }
}
