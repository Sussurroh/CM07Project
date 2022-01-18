package com.example.cm07project;

public class Products {
    /** Represents com.example.cm07project.Products on Firebase DB*/

    public String userID;
    public String item;

    public String getUserID() {
        return userID;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Products(String userID, String item) {
        this.userID = userID;
        this.item = item;
    }
}
