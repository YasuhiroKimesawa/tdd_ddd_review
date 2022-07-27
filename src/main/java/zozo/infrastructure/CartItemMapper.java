package zozo.infrastructure; /*
                              * Copyright 2022 Junichi Kato
                              *
                              * Licensed under the Apache License, Version 2.0 (the "License");
                              * you may not use this file except in compliance with the License.
                              * You may obtain a copy of the License at
                              *
                              *     http://www.apache.org/licenses/LICENSE-2.0
                              *
                              * Unless required by applicable law or agreed to in writing, software
                              * distributed under the License is distributed on an "AS IS" BASIS,
                              * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
                              * See the License for the specific language governing permissions and
                              * limitations under the License.
                              */

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
