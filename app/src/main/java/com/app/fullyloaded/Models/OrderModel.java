package com.app.fullyloaded.Models;

public class OrderModel {

    private String ProductName, ProductImage, ProductPrice, ProductQuantity;

    /*public OrderModel(String ProductName, String ProductImage, String ProductPrice, String ProductQuantity) {
        this.ProductName = ProductName;
        this.ProductImage = ProductImage;
        this.ProductPrice = ProductPrice;
        this.ProductQuantity = ProductQuantity;
    }*/

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        ProductQuantity = productQuantity;
    }
}
