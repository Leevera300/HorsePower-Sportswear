package com.horsepower.product.domain;

import java.util.List;

public class ProductDetailListWrapper {
    private List<ProductDetail> productDetails;

    // Getters and setters
    public List<ProductDetail> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<ProductDetail> productDetails) {
        this.productDetails = productDetails;
    }
}
