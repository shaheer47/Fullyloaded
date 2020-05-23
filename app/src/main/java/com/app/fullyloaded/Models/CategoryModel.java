package com.app.fullyloaded.Models;

public class CategoryModel {

    private String CategoryID, CategoryName, CategoryCreatedAt, CategoryUpdatedAt;

    /*public CategoryModel(String CategoryID, String CategoryName, String CategoryCreatedAt, String CategoryUpdatedAt) {
        this.CategoryID = CategoryID;
        this.CategoryName = CategoryName;
        this.CategoryCreatedAt = CategoryCreatedAt;
        this.CategoryUpdatedAt = CategoryUpdatedAt;
    }*/

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCategoryCreatedAt() {
        return CategoryCreatedAt;
    }

    public void setCategoryCreatedAt(String categoryCreatedAt) {
        CategoryCreatedAt = categoryCreatedAt;
    }

    public String getCategoryUpdatedAt() {
        return CategoryUpdatedAt;
    }

    public void setCategoryUpdatedAt(String categoryUpdatedAt) {
        CategoryUpdatedAt = categoryUpdatedAt;
    }
}
