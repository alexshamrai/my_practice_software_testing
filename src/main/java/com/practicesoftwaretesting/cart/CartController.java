package com.practicesoftwaretesting.cart;

import com.practicesoftwaretesting.cart.model.AddCartItemRequest;
import com.practicesoftwaretesting.common.BaseClient;
import io.restassured.response.Response;

public class CartController extends BaseClient {

    public Response createCart() {
        return baseClient()
                .post("/carts");
    }

    public Response addItemToCart(String cartId, AddCartItemRequest cartItem) {
        return baseClient()
                .body(cartItem)
                .post("/carts/" + cartId);
    }

    public Response getCart(String cartId) {
        return baseClient()
                .get("/carts/" + cartId);
    }

    public Response deleteCart(String cartId) {
        return baseClient()
                .delete("/carts/" + cartId);
    }
}
