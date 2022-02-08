package zozo;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CartTest {
    @Nested
    public class カートに商品とその数量を追加する {
        @Nested
        public class 合計金額が上限を超えないとき {
            @Test
            public void カートオブジェクトにTシャツを1個追加する() {
            }

            @Test
            public void カートオブジェクトにTシャツを2個追加する() {
            }
        }
        @Nested
        public class 合計金額が上限を超えるとき {
            @Test
            public void カートオブジェクトにTシャツを1個追加する() {
            }

            @Test
            public void カートオブジェクトにTシャツを2個追加する() {
            }
        }
    }

    @Test
    public void カートから追加された商品を削除する() {

    }

    @Test
    public void カートから追加された商品の数量を変更する() {

    }

    @Test
    public void カートの内容を確認する() {
    }
}