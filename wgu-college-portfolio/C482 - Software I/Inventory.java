package com.example.expediant_proj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * The Inventory class contains Lists and Methods that are utilized by the Controller Classes for Adding and Modifying Parts and Products
 *
 * @author @author Andrew Grim : C482 Software I : 8/24/22
 * */
public class Inventory {
    /**
     * Static Variables of the Inventory Class
     */
    private static int partId = 1;
    private static int prodId = 1;
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    /**
     * @return all parts
     * */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    /**
     * @return all products
     * */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    /**
     * @param newPart adds to allParts
     * */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    /**
     * @param newProduct adds to allProducts
     * */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    public static Part lookupPart(int id) {
        for(Part p : allParts) {
            if(p.getId() == id) {
                return p;
            }
        } return null;
    }
    public static ObservableList<Part> lookupPart(String name) {
        ObservableList searchedPart = FXCollections.observableArrayList();
        for(Part n : allParts) {
            if(n.getName().toLowerCase().contains(name.toLowerCase())) {
                searchedPart.add(n);
            }
        } return searchedPart;
    }
    public static void updatePart(int index, Part selectedPart) {
        for(int i = 0; i < allParts.size(); i++) {
            if(allParts.get(i).getId() == index) {
                allParts.set(i, selectedPart);
            }
        }
    }
    public static int checkPartForDuplicateID() {
        for(int i = 0; i < allParts.size(); i++) {
            if(allParts.get(i).getId() == partId) {
                partId = partId + 1;
            }
        }return partId;
    }
    public static Product lookupProduct(int id) {
        for(Product p : allProducts) {
            if(p.getId() == id) {
                return p;
            }
        } return null;
    }
    public static ObservableList<Product> lookupProduct(String name) {
        ObservableList searchedProduct = FXCollections.observableArrayList();
        for(Product n : allProducts) {
            if(n.getName().toLowerCase().contains(name.toLowerCase())) {
                searchedProduct.add(n);
            }
        } return searchedProduct;
    }
    public static void updateProduct(int index, Product selectedProduct) {
        for(int i = 0; i < allProducts.size(); i++) {
            if(allProducts.get(i).getId() == index) {
                allProducts.set(i, selectedProduct);
            }
        }
    }
   public static int checkProductForDuplicateID() {
        for(int i = 0; i < allProducts.size(); i++) {
            if(allProducts.get(i).getId() == prodId) {
                prodId = prodId + 1;
            }
        }return prodId;
    }
    /**
     * @return an incremented partId
     * */
    public static int generateNewPartId() {
        checkPartForDuplicateID();
        return partId;
    }
    /**
     * @return an incremented prodId
     * */
    public static int generateNewProductId() {
        checkProductForDuplicateID();
        return prodId;
    }
    /**
     * Deletes part from allParts
     * @param selectedPart deletes thisPart
     * @return boolean if removed.
     * */
    public static <Part> boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }
    /**
     * Deletes part from allParts
     * @param selectedProduct deletes selectedProduct
     * @return boolean if removed.
     * */
    public static <Product> boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }
}