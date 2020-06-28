package com.example.fat4you;

public class Recipit {
    String imagePath;
    String title;
    String recipit;
    String products;
    String type;


    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String email;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public Recipit() {
    }

    public Recipit(String imagePath, String title, String recipit, String type,String email,String products) {
        this.imagePath = imagePath;
        this.title = title;
        this.products = products;
        this.recipit = recipit;
        this.type = type;
        this.email = email;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRecipit() {
        return recipit;
    }

    public void setRecipit(String recipit) {
        this.recipit = recipit;
    }
}
