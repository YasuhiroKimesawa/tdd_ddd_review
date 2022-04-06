package zozo;


import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 1. カートには購入できる上限金額を設定できる
 * - 金額が日本円を前提とする
 * 2. カートには買いたい商品を追加できる。数量も指定できる
 * - 商品には名前、価格、ポイント還元率(%)が含まれる
 * - ただし、追加できる商品の数量は、1〜99個までとする
 * - ただし、カートの上限金額を超える場合は商品を追加できない
 * <p>
 * ---
 * <p>
 * 3. カートから商品を削除できる
 * - ただし、登録されていない商品は削除できない
 * 4. カート内の商品数を変更できる
 * - ただし、登録されていない商品の数量は変更できない
 * 5. カート内の商品内容を確認できる
 * - 商品と商品数以外に合計金額も確認できる
 * - 獲得できる予定のポイントを確認できる
 */
public class CartTest {

    @Nested
    class カートには購入できる上限金額を設定できる {
        @Test
        public void _100のとき() {
            var expected = 100;
            var cart1 = new Cart(expected);
            var actual = cart1.upperLimit();
            assertEquals(expected, actual);
        }

        @Test
        public void _200のとき() {
            var expected = 200;
            var cart0 = new Cart(expected);
            var actual = cart0.upperLimit();
            assertEquals(expected, actual);
        }

    }

    @Nested
    class カートには買いたい商品を追加できる_数量も指定できる {

        @Test
        public void _1つの商品を追加した場合() throws AddCartException {
            var cart0 = new Cart(100);
            var cart1 = cart0.addItem("商品1", 1, 50);
            assertEquals(cart1.itemSize(), 1);
            assertTrue(cart1.containsByName("商品1"));
        }

        @Test
        public void _2つの商品を追加した場合() throws AddCartException {
            var cart0 = new Cart(100);
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
            var cart0 = new Cart(10000);
            var cart1 = cart0.addItem("商品2", 1, 10000);
            assertThrows(AddCartException.class,
                    () -> cart1.addItem("商品1", 1, 1000));
        }

    }

    @Nested
    class ただし追加できる商品の数量は1_99個までとする {
        @Test
        public void _0個の場合() throws AddCartException {
            var cart0 = new Cart(10000);
            assertThrows(AddCartException.class, () ->
                    cart0.addItem("商品1", 0, 100)
            );
            assertEquals(0, cart0.itemSizeByName("商品1"));
        }

        @Test
        public void _1個の場合() throws AddCartException {
            var cart0 = new Cart(10000);
            var cart1 = cart0.addItem("商品1", 1, 100);
            assertEquals(1, cart1.itemSizeByName("商品1"));
        }

        @Test
        public void _99個の場合() throws AddCartException {
            var cart0 = new Cart(10000);
            var cart1 = cart0.addItem("商品1", 99, 100);
            assertEquals(1, cart1.itemSizeByName("商品1"));
        }

        @Test
        public void _100個の場合() throws AddCartException {
            var cart = new Cart(10000);
            assertThrows(AddCartException.class, () ->
                    cart.addItem("商品1", 100, 100));
            assertEquals(0, cart.itemSizeByName("商品1"));
        }
    }

}
