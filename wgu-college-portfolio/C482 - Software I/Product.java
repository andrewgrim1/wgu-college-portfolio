package com.example.expediant_proj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * This file contains the Constructor for a Product as well as the necessary Getters and Setters associated with the Product
 *
 * @author Andrew Grim : C482 Software I : 8/24/22
 * */
public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**
     * Constructor creates a new Product Instance.
     * @param id product id
     * @param name product name
     * @param price product price
     * @param stock product inventory
     * @param min product minimum inventory
     * @param max product maximum inventory
     * */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    //setters and getters
    /**@return the ID
     * */
    public int getId() {
        return id;
    }
    /**
     * @param id sets as id
     * */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return the name
     * */
    public String getName() {
        return name;
    }
    /**
     * @param name sets as name
     * */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the price
     * */
    public double getPrice() {
        return price;
    }
    /**
     * @param price sets as price
     * */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * @return the inventory
     * */
    public int getStock() {
        return stock;
    }
    /**
     * @param stock sets as inventory
     * */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**
     * @return the minimum
     * */
    public int getMin() {
        return min;
    }
    /**
     * @param min sets as minimum
     * */
    public void setMin(int min) {
        this.min = min;
    }
    /**
     * @return the maximum
     * */
    public int getMax() {
        return max;
    }
    /**
     * @param max sets as maximum
     * */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @param part adds to list of parts associated with product
     * */
    public void addAssociatedPart(Part part) {

        associatedParts.add(part);
    }
    /**
     * @param selectedAssociatedPart removes from the list of parts associated with the product
     * */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {

        associatedParts.remove(selectedAssociatedPart);
        return true;
    }
    /**
     * @return all parts associated with the product.
     * */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}