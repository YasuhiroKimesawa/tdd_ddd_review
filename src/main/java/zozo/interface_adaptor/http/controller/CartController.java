package zozo.interface_adaptor.http.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import zozo.use_case.CreateCartUseCase;

import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {
    private CreateCartUseCase createCartUseCase;

    public CartController(CreateCartUseCase createCartUseCase) {
        this.createCartUseCase = createCartUseCase;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public UUID create(@RequestBody CreateCartRequest createCart) {
        return createCartUseCase.execute( UUID.fromString(createCart.getUserAccountId()), createCart.getUpperLimit());
    }
}
