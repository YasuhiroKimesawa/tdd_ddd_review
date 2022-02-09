package zozo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart {

  private final List<Good> goods;

  public Cart() {
    goods = List.of();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cart cart = (Cart) o;
    return goods.equals(cart.goods);
  }

  @Override
  public String toString() {
    return "Cart{" +
        "goods=" + goods +
        '}';
  }

  @Override
  public int hashCode() {
    return Objects.hash(goods);
  }

  public Cart(List<Good> goods) {
    this.goods = goods;
  }

  public Cart addGoods(Good good, int i) {
    var list = new ArrayList<>(goods);
    list.add(good);
    return new Cart(list);
  }
}
