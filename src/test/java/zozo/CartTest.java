package zozo;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 1. カートには購入できる上限金額を設定できる
 *     - 金額が日本円を前提とする
 * 1. カートには買いたい商品を追加できる。数量も指定できる
 *     - 商品には名前、価格、ポイント還元率(%)が含まれる
 *     - ただし、追加できる商品の数量は、0〜99個までとする
 *     - ただし、カートの上限金額を超える場合は商品を追加できない
 * 1. カートから商品を削除できる
 *     - ただし、登録されていない商品は削除できない
 * 1. カート内の商品数を変更できる
 *     - ただし、登録されていない商品の数量は変更できない
 * 1. カート内の商品内容を確認できる
 *     - 商品と商品数以外に合計金額も確認できる
 *     - 獲得できる予定のポイントを確認できる
 */
public class CartTest {

  @Test
  public void カートには買いたい商品を追加できる_数量も指定できる() {
    var cart = new Cart();
    var good = new Good();

    // TODO: cartに商品以外を追加することがある？(ノベルティなど？)
    var actual = cart.addGoods(good, 1);
    var expected = new Cart(List.of(good));

    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void カートには買いたい商品を追加できる_数量も指定できる_2() {
    var cart = new Cart();
    var good = new Good();

    // TODO: cartに商品以外を追加することがある？(ノベルティなど？)
    var actual = cart.addGoods(good, 2);
    var expected = new Cart(List.of(good, good));

    Assertions.assertEquals(expected, actual);
  }
}
