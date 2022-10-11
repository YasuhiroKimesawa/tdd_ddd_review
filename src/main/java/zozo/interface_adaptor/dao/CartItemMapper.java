package zozo.interface_adaptor.dao;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
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
