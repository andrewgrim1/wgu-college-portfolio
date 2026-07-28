package com.example.expediant_proj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 *
 * The Main Controller Class controls the methods that allow the Main Window to function
 * Main Controller Class that implements Initializable
 * This Class controls the Main Application.
 * The Main Application allows the User to Utilize the Buttons on the Parts and Products Panes
 * The Main Application allows the User to Search the Parts and Products TextFields
 * The Main Application allows the User to Select and View Parts and Products
 * The Main Application initializes input data
 * @author Andrew Grim : C482 Software I : 8/24/22
 * */
public class MainCONTROLLER implements Initializable {
    /**
     * Variables of the Main Controller Class
     */
    @FXML
    private TableView<Part> mainAPP_tbl_parts;
    @FXML
    private TableColumn<Part, Integer> mainAPP_col_partID;
    @FXML
    private TableColumn<Part, String> mainAPP_col_partName;
    @FXML
    private TableColumn<Part, Integer> mainAPP_col_partStock;
    @FXML
    private TableColumn<Part, Double> mainAPP_col_partPrice;
    @FXML
    private TableView<Product> mainAPP_tbl_products;
    @FXML
    private TableColumn<Product, Integer> mainAPP_col_productID;
    @FXML
    private TableColumn<Product, String> mainAPP_col_productName;
    @FXML
    private TableColumn<Product, Integer> mainAPP_col_productStock;
    @FXML
    private TableColumn<Product, Double> mainAPP_col_productPrice;
    @FXML
    private TextField mainAPP_txt_part_search;
    @FXML
    private TextField mainAPP_txt_product_search;
    @FXML
    private Button open_addPartForm, searchPartsBtn, searchProductsBtn;
    @FXML
    private Label partsLabel, productsLabel;
    @FXML
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();
    /**
     * Static Variables
     */
    private static Part targetPart;
    public static Product targetProduct;
    public static Part getTargetPart() {
        return targetPart;
    }
    public static Product getTargetProduct() {
        return targetProduct;
    }

