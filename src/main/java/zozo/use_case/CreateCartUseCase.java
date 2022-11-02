package zozo.use_case;

import org.springframework.stereotype.Service;
import zozo.domain.model.Cart;
import zozo.domain.repository.CartRepository;

import java.util.UUID;

@Service
public class CreateCartUseCase {

  private final CartRepository cartRepository;

  public CreateCartUseCase(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  public UUID execute(UUID userAccountId, int upperLimit) {
    var cart = new Cart(UUID.randomUUID(), userAccountId, upperLimit);
    cartRepository.store(cart);
    return cart.id();
  }
}
