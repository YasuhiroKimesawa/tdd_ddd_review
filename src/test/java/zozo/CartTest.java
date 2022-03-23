package zozo;


import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 1. カートには購入できる上限金額を設定できる
 * - 金額が日本円を前提とする
 * 1. カートには買いたい商品を追加できる。数量も指定できる
 * - 商品には名前、価格、ポイント還元率(%)が含まれる
 * - ただし、追加できる商品の数量は、0〜99個までとする
 * - ただし、カートの上限金額を超える場合は商品を追加できない
 * 1. カートから商品を削除できる
 * - ただし、登録されていない商品は削除できない
 * 1. カート内の商品数を変更できる
 * - ただし、登録されていない商品の数量は変更できない
 * 1. カート内の商品内容を確認できる
 * - 商品と商品数以外に合計金額も確認できる
 * - 獲得できる予定のポイントを確認できる
 */
public class CartTest {

    @Nested
    class カートには購入できる上限金額を設定できる {
        @Test
        public void _100のとき() {
            var expected = 100;
            var cart = new Cart(expected);
            var actual = cart.upperLimit();
            assertEquals(expected, actual);
        }

        @Test
        public void _200のとき() {
            var expected = 200;
            var cart = new Cart(expected);
            var actual = cart.upperLimit();
            assertEquals(expected, actual);
        }

    }

    @Nested
    class カートには買いたい商品を追加できる_数量も指定できる {

        @Test
        public void _1つの商品を追加した場合() {
            var cart = new Cart(100);
            cart.addItem("商品1", 1, 50);
            assertEquals(cart.itemSize(), 1);
            assertTrue(cart.containsByName("商品1"));
        }

        @Test
        public void _2つの商品を追加した場合() {
            var cart = new Cart(100);
            cart.addItem("商品1", 1, 50);
            cart.addItem("商品2", 2, 50);
            assertEquals(cart.itemSize(), 2);
            assertTrue(cart.containsByName("商品1"));
            assertTrue(cart.containsByName("商品2"));
        }

    }


}
