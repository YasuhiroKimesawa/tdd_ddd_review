package zozo.infrastructure;

import org.apache.ibatis.annotations.Flush;
import org.apache.ibatis.annotations.Insert;

public interface CartMapper {
    @Insert("insert into carts (id, user_account_id, upper_limit) values(#{id}, #{userAccountId}, #{upperLimit})")
    @Flush
    int insertCart(CartRecord cart);
}
