package zozo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartRepository {

  void store(Cart aggregate);

  Optional<Cart> findById(UUID id);

  List<Cart> findAllById(UUID userAccountId);

  void deleteById(UUID id);
}
