package zozo.domain.model;

import java.util.*;
import java.util.stream.Collectors;

public class Cart {

  private UUID id;

  private UUID userAccountId;

  private int upperLimit;

  private List<CartItem> cartItems;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Cart cart = (Cart) o;
    return upperLimit == cart.upperLimit
        && id.equals(cart.id)
        && userAccountId.equals(cart.userAccountId)
        && cartItems.equals(cart.cartItems);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userAccountId, upperLimit, cartItems);
  }

  public Cart(UUID id, UUID userAccountId, int upperLimit, List<CartItem> cartItems) {
    this.id = id;
    this.userAccountId = userAccountId;
    this.upperLimit = upperLimit;
    this.cartItems = cartItems;
  }

  public Cart(UUID id, UUID userAccountId, int upperLimit) {
    this(id, userAccountId, upperLimit, new ArrayList<>());
  }

  public UUID id() {
    return id;
  }

  public UUID userAccountId() {
    return userAccountId;
  }

  public int upperLimit() {
    return upperLimit;
  }

  public int totalPrice() {
    return cartItems.stream()
        .reduce(0, (result, element) -> result + element.totalPrice(), Integer::sum);
  }

  public List<CartItem> cartItems() {
    return new ArrayList<>(cartItems);
  }

  public Cart addItem(String itemName, int quantity, int price) throws AddCartException {
    var cartItem = new CartItem(UUID.randomUUID(), id, itemName, quantity, price);
    var totalPrice = totalPrice() + cartItem.totalPrice();
    if (totalPrice > upperLimit) {
      throw new AddCartException();
    } else {
      var clonedCartItems = new ArrayList<>(cartItems);
      clonedCartItems.add(cartItem);
      return new Cart(id, userAccountId, upperLimit, clonedCartItems);
    }
  }

  public int itemSize() {
    return cartItems.size();
  }

  public boolean containsByName(String itemName) {
    return cartItems.stream().anyMatch(element -> element.getItemName().equals(itemName));
  }

  public int itemSizeByName(String itemName) {
    var count = cartItems.stream().filter(item -> item.getItemName().equals(itemName)).count();
    return (int) count;
  }

  public Cart removeItemByItemName(String itemName) throws AddCartException {
    if (!containsByName(itemName)) {
      throw new AddCartException();
    }
    var clonedCartItems =
        cartItems.stream()
            .filter(element -> !element.getItemName().equals(itemName))
            .collect(Collectors.toList());
    return new Cart(id, userAccountId, upperLimit, clonedCartItems);
  }

  public Cart updateQuantity(String itemName, int quantity) throws AddCartException {
    if (!containsByName(itemName)) {
      throw new AddCartException();
    }
    // 相性が悪すぎる......
    //        var clonedCartItems = cartItems.stream().map(element ->
    //                {
    //                    if (element.getItemName().equals(itemName)) {
    //                        try {
    //                            return element.withQuantity(quantity);
    //                        } catch (AddCartException e) {
    //                            throw new RuntimeException(e);
    //                        }
    //                    } else {
    //                        return element;
    //                    }
    //                }
    //        ).collect(Collectors.toList());

    var clonedCartItems = new ArrayList<CartItem>();
    for (CartItem element : cartItems) {
      if (element.getItemName().equals(itemName)) {
        clonedCartItems.add(element.withQuantity(quantity));
      } else {
        clonedCartItems.add(element);
      }
    }
    return new Cart(id, userAccountId, upperLimit, clonedCartItems);
  }

  public Optional<CartItem> itemByName(String itemName) {
    return cartItems.stream().filter(element -> element.getItemName().equals(itemName)).findAny();
  }
}
