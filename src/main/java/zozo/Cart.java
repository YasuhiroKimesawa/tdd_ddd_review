package zozo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                .reduce(0, (result, element) -> result + element.totalPrice(), Integer::sum);
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

    public Cart removeItemByItemName(String itemName) throws AddCartException {
        if (!containsByName(itemName)) {
            throw new AddCartException();
        }
        var clonedCartItems = new ArrayList<>(cartItems);
        var updateCartItems = clonedCartItems.stream()
                .filter(element -> !element.getItemName().equals(itemName))
                .collect(Collectors.toList());
        return new Cart(upperLimit, updateCartItems);
    }

    public Cart updateQuantity(String itemName, int quantity) throws AddCartException {
        if (!containsByName(itemName)) {
            throw new AddCartException();
        }
        var clonedCartItems = new ArrayList<CartItem>();
        for (CartItem element : cartItems) {
            if (element.getItemName().equals(itemName)) {
                clonedCartItems.add(element.withQuantity(quantity));
            } else {
                clonedCartItems.add(element);
            }
        }
        return new Cart(upperLimit, clonedCartItems);
    }

    public Optional<CartItem> itemByName(String itemName) {
        return cartItems.stream().filter(element -> element.getItemName().equals(itemName)).findAny();
    }
}
