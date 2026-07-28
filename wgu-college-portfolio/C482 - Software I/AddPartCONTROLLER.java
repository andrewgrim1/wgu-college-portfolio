package com.example.expediant_proj;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * The Add Part Controller class allows for User Input into the Text Fields and Radio Buttons
 * This class ensures that user input is valid and handles all possible exceptions
 *
 * @author Andrew Grim : C482 Software I : 8/24/22
 * */
public class AddPartCONTROLLER implements Initializable {
    /**
     * Variables of the Add Part Controller Class
     */
    @FXML
    public TextField part_ID_txt;
    @FXML
    public ToggleGroup partType;
    @FXML
    public TextField part_Name_txt;
    @FXML
    public TextField part_Inv_txt;
    @FXML
    public TextField part_Price_txt;
    @FXML
    public TextField part_Max_txt;
    @FXML
    public TextField part_Min_txt;
    @FXML
    public TextField part_SwitchType_txt;
    @FXML
    public Label switchType_lbl;
    @FXML
    public RadioButton tgl_InHouse, tgl_Outsourced;
    @FXML
    public Button save_Btn;
    @FXML
    public Button cancel_Btn;
    /**
     * @param min - The user inputs the minimum
     * @param max - The user inputs the maximum
     * @param stock - The user inputs the stock
     * @return logical - The boolean logical is returned
     * This method checks to make sure the Stock value is between the Minimum and Maximum
     */
    private boolean stockIsBetweenMinAndMax(int min, int max, int stock){
        boolean logical = true;
        if (min > stock || max < stock) {
            logical = false;
            alertCases(3);
        }
        return logical;
    }
    /**
     * @param min - The user inputs the minimum
     * @param max - The user inputs the maximum
     * @return logical - The boolean logical is returned
     * This method checks to make sure the Minimum is less than the Maximum
     */
    private boolean minIsLessThanMax(int min, int max){
        boolean logical = true;
        if (0 > min || max <= min) {
            logical = false;
            alertCases(4);
        }
        return logical;
    }

    /**
     * @param event - On the Button Save Press Event
     * @throws IOException - IOException is thrown
     * The save_input method saves the Add Part Form Data and handles all Exceptions
     */
    @FXML
    void save_input(ActionEvent event) throws IOException {
        try {
            int partId = 0;
            String partName = part_Name_txt.getText();
            Double partPrice = Double.parseDouble(part_Price_txt.getText());
            int partInv = Integer.parseInt(part_Inv_txt.getText());
            int partMin = Integer.parseInt(part_Min_txt.getText());
            int partMax = Integer.parseInt(part_Max_txt.getText());
            int machineID;
            String companyName;
            boolean checkPart = false;
            if (!tgl_InHouse.isSelected() && !tgl_Outsourced.isSelected()) {
                alertCases(6);
            }
            if (partName.isEmpty()) {
                alertCases(1);
            } else {
                if (minIsLessThanMax(partMin, partMax) && stockIsBetweenMinAndMax(partMin, partMax, partInv)) {
                    if (tgl_InHouse.isSelected()) {
                        try {
                            machineID = Integer.parseInt(part_SwitchType_txt.getText());
                            InHouse newInHouse = new InHouse(partId, partName, partPrice, partInv, partMin, partMax, machineID);
                            newInHouse.setId(Inventory.generateNewPartId());
                            Inventory.addPart(newInHouse);
                            checkPart = true;
                        } catch (Exception e) {
                            alertCases(2);
                        }
                    }
                    if (tgl_Outsourced.isSelected()) {
                        companyName = part_SwitchType_txt.getText();
                        Outsourced newOutsourced = new Outsourced(partId, partName, partPrice, partInv, partMin, partMax, companyName);
                        Inventory.addPart(newOutsourced);
                        newOutsourced.setId(Inventory.generateNewPartId());
                        checkPart = true;
                    }
                    if (checkPart) {open_MainScreen(event);}
                }
            }
        } catch (Exception e){
            alertCases(5);
        }
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
     * @param event - When the cancel button is pressed the user returns to the Main Screen
     * @throws IOException - IOException is thrown
     * This method cancels the Add Part Form and returns to the Main Screen
     */
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
     * @param event - On a Toggle Event, the switchType Label changes to Machine ID or Company Name
     */
    public void onInHouseTgl(ActionEvent event) {
        switchType_lbl.setText("Machine ID");
    }
    public void onOutsourcedTgl(ActionEvent event) {
        switchType_lbl.setText("Company Name");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * @param alertNumber - The alertNumber integer informs the program which Error is being encountered.
     *                    The alertCases switch case handles all possible errors.
     */
    private void alertCases(int alertNumber) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        switch (alertNumber) {
            case 1:
                alert.setTitle("Error!");
                alert.setHeaderText("Type Error:");
                alert.setContentText("Please enter a correct name type.");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error!");
                alert.setHeaderText("Type Error:");
                alert.setContentText("Please enter a correct machine ID type.");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error!");
                alert.setHeaderText("Logic Error:");
                alert.setContentText("Inventory must be between minimum and maximum values.");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Error!");
                alert.setHeaderText("Logic Error:");
                alert.setContentText("Minimum value must be less than maximum value.");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("Error!");
                alert.setHeaderText("Form Error:");
                alert.setContentText("Please ensure all fields contain values.");
                alert.showAndWait();
                break;
            case 6:
                alert.setTitle("Error!");
                alert.setHeaderText("Input Error:");
                alert.setContentText("In-House or Outsourced must be selected.");
                alert.showAndWait();
                break;
        }
    }
}