    /**
     * @param event - Add Part Button Press event
     * @throws IOException - throws IOException
     * This method opens the Add Part Form
     */
    @FXML
    public void open_addPartForm(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddPartGUI.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param event - Modify Part Button Press event
     * @throws IOException - throws IOException
     * This method opens the Modify Part Form
     */
    @FXML
    public void open_modifyPartForm(ActionEvent event) throws IOException {
        targetPart = mainAPP_tbl_parts.getSelectionModel().getSelectedItem();
        if (targetPart == null) {
            alertCases(1);
        } else {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ModifyPartGUI.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }

    /**
     * @param event - Delete Part Button Press event
     * @throws IOException - throws IOException
     * This method deletes a Part from the Parts Table and handles user error.
     */
    @FXML
    public void delete_Part(ActionEvent event) throws IOException {
        Part thisPart = mainAPP_tbl_parts.getSelectionModel().getSelectedItem();
        if (thisPart == null) {
            alertCases(1);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Caution!");
            alert.setContentText("Proceed with deletion?");
            Optional<ButtonType> response = alert.showAndWait();
            if (response.isPresent() && response.get() == ButtonType.OK) {
                Inventory.deletePart(thisPart);
            }
        }
    }

    /**
     * @param event - Add Product Form Button Press Event
     * @throws IOException - throws IOException
     * This method opens the Add Product Form
     */
    @FXML
    public void open_addProductForm(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddProductGUI.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param event - Modify Product Form Button Press Event
     * @throws IOException - throws IOException
     * This method opens the Modify Product Form
     */
    @FXML
    public void open_modifyProductForm(ActionEvent event) throws IOException {
        targetProduct = mainAPP_tbl_products.getSelectionModel().getSelectedItem();
        if (targetProduct == null) {
            alertCases(2);
        } else {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ModifyProductGUI.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }

    /**
     *This method deletes a product from the table. User error is handled.
     */
    @FXML
    public void delete_Product() {
        Product product = mainAPP_tbl_products.getSelectionModel().getSelectedItem();
        try {
            if(product.getAllAssociatedParts().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setContentText("Are you sure you want to delete the product?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK) {
                    Inventory.deleteProduct(product);
                }
            } else {
                alertCases(5);
            }
        } catch (NullPointerException e) {
            alertCases(2);
        }
    }

    /**
     * @param event - When a user searches the Parts TextField
     *              A user search query on the Parts TextField will result in a match or an error
     */
    @FXML
    public void search_txt_parts(ActionEvent event) {
       String user_query = mainAPP_txt_part_search.getText();
        if(user_query.isEmpty()) {
            mainAPP_tbl_parts.setItems(Inventory.getAllParts());
            partsLabel.setText("");
        } else {
            try {
                int id = Integer.parseInt(user_query);
                if(Inventory.lookupPart(id) == null) {
                    partsLabel.setText("Error: Match not Found");
                } else {
                    mainAPP_tbl_parts.getSelectionModel().select(Inventory.lookupPart(id));
                    partsLabel.setText("");
                }
            } catch (NumberFormatException e) {
                mainAPP_tbl_parts.setItems(Inventory.lookupPart(user_query));
                if(mainAPP_tbl_parts.getItems().isEmpty()) {
                    partsLabel.setText("Error: Match not Found");
                } else {
                    partsLabel.setText("");
                }
            } finally {
                mainAPP_txt_part_search.clear();
            }
        }
    }

    /**
     * @param event - When a user searches the Products TextField
     *              A user search query on the Products TextField will result in a match or an error
     */
    @FXML
    public void search_txt_products(ActionEvent event) {
        String user_query = mainAPP_txt_product_search.getText();
        if(user_query.isEmpty()) {
            mainAPP_tbl_products.setItems(Inventory.getAllProducts());
            productsLabel.setText("");
        } else {
            try {
                int id = Integer.parseInt(user_query);
                if(Inventory.lookupProduct(id) == null) {
                    productsLabel.setText("Error: Match not Found");
                } else {
                    mainAPP_tbl_products.getSelectionModel().select(Inventory.lookupProduct(id));
                    productsLabel.setText("");
                }
            } catch (NumberFormatException e) {
                mainAPP_tbl_products.setItems(Inventory.lookupProduct(user_query));
                if(mainAPP_tbl_products.getItems().isEmpty()) {
                    productsLabel.setText("Error: Match not Found");
                } else {
                    productsLabel.setText("");
                }
            } finally {
                mainAPP_txt_product_search.clear();
            }
        }
    }

    /**
     * @param event - Exit Button event
     * @throws IOException - throws IOException
     * The Exit method exits the program
     */
    @FXML
    public void exit_Program(ActionEvent event) throws IOException {
        System.exit(0);
    }

    /**
     * @param url - Initializes the Parts and Products Data in the Main Tables
     * @param resourceBundle - Initializes the Parts and Products Data in the Main Tables
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainAPP_tbl_parts.setItems((ObservableList<Part>) Inventory.getAllParts());
        mainAPP_col_partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainAPP_col_partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainAPP_col_partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        mainAPP_col_partStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        mainAPP_tbl_products.setItems((ObservableList<Product>) Inventory.getAllProducts());
        mainAPP_col_productID.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainAPP_col_productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainAPP_col_productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        mainAPP_col_productStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    /**
     * @param alertNumber - The alertNumber integer informs the program which Error is being encountered.
     *                    The alertCases switch case handles all possible errors.
     */
    private void alertCases(int alertNumber) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        switch(alertNumber) {
            case 1:
                alert.setTitle("Error!");;
                alert.setHeaderText("Error:");
                alert.setContentText("You must select a Part.");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error!");;
                alert.setHeaderText("Error:");
                alert.setContentText("You must select a Product.");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error!");
                alert.setHeaderText("Error:");
                alert.setContentText("Your search did not match any Parts.");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Error!");;
                alert.setHeaderText("Error:");
                alert.setContentText("Your search did not match any Products.");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("Error!");;
                alert.setHeaderText("Error:");
                alert.setContentText("Remove Associated Parts from Product before Deletion.");
                alert.showAndWait();
                break;
        }
    }
}