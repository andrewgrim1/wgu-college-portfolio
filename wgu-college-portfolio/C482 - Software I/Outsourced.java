package com.example.expediant_proj;
/**
 * This file contains the Constructor for an Outsourced Part as well as the necessary Getters and Setters
 *
 * @author Andrew Grim : C482 Software I : 8/24/22
 *
 * */
public class Outsourced extends Part {
    private String companyName;
    /**
     * Constructor for an Outsourced Part
     * @param id part id
     * @param name part name
     * @param price part price
     * @param stock part stock
     * @param min part min
     * @param max part max
     * @param companyName part Company name
     * */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    /**
     * @return company name
     * */
    public String getCompanyName() {
        return this.companyName;
    }
    /**
     * @param companyName sets company name
     * */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}