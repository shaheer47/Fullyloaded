package com.app.fullyloaded.Models;

public class CartModel {

    private String ID, ProductID, ProductName, ProductImage, ProductPrice, ProductQuantity;

    /*public CartModel(String ID, String ProductID, String ProductName, String ProductImage, String ProductPrice, String ProductQuantity) {
        this.ID = ID;
        this.ProductID = ProductID;
        this.ProductName = ProductName;
        this.ProductImage = ProductImage;
        this.ProductPrice = ProductPrice;
        this.ProductQuantity = ProductQuantity;
    }*/

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

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
