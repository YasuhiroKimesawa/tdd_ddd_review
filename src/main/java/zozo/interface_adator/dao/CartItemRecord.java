package zozo.interface_adator.dao;

import java.util.Objects;

public class CartItemRecord {
  private String id;

  private String cartId;

  private String itemName;

  private int quantity;

  private int price;

  public CartItemRecord(String id, String cartId, String itemName, int quantity, int price) {
    this.id = id;
    this.cartId = cartId;
    this.itemName = itemName;
    this.quantity = quantity;
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CartItemRecord that = (CartItemRecord) o;
    return quantity == that.quantity
        && price == that.price
        && id.equals(that.id)
        && cartId.equals(that.cartId)
        && itemName.equals(that.itemName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, cartId, itemName, quantity, price);
  }

  public String getId() {
    return id;
  }

  public String getCartId() {
    return cartId;
  }

  public String getItemName() {
    return itemName;
  }

  public int getQuantity() {
    return quantity;
  }

  public int getPrice() {
    return price;
  }
}
