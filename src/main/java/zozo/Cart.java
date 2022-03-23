package zozo;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private int upperLimit;

    private List<CartItem> cartItems;

    public Cart(int upperLimit) {
        this.upperLimit = upperLimit;
        cartItems = new ArrayList<>();
    }

    public int upperLimit() {
        return upperLimit;
    }

    public void addItem(String itemName, int quantity, int price) {
        cartItems.add(new CartItem(itemName, quantity, price));
    }

    public int itemSize() {
        return cartItems.size();
    }

    public boolean containsByName(String itemName) {
        return cartItems.stream().anyMatch(element -> element.getItemName().equals(itemName));
    }
}
