package com.example.expediant_proj;

/**
 *
 * This file contains the Constructor for an InHouse Part as well as the necessary Getters and Setters
 *
 * @author @author Andrew Grim : C482 Software I : 8/24/22
 *
 * */

public class InHouse extends Part {

    private int machineId;
    /**
     * Constructor for an InHouse Part
     * @param id part id
     * @param name part name
     * @param price part price
     * @param stock part stock
     * @param min part min
     * @param max part max
     * @param machineId part machine id
     * */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    /**
     * @return machineId
     * */
    public int getMachineId() {
        return machineId;
    }
    /**
     * @param machineId  sets new machineId
     * */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}