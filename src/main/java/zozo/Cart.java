package zozo;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private int upperLimit;

    private List<CartItem> cartItems;

    public Cart(int upperLimit, List<CartItem> cartItems) {
        this.upperLimit = upperLimit;
        this.cartItems = cartItems;
    }

    public Cart(int upperLimit) {
        this(upperLimit, new ArrayList<>());
    }

    public int upperLimit() {
        return upperLimit;
    }

    public int totalPrice() {
        return cartItems.stream()
                .reduce(0, (s, e) -> s + e.totalPrice(), Integer::sum);
    }

    public Cart addItem(String itemName, int quantity, int price)
            throws AddCartException {
        var cartItem = new CartItem(itemName, quantity, price);
        var totalPrice = totalPrice() + cartItem.totalPrice();
        if (totalPrice > upperLimit) {
            throw new AddCartException();
        } else {
            var clonedCartItems = new ArrayList<>(cartItems);
            clonedCartItems.add(cartItem);
            return new Cart(upperLimit, clonedCartItems);
        }
    }

    public int itemSize() {
        return cartItems.size();
    }

    public boolean containsByName(String itemName) {
        return cartItems.stream().anyMatch(element -> element.getItemName().equals(itemName));
    }

    public int itemSizeByName(String itemName) {
        var count = cartItems.stream()
                .filter(item -> item.getItemName().equals(itemName))
                .count();
        return (int) count;
    }
}
