package com.example.expediant_proj;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
/**
 * @author Andrew Grim : 08/24/2022 : C482 SOFTWARE I : Inventory Management System
 * Main start method
 * FUTURE ENHANCEMENT: A database could be integrated with this application which would allow for input data to be retained outside of the application.
 * LOGICAL ERROR: I was originally unable to generate a search error for a Product in the MainGUI if the Product ID was a certain size.
 * If the Product ID was just one integer value greater than the largest existing Product ID (LargestProductID+1) then a search error would not generate.
 * This was resolved by making changes to the lookupProduct method in the Inventory.java file
 *
 *
 * The Javadoc files can be found in the expediant_proj folder
 * The expediant_proj folder contains the .idea, .mvn, and.src folders as well
 *
 * Main Class that extends Application
 * **/
public class MainAPP extends Application {
    /**
     * Opens the MainGUI.fxml window which is the base window of the Application.
     * @param stage - Stage is the argument taken by the start method
     * @throws IOException - Start method throws an IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainGUI.fxml")));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
    }
    /**
     * @param args - Main Method that launches the Program
     *             Sample Data has been added to populate both the Part and Product Tables
     */
    public static void main(String[] args) {
        Part part1 = new InHouse(1, "GPU", 400.00, 20, 1, 50, 111111);
        Part part2 = new InHouse(2, "CPU", 200.00, 40, 1, 50, 222222);
        Part part3 = new Outsourced(3, "Motherboard", 99.99, 30, 1, 50, "Microsoft");
        Part part4 = new Outsourced(4, "CMOS", 12.00, 45, 1, 50, "Apple");
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Product product1 = new Product(1, "Laptop", 200.00, 6, 1, 10);
        Product product2 = new Product(2, "GamingPC", 400.00, 2, 1, 10);
        Product product3 = new Product(3, "WorkStation", 300.00, 3, 1, 10);
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        launch();
    }
}