package zozo.interface_adaptor.dao;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Flush;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface CartItemMapper {
  @Insert(
      "insert into cart_items (id, cart_id, item_name, quantity, price) values(#{id}, #{cartId}, #{itemName}, #{quantity}, #{price}) ON DUPLICATE KEY UPDATE cart_id = VALUES(cart_id), item_name = VALUES(item_name), quantity = VALUES(quantity), price = VALUES(price)")
  @Flush
  int upsert(CartItemRecord cartItemRecord);

  @Select("select * from cart_items where cart_id = #{cartId}")
  List<CartItemRecord> findByCartId(String cartId);

  @Delete("delete from cart_items where cart_id = #{cartId}")
  int deleteByCartId(String cartId);
}
