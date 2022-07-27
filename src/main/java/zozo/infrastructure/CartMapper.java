package zozo.infrastructure;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Delete;
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
  int upsert(CartRecord cartRecord);

  @Select("select * from carts where id = #{id}")
  Optional<CartRecord> findById(String id);

  @Select("select * from carts where user_account_id = #{userAccountId}")
  List<CartRecord> findByUserAccountId(String userAccountId);

  @Delete("delete from carts where id = #{id}")
  int deleteById(String id);
}
