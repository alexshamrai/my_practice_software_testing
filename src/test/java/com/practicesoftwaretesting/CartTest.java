package com.practicesoftwaretesting;

import com.practicesoftwaretesting.cart.CartController;
import com.practicesoftwaretesting.cart.model.AddCartItemRequest;
import com.practicesoftwaretesting.cart.model.CartDetails;
import com.practicesoftwaretesting.cart.model.CartItem;
import com.practicesoftwaretesting.cart.model.CreateCartResponse;
import com.practicesoftwaretesting.cart.model.UpdateCartResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CartTest extends BaseTest {

    private static final String PRODUCT_ID = "01HYGY7EEFTG1EP7Y2Q9N3MXQQ";

    @Test
    void createUpdateAndDeleteCart() {
        var cartController = new CartController();

        var createdCart = cartController.createCart()
                .as(CreateCartResponse.class);
        Assertions.assertNotNull(createdCart.getId());
        var cartId = createdCart.getId();

        var updateCartResponse = cartController
                .addItemToCart(cartId, new AddCartItemRequest(PRODUCT_ID, 1))
                .as(UpdateCartResponse.class);
        Assertions.assertNotNull(updateCartResponse.getResult());

        var cartDetails = cartController.getCart(cartId)
                .as(CartDetails.class);
        var productIds = cartDetails.getCartItems().stream().map(CartItem::getProductId).toList();
        Assertions.assertTrue(productIds.contains(PRODUCT_ID));

        cartController.deleteCart(cartId)
                .then()
                .statusCode(204);
    }
}

