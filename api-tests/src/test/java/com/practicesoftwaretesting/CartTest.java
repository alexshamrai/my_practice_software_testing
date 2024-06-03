package com.practicesoftwaretesting;

import com.practicesoftwaretesting.cart.CartController;
import com.practicesoftwaretesting.cart.model.AddCartItemRequest;
import com.practicesoftwaretesting.cart.model.CartItem;
import com.practicesoftwaretesting.product.ProductController;
import com.practicesoftwaretesting.product.model.ProductsRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CartTest extends BaseTest {

    private String authToken;
    private String productId;

    @BeforeEach
    void beforeEach() {
        authToken = registerAndLoginNewUser();

        var productsRequest = ProductsRequest.builder()
                .page(1)
                .build();
        productId = new ProductController().getProducts(productsRequest)
                .as()
                .getData()
                .getFirst()
                .getId();
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
                .addItemToCart(cartId, new AddCartItemRequest(productId, 1))
                .assertStatusCode(200)
                .as();
        Assertions.assertNotNull(updateCartResponse.getResult());

        var cartDetails = cartController.getCart(cartId)
                .assertStatusCode(200)
                .as();
        var productIds = cartDetails.getCartItems()
                .stream()
                .map(CartItem::getProductId).toList();
        Assertions.assertTrue(productIds.contains(productId));

        cartController.deleteCart(cartId)
                .assertStatusCode(204);
    }
}

