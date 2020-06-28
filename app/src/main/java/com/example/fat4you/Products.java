package com.example.fat4you;

import com.google.firebase.database.PropertyName;

public class Products {
    @PropertyName("Image")
    private String image;
    @PropertyName("Name")
    private String Name;
    @PropertyName("products")
    private String Products;
    @PropertyName("How")
    private String How;

    public Products() {} //no-argument constructor

    public Products(String image, String Name, String Product, String How) {
        this.image = image;
        this.Name = Name;
        this.Products = Product;
        this.How = How;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setProduct(String product) {
        Products = product;
    }

    public void setHow(String how) {
        How = how;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return Name;
    }

    public String getProduct() {
        return Products;
    }



    public String getHow() {
        return How;
    }
}
