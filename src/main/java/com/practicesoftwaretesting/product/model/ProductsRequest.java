package com.practicesoftwaretesting.product.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductsRequest {

    private String brand;
    private String category;
    private boolean rental;
    private String between;
    private String sort;
    private int page;
}
