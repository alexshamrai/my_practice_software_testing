package com.practicesoftwaretesting;

import com.practicesoftwaretesting.cart.CartController;
import com.practicesoftwaretesting.cart.model.AddCartItemRequest;
import com.practicesoftwaretesting.cart.model.CartItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CartTest extends BaseTest {

    private static final String PRODUCT_ID = "01HZ0G1KCJ1Z7ZQQMCW29YPZ1G";

    private String authToken;

    @BeforeEach
    void beforeEach() {
        authToken = registerAndLoginNewUser();
    }

    @Test
    void createUpdateAndDeleteCart() {
        var cartController = new CartController().withToken(authToken);

        var createdCart = cartController.createCart()
                .assertStatusCode(201)
                .as();
        Assertions.assertNotNull(createdCart.getId());
        var cartId = createdCart.getId();

        var updateCartResponse = cartController
                .addItemToCart(cartId, new AddCartItemRequest(PRODUCT_ID, 1))
                .assertStatusCode(200)
                .as();
        Assertions.assertNotNull(updateCartResponse.getResult());

        var cartDetails = cartController.getCart(cartId)
                .assertStatusCode(200)
                .as();
        var productIds = cartDetails.getCartItems()
                .stream()
                .map(CartItem::getProductId).toList();
        Assertions.assertTrue(productIds.contains(PRODUCT_ID));

        cartController.deleteCart(cartId)
                .assertStatusCode(204);
    }
}

