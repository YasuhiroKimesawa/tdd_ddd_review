package zozo.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * 1. カートには購入できる上限金額を設定できる
 *
 * <p>- 金額が日本円を前提とする
 *
 * <p>2. カートには買いたい商品を追加できる。数量も指定できる
 *
 * <p>- 商品には名前、価格、ポイント還元率(%)が含まれる - ただし、追加できる商品の数量は、1〜99個までとする - ただし、カートの上限金額を超える場合は商品を追加できない
 *
 * <p>3. カートから商品を削除できる
 *
 * <p>- ただし、登録されていない商品は削除できない
 *
 * <p>4. カート内の商品数を変更できる - ただし、登録されていない商品の数量は変更できない
 *
 * <p>---
 *
 * <p>5. カート内の商品内容を確認できる
 *
 * <p>- 商品と商品数以外に合計金額も確認できる - 獲得できる予定のポイントを確認できる
 */
public class CartTest {

  @Nested
  class カートには購入できる上限金額を設定できる {
    @Test
    public void _100円のとき() {
      var id = UUID.randomUUID();
      var userAccountId = UUID.randomUUID();
      var expected = 100;
      var cart1 = new Cart(id, userAccountId, expected);
      var actual = cart1.upperLimit();
      assertEquals(expected, actual);
    }

    @Test
    public void _200円のとき() {
      var id = UUID.randomUUID();
      var userAccountId = UUID.randomUUID();
      var expected = 200;
      var cart0 = new Cart(id, userAccountId, expected);
      var actual = cart0.upperLimit();
      assertEquals(expected, actual);
    }
  }

  @Nested
  class カートには買いたい商品を追加できる_数量も指定できる {

    @Test
    public void _1つの商品を追加した場合() throws AddCartException {
      var id = UUID.randomUUID();
      var userAccountId = UUID.randomUUID();
      var cart0 = new Cart(id, userAccountId, 100);
      var cart1 = cart0.addItem("商品1", 1, 50);
      assertEquals(cart1.itemSize(), 1);
      assertTrue(cart1.containsByName("商品1"));
    }

    @Test
    public void _2つの商品を追加した場合() throws AddCartException {
      var id = UUID.randomUUID();
      var userAccountId = UUID.randomUUID();
      var cart0 = new Cart(id, userAccountId, 100);
      var cart1 = cart0.addItem("商品1", 1, 50);
      var cart2 = cart1.addItem("商品2", 1, 50);
      assertEquals(cart2.itemSize(), 2);
      assertTrue(cart2.containsByName("商品1"));
      assertTrue(cart2.containsByName("商品2"));
    }
  }

  @Nested
  class ただしカートの上限金額を超える場合は商品を追加できない {

    @Test
    public void _上限が10000円で11000円の商品を追加する場合() throws AddCartException {
      var id = UUID.randomUUID();
      var userAccountId = UUID.randomUUID();
      var cart0 = new Cart(id, userAccountId, 10000);
      var cart1 = cart0.addItem("商品2", 1, 10000);
      assertThrows(AddCartException.class, () -> cart1.addItem("商品1", 1, 1000));
    }
  }

  @Nested
  class ただし追加できる商品の数量は1_99個までとする {
    @Test
    public void _0個の場合() throws AddCartException {
      var id = UUID.randomUUID();
      var userAccountId = UUID.randomUUID();
      var cart0 = new Cart(id, userAccountId, 10000);
      assertThrows(AddCartException.class, () -> cart0.addItem("商品1", 0, 100));
      assertEquals(0, cart0.itemSizeByName("商品1"));
    }

    @Test
    public void _1個の場合() throws AddCartException {
      var id = UUID.randomUUID();
      var userAccountId = UUID.randomUUID();
      var cart0 = new Cart(id, userAccountId, 10000);
      var cart1 = cart0.addItem("商品1", 1, 100);
      assertEquals(1, cart1.itemSizeByName("商品1"));
    }

    @Test
    public void _99個の場合() throws AddCartException {
      var id = UUID.randomUUID();
      var userAccountId = UUID.randomUUID();
      var cart0 = new Cart(id, userAccountId, 10000);
      var cart1 = cart0.addItem("商品1", 99, 100);
      assertEquals(1, cart1.itemSizeByName("商品1"));
    }

    @Test
    public void _100個の場合() throws AddCartException {
      var id = UUID.randomUUID();
      var userAccountId = UUID.randomUUID();
      var cart = new Cart(id, userAccountId, 10000);
      assertThrows(AddCartException.class, () -> cart.addItem("商品1", 100, 100));
      assertEquals(0, cart.itemSizeByName("商品1"));
    }
  }

  @Nested
  class カートから商品を削除できる {
    @Test
    public void _登録済みの商品を1個削除する() throws AddCartException {
      var id = UUID.randomUUID();
      var userAccountId = UUID.randomUUID();
      var cart = new Cart(id, userAccountId, 100);
      var itemName = "商品1";
      var cartUpdated = cart.addItem(itemName, 1, 100);
      assertEquals(1, cartUpdated.itemSizeByName("商品1"));
      var cartRemoved = cartUpdated.removeItemByItemName(itemName);
      assertEquals(0, cartRemoved.itemSizeByName("商品1"));
    }

    @Test
    public void _未登録の商品を1個削除する() throws AddCartException {
      var id = UUID.randomUUID();
      var userAccountId = UUID.randomUUID();
      var cart = new Cart(id, userAccountId, 100);
      var itemName = "商品1";
      assertThrows(AddCartException.class, () -> cart.removeItemByItemName(itemName));
    }
  }

  @Nested
  class カート内の商品数を変更できる {
    // * - ただし、登録されていない商品の数量は変更できない
    @Test
    public void _登録済みの商品の数量を変更する() throws AddCartException {
      var id = UUID.randomUUID();
      var userAccountId = UUID.randomUUID();
      var cart = new Cart(id, userAccountId, 10000);
      var itemName = "商品1";
      var cartUpdated = cart.addItem(itemName, 1, 100);
      Cart cartUpdated2 = cartUpdated.updateQuantity(itemName, 10);
      assertEquals(cartUpdated2.itemSizeByName(itemName), 1);
      var cartItem = cartUpdated2.itemByName(itemName).get();
      assertEquals(cartItem.getQuantity(), 10);
    }
  }
}
