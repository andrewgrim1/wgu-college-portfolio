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
 * The Add Product Controller class allows for User Input into the Text Fields
 * This class ensures that user input is valid and handles all possible exceptions
 * This class also allows the user to add, search for, and remove Associated Parts to the Product
 *
 * @author Andrew Grim : C482 Software I : 8/24/22
 * */
public class AddProductCONTROLLER implements Initializable {
    /**
     * Variables of the Add Product Controller Class
     */
    @FXML
    private ObservableList<Part> partsAddedToProd = FXCollections.observableArrayList();
    @FXML
    public TextField parts_Directory_txt_search;
    @FXML
    public Button save_input_Btn;
    @FXML
    public Button cancel_Btn;
    @FXML
    public TextField addProdId;
    @FXML
    public TextField product_txt_Name;
    @FXML
    public TextField product_txt_Inv;
    @FXML
    public TextField product_txt_Max;
    @FXML
    public TextField product_txt_Min;
    @FXML
    public TextField product_txt_Price;
    @FXML
    public TableView<Part> parts_Directory;
    @FXML
    public TableColumn<Part, Integer> parts_Directory_col_partID;
    @FXML
    public TableColumn<Part, String> parts_Directory_col_Name;
    @FXML
    public TableColumn<Part, Integer> parts_Directory_col_Inv;
    @FXML
    public TableColumn<Part, Double> parts_Directory_col_Price;
    @FXML
    public TableView<Part> parts_Associated;
    @FXML
    public TableColumn<Part, Integer> parts_Associated_col_partID;
    @FXML
    public TableColumn<Part, String> parts_Associated_col_Name;
    @FXML
    public TableColumn<Part, Integer> parts_Associated_col_Inv;
    @FXML
    public TableColumn<Part, Double> parts_Associated_col_Price;
    @FXML
    public Button addPart_Btn, removePart_Btn, search_Btn;
    /**
     * @param min - The user inputs the minimum
     * @param max - The user inputs the maximum
     * @return valid - The boolean logical is returned
     * This method checks to make sure the Minimum is less than the Maximum
     */
    boolean minLessThanMax(int min, int max) {
        boolean valid = true;
        if(min < 1 || min >= max) {
            valid = false;
            alertCases(3);
        }
        return valid;
    }
    /**
     * @param min - The user inputs the minimum
     * @param max - The user inputs the maximum
     * @param stock - The user inputs the stock
     * @return logical - The boolean logical is returned
     * This method checks to make sure the Stock value is between the Minimum and Maximum
     */
    boolean stockIsBetweenMinAndMax(int stock, int min, int max) {
        boolean valid = true;
        if(stock > max || stock < min) {
            valid = false;
            alertCases(2);
        }
        return valid;
    }
    /**
     * @param event - When this method is called the Main Screen opens back up
     * @throws IOException - IOException is thrown
     * This method opens the Main Screen
     */
    private void open_MainScreen(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainGUI.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    /**
     * @param event - On a Parts Directory TextField Search Event, the Parts Directory is searched for a match on Name or ID
     * @throws IOException - This method handles exceptions
     */
    @FXML
    public void search_parts_Directory(ActionEvent event) throws IOException {
        String user_query = parts_Directory_txt_search.getText();
        if(user_query.isEmpty()) {
            parts_Directory.setItems(Inventory.getAllParts());
        } else {
            try {
                int id = Integer.parseInt(user_query);
                if(Inventory.lookupPart(id) == null) {
                    alertCases(5);
                } else {
                    parts_Directory.getSelectionModel().select(Inventory.lookupPart(id));
                }
            } catch (NumberFormatException e) {
                parts_Directory.setItems(Inventory.lookupPart(user_query));
                if(parts_Directory.getItems().isEmpty()) {
                    alertCases(5);
                }
            } finally {
                parts_Directory_txt_search.clear();
            }
        }
    }
    /**
     * @param event - On an Add Button Press Event, the selected Part is Added to the List of Associated Parts
     * @throws IOException - This method checks for user error
     */
    @FXML
    public void add_associatedPart(ActionEvent event) throws IOException {
        Part thisPart = (Part) parts_Directory.getSelectionModel().getSelectedItem();
        if (thisPart == null) {
            alertCases(6);
        } else {
            partsAddedToProd.add(thisPart);
            parts_Associated.setItems(partsAddedToProd);
        }
    }
    /**
     * @param event - On a Remove Associated Part Button Event, the selected Part is Removed from the list of Associated Parts
     * @throws IOException - This method checks for user error
     */
    @FXML
    public void delete_associatedPart(ActionEvent event) throws IOException {
        Part thisPart = (Part) parts_Associated.getSelectionModel().getSelectedItem();
        if (thisPart == null) {
            alertCases(6);
        } else {
            Alert confirmRemoval = new Alert(Alert.AlertType.CONFIRMATION);
            confirmRemoval.setTitle("Alert");
            confirmRemoval.setContentText("Remove Selected Part?");
            Optional<ButtonType> answer = confirmRemoval.showAndWait();
            if (answer.isPresent() && answer.get() == ButtonType.OK) {
                partsAddedToProd.remove(thisPart);
                parts_Associated.setItems(partsAddedToProd);
            }
        }
    }
    /**
     * @param event - On a Save Button press event the save_input method checks for errors and then saves the user data
     * @throws IOException - The program then returns to the Main Window
     */
    @FXML
    public void save_input(ActionEvent event) throws IOException {
        try {
            int prodId = 0;
            String prodName = product_txt_Name.getText();
            Double price = Double.parseDouble(product_txt_Price.getText());
            int inv = Integer.parseInt(product_txt_Inv.getText());
            int min = Integer.parseInt(product_txt_Min.getText());
            int max = Integer.parseInt(product_txt_Max.getText());
            if (prodName.isEmpty()) {
                alertCases(1);
            } else {
                if (minLessThanMax(min, max) && stockIsBetweenMinAndMax(inv, min, max)) {
                    Product newProd = new Product (prodId, prodName, price, inv, min, max);
                    for (Part part : partsAddedToProd) {
                        newProd.addAssociatedPart(part);
                    }
                    newProd.setId(Inventory.generateNewProductId());
                    Inventory.addProduct(newProd);
                    open_MainScreen(event);
                }
            }
        } catch (Exception e) {
            alertCases(4);
        }
    }
    /**
     * @param event - When the cancel button is pressed the user returns to the Main Screen
     * @throws IOException - IOException is thrown
     * This method cancels the Add Part Form and returns to the Main Screen
     */
    @FXML
    public void cancel(ActionEvent event) throws IOException {
        Alert alert_Exit = new Alert(Alert.AlertType.CONFIRMATION);
        alert_Exit.setTitle("Cancel?");
        alert_Exit.setContentText("Continue with Cancellation? Data won't be saved...");
        Optional<ButtonType> response = alert_Exit.showAndWait();
        if (response.isPresent() && response.get() == ButtonType.OK) {
            open_MainScreen(event);
        }
    }
    /**
     * @param alertNumber - The alertNumber integer informs the program which Error is being encountered.
     *                    The alertCases switch case handles all possible errors.
     */
    private void alertCases(int alertNumber) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        switch (alertNumber) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Please enter a Product name");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Your Inventory must be a number equal to or between your Minimum and Maximum");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Your minimum must be less than your maximum");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Please fill out all fields");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("No Parts Found");
                alert.showAndWait();
                break;
            case 6:
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Please select a part.");
                alert.showAndWait();
                break;
        }
    }
    /**
     * @param url - The data for the Associated Parts of the Product is Initialized
     * @param resourceBundle - The data for the Directory of Parts is Initialized
     */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            parts_Directory_col_partID.setCellValueFactory(new PropertyValueFactory<>("id"));
            parts_Directory_col_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
            parts_Directory_col_Inv.setCellValueFactory(new PropertyValueFactory<>("stock"));
            parts_Directory_col_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
            parts_Directory.setItems(Inventory.getAllParts());
            parts_Associated_col_partID.setCellValueFactory(new PropertyValueFactory<>("id"));
            parts_Associated_col_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
            parts_Associated_col_Inv.setCellValueFactory(new PropertyValueFactory<>("stock"));
            parts_Associated_col_Price.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}