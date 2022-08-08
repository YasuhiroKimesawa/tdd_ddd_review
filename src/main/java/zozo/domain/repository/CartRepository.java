package zozo.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import zozo.domain.model.Cart;

public interface CartRepository {

  void store(Cart aggregate);

  Optional<Cart> findById(UUID id);

  List<Cart> findAllById(UUID userAccountId);

  void deleteById(UUID id);
}
