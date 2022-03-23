package zozo;

import java.util.Objects;

public class CartItem {
    private String itemName;
    private int quantity;
    private int price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return quantity == cartItem.quantity && price == cartItem.price && itemName.equals(cartItem.itemName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemName, quantity, price);
    }

    public CartItem(String itemName, int quantity, int price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
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
}
