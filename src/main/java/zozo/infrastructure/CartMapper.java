package zozo.infrastructure;

import org.apache.ibatis.annotations.Flush;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface CartMapper {
  @Insert(
      "insert into carts (id, user_account_id, upper_limit) values(#{id}, #{userAccountId}, #{upperLimit})")
  @Flush
  int insertCart(CartRecord cartRecord);

  @Insert(
      "insert into carts (id, user_account_id, upper_limit) values(#{id}, #{userAccountId}, #{upperLimit}) ON DUPLICATE KEY UPDATE user_account_id = VALUES(user_account_id), upper_limit = VALUES(upper_limit)")
  @Flush
  int upsertCart(CartRecord cartRecord);

  @Select("select * from cart where id = #{id}")
  CartRecord findById(String id);
}